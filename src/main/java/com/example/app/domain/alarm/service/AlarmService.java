package com.example.app.domain.alarm.service;

import com.example.app.data.entity.UsersEntity;
import com.example.app.domain.alarm.dto.AlarmDTO;
import com.example.app.domain.group.service.GroupService;
import com.example.app.domain.message.dto.MessageDTO;
import com.example.app.domain.user.service.UserService;
import com.example.app.global.error.exception.BusinessException;
import com.example.app.global.error.exception.ErrorCode;
import com.example.app.kafka.Producer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AlarmService {
    private static final long maxRequestCount = 5;
    private final GroupService groupService;
    private final UserService userService;
    private final Producer producer;

    public AlarmService(GroupService groupService, UserService userService, Producer producer) {
        this.groupService = groupService;
        this.userService = userService;
        this.producer = producer;
    }

    /**
     * "@user1, @group1, @"
     * @param alarmDTO 알람 대상, 내용이 담긴 DTO
     * @return 알람 대상의 수
     */
    public Long pushAlarm(AlarmDTO alarmDTO) {
        Set<UsersEntity> users = new HashSet<>();
        for (String target : alarmDTO.getTarget()){
            if (target.equals("@all")) {
                users.addAll(userService.getAllUsers());
                break;
            } if (target.startsWith("@@")){
                users.addAll(groupService.getUsersByGroupName(target.substring(2)));
            } else if (target.startsWith("@")) {
                users.add(userService.findByUserNameOrNull(target.substring(1)));
            }
        }

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage(produceMessage(alarmDTO));

        Iterator<UsersEntity> iterator = users.iterator();
        while (iterator.hasNext()) {
            int count = 0;
            List<String> targetTokens = new ArrayList<>();
            while (iterator.hasNext() && count<maxRequestCount) {
                UsersEntity user = iterator.next();
                if (user != null) {
                    targetTokens.add(user.getToken());
                    count++;
                }
            }
            messageDTO.setToken(targetTokens);
            producer.create(serializeInJson(messageDTO));
        }
        return (long) users.size();
    }

    private String serializeInJson(MessageDTO messageDTO) {
        ObjectMapper objectMapper = new ObjectMapper();

        String serialized;
        try {
            serialized = objectMapper.writeValueAsString(messageDTO);
        } catch (IOException e) {
            log.error("Error serializing message to JSON: {}", e.getMessage());
            throw new BusinessException(ErrorCode.MESSAGE_SERIALIZE_EXCEPTION);
        }
        return serialized;
    }

    private String produceMessage(AlarmDTO alarmDTO) {
        return "["
            + alarmDTO.getSeverity().name()
            + "] "
            + alarmDTO.getMessage();
    }
}

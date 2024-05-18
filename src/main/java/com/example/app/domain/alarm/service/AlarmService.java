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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
     * 함수의 목적 : "@user1, @group1, @all"과 같은 사용자 입력에 따라
     * 해당되는 사람에게 메시지를 보내는 함수.
     *
     * @param alarmDTO 알람 대상과 알람 내용이 담긴 DTO
     * @return 알람 대상 유저의 수
     */
    public Long pushAlarm(AlarmDTO alarmDTO) {
        Set<String> targets = Set.copyOf(alarmDTO.getTarget());

        Set<UsersEntity> users = targets.stream()
                .flatMap(target -> getUsersByTarget(target).stream())
                .collect(Collectors.toSet());

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage(produceMessage(alarmDTO));

        Iterator<UsersEntity> iterator = users.iterator();

        /*
          코드의 의도 : maxRequestCount만큼의 메시지를 한 건의 이벤트로 발행하여
          외부 서버가 한 번에 maxRequestCount만큼만 처리할 수 있도록 함.

          @maxRequestCount: 외부 서버가 한 번에 처리할 수 있는 메시지 건 수
         */
        while (iterator.hasNext()) {
            List<String> targetTokens = new ArrayList<>();
            for (int count = 0; iterator.hasNext() && count<maxRequestCount; count++) {
                UsersEntity user = iterator.next();
                if (user != null) {
                    targetTokens.add(user.getToken());
                }
            }
            messageDTO.setToken(targetTokens);
            producer.create(serializeInJson(messageDTO));
        }
        return (long) users.size();
    }

    private List<UsersEntity> getUsersByTarget(String target) {
        if (target.equals("@all")) {
            return new ArrayList<>(userService.getAllUsers());
        } else if (target.startsWith("@@")){
            return groupService.getUsersByGroupName(target.substring(2));
        } else if (target.startsWith("@")) {
            UsersEntity user = userService.findByUserNameOrNull(target.substring(1));
            return user != null ? Collections.singletonList(user) : Collections.emptyList();
        }
        return Collections.emptyList();
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

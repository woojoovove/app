package com.example.app.domain.alarm.service;

import com.example.app.data.entity.UsersEntity;
import com.example.app.domain.alarm.dto.AlarmDTO;
import com.example.app.domain.group.service.GroupService;
import com.example.app.domain.message.dto.MessageDTO;
import com.example.app.domain.user.service.UserService;
import com.example.app.global.error.exception.BusinessException;
import com.example.app.global.error.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AlarmHelperService {
    private final UserService userService;
    private final GroupService groupService;

    public AlarmHelperService(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    public List<UsersEntity> getUsersByTarget(String target) {
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

    public String serializeInJson(MessageDTO messageDTO) {
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

    public String produceMessage(AlarmDTO alarmDTO) {
        return "["
            + alarmDTO.getSeverity().name()
            + "] "
            + alarmDTO.getMessage();
    }
}

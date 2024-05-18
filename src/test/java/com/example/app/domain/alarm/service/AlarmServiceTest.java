package com.example.app.domain.alarm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.example.app.data.entity.UsersEntity;
import com.example.app.data.enums.Severity;
import com.example.app.domain.alarm.dto.AlarmDTO;
import com.example.app.domain.message.dto.MessageDTO;
import com.example.app.kafka.Producer;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AlarmServiceTest {

    @InjectMocks
    private AlarmService alarmService;
    @Mock
    private AlarmHelperService alarmHelperService;
    @Mock
    private Producer producer;

    private static final long maxRequestCount = 5;

    /*
    1. set에 유저를 넣고 서비스의 결과 set.size()와 일치하는지.
    2. set에 넣은 유저의 토큰이 MessageDTO의 tokens와 일치하는지.
    2. set에 1개를 넣고 producer를 1번 호출하는지
    3. set에 6개를 넣고 producer를 2번 호출하는지
    4. producer.create()에 넘어간 인수를 역직렬화 해서
       set에 넣은 정보랑 일치하는지
     */

    /*
    코드의 의도 : pushAlarmService 내에서 HelperService의 3가지 메소드가
    서로 의존하고 있으므로 3가지 메소드 간에 문제 없이 잘 동작하는지 확인하고
    그럼으로써 서비스 로직이 잘 동작하는지 확인
     */
    @Test
    @DisplayName("알람 전송 서비스 로직 확인 - user 1명")
    void pushAlarmTest() {

        AlarmDTO alarmDTO = AlarmDTO.builder()
            .target(List.of("@user1"))
            .severity(Severity.HIGH)
            .message("node 1 down")
            .build();

        List<String> token = List.of("token1");

        UsersEntity user = UsersEntity.builder()
            .id(1L)
            .nickname("user1")
            .token("token1")
            .build();

        MessageDTO messageDTO = MessageDTO.builder()
            .token(token)
            .message("[" + alarmDTO.getSeverity().name()
            + "] " + alarmDTO.getMessage())
            .build();

        String serializedMessage = "{\"token\":[\"token1\"],\"message\":\"[HIGH] node 1 down\"}";
        when(alarmHelperService.getUsersByTarget(anyString()))
            .thenReturn(List.of(user));
        when(alarmHelperService.produceMessage(alarmDTO))
            .thenReturn(messageDTO.getMessage());
        when(alarmHelperService.serializeInJson(messageDTO))
            .thenReturn(serializedMessage);

        // 1. set에 유저를 넣고 서비스의 결과가 set.size()와 일치하는지 확인
        assertEquals(alarmService.pushAlarm(alarmDTO), 1L);

        // 2. set에 넣은 유저의 토큰이 MessageDTO의 token과 일치하는지.
        assertEquals(messageDTO.getToken(), token);

        // 3. set에 유저 1명을 넣고 producer가 1회 호출 되는지
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(producer, times(1)).create(argumentCaptor.capture());

        // 4. serializeInJson()에 값을 넣고 producer가 해당 값을 받는지
        String capturedArgument = argumentCaptor.getValue();
        assertNotNull(capturedArgument);
        assertEquals(capturedArgument, serializedMessage);

        // 5. produceMessage()에 메시지를 넣고 serializeInJson()가 해당 값을 받는지
        ArgumentCaptor<MessageDTO> dtoArgumentCaptor = ArgumentCaptor.forClass(MessageDTO.class);
        verify(alarmHelperService, times(1)).serializeInJson(dtoArgumentCaptor.capture());

        MessageDTO capturedMessge = dtoArgumentCaptor.getValue();
        assertNotNull(capturedMessge);
        assertEquals(messageDTO.getMessage(), capturedMessge.getMessage());
        assertEquals(messageDTO.getToken(), capturedMessge.getToken());

    }

}

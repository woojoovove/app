package com.example.app.domain.alarm.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.app.data.enums.Severity;
import com.example.app.domain.alarm.dto.AlarmDTO;
import com.example.app.domain.alarm.service.AlarmService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Disabled("TODO : ResourceHttpRequestHandler 이슈 해결")
@WebMvcTest(AlarmControllerTest.class)
public class AlarmControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AlarmService alarmService;

    @Test
    @DisplayName("알람 전송 컨트롤러 로직 확인")
    void pushAlarmTest() throws Exception {
        AlarmDTO alarmDTO = AlarmDTO.builder()
            .severity(Severity.HIGH)
            .target(List.of("@user1", "@@group1"))
            .build();
        String content = new ObjectMapper().writeValueAsString(alarmDTO);

        when(alarmService.pushAlarm(Mockito.any(AlarmDTO.class))).thenReturn(10L);
        ResultActions actions =
            mockMvc.perform(post("/v1/alarm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andDo(print());

        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.data").value(10L));
    }

}

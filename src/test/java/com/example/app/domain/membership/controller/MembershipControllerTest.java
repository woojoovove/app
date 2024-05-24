package com.example.app.domain.membership.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.app.domain.membership.dto.MembershipDTO.JoinDTO;
import com.example.app.domain.membership.dto.MembershipDTO.JoinResultDTO;
import com.example.app.domain.membership.service.MembershipService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(MembershipController.class)
public class MembershipControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MembershipService membershipService;

    @Test
    @DisplayName("알림 그룹 참가 컨트롤러 로직 확인")
    void joinGroupTest() throws Exception {
        JoinDTO joinDTO =  JoinDTO.builder()
            .userNickname("user1")
            .groupName("group1")
            .build();

        String content = new ObjectMapper().writeValueAsString(joinDTO);
        JoinResultDTO joinResultDTO = JoinResultDTO.builder()
            .userId(1L)
            .userNickname("user1")
            .groupId(2L)
            .groupName("group1")
            .membershipId(3L)
            .build();

        when(membershipService.joinGroup(Mockito.any(JoinDTO.class))).thenReturn(joinResultDTO);

        ResultActions actions =
            mockMvc.perform(post("/v1/membership/join")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andDo(print());

        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.data.userNickname").value(joinDTO.getUserNickname()))
            .andExpect(jsonPath("$.data.groupName").value(joinDTO.getGroupName()));
    }
}

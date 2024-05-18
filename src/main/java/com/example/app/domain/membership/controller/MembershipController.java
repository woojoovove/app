package com.example.app.domain.membership.controller;

import com.example.app.domain.membership.dto.MembershipDTO.JoinDTO;
import com.example.app.domain.membership.dto.MembershipDTO.JoinResultDTO;
import com.example.app.domain.membership.dto.MembershipDTO.LeaveDTO;
import com.example.app.domain.membership.dto.MembershipDTO.LeaveResultDTO;
import com.example.app.domain.membership.service.MembershipService;
import com.example.app.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/membership")
@Tag(name = "사용자 알림 그룹 관리")
public class MembershipController {
    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }


    @PostMapping("/join")
    @Operation(summary = "사용자 이름과 그룹 이름으로 그룹에 참여하는 API")
    public ResponseEntity<DataResponse<JoinResultDTO>> joinGroup(@RequestBody JoinDTO joinGroupDTO) {
        return DataResponse.success(membershipService.joinGroup(joinGroupDTO));
    }
    @PutMapping("/leave")
    @Operation(summary = "사용자 이름과 그룹 이름으로 그룹을 떠나는 API")
    public ResponseEntity<DataResponse<LeaveResultDTO>> leaveGroup(@RequestBody LeaveDTO leaveGroupDTO) {
        return DataResponse.success(membershipService.leaveGroup(leaveGroupDTO));
    }
}

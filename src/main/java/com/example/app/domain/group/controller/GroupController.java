package com.example.app.domain.group.controller;

import com.example.app.domain.group.dto.GroupDTO;
import com.example.app.domain.group.service.GroupService;
import com.example.app.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/group")
@Tag(name = "그룹 관리")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }
    @PostMapping("/create")
    @Operation(summary = "그룹 생성 API")
    public ResponseEntity<DataResponse<GroupDTO.Get>> createGroup(@RequestBody GroupDTO.Create groupDTO) {
        return DataResponse.success(groupService.add(groupDTO));
    }
}

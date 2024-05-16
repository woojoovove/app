package com.example.app.domain.membership.service;

import com.example.app.data.entity.GroupsEntity;
import com.example.app.data.entity.MembershipEntity;
import com.example.app.data.entity.UsersEntity;
import com.example.app.data.repository.MembershipRepository;
import com.example.app.data.repository.MembershipRepositorySupport;
import com.example.app.domain.group.service.GroupService;
import com.example.app.domain.membership.dto.MembershipDTO.JoinDTO;
import com.example.app.domain.membership.dto.MembershipDTO.JoinResultDTO;
import com.example.app.domain.membership.dto.MembershipDTO.LeaveDTO;
import com.example.app.domain.membership.dto.MembershipDTO.LeaveResultDTO;
import com.example.app.domain.user.service.UserService;
import com.example.app.global.error.exception.BusinessException;
import com.example.app.global.error.exception.ErrorCode;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MembershipService {

    private final UserService userService;
    private final GroupService groupService;
    private final MembershipRepository membershipRepository;
    private final MembershipRepositorySupport membershipRepositorySupport;

    public MembershipService(UserService userService, GroupService groupService,
        MembershipRepository membershipRepository,
        MembershipRepositorySupport membershipRepositorySupport) {
        this.userService = userService;
        this.groupService = groupService;
        this.membershipRepository = membershipRepository;
        this.membershipRepositorySupport = membershipRepositorySupport;
    }


    public JoinResultDTO joinGroup(JoinDTO joinDTOGroupDTO) {

        UsersEntity user = userService.findByUserNameOrThrow(joinDTOGroupDTO.getUserNickname());
        GroupsEntity group = groupService.findByNameOrNull(joinDTOGroupDTO.getGroupName());
        MembershipEntity membership = membershipRepository.save(
            MembershipEntity.builder()
                .user(user)
                .group(group)
                .build()
        );

        return JoinResultDTO.builder()
            .membershipId(membership.getId())
            .userId(user.getId())
            .userNickname(user.getNickname())
            .groupId(group.getId())
            .groupName(group.getName())
            .build();
    }

    public LeaveResultDTO leaveGroup(LeaveDTO leaveDTO) {
        UsersEntity user = userService.findByUserNameOrThrow(leaveDTO.getUserNickname());
        GroupsEntity group = groupService.findByNameOrNull(leaveDTO.getGroupName());
        MembershipEntity membershipEntity = findByUserIdAndGroupIdOrThrow(user, group);
        membershipRepository.delete(membershipEntity);

        return LeaveResultDTO.builder()
            .membershipId(membershipEntity.getId())
            .userId(user.getId())
            .userNickname(user.getNickname())
            .groupId(group.getId())
            .groupName(group.getName())
            .build();
    }

    public MembershipEntity findByUserIdAndGroupIdOrThrow(UsersEntity user, GroupsEntity group) {
        Optional<MembershipEntity> optional = membershipRepositorySupport.findByUserAndGroup(user, group);
        return optional.orElseThrow(()-> new BusinessException(ErrorCode.MEMBERSHIP_NOT_FOUND));
    }

}

package com.example.app.domain.group.service;

import com.example.app.data.entity.GroupsEntity;
import com.example.app.data.entity.UsersEntity;
import com.example.app.data.repository.GroupRepository;
import com.example.app.data.repository.GroupRepositorySupport;
import com.example.app.domain.group.dto.GroupDTO;
import com.example.app.global.error.exception.BusinessException;
import com.example.app.global.error.exception.ErrorCode;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupRepositorySupport groupRepositorySupport;
    private final ModelMapper modelMapper;

    public GroupService(GroupRepository groupRepository,
        GroupRepositorySupport groupRepositorySupport, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.groupRepository = groupRepository;
        this.groupRepositorySupport = groupRepositorySupport;
    }

    public GroupDTO.Get add(GroupDTO.Create group) {
        Optional<GroupsEntity> optional = groupRepository.findByName(group.getName());
        if (optional.isPresent()) throw new BusinessException(ErrorCode.DUPLICATE_GROUP_NAME);
        GroupsEntity entity = GroupsEntity.builder().name(group.getName()).build();
        return modelMapper.map(groupRepository.save(entity), GroupDTO.Get.class);
    }

    public List<UsersEntity> getUsersByGroupNames(List<String> groupNames) {

        List<GroupsEntity> groups = groupRepositorySupport.getGroupsByGroupNames(groupNames);
        Set<UsersEntity> users = new HashSet<>();
        for (GroupsEntity group : groups) {
            users.addAll(group.getUsers());
        }
        return users.stream().toList();
    }

    public List<UsersEntity> getUsersByGroupName(String groupName) {

        Optional<GroupsEntity> optional = groupRepository.findByName(groupName);
        return optional.map(GroupsEntity::getUsers).orElse(null);
    }

    public GroupsEntity findByName(String groupName) {
        Optional<GroupsEntity> optional = groupRepository.findByName(groupName);
        return optional.orElse(null);
    }
}

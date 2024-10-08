package com.example.app.domain.group.service;

import com.example.app.data.entity.GroupsEntity;
import com.example.app.data.entity.UsersEntity;
import com.example.app.data.repository.GroupRepository;
import com.example.app.data.repository.GroupRepositorySupport;
import com.example.app.domain.group.dto.GroupDTO;
import com.example.app.global.error.exception.BusinessException;
import com.example.app.global.error.exception.ErrorCode;
import java.util.List;
import java.util.Optional;
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

    public List<UsersEntity> getUsersByGroupName(String groupName) {
        return groupRepositorySupport.findUsersByGroupName(groupName);
    }

    public GroupsEntity findByNameOrThrow(String groupName) {
        Optional<GroupsEntity> optional = groupRepository.findByName(groupName);
        return optional.orElseThrow(() -> new BusinessException(ErrorCode.GROUP_NOT_FOUND));
    }
}

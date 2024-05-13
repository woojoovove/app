package com.example.app.domain.service;

import com.example.app.data.entity.GroupsEntity;
import com.example.app.data.repository.GroupRepository;
import com.example.app.domain.dto.GroupDTO;
import com.example.app.global.error.exception.BusinessException;
import com.example.app.global.error.exception.ErrorCode;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    public GroupService(GroupRepository groupRepository,ModelMapper modelMapper) {
        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
    }

    public GroupDTO.Get add(GroupDTO.Create group) {
        Optional<GroupsEntity> optional = groupRepository.findByName(group.getName());
        if (optional.isPresent()) throw new BusinessException(ErrorCode.DUPLICATE_GROUP_NAME);
        GroupsEntity entity = GroupsEntity.builder().name(group.getName()).build();
        return modelMapper.map(groupRepository.save(entity), GroupDTO.Get.class);
    }
}

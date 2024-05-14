package com.example.app.domain.user.service;

import com.example.app.data.entity.GroupsEntity;
import com.example.app.data.entity.UsersEntity;
import com.example.app.data.repository.UsersRepository;
import com.example.app.domain.group.service.GroupService;
import com.example.app.domain.user.dto.UserDTO.JoinGroup;
import com.example.app.domain.user.dto.UserDTO.JoinGroupResult;
import com.example.app.global.error.exception.BusinessException;
import com.example.app.global.error.exception.ErrorCode;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UsersRepository userRepository;
    private final GroupService groupService;


    public UserService(UsersRepository userRepository, GroupService groupService) {
        this.userRepository = userRepository;
        this.groupService = groupService;
    }


    public UsersEntity findByUserName(String name) {
        Optional<UsersEntity> optional = userRepository.findByNickname(name);
        return optional.orElse(null);
    }

    public List<UsersEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public JoinGroupResult joinGroup(JoinGroup joinGroupDTO) {

        UsersEntity user = findByUserName(joinGroupDTO.getUserNickname());
        if (user == null) throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        GroupsEntity group = groupService.findByName(joinGroupDTO.getGroupName());
        if (group == null) throw new BusinessException(ErrorCode.GROUP_NOT_FOUND);
        user.setGroup(group);
        userRepository.save(user);

        JoinGroupResult result = new JoinGroupResult();
        result.setUserId(user.getId());
        result.setUserNickname(user.getNickname());
        result.setGroupId(group.getId());
        result.setGroupName(group.getName());

        return result;
    }
}

package com.example.app.domain.user.service;

import com.example.app.data.entity.UsersEntity;
import com.example.app.data.repository.UsersRepository;
import com.example.app.global.error.exception.BusinessException;
import com.example.app.global.error.exception.ErrorCode;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UsersRepository userRepository;

    public UserService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 없다고 바로 throw하지 않는 메소드가 필요함
    // 예: 클라이언트가 알람 타겟을 표기할 때, 존재하지 않는 이름을 입력할 수도 있음.
    public UsersEntity findByUserNameOrNull(String name) {
        Optional<UsersEntity> optional = userRepository.findByNickname(name);
        return optional.orElse(null);
    }

    public UsersEntity findByUserNameOrThrow(String name) {
        Optional<UsersEntity> optional = userRepository.findByNickname(name);
        return optional.orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    public List<UsersEntity> getAllUsers() {
        return userRepository.findAll();
    }


}

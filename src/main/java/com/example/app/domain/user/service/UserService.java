package com.example.app.domain.user.service;

import com.example.app.data.entity.UsersEntity;
import com.example.app.data.repository.UsersRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UsersRepository userRepository;


    public UserService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UsersEntity findByUserName(String name) {
        Optional<UsersEntity> optional = userRepository.findByNickname(name);
        return optional.orElse(null);
    }

    public List<UsersEntity> getAllUsers() {
        return userRepository.findAll();
    }
}

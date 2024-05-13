package com.example.app.domain.user.service;

import com.example.app.data.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UsersRepository userRepository;


    public UserService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }


}

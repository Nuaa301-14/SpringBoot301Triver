package com.example.javacrawler.service.serviceImpl;

import com.example.javacrawler.entity.User;
import com.example.javacrawler.mapper.UserMapper;
import com.example.javacrawler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByName(String name) {
        return userMapper.getUserByName(name);
    }
}

package com.example.javacrawler.service;

import com.example.javacrawler.entity.User;

public interface UserService {

    User getUserByName(String name);

    int insertUser(User user);
}

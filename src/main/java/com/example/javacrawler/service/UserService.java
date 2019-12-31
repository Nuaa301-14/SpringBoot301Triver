package com.example.javacrawler.service;

import com.example.javacrawler.entity.User;
import com.example.javacrawler.pojo.BSResult;

public interface UserService {

    User getUserByName(String name);

    BSResult checkUserExistByUsername(String username);

    int insert(User user);
}

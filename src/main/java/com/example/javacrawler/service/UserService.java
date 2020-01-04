package com.example.javacrawler.service;

import com.example.javacrawler.entity.User;
import com.example.javacrawler.pojo.BSResult;

import java.util.List;
import java.util.Map;

public interface UserService {

    User getUserByName(String name);

    BSResult checkUserExistByUsername(String username);

    int insert(User user);

    User seletebyId(int id);

    void update(User User);

    boolean compareAndChange(int id, String oldPwd, String newPwd);

    List<User> seleteList(Map map);
}

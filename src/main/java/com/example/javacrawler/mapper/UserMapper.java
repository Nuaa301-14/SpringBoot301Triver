package com.example.javacrawler.mapper;

import com.example.javacrawler.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    User getUserByName(String name);

    int insert(User user);
}

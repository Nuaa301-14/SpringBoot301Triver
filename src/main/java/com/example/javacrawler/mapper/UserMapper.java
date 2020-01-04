package com.example.javacrawler.mapper;

import com.example.javacrawler.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {

    User getUserByName(String name);

    int insert(User user);

    User selectById(@Param("param") Map map);

    void update(User user);

    List<User> seleteList(@Param("param") Map map);
}

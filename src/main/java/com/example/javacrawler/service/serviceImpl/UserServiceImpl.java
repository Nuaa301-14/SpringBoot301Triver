package com.example.javacrawler.service.serviceImpl;

import com.example.javacrawler.entity.User;
import com.example.javacrawler.mapper.UserMapper;
import com.example.javacrawler.pojo.BSResult;
import com.example.javacrawler.service.UserService;
import com.example.javacrawler.util.BSResultUtil;
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

    @Override
    public BSResult checkUserExistByUsername(String username) {
        User user = userMapper.getUserByName(username);
        if (user!=null){
            return BSResultUtil.build(400, "用户名已被注册", false);
        }else{
            return BSResultUtil.build(200, "用户名可以使用", true);
        }
    }

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }
}

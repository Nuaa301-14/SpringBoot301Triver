package com.example.javacrawler.service.serviceImpl;

import com.example.javacrawler.entity.User;
import com.example.javacrawler.mapper.UserMapper;
import com.example.javacrawler.pojo.BSResult;
import com.example.javacrawler.service.UserService;
import com.example.javacrawler.util.BSResultUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (user != null) {
            return BSResultUtil.build(400, "用户名已被注册", false);
        } else {
            return BSResultUtil.build(200, "用户名可以使用", true);
        }
    }

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public User seletebyId(int id) {
        Map map = new HashMap();
        map.put("userId", id);
        return userMapper.selectById(map);
    }

    @Override
    public void update(User User) {
        userMapper.update(User);
    }

    @Override
    public boolean compareAndChange(int id, String oldPwd, String newPwd) {
        Map map = new HashMap();
        map.put("userId", id);
        User user = userMapper.selectById(map);
        String password = user.getPassword();
        if (oldPwd.equals(password)) {
            user.setPassword(newPwd);
            userMapper.update(user);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<User> seleteList(Map map) {
        PageHelper.startPage((int)map.get("page"),(int)map.get("pageSize"));
        return userMapper.seleteList(map);
    }
}

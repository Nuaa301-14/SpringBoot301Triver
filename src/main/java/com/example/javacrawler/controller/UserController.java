package com.example.javacrawler.controller;

import com.example.javacrawler.entity.Role;
import com.example.javacrawler.entity.User;
import com.example.javacrawler.mapper.RoleMapper;
import com.example.javacrawler.pojo.BSResult;
import com.example.javacrawler.service.UserService;
import com.example.javacrawler.util.BSResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserService userService;

    private final String USERNAME_PASSWORD_NOT_MATCH = "用户名或密码错误";

    private final String USERNAME_CANNOT_NULL = "用户名不能为空";


    /**
     * 用户登陆
     *
     * @param username
     * @param password
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "login.html", method = RequestMethod.POST)
    public String login(@RequestParam(value = "username", required = false) String username,
                        @RequestParam(value = "password", required = false) String password,
                        HttpServletRequest request, Model model) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            /**
             * 返回登陆界面
             */
            return "Admin/login";
        }
        SecurityUtils.getSubject().logout();
        Subject userSubject = SecurityUtils.getSubject();
        if (!userSubject.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(false);//禁止记住我功能
            try {
                //登陆成功
                userSubject.login(token);
                // 获取用户
                User user = (User) userSubject.getPrincipal();
                request.getSession().setAttribute("loginUser", user);
                // 获取用户权限信息
                List<Role> roles = roleMapper.findRolesByUserId(user.getId());
                for (Role role : roles) {
                    String code = role.getCode();
                    if (code.equals("admin") || code.equals("user-manager") || code.equals("role-privilege-manager")) {
                        /**
                         * 管理员用户
                         */
                        return "redirect:/admin/index";
                    } else if (code.equals("customer") || code.equals("vip-customer")) {
                        /**
                         * 普通用户或vip用户
                         */
                        return "login";
                    } else {
                        return "Admin/login";
                    }
                }
            } catch (UnknownAccountException | IncorrectCredentialsException uae) {
                model.addAttribute("loginMsg", USERNAME_PASSWORD_NOT_MATCH);
                return "Admin/login";
            } catch (LockedAccountException lae) {
                model.addAttribute("loginMsg", "账户已被冻结！");
                return "Admin/login";
            } catch (AuthenticationException ae) {
                model.addAttribute("loginMsg", "登录失败！");
                return "Admin/login";
            }
        }else {

        }
        return "redirect:/admin/";
    }

    @RequestMapping(value = "/user/changePwd")
    public String changePwd(HttpServletRequest request, Model model) {
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        System.out.println(loginUser.getId());

        return "changePwd";
    }

    @RequestMapping(value = "/user/logout")
    @ResponseBody
    public String logout(){
        SecurityUtils.getSubject().logout();
        System.out.println("用户 退出");
        return "success";
    }

    @RequestMapping("/user/checkUserExist")
    @ResponseBody
    public BSResult checkUserExist(String username) {
        if (StringUtils.isEmpty(username)) {
            return BSResultUtil.build(200, USERNAME_CANNOT_NULL, false);
        }
        return userService.checkUserExistByUsername(username);
    }

    @PostMapping(value = "/register")
    public String register(@RequestParam(value = "username", required = false) String username,
                           @RequestParam(value = "password", required = false) String password,
                           @RequestParam(value = "password-confirm", required = false) String password_confirm,
                           @RequestParam(value = "email", required = false) String email,
                           @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                           HttpServletRequest request, Model model) {
        if (!password.equals(password_confirm)){
            model.addAttribute("registerError","密码不一致");
            return "redirect:/user/register";
        }
        BSResult isExist = checkUserExist(username);
        if ((Boolean)isExist.getData()){
            User user=new User();
            user.setName(username);
            user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
            userService.insert(user);
            request.getSession().setAttribute("registerUser",user);
            return "redirect:/registerSuccess";
        }else {
            model.addAttribute("registerError","用户已经存在");
            return "redirect:/user/register";
        }
    }

    @RequestMapping("/user/userInfo")
    public String userInfo(){
        return "userInfo";
    }


}

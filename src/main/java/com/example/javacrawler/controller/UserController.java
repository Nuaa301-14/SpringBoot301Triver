package com.example.javacrawler.controller;

import com.example.javacrawler.entity.Role;
import com.example.javacrawler.entity.User;
import com.example.javacrawler.entity.UserRole;
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
import javax.servlet.http.HttpSession;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                HttpSession session = request.getSession(true);
                session.setAttribute("loginUser", user);
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
                        return "redirect:/io/scenery.html";
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
    public String changePwd(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        System.out.println(loginUser.getId());
        model.addAttribute("userId",loginUser.getId());
        return "changePwd";
    }
    @RequestMapping(value = "/user/PwdChange")
    @ResponseBody
    public Map<String,Object> PwdChange(@RequestBody List<Map<String,Object>> change,HttpSession session){
        Map<String, Object> userInfoMap = change.get(0);
        Map<String,Object> map=new HashMap<>();
        int id = (int) userInfoMap.get("id");
        String oldPwd= (String) userInfoMap.get("oldPwd");
        String newPwd= (String) userInfoMap.get("newPwd");
        String confirmPwd= (String) userInfoMap.get("confirmPwd");
        boolean suc=userService.compareAndChange(id,oldPwd,newPwd);
        if (suc==true){
            User loginUser = (User) session.getAttribute("loginUser");
            loginUser.setPassword(newPwd);
            session.setAttribute("loginUser",loginUser);
            map.put("reply","success");
        }else {
            map.put("reply","fail");
        }
        return map;
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
//            user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
            user.setPassword(password);
            user.setUpdated(new Date());
            user.setCreated(new Date());
            userService.insert(user);
            user=userService.getUserByName(user.getName());
            UserRole role=new UserRole();
            role.setUserId(user.getId());
            role.setRoleId(2);
            role.setCreated(new Date());
            role.setUpdated(new Date());
            roleMapper.insertRole(role);

            request.getSession().setAttribute("registerUser",user);
            return "redirect:/registerSuccess";
        }else {
            model.addAttribute("registerError","用户已经存在");
            return "redirect:/user/register";
        }
    }

    @RequestMapping("/user/userInfo")
    public String userInfo(HttpSession session, Model model){
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("userId",loginUser.getId());
        return "userInfo";
    }

    @RequestMapping(value = "/user/changeInfo")
    @ResponseBody
    public Map<String, Object> changeInfo(@RequestBody List<Map<String,Object>> edit,HttpSession session){
        Map<String ,Object> map=new HashMap<>();
        Map<String, Object> userInfoMap = edit.get(0);
        String userName= (String) userInfoMap.get("userName");
        String realName= (String) userInfoMap.get("realName");
        String gender= (String) userInfoMap.get("gender");
        String  phoneNumber= (String) userInfoMap.get("phoneNumber");
        String birth= (String) userInfoMap.get("birth");
        String userEmail= (String) userInfoMap.get("email");
        User loginUser = (User) session.getAttribute("loginUser");
        loginUser.setUserName(userName);
        loginUser.setRealName(realName);
        loginUser.setGender(gender);
        loginUser.setPhoneNumber(phoneNumber);
        loginUser.setBirth(strToDate(birth));
        loginUser.setEmail(userEmail);
        loginUser.setUpdated(new Date());
        userService.update(loginUser);
        session.setAttribute("loginUser",loginUser);
        map.put("user",loginUser);
        return map;
    }

    public Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }


}

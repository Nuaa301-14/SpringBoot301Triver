package com.example.javacrawler.shiro;

import com.example.javacrawler.entity.Privilege;
import com.example.javacrawler.entity.Role;
import com.example.javacrawler.entity.User;
import com.example.javacrawler.mapper.RoleMapper;
import com.example.javacrawler.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Realm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 执行授权逻辑
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("654231");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        User user = (User) principalCollection.getPrimaryPrincipal();
        List<Role> roles = roleMapper.findRolesByUserId(user.getId());
        for (Role role : roles) {
            authorizationInfo.addRole(role.getCode());
//            List<Privilege> privileges = roleMapper.findPrivilegesByRoleId(role.getRoleId());
//            for (Privilege privilege : privileges) {
//                authorizationInfo.addStringPermission(privilege.getCode());
//            }
        }
        return authorizationInfo;
    }

    /**
     * 执行认证逻辑
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        String name = "root";
//        String password = "161630126";

        String username = (String) authenticationToken.getPrincipal();

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        /**
         * 数据库中username不允许相同
         * 所以只需要一个User来记录
         */
        User user = userService.getUserByName(((UsernamePasswordToken) authenticationToken).getUsername());

        if (user == null) {
            return null;    //shiro底层会抛出UnknowAccountException
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        return simpleAuthenticationInfo;
    }
}

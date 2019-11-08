package com.example.javacrawler.shiro;

import com.example.javacrawler.entity.User;
import com.example.javacrawler.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class Realm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 执行授权逻辑
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("654231");
        return null;
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
        String name="root";
        String password="161630126";


        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;

        User user = userService.getUserByName(((UsernamePasswordToken) authenticationToken).getUsername());

        if (user==null){
            return null;    //shiro底层会抛出UnknowAccountException
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo("",user.getPassword(),getName());
        return simpleAuthenticationInfo;
    }
}

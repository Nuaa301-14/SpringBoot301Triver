package com.example.javacrawler.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean(name = "ShiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("SecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        // 添加shiro过滤器
        /**
         * Shiro内置的过滤器,可以实现权限相关的拦截器
         *  常用的过滤器:
         *      anon: 无需认证(登陆)可以访问
         *      authc:必须通过认证才可以访问
         *      user:如果使用rememberMe的功能可以直接访问
         *      perms:该资源必须得到资源权限才可以访问
         *      role:该资源必须得到角色权限才可以访问
         */
        Map<String,String> filterMap=new LinkedHashMap<>();
        filterMap.put("/login","authc");

        // 修改调整的登陆界面
        shiroFilterFactoryBean.setLoginUrl("/get");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);


        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean(name = "SecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("realm") Realm realm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }


    /**
     * 创建Realm
     */
    @Bean(name = "realm")
    public Realm getRealm() {
        return new Realm();
    }

    /**
     * 配置ShiroDialect，用于thymeleaf和shiro标签配合使用
     */

}

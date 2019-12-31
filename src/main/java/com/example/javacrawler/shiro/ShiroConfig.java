package com.example.javacrawler.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


//    @Bean("hashedCredentialsMatcher")
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
//        //指定加密方式为MD5
//        credentialsMatcher.setHashAlgorithmName("MD5");
//        //加密次数
//        credentialsMatcher.setHashIterations(1024);
////        true指定Hash散列值使用Hex加密存. false表明hash散列值用用Base64-encoded存储
//        credentialsMatcher.setStoredCredentialsHexEncoded(true);
//        return credentialsMatcher;
//    }


    @Bean("myRealm")
    @DependsOn("lifecycleBeanPostProcessor")//让这个类先于meRealm加载
    public Realm myRealm() {
        Realm myRealm = new Realm();
//        //不启用授权缓存
        myRealm.setAuthorizationCachingEnabled(false);
//        //加密机制
//        myRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return myRealm;
    }

    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
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
        filterMap.put("/admin/**","authc");

        // 修改调整的登陆界面
        shiroFilterFactoryBean.setLoginUrl("/");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);


        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean(name = "SecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("myRealm") Realm realm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

//    /**
//     * @Description: Shiro生命周期，保证实现了Shiro内部lifecycle函数的bean执行
//     * @Param: []
//     * @return: org.apache.shiro.spring.LifecycleBeanPostProcessor
//     * @Author: ywj
//     * @Date: 2019/3/28 0028
//     */
//    @Bean("lifecycleBeanPostProcessor")
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }


    /**
     * 配置ShiroDialect，用于thymeleaf和shiro标签配合使用
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
}

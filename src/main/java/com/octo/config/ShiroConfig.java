package com.octo.config;

import com.octo.filter.JwtFilter;
import com.octo.shiro.MyRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zms
 */
@Configuration
public class ShiroConfig {
    @Bean
    public MyRealm myRealm() {
        return new MyRealm();
    }

    @Bean
    public DefaultWebSecurityManager securityManager(Realm myRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        // 添加过滤器
        HashMap<String, Filter> filterMap = new HashMap<>(2);
        filterMap.put("jwt", new JwtFilter());
        filterFactoryBean.setFilters(filterMap);
        // 添加URL过滤规则
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/user/login", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/captcha", "anon");
        filterChainDefinitionMap.put("/**", "jwt"); // 需要认证
//        filterChainDefinitionMap.put("/**", "anon");
        filterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return filterFactoryBean;
    }
}

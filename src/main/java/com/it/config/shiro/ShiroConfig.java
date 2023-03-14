package com.it.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.it.realm.AccountRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置文件
 
 */
@Configuration
public class ShiroConfig {
    
    private static final String SHIRO_DIALECT = "shiroDialect";
    private static final String SHIRO_FILTER = "shiroFilter";
    private String hashAlgorithmName = "md5";// 加密方式
    private int hashIterations = 2;// 散列次数


    /**
     * 声明凭证匹配器
     */
    @Bean("credentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(hashAlgorithmName);
        credentialsMatcher.setHashIterations(hashIterations);
        return credentialsMatcher;
    }

    /**
     * 注入自定义的accountRealm
     * @return
     */
    @Bean
    public AccountRealm getaccountRealm(CredentialsMatcher credentialsMatcher){
        AccountRealm accountRealm = new AccountRealm();
        //注入凭证匹配器
        accountRealm.setCredentialsMatcher(credentialsMatcher);
        return accountRealm;
    }


    /**
     * 创建DefaultWebSecurityManager对象，关联自定义的accountRealm对象
     * @param accountRealm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(AccountRealm accountRealm){
        //创建DefaultWebSecurityManager对象
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //关联自定义realm
        defaultWebSecurityManager.setRealm(accountRealm);
        //返回DefaultWebSecurityManager对象
        return defaultWebSecurityManager;
    }

    /**
     * 创建ShiroFilterFactoryBean对象，设置安全管理器
     * @param securityManager
     * @return
     */
    @Bean(SHIRO_FILTER)
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        //创建ShiroFilterFactoryBean对象
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        factoryBean.setSecurityManager(securityManager);
        //设置过滤器链
        Map<String,String> filterChainDefinitionsMap = new LinkedHashMap<>();
        //放行路径（匿名访问）
        filterChainDefinitionsMap.put("/resources/**","anon");//静态资源
        filterChainDefinitionsMap.put("/user/login","anon");//登录请求
        filterChainDefinitionsMap.put("/login","anon");//去到登录页面
        filterChainDefinitionsMap.put("/","anon");//去到登录页面
        filterChainDefinitionsMap.put("/login.html","anon");//去到登录页面
        filterChainDefinitionsMap.put("/favicon.ico","anon");//去到登录页面
        //退出
        filterChainDefinitionsMap.put("/logout","logout");
        //其他的都拦截
        filterChainDefinitionsMap.put("/**","authc");
        //将过滤器链设置到shiroFilterFactoryBean对象中
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionsMap);
        //身份验证失败要去到登录页面
        //如果不设置loginUrl,则默认找login.jsp页面
        factoryBean.setLoginUrl("/login");
        return factoryBean;
    }

    /**
     * 注册shiro的委托过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> delegatingFilterProxy() {
        FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean = new FilterRegistrationBean<DelegatingFilterProxy>();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName(SHIRO_FILTER);
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    /* 加入注解的使用，不加入这个注解不生效--开始 */
    /**
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    /* 加入注解的使用，不加入这个注解不生效--结束 */

    /**
     * 这里是为了能在html页面引用shiro标签，上面两个函数必须添加，不然会报错
     *
     * @return
     */
    @Bean(name = SHIRO_DIALECT)
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}

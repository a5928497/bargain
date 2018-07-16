package com.yukoon.bargain.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.yukoon.bargain.realms.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") org.apache.shiro.mgt.SecurityManager manager){
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager( manager);
		//配置登录的url和登录成功的url
		bean.setLoginUrl("/login");
		bean.setSuccessUrl("/users");
		//配置访问权限
		LinkedHashMap<String, String> filterChainDefinitionMap=new LinkedHashMap<>();
		filterChainDefinitionMap.put("/login", "anon"); //表示可以匿名访问
		filterChainDefinitionMap.put("/logout","logout");
		filterChainDefinitionMap.put("/error/*.html","anon");
		filterChainDefinitionMap.put("/css/**","anon");
		filterChainDefinitionMap.put("/js/**","anon");
		filterChainDefinitionMap.put("/index","anon");
		filterChainDefinitionMap.put("/images/**","anon");
		filterChainDefinitionMap.put("/webjars/**","anon");
		filterChainDefinitionMap.put("/backend","anon");
		filterChainDefinitionMap.put("/toregister","anon");
		filterChainDefinitionMap.put("/register","anon");
		filterChainDefinitionMap.put("/actInfo/**","anon");
        filterChainDefinitionMap.put("/joinIn","anon");
		filterChainDefinitionMap.put("/game/**","anon");
		filterChainDefinitionMap.put("/reward_images/**","anon");
		filterChainDefinitionMap.put("/allhelpers/**","anon");
		filterChainDefinitionMap.put("/public/**","anon");
		filterChainDefinitionMap.put("/test**","anon");
		filterChainDefinitionMap.put("/test/**","anon");
		filterChainDefinitionMap.put("/*", "authc");//表示需要认证才可以访问
		filterChainDefinitionMap.put("/**", "authc");//表示需要认证才可以访问
		filterChainDefinitionMap.put("/*.*", "authc");
		bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return bean;
	}

	//配置核心安全事务管理器
	@Bean(name="securityManager")
	public DefaultWebSecurityManager securityManager() {
		System.err.println("--------------shiro已经加载----------------");
		DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
		manager.setRememberMeManager(rememberMeManager());
		manager.setRealm(userRealm());
		return manager;
	}
	//配置MD5验证器
	@Bean(name = "hashedCredentialsMatcher")
	public HashedCredentialsMatcher getHashedCredentialsMatcher() {
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("MD5");
		credentialsMatcher.setHashIterations(1024);
		credentialsMatcher.setStoredCredentialsHexEncoded(true);
		return credentialsMatcher;
	}
	//配置自定义的权限登录器
	@Bean(name="userRealm")
	public UserRealm userRealm() {
		UserRealm userRealm = new UserRealm();
		//加入MD5验证器
		userRealm.setCredentialsMatcher(getHashedCredentialsMatcher());
		return userRealm;
	}

	//配置rememberCookie
	@Bean
	public SimpleCookie rememberCookie(){
		SimpleCookie cookie = new SimpleCookie("rememberMe");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(31536000);
		cookie.setPath("/");
		return cookie;
	}

	//配置RememberMe管理器
	@Bean
	public RememberMeManager rememberMeManager() {
		CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
		rememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
		rememberMeManager.setCookie(rememberCookie());
		return rememberMeManager;
	}
	//配置缓存管理器，管理用户验证Cache
	@Bean(name = "cacheShiroManager")
	public CacheManager CacheManage() {
		return new EhCacheManager();
	}

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor LifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator AutoProxyCreator(){
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		return creator;
	}

	//配置会话验证调度器，每15分钟执行一次验证
	@Bean(name = "sessionValidationScheduler")
	public ExecutorServiceSessionValidationScheduler ExecutorServiceSessionValidationScheduler() {
		ExecutorServiceSessionValidationScheduler scheduler = new ExecutorServiceSessionValidationScheduler();
		scheduler.setInterval(15 * 60 *1000);
		return scheduler;
	}

	//配置sessionCookie
	@Bean(name = "sessionIdCookie")
	public SimpleCookie SessionIdCookie() {
		SimpleCookie cookie = new SimpleCookie("sid");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(-1);
		return cookie;
	}

	//配置sessionManager
	@Bean(name = "sessionManager")
	public DefaultWebSessionManager SessionManage() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(1800000);
		sessionManager.setSessionValidationScheduler(ExecutorServiceSessionValidationScheduler());
		sessionManager.setSessionValidationSchedulerEnabled(true);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setSessionIdCookie(SessionIdCookie());
		EnterpriseCacheSessionDAO cacheSessionDAO = new EnterpriseCacheSessionDAO();
		cacheSessionDAO.setCacheManager(CacheManage());
		sessionManager.setSessionDAO(cacheSessionDAO);
		// -----可以添加session 创建、删除的监听器

		return sessionManager;
	}

	@Bean
	public MethodInvokingFactoryBean getMethodInvokingFactoryBean(){
		MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
		factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		factoryBean.setArguments(new Object[]{securityManager()});
		return factoryBean;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor AuthorizationAttributeSourceAdvisor(){
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager());
		return advisor;
	}

	//配置thymeleaf支持
	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

	//加入注解的使用，不加入这个注解不生效
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(org.apache.shiro.mgt.SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
}

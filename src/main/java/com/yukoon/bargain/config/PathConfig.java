package com.yukoon.bargain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "pathconfig")
@PropertySource("classpath:pathconfig.yml")
public class PathConfig extends WebMvcConfigurerAdapter {

	@Value("${rewardImgsPath}")
	private String rewardImgsPath;
	@Resource(name="thymeleafViewResolver")
	private ThymeleafViewResolver thymeleafViewResolver;

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		if (thymeleafViewResolver != null) {
			Map<String,Object> map = new HashMap<>();
			map.put("img_path",rewardImgsPath);
			thymeleafViewResolver.setStaticVariables(map);
		}
		super.configureViewResolvers(registry);
	}

	//配置前台显示图片映射路径
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/reward_images/**").addResourceLocations("file:"+rewardImgsPath);
        super.addResourceHandlers(registry);
    }

	public String getRewardImgsPath() {
		return rewardImgsPath;
	}

	public void setRewardImgsPath(String rewardImgsPath) {
		this.rewardImgsPath = rewardImgsPath;
	}
}
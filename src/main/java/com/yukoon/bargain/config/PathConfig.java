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
	@Value("${advImgPath}")
	private String advImgPath;
	@Value("${shareImgPath}")
	private String shareImgPath;
	@Resource(name="thymeleafViewResolver")
	private ThymeleafViewResolver thymeleafViewResolver;

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		if (thymeleafViewResolver != null) {
			Map<String,Object> map = new HashMap<>();
			map.put("img_path",rewardImgsPath);
			map.put("adv_img_path",advImgPath);
			map.put("shareImgPath",shareImgPath);
			thymeleafViewResolver.setStaticVariables(map);
		}
		super.configureViewResolvers(registry);
	}

	//配置前台显示图片、广告图片映射路径
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/reward_images/**").addResourceLocations("file:"+rewardImgsPath);
		registry.addResourceHandler("/adv_images/**").addResourceLocations("file:"+advImgPath);
		registry.addResourceHandler("/share_images/**").addResourceLocations("file:"+shareImgPath);
        super.addResourceHandlers(registry);
    }

	public String getRewardImgsPath() {
		return rewardImgsPath;
	}

	public void setRewardImgsPath(String rewardImgsPath) {
		this.rewardImgsPath = rewardImgsPath;
	}

	public String getAdvImgPath() {
		return advImgPath;
	}

	public void setAdvImgPath(String advImgPath) {
		this.advImgPath = advImgPath;
	}
}

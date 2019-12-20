package com.best.java.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: xjxu3
 * @Date: 2019/12/20 8:57
 * @Description:
 */
@Configuration
@EnableConfigurationProperties(ConfigProperties.class)
@ConditionalOnClass(AutoConfigService.class)
//myauto.config.open是open才生产bean，如果没有配置（matchIfMissing = true），则默认可以生成
@ConditionalOnProperty(prefix = "myauto.config.open", value = "open", matchIfMissing = true)
public class AutoConfig {

	@Autowired
	private ConfigProperties configProperties;

	@Bean(name = "autoConfigService")
	@ConditionalOnMissingBean(AutoConfigService.class)
	public AutoConfigService getAutoConfigService() {
		return new AutoConfigService(configProperties);
	}
}

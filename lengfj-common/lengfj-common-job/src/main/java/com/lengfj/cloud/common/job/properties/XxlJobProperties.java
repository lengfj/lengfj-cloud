package com.lengfj.cloud.common.job.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * xxl-job配置
 */
@Data
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {

	@NestedConfigurationProperty
	private XxlAdminProperties admin = new XxlAdminProperties();

	@NestedConfigurationProperty
	private XxlExecutorProperties executor = new XxlExecutorProperties();
	/**
	 * 是否debug启动（作用与开发本地）
	 */
	private Boolean isDebug = false;
}

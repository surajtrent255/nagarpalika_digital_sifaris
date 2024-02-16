/**
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * Since Feb 1, 2020
 */
package com.ishanitech.ipalika.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "rest")
@Data
public class RestBaseProperty {
	private String protocol;
	private String domain;
	private String port;
	private String resourceLocation;
}

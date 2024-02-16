package com.ishanitech.ipalika.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "database")
public class DataSourceBean {

	private String username;
	private String password;
}

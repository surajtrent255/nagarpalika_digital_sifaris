package com.ishanitech.ipalika.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "jwt")
@Component
@Data
public class JwtProperties {
	private String applicationName;
	private long expirationDate;
	private SecurityProperty security;
	private JwtAuthenticationProperty auth;
}

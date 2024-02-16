package com.ishanitech.ipalika.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "rest-microservice-api")
public class MicroServiceUrl {
        private String protocol;
        private String domain;
        private int port;
}

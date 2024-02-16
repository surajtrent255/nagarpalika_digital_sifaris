package com.ishanitech.ipalika;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Properties;


@EnableScheduling
@SpringBootApplication
@EnableSwagger2
public class IpalikaDataCollectionApplication {
	@Value("${server.port}")
	private int httpPort;
	public static void main(String[] args) {
		SpringApplication.run(IpalikaDataCollectionApplication.class, args);
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}
	
   @Bean
   public Docket productApi() {
	  return new Docket(DocumentationType.SWAGGER_2).select()
		 .apis(RequestHandlerSelectors.basePackage("com.ishanitech.ipalika.controller")).build();
   }
	/**
	 * Create bean for rest template. Using rest template we can call remote rest api.
	 * @return restTemplate RestTemplate object
	 */
	@Bean
	RestTemplate restTemplate() {
		RestTemplate restTemplate = defaultRestTemplate();
		restTemplate.setRequestFactory(clientHttpRequestFactory());
		return restTemplate;
	}

	/**
	 * @return restTemplate a default rest template with default mapper.
	 */
	private static RestTemplate defaultRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		/*
		 * ObjectMapper objectMapper = new ObjectMapper();
		 * objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
		 * false); MappingJackson2HttpMessageConverter converter = new
		 * MappingJackson2HttpMessageConverter();
		 * converter.setObjectMapper(objectMapper);
		 * restTemplate.setMessageConverters(Arrays.asList(converter));
		 */
		return restTemplate;
	}

	/**
	 *
	 * @return ClientHttpRequestFacoty custom http client request factory for unsupported http methods like patch, head etc.
	 */
	private ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setConnectTimeout(80000);
		factory.setReadTimeout(80000);
		return factory;
	}


	// Lets configure additional connector to enable support for both HTTP and HTTPS
//	 	@Bean
//	 	@Profile("prod")
//	 	public ServletWebServerFactory servletContainer() {
//	 		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {@Override
//			protected void postProcessContext(Context context) {
//				SecurityConstraint securityConstraint = new SecurityConstraint();
//				securityConstraint.setUserConstraint("CONFIDENTIAL");
//				SecurityCollection collection = new SecurityCollection();
//				collection.addPattern("/*");
//				securityConstraint.addCollection(collection);
//				context.addConstraint(securityConstraint);
//			}
//		};
//	 		tomcat.addAdditionalTomcatConnectors(createStandardConnector());
//	 		return tomcat;
//	 	}

	private Connector createStandardConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(httpPort);
		connector.setSecure(false);
		connector.setRedirectPort(443);
		return connector;
	}
	@Bean
	public JavaMailSender blMailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost("mail.ishanitech.com");
		sender.setUsername("test@ishanitech.com");
		sender.setPassword("Password1*#");
		sender.setPort(587);
		sender.setDefaultEncoding("UTF-8");
		sender.setProtocol("smtp");
		Properties javaMailProps = new Properties();
		javaMailProps.setProperty("mail.smtp.starttls.enable", "true");
		javaMailProps.setProperty("mail.smtp.auth", "true");
		javaMailProps.setProperty("mail.smtp.timeout", "25000");
		sender.setJavaMailProperties(javaMailProps);
		return sender;
	}

}

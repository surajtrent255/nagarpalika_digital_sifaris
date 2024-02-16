/**
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * Since Dec 11, 2019
 */
package com.ishanitech.ipalika.config;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatasourceConfig {
	@Autowired
	DataSourceBean dsb;

	@Primary
	@Bean
	HikariDataSource datasource() {
		HikariConfig config = new HikariConfig();
		config.setUsername(dsb.getUsername());
		config.setPassword(dsb.getPassword());

		config.setDriverClassName("com.mysql.cj.jdbc.Driver");
		config.setJdbcUrl("jdbc:mysql://localhost:3306/ipalika_v3?createDatabaseIfNotExist=true&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull&useUnicode=yes&characterEncoding=UTF-8");
		return new HikariDataSource(config);
	}

	@Bean
	TransactionAwareDataSourceProxy transactionAwareDataSourceProxy() {
		TransactionAwareDataSourceProxy proxy = new TransactionAwareDataSourceProxy(datasource());
		return proxy;
	}

	@Bean
	PlatformTransactionManager platformTransactionManager() {
		return new DataSourceTransactionManager(transactionAwareDataSourceProxy());
	}

	@Bean
	Jdbi jdbiBean() {
		Jdbi jdbi = Jdbi.create(transactionAwareDataSourceProxy());
		jdbi.installPlugin(new SqlObjectPlugin());
		return jdbi;
	}
}
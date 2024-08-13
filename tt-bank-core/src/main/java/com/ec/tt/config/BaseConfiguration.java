package com.ec.tt.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = { "com.ec.tt" })
@EnableJpaRepositories(basePackages = { "com.ec.tt" })
@ComponentScan(basePackages = { "com.ec.tt" })
@EnableTransactionManagement
public class BaseConfiguration {
}

package com.kkoemets.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

import static org.springframework.boot.jdbc.DataSourceBuilder.create;

@Configuration
@PropertySource({"classpath:integration-test.properties"})
public class TestCoreConnectionConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return create().build();
    }

}

package com.kkoemets.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;


@SpringBootTest
@ContextConfiguration("classpath*:integration-test.xml")
@PropertySource({"classpath:integration-test.properties"})
@Configuration
public abstract class CoreIntegTest {

    @Autowired
    protected DataSource dataSource;

}

package com.kkoemets.core;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@ContextConfiguration("classpath*:integration-test.xml")
public abstract class CoreIntegTest {
}

package com.kkoemets.core;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@ContextConfiguration("classpath*:integration-test.xml")
public abstract class CoreIntegTest {
}

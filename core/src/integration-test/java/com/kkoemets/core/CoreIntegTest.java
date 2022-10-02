package com.kkoemets.core;

import org.junit.jupiter.api.BeforeEach;
import org.slf4j.MDC;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@ContextConfiguration("classpath*:integration-test.xml")
public abstract class CoreIntegTest {

    @BeforeEach
    public void beforeEach() {
        MDC.put("test", "987654321");
    }

}

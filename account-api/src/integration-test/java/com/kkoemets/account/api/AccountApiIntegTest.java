package com.kkoemets.account.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.MDC;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public abstract class AccountApiIntegTest {

    @BeforeEach
    public void beforeEach(TestInfo info) {
        MDC.put("requestId", "%s.%s"
                .formatted(Arrays.stream(this.getClass().getName().split("\\."))
                                .reduce((a, b) -> b)
                                .stream()
                                .findFirst()
                                .orElseThrow(),
                        info.getTestMethod()
                                .orElseThrow()
                                .getName()));
    }

}

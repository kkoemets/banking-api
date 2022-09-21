package com.kkoemets.account.api;

import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;
import static java.util.Collections.emptyMap;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.messaging.MessageHeaders.CONTENT_TYPE;


@SpringBootTest
@ContextConfiguration("classpath*:rest-test.xml")
public abstract class AccountApiRestTest {
    private static final Logger log = getLogger(AccountApiRestTest.class);
    private static final String HOST = "http://localhost:8080";

    protected String post(String url) {
        return post(url, emptyMap());
    }

    protected String post(String url, Map<String, Object> json) {
        log.info("Request to-{}", url);
        String response = WebClient.create()
                .post()
                .uri("%s%s".formatted(HOST, url))
                .body(Mono.just(json), Map.class)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info("Response-{}", response);
        return response;
    }

}

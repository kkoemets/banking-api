package com.kkoemets.account.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkoemets.account.api.AccountApi;
import com.kkoemets.account.api.AccountApiIntegTest;
import com.kkoemets.account.api.controller.json.request.CreateAccountJson;
import com.kkoemets.account.api.controller.json.request.CreateTransactionJson;
import com.kkoemets.domain.id.AccountId;
import org.assertj.core.api.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.kkoemets.account.api.utils.UrlUtil.replace;
import static java.util.Objects.nonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@SpringBootTest(classes = AccountApi.class, webEnvironment = RANDOM_PORT)
abstract class AccountControllerIntegTest extends AccountApiIntegTest {

    @Autowired
    private ObjectMapper om;
    @LocalServerPort
    private int port;
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    protected void assertFieldValidationError(Supplier<ResponseEntity<String>> apiCall, String currencies, String message) {
        assertThat(findFieldValidationErrors(apiCall.get(), currencies)).contains(message);
    }

    protected ResponseEntity<String> postCreateAccount(CreateAccountJson json) {
        return post("/api/v1/accounts", json);
    }

    protected ResponseEntity<String> createTransaction(AccountId accountId, CreateTransactionJson json) {
        return post(replace("/api/v1/accounts/{accountId}/transactions", accountId.getValue().toString()), json);
    }

    protected void assertThatHttp200(Supplier<ResponseEntity<?>> apiCall) {
        assertThat(apiCall.get().getStatusCode()).is(new Condition<>(s -> s == OK, "HTTP 200"));
    }

    protected void assertThatHttp404(Supplier<ResponseEntity<?>> apiCall) {
        ResponseEntity<?> response = apiCall.get();
        assertThat(response.getStatusCode()).is(new Condition<>(s -> s == NOT_FOUND, "HTTP 404"));
    }

    private ResponseEntity<String> post(String url, Object json) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(APPLICATION_JSON);

            return restTemplate.exchange(formatUrl(url),
                    HttpMethod.POST, new HttpEntity<>(om.writeValueAsString(json), headers), String.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected ResponseEntity<String> get(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);

        return restTemplate.exchange(formatUrl(url),
                HttpMethod.GET, new HttpEntity<>(headers), String.class);
    }

    private String formatUrl(String x) {
        return "http://localhost:%d%s".formatted(port, x);
    }

    private Map<String, Object> extractBody(ResponseEntity<String> response) {
        try {
            return om.readValue(response.getBody(), new TypeReference<>() {
                @Override
                public Type getType() {
                    return super.getType();
                }
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> findFieldValidationErrors(ResponseEntity<String> response, String fieldName) {
        return getErrorMap(response)
                .stream()
                .filter(e -> nonNull(e.get(fieldName)))
                .findFirst()
                .orElseThrow()
                .get(fieldName);
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, List<String>>> getErrorMap(ResponseEntity<String> response) {
        return (List<Map<String, List<String>>>) extractBody(response).get("error");
    }

}

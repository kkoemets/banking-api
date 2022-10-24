package com.kkoemets.account.api;

import com.kkoemets.account.api.bl.account.CreateAccount;
import com.kkoemets.account.api.bl.account.CreateAccountDto;
import com.kkoemets.account.api.bl.transaction.CreateNewTransaction;
import com.kkoemets.account.api.bl.transaction.CreateNewTransactionDto;
import com.kkoemets.account.api.bl.transaction.CreateNewTransactionResultDto;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.codes.TransactionDirection;
import com.kkoemets.domain.id.AccountId;
import com.kkoemets.domain.id.CustomerId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import static com.kkoemets.domain.codes.CountryIsoCode2.EE;
import static com.kkoemets.domain.codes.Currency.EUR;

@SpringBootTest
public abstract class AccountApiIntegTest {

    @Autowired
    private CreateAccount createAccount;
    @Autowired
    private CreateNewTransaction createNewTransaction;
    @Autowired
    private DataSource dataSource;

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

    protected AccountId createAccount() {
        return createAccount.create(new CreateAccountDto(new CustomerId(1L), List.of(EUR), EE)).accountId();
    }

    protected CreateNewTransactionResultDto createNewTransaction(Money amount, TransactionDirection direction) {
        return createNewTransaction.create(
                new CreateNewTransactionDto(createAccount(), amount, "test_description", direction));
    }

    protected void runQuery(String sql) {
        try (Statement statement = dataSource.getConnection().createStatement()) {
            statement.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

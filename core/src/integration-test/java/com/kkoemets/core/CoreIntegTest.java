package com.kkoemets.core;

import com.kkoemets.core.bl.account.CreateAccount;
import com.kkoemets.core.bl.account.CreateAccountDto;
import com.kkoemets.core.bl.transaction.CreateNewTransaction;
import com.kkoemets.core.bl.transaction.CreateNewTransactionDto;
import com.kkoemets.core.service.AccountService;
import com.kkoemets.core.service.TransactionService;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.codes.TransactionDirection;
import com.kkoemets.domain.id.AccountId;
import com.kkoemets.domain.id.CustomerId;
import com.kkoemets.domain.id.TransactionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import static com.kkoemets.domain.codes.CountryIsoCode2.EE;
import static com.kkoemets.domain.codes.Currency.EUR;
import static com.kkoemets.domain.codes.TransactionDirection.IN;
import static com.kkoemets.domain.codes.TransactionDirection.OUT;
import static java.math.BigDecimal.TEN;


@SpringBootTest
@ContextConfiguration("classpath*:integration-test.xml")
public abstract class CoreIntegTest {
    private static final Money TEN_EUROS = new Money(TEN, EUR);

    @Autowired
    private AccountService accounts;
    @Autowired
    private TransactionService transactions;
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
        AccountId accountId = accounts.getNextSeqValue();
        createAccount.create(new CreateAccountDto(accountId, new CustomerId(1L), List.of(EUR), EE));
        return accountId;
    }

    protected TransactionId createNewIncomingTransaction() {
        return createNewTransaction(TEN_EUROS, IN);
    }

    protected TransactionId createNewOutgoingTransaction() {
        return createNewTransaction(TEN_EUROS, OUT);
    }

    protected TransactionId createNewTransaction(Money amount, TransactionDirection direction) {
        TransactionId transactionId = transactions.getNextSeqValue();
        createNewTransaction.create(new CreateNewTransactionDto(transactionId,
                createAccount(), amount, "test_description", direction));
        return transactionId;
    }

    protected void runQuery(String sql) {
        try (Statement statement = dataSource.getConnection().createStatement()) {
            statement.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

package com.kkoemets.account.api.bl.balance;

import com.kkoemets.account.api.exception.BadRequestException;
import com.kkoemets.core.amqp.message.BalanceChangedMessage;
import com.kkoemets.core.amqp.queue.BalanceChangedQueue;
import com.kkoemets.core.service.BalanceService;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.id.AccountId;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.kkoemets.account.api.exception.AccountApiExceptionMessage.ERROR_BALANCE_IN_CURRENCY_DOES_NOT_EXIST;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class IncreaseBalance {
    private static final Logger log = getLogger(IncreaseBalance.class);

    @Autowired
    private BalanceService balances;
    @Autowired
    private BalanceChangedQueue balanceChangedQueue;

    public Money increase(AccountId accountId, Money amount) {
        log.info("Increasing balance");

        Money currentBalance = balances.findBalance(accountId, amount.currency())
                .orElseThrow(() -> new BadRequestException(ERROR_BALANCE_IN_CURRENCY_DOES_NOT_EXIST));

        Money balance = balances.increase(accountId, amount);

        balanceChangedQueue.send(new BalanceChangedMessage(accountId, currentBalance.amount(), balance.amount(),
                balance.currency()));

        return balance;
    }

}

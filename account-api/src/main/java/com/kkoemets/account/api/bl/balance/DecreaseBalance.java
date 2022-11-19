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
import static com.kkoemets.account.api.exception.AccountApiExceptionMessage.ERROR_NOT_ENOUGH_FUNDS;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class DecreaseBalance {
    private static final Logger log = getLogger(DecreaseBalance.class);

    @Autowired
    private BalanceService balances;
    @Autowired
    private BalanceChangedQueue balanceChangedQueue;


    public Money decrease(AccountId accountId, Money amount) {
        log.info("Decreasing balance");

        Money currentBalance = balances.findBalance(accountId, amount.currency())
                .map(balance -> {
                    if (balance.lessThan(amount)) {
                        log.warn("Not enough funds");
                        throw new BadRequestException(ERROR_NOT_ENOUGH_FUNDS);
                    }

                    return balance;
                })
                .orElseThrow(() -> {
                    log.warn("No balance in currency");
                    throw new BadRequestException(ERROR_BALANCE_IN_CURRENCY_DOES_NOT_EXIST);
                });

        Money balance = balances.decrease(accountId, amount);

        balanceChangedQueue.send(new BalanceChangedMessage(accountId, currentBalance.amount(), balance.amount(),
                balance.currency()));

        return balance;
    }

}

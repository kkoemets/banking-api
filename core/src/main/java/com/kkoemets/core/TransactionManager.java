package com.kkoemets.core;

import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import java.sql.SQLException;

import static org.slf4j.LoggerFactory.getLogger;

public class TransactionManager extends DataSourceTransactionManager {
    private static final Logger log = getLogger(TransactionManager.class);

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        log.info("Initializing transaction");
        try {
            getDataSource()
                    .getConnection()
                    .prepareStatement("""
                            SELECT set_config("session_id"), %s, false)
                            """.formatted(MDC.get("requestId")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        super.doBegin(transaction, definition);
    }

}

package com.kkoemets.core;

import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.slf4j.LoggerFactory.getLogger;

public class TransactionManager extends DataSourceTransactionManager {
    private static final Logger log = getLogger(TransactionManager.class);

    @Override
    protected void prepareTransactionalConnection(Connection con, TransactionDefinition definition)
            throws SQLException {
        log.info("Setting app.session_id");

        try (Statement stmt = con.createStatement()) {
            stmt.execute("select set_config('app.session_id', '%s', false)".formatted(MDC.get("requestId")));
        }

        super.prepareTransactionalConnection(con, definition);
    }

}

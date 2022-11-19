package com.kkoemets.core.amqp.message;


import com.kkoemets.domain.id.TransactionId;

public class TransactionCreatedMessage implements AmqpMessage {
    private TransactionId transactionId;

    public TransactionCreatedMessage() {
    }

    public TransactionCreatedMessage(TransactionId transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionId getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(TransactionId transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "TransactionCreatedMessage{" +
                "transactionId=" + transactionId +
                '}';
    }

}

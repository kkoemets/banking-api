package com.kkoemets.account.api.controller.json.request;

import javax.validation.constraints.*;

import static com.kkoemets.account.api.controller.FieldValidation.*;
import static com.kkoemets.account.api.controller.FieldValidation.Regex.TRANSACTION_DIRECTION;
import static com.kkoemets.account.api.controller.ValidationMessage.*;

public class CreateTransactionJson {
    @NotNull(message = FIELD_MANDATORY)
    @Min(value = MIN_ACCOUNT_ID, message = FIELD_INVALID_VALUE)
    private Long accountId;
    @NotBlank(message = FIELD_MANDATORY)
    @Pattern(regexp = Regex.TRANSACTION_AMOUNT, message = FIELD_INVALID_VALUE)
    private String amount;
    @NotBlank(message = FIELD_MANDATORY)
    @Pattern(regexp = Regex.CURRENCY, message = FIELD_INVALID_VALUE)
    private String currency;
    @NotBlank(message = FIELD_MANDATORY)
    @Pattern(regexp = TRANSACTION_DIRECTION, message = FIELD_INVALID_VALUE)
    private String direction;
    @NotBlank(message = FIELD_MANDATORY)
    @Size(max = DESCRIPTION_LENGTH, message = FIELD_INVALID_TOO_LONG)
    private String description;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

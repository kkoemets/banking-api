package com.kkoemets.account.api.controller.json.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.kkoemets.account.api.controller.FieldValidation.DESCRIPTION_LENGTH;
import static com.kkoemets.account.api.controller.FieldValidation.Regex;
import static com.kkoemets.account.api.controller.ValidationMessage.*;

public class CreateTransactionJson {
    @NotBlank(message = FIELD_MANDATORY)
    @Pattern(regexp = Regex.TRANSACTION_AMOUNT, message = FIELD_INVALID_VALUE)
    @Pattern(regexp = Regex.AMOUNT_MORE_THAN_ZERO, message = FIELD_MUST_BE_MORE_THAN_ZERO)
    private String amount;
    @NotBlank(message = FIELD_MANDATORY)
    @Pattern(regexp = Regex.CURRENCY, message = FIELD_INVALID_VALUE)
    private String currency;
    @NotBlank(message = FIELD_MANDATORY)
    @Pattern(regexp = Regex.TRANSACTION_DIRECTION, message = FIELD_INVALID_VALUE)
    private String direction;
    @NotBlank(message = FIELD_MANDATORY)
    @Size(max = DESCRIPTION_LENGTH, message = FIELD_INVALID_TOO_LONG)
    private String description;

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

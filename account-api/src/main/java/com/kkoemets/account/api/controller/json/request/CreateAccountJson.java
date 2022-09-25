package com.kkoemets.account.api.controller.json.request;

import com.kkoemets.account.api.controller.ListValueValidation;

import javax.validation.constraints.*;
import java.util.List;

import static com.kkoemets.account.api.controller.FieldValidation.*;
import static com.kkoemets.account.api.controller.ValidationMessage.FIELD_INVALID_VALUE;
import static com.kkoemets.account.api.controller.ValidationMessage.FIELD_MANDATORY;

public class CreateAccountJson {

    @NotNull(message = FIELD_MANDATORY)
    @Min(value = MIN_CUSTOMER_ID, message = FIELD_INVALID_VALUE)
    private Long customerId;
    @NotBlank(message = FIELD_MANDATORY)
    @Pattern(regexp = COUNTRY_CODE_REGEX, message = FIELD_INVALID_VALUE)
    private String countryCode;
    @NotEmpty(message = FIELD_MANDATORY)
    @Size(max = MAX_CURRENCIES)
    @ListValueValidation(message = FIELD_INVALID_VALUE, regexp = CURRENCY_REGEX)
    private List<String> currencies;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<String> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<String> currencies) {
        this.currencies = currencies;
    }

}

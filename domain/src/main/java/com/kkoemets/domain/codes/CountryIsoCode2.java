package com.kkoemets.domain.codes;

import static org.apache.commons.lang3.StringUtils.isAlpha;

public class CountryIsoCode2 extends StringValue {
    private static final int COUNTY_CODE_LENGTH = 2;
    public static final CountryIsoCode2 EE = create("EE");

    private CountryIsoCode2(String value) {
        super(value);
    }

    public static CountryIsoCode2 create(String countryCode) {
        if (!isAlpha(countryCode) || countryCode.length() != COUNTY_CODE_LENGTH) {
            throw new IllegalArgumentException("Invalid country code-" + countryCode);
        }

        return new CountryIsoCode2(countryCode.toUpperCase());
    }

}

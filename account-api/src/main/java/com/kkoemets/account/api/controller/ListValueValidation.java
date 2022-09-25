package com.kkoemets.account.api.controller;

import org.springframework.messaging.handler.annotation.Payload;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import static java.util.Objects.nonNull;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ListValuesValidator.class)
public @interface ListValueValidation {

    String message();

    String regexp();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

class ListValuesValidator implements ConstraintValidator<ListValueValidation, List<String>> {

    private String regexp;

    @Override
    public void initialize(ListValueValidation listValueValidation) {
        regexp = listValueValidation.regexp();
    }

    @Override
    public boolean isValid(List<String> objects, ConstraintValidatorContext context) {
        return nonNull(objects) && objects.stream().allMatch(s -> nonNull(s) && s.matches(regexp));
    }

}

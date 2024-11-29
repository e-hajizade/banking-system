package com.example.banking.entity.validator;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinBalanceValidator.class)
public @interface MinBalance {

	String message() default "Balance must be at least {value}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
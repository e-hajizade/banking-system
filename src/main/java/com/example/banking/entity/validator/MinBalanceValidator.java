package com.example.banking.entity.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class MinBalanceValidator implements ConstraintValidator<MinBalance, Double> {

	@Value("${app.transaction.minBalance}")
	private double minBalance;

	@Override
	public boolean isValid(Double value, ConstraintValidatorContext context) {

		if (value > 0 && value >= minBalance) {
			return true;
		} else {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()
					.replace("{value}", String.valueOf(minBalance)))
					.addConstraintViolation();
			return false;
		}
	}
}

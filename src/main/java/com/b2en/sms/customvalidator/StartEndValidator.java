package com.b2en.sms.customvalidator;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

public class StartEndValidator implements ConstraintValidator<StartEndValid, Object> {

	private String startDateStr;
	private String endDateStr;
	
	@Override
	public void initialize(StartEndValid constraintAnnotation) {
		startDateStr = constraintAnnotation.start();
		endDateStr = constraintAnnotation.end();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		final Object startObj;
		final Object endObj;
		long calDate = 0;
		try {
			startObj = BeanUtils.getProperty(value, startDateStr);
			endObj = BeanUtils.getProperty(value, endDateStr);
			
			Date startDate = sdf.parse((String)startObj);
			Date endDate = sdf.parse((String)endObj);
			
			calDate = endDate.getTime() - startDate.getTime();
		} catch (Exception e) {
			return false;
		}

		return (calDate>0);
	}

}

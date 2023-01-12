package com.ilbolan.pitoswebproject.forms.annotations.validators;

import com.ilbolan.pitoswebproject.forms.annotations.OrderAmountConstrains;
import com.ilbolan.pitoswebproject.models.beans.Order;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

/**
 * Validator class for OrderAmountConstrains annotation
 */
public class OrderAmountValidator implements ConstraintValidator<OrderAmountConstrains, List<Order>> {

    @Override
    public void initialize(OrderAmountConstrains constraintAnnotation) {}

    @Override
    public boolean isValid(List<Order> orders, ConstraintValidatorContext constraintValidatorContext) {
        int totalCnt = 0;
        for(var order : orders){

            // each pie quantity between 0-100
            if(order.getQuantity() > 100 || order.getQuantity() < 0)
                return false;

            totalCnt += order.getQuantity();
        }
        // at least one pie
        return totalCnt != 0;
    }
}

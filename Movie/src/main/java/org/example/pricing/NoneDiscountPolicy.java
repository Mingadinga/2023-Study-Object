package org.example.pricing;

import org.example.DiscountPolicy;
import org.example.Money;
import org.example.Screening;

public class NoneDiscountPolicy extends DiscountPolicy {
    @Override
    protected Money getDiscountAmount(Screening screening) {
        return Money.ZERO;
    }
}
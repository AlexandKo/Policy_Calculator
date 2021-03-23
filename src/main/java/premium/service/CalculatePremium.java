package premium.service;

import premium.domen.Policy;

import java.math.BigDecimal;

public interface CalculatePremium {
    BigDecimal calculate(Policy policy);
}
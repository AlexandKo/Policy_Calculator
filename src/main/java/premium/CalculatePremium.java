package premium;

import premium.domen.Policy;

import java.math.BigDecimal;

public interface CalculatePremium {
    BigDecimal calculate(Policy policy);
}
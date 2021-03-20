package premium.service;

import java.math.BigDecimal;

public enum Coefficient {
    FIRE_COEFFICIENT(new BigDecimal("0.014")),
    FIRE_COEFFICIENT_EXTRA(new BigDecimal("0.024")),
    THEFT_COEFFICIENT(new BigDecimal("0.11")),
    THEFT_COEFFICIENT_EXTRA(new BigDecimal("0.05"));

    private final BigDecimal coefficient;

    Coefficient(BigDecimal coefficient) {
        this.coefficient = coefficient;
    }

    public BigDecimal getCoefficient() {
        return this.coefficient;
    }
}
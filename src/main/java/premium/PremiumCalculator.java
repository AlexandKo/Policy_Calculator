package premium;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import premium.domen.Policy;
import premium.service.CalculatePremium;

import java.math.BigDecimal;

@Component
public class PremiumCalculator {
    @Autowired
    private CalculatePremium calculatePremium;

    public BigDecimal calculate(Policy policy) {
        return calculatePremium.calculate(policy);
    }
}
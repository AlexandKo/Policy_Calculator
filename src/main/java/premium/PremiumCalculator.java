package premium;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import premium.domen.Policy;
import premium.service.CalculatePremiumService;

import java.math.BigDecimal;

@Component
public class PremiumCalculator {
    @Autowired
    private CalculatePremiumService calculatePremiumService;

    public BigDecimal calculate(Policy policy) {
        return calculatePremiumService.premiumCalculate(policy);
    }
}
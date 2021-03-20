package premium.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import premium.domen.Policy;

import java.math.BigDecimal;

@Service
public class CalculatePremiumService {
    @Autowired
    private FireRiskCalculateService fireRiskCalculateService;
    @Autowired
    private TheftRiskCalculateService theftRiskCalculateService;

    public BigDecimal premiumCalculate(Policy policy) {
        if (policy.getPolicyObjectList().size() == 0) {
            return new BigDecimal("0.00");
        }
        BigDecimal firePremium = fireRiskCalculateService.calculateFireRisk(policy);
        BigDecimal theftPremium = theftRiskCalculateService.calculateTheftRisk(policy);

        return firePremium.add(theftPremium);
    }
}
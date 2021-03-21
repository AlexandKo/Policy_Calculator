package premium.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import premium.domen.PolicyObject;
import premium.domen.PolicySubObject;
import premium.domen.Risk;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class FireRiskCalculateService {
    @Autowired
    private PolicySubObjectsRisk policySubObjectsRisk;
    @Autowired
    private CalculateRiskSum calculateRiskSum;

    protected BigDecimal calculateFireRisk(PolicyObject policyObject) {
        List<PolicySubObject> fireRiskSubObject = policySubObjectsRisk.getRiskSubObjects(policyObject, Risk.FIRE);
        BigDecimal fireRiskSum = calculateRiskSum.calculateRiskSum(fireRiskSubObject);

        BigDecimal fireRiskPremium;
        if (fireRiskSum.compareTo(new BigDecimal("100.00")) <= 0) {
            fireRiskPremium = fireRiskSum.multiply(Coefficient.FIRE_COEFFICIENT.getCoefficient())
                    .setScale(2, RoundingMode.HALF_UP);
        } else {
            fireRiskPremium = fireRiskSum.multiply(Coefficient.FIRE_COEFFICIENT_EXTRA.getCoefficient())
                    .setScale(2, RoundingMode.HALF_UP);
        }
        return fireRiskPremium;
    }
}
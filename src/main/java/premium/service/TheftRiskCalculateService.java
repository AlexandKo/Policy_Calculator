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
public class TheftRiskCalculateService {
    @Autowired
    private PolicySubObjectsRisk policySubObjectsRisk;
    @Autowired
    private CalculateRiskSum calculateRiskSum;

    protected BigDecimal calculateTheftRisk(PolicyObject policyObject) {
        List<PolicySubObject> theftRiskSubObject = policySubObjectsRisk.getRiskSubObjects(policyObject, Risk.THEFT);
        BigDecimal theftRiskSum = calculateRiskSum.calculateRiskSum(theftRiskSubObject);

        BigDecimal theftRiskPremium;
        if (theftRiskSum.compareTo(new BigDecimal("15.00")) < 0) {
            theftRiskPremium = theftRiskSum.multiply(Coefficient.THEFT_COEFFICIENT.getCoefficient())
                    .setScale(2, RoundingMode.HALF_UP);
        } else {
            theftRiskPremium = theftRiskSum.multiply(Coefficient.THEFT_COEFFICIENT_EXTRA.getCoefficient())
                    .setScale(2, RoundingMode.HALF_UP);
        }
        return theftRiskPremium;
    }
}
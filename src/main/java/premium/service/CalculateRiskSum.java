package premium.service;

import org.springframework.stereotype.Component;
import premium.domen.PolicySubObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
class CalculateRiskSum {

    protected BigDecimal calculateRiskSum(List<PolicySubObject> subObjects) {
        return subObjects.stream()
                .map(PolicySubObject::getInsuredSum)
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0.00"))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
package premium.service;

import org.springframework.stereotype.Service;
import premium.domen.Policy;
import premium.domen.PolicyObject;
import premium.domen.PolicySubObject;
import premium.domen.Risk;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class TheftRiskCalculateService {
    private List<PolicySubObject> theftRiskSubObject = new ArrayList<>();

    protected BigDecimal calculateTheftRisk(Policy policy) {
        getTheftRiskSubObjects(policy);
        BigDecimal theftRiskSum = getTheftRiskSum().setScale(2, RoundingMode.HALF_UP);

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

    private void getTheftRiskSubObjects(Policy policy) {
        List<PolicyObject> policyObjects = policy.getPolicyObjectList();
        AtomicReference<List<PolicySubObject>> atomicFireRiskSubObjects = new AtomicReference<>();

        policyObjects.forEach(p -> atomicFireRiskSubObjects.set(p.getSubObjectList()
                .stream()
                .filter(s -> s.getRisk() == Risk.THEFT)
                .collect(Collectors.toList())));
        theftRiskSubObject = atomicFireRiskSubObjects.get();
    }

    private BigDecimal getTheftRiskSum() {
        return theftRiskSubObject.stream()
                .map(PolicySubObject::getInsuredSum)
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0.00"));
    }
}
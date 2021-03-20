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
public class FireRiskCalculateService {
    private List<PolicySubObject> fireRiskSubObject = new ArrayList<>();

    protected BigDecimal calculateFireRisk(Policy policy) {
        getFireRiskSubObjects(policy);
        BigDecimal fireRiskSum = getFireRiskSum().setScale(2, RoundingMode.HALF_UP);

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

    private void getFireRiskSubObjects(Policy policy) {
        List<PolicyObject> policyObjects = policy.getPolicyObjectList();
        AtomicReference<List<PolicySubObject>> atomicFireRiskSubObjects = new AtomicReference<>();

        policyObjects.forEach(p -> atomicFireRiskSubObjects.set(p.getSubObjectList()
                .stream()
                .filter(s -> s.getRisk() == Risk.FIRE)
                .collect(Collectors.toList())));
        fireRiskSubObject = atomicFireRiskSubObjects.get();
    }

    private BigDecimal getFireRiskSum() {
        return fireRiskSubObject.stream()
                .map(PolicySubObject::getInsuredSum)
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0.00"));
    }
}
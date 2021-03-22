package premium.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import premium.CalculatePremium;
import premium.domen.Policy;
import premium.domen.PolicyObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
class CalculatePremiumService implements CalculatePremium {
    @Autowired
    private FireRiskCalculateService fireRiskCalculateService;
    @Autowired
    private TheftRiskCalculateService theftRiskCalculateService;

    @Override
    public BigDecimal calculate(Policy policy) {
        if (isPolicyObjectNoLoaded(policy)) {
            return new BigDecimal("0.00");
        }

        List<PolicyObject> policyObjects = policy.getPolicyObjectList();
        AtomicReference<BigDecimal> premium = new AtomicReference<>(new BigDecimal("0.00"));

        policyObjects.forEach(policyObject -> {
            BigDecimal firePremium = fireRiskCalculateService.calculateFireRisk(policyObject);
            BigDecimal theftPremium = theftRiskCalculateService.calculateTheftRisk(policyObject);
            premium.updateAndGet(policyPremium -> policyPremium
                    .add(firePremium)
                    .add(theftPremium));
        });
        return premium.get();
    }

    private boolean isPolicyObjectNoLoaded(Policy policy) {
        final int NO_POLICY_OBJECT = 0;
        return policy.getPolicyObjectList().size() == NO_POLICY_OBJECT;
    }
}
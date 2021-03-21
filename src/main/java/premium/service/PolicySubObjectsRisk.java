package premium.service;

import org.springframework.stereotype.Component;
import premium.domen.PolicyObject;
import premium.domen.PolicySubObject;
import premium.domen.Risk;

import java.util.List;
import java.util.stream.Collectors;

@Component
class PolicySubObjectsRisk {

    protected List<PolicySubObject> getRiskSubObjects(PolicyObject policyObject, Risk risk) {
        List<PolicySubObject> policySubObjects = policyObject.getSubObjectList();

        return policySubObjects.stream()
                .filter(subObject -> subObject.getRisk() == risk)
                .collect(Collectors.toList());
    }
}
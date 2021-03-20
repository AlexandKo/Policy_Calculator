package premium.domen;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Policy {
    @Getter
    private final List<PolicyObject> policyObjectList = new ArrayList<>();
    @Getter
    @Setter
    private String policyNumber;
    @Getter
    @Setter
    private PolicyStatus policyStatus;

    public void addPolicyObject(PolicyObject policyObject) {
        policyObjectList.add(policyObject);
    }
}
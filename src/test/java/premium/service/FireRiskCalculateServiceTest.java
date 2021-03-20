package premium.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import premium.domen.Policy;
import premium.domen.PolicyObject;
import premium.domen.PolicySubObject;
import premium.domen.Risk;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FireRiskCalculateServiceTest {
    private final FireRiskCalculateService fireRiskCalculateService = new FireRiskCalculateService();
    private Policy policy;
    private PolicyObject policyObject;

    @BeforeEach
    public void startUp() {
        policy = new Policy();
        policyObject = new PolicyObject();
    }

    @Test
    public void fireRiskLess_100() {
        PolicySubObject policySubObject = new PolicySubObject("Item_1",
                new BigDecimal("85.55"), Risk.FIRE);

        policyObject.addSubObject(policySubObject);
        policy.addPolicyObject(policyObject);

        BigDecimal fireRiskPremium = fireRiskCalculateService.calculateFireRisk(policy);

        assertEquals(new BigDecimal("1.20"), fireRiskPremium);
    }

    @Test
    public void fireRiskEqual_100() {
        PolicySubObject policySubObject = new PolicySubObject("Item_1",
                new BigDecimal("100.00"), Risk.FIRE);

        policyObject.addSubObject(policySubObject);
        policy.addPolicyObject(policyObject);

        BigDecimal fireRiskPremium = fireRiskCalculateService.calculateFireRisk(policy);

        assertEquals(new BigDecimal("1.40"), fireRiskPremium);
    }

    @Test
    public void fireRiskMore_100() {
        PolicySubObject policySubObject = new PolicySubObject("Item_1",
                new BigDecimal("150.55"), Risk.FIRE);

        policyObject.addSubObject(policySubObject);
        policy.addPolicyObject(policyObject);

        BigDecimal fireRiskPremium = fireRiskCalculateService.calculateFireRisk(policy);

        assertEquals(new BigDecimal("3.61"), fireRiskPremium);
    }

    @Test
    public void noFireRisk() {
        PolicySubObject policySubObject = new PolicySubObject("Item_1",
                new BigDecimal("150.55"), Risk.THEFT);

        policyObject.addSubObject(policySubObject);
        policy.addPolicyObject(policyObject);

        BigDecimal fireRiskPremium = fireRiskCalculateService.calculateFireRisk(policy);

        assertEquals(new BigDecimal("0.00"), fireRiskPremium);
    }

    @Test
    public void twoFireSubObjects_less_100() {
        PolicySubObject policySubObject_1 = new PolicySubObject("Item_1",
                new BigDecimal("35.48"), Risk.FIRE);

        PolicySubObject policySubObject_2 = new PolicySubObject("Item_2",
                new BigDecimal("11.19"), Risk.FIRE);

        policyObject.addSubObject(policySubObject_1);
        policyObject.addSubObject(policySubObject_2);
        policy.addPolicyObject(policyObject);

        BigDecimal fireRiskPremium = fireRiskCalculateService.calculateFireRisk(policy);

        assertEquals(new BigDecimal("0.65"), fireRiskPremium);
    }

    @Test
    public void twoFireSubObjects_equal_100() {
        PolicySubObject policySubObject_1 = new PolicySubObject("Item_1",
                new BigDecimal("45.15"), Risk.FIRE);

        PolicySubObject policySubObject_2 = new PolicySubObject("Item_2",
                new BigDecimal("54.85"), Risk.FIRE);

        policyObject.addSubObject(policySubObject_1);
        policyObject.addSubObject(policySubObject_2);
        policy.addPolicyObject(policyObject);

        BigDecimal fireRiskPremium = fireRiskCalculateService.calculateFireRisk(policy);

        assertEquals(new BigDecimal("1.40"), fireRiskPremium);
    }

    @Test
    public void twoFireSubObjects_more_100() {
        PolicySubObject policySubObject_1 = new PolicySubObject("Item_1",
                new BigDecimal("103.93"), Risk.FIRE);

        PolicySubObject policySubObject_2 = new PolicySubObject("Item_2",
                new BigDecimal("289.44"), Risk.FIRE);

        policyObject.addSubObject(policySubObject_1);
        policyObject.addSubObject(policySubObject_2);
        policy.addPolicyObject(policyObject);

        BigDecimal fireRiskPremium = fireRiskCalculateService.calculateFireRisk(policy);

        assertEquals(new BigDecimal("9.44"), fireRiskPremium);
    }

    @Test
    public void twoFireSubObjects_more_100_SumRound() {
        PolicySubObject policySubObject_1 = new PolicySubObject("Item_1",
                new BigDecimal("103.9399"), Risk.FIRE);

        PolicySubObject policySubObject_2 = new PolicySubObject("Item_2",
                new BigDecimal("289.44874"), Risk.FIRE);

        policyObject.addSubObject(policySubObject_1);
        policyObject.addSubObject(policySubObject_2);
        policy.addPolicyObject(policyObject);

        BigDecimal fireRiskPremium = fireRiskCalculateService.calculateFireRisk(policy);

        assertEquals(new BigDecimal("9.44"), fireRiskPremium);
    }
}
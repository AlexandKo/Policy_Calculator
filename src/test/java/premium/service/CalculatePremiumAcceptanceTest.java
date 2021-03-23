package premium.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import premium.domen.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(classes = {CalculatePremiumService.class, PolicySubObjectsRisk.class, CalculateRiskSum.class,
        FireRiskCalculateService.class, TheftRiskCalculateService.class})
public class CalculatePremiumAcceptanceTest {
    @Autowired
    private CalculatePremium calculatePremium;
    @Autowired
    private ApplicationContext context;
    private Policy policy;
    private PolicyObject policyObject;

    @BeforeEach
    public void startUp() {
        policy = new Policy();
        policyObject = new PolicyObject();
    }

    @Test
    public void contextStarUp() {
        assertNotNull(context);
    }

    @Test
    public void test_OneFireRisk_OneTheftRisk_1() {
        PolicySubObject policySubObject_1 = new PolicySubObject("Item_1",
                new BigDecimal("100.00"), Risk.FIRE);
        PolicySubObject policySubObject_2 = new PolicySubObject("Item_2",
                new BigDecimal("8.00"), Risk.THEFT);

        policyObject.addSubObject(policySubObject_1);
        policyObject.addSubObject(policySubObject_2);
        policy.addPolicyObject(policyObject);

        BigDecimal premium = calculatePremium.calculate(policy);

        assertEquals(new BigDecimal("2.28"), premium);
    }

    @Test
    public void test_OneFireRisk_OneTheftRisk_2() {
        PolicySubObject policySubObject_1 = new PolicySubObject("Item_1",
                new BigDecimal("500.00"), Risk.FIRE);
        PolicySubObject policySubObject_2 = new PolicySubObject("Item_2",
                new BigDecimal("102.51"), Risk.THEFT);

        policyObject.addSubObject(policySubObject_1);
        policyObject.addSubObject(policySubObject_2);
        policy.addPolicyObject(policyObject);

        BigDecimal premium = calculatePremium.calculate(policy);

        assertEquals(new BigDecimal("17.13"), premium);
    }

    @Test
    public void noPolicyObject() {
        BigDecimal premium = calculatePremium.calculate(policy);

        assertEquals(new BigDecimal("0.00"), premium);
    }

    @Test
    public void noPolicySubObject() {
        policy.addPolicyObject(policyObject);
        BigDecimal premium = calculatePremium.calculate(policy);

        assertEquals(new BigDecimal("0.00"), premium);
    }

    @Test
    public void test_FireRisk_less_100() {
        policyObject.addSubObject(new PolicySubObject("Item_1", new BigDecimal("3.90"), Risk.FIRE));
        policyObject.addSubObject(new PolicySubObject("Item_2", new BigDecimal("5.10"), Risk.FIRE));
        policyObject.addSubObject(new PolicySubObject("Item_3", new BigDecimal("6.87"), Risk.FIRE));

        policy.addPolicyObject(policyObject);

        BigDecimal premium = calculatePremium.calculate(policy);

        assertEquals(new BigDecimal("0.22"), premium);
    }

    @Test
    public void test_FireRisk_more_100() {
        policyObject.addSubObject(new PolicySubObject("Item_1", new BigDecimal("98.55"), Risk.FIRE));
        policyObject.addSubObject(new PolicySubObject("Item_2", new BigDecimal("123.14"), Risk.FIRE));
        policyObject.addSubObject(new PolicySubObject("Item_3", new BigDecimal("78.98"), Risk.FIRE));

        policy.addPolicyObject(policyObject);

        BigDecimal premium = calculatePremium.calculate(policy);

        assertEquals(new BigDecimal("7.22"), premium);
    }

    @Test
    public void test_TheftRisk_less_15() {
        policyObject.addSubObject(new PolicySubObject("Item_1", new BigDecimal("1.15"), Risk.THEFT));
        policyObject.addSubObject(new PolicySubObject("Item_2", new BigDecimal("6.23"), Risk.THEFT));
        policyObject.addSubObject(new PolicySubObject("Item_3", new BigDecimal("3.10"), Risk.THEFT));

        policy.addPolicyObject(policyObject);

        BigDecimal premium = calculatePremium.calculate(policy);

        assertEquals(new BigDecimal("1.15"), premium);
    }

    @Test
    public void test_TheftRisk_more_15() {
        policyObject.addSubObject(new PolicySubObject("Item_1", new BigDecimal("5.78"), Risk.THEFT));
        policyObject.addSubObject(new PolicySubObject("Item_2", new BigDecimal("85.36"), Risk.THEFT));
        policyObject.addSubObject(new PolicySubObject("Item_3", new BigDecimal("149.44"), Risk.THEFT));

        policy.addPolicyObject(policyObject);

        BigDecimal premium = calculatePremium.calculate(policy);

        assertEquals(new BigDecimal("12.03"), premium);
    }

    @Test
    public void test_FireAndTheftRisk() {
        policyObject.addSubObject(new PolicySubObject("Item_1", new BigDecimal("98.55"), Risk.FIRE));
        policyObject.addSubObject(new PolicySubObject("Item_2", new BigDecimal("123.14"), Risk.FIRE));
        policyObject.addSubObject(new PolicySubObject("Item_3", new BigDecimal("78.98"), Risk.FIRE));
        policyObject.addSubObject(new PolicySubObject("Item_4", new BigDecimal("5.78"), Risk.THEFT));
        policyObject.addSubObject(new PolicySubObject("Item_5", new BigDecimal("85.36"), Risk.THEFT));
        policyObject.addSubObject(new PolicySubObject("Item_6", new BigDecimal("149.44"), Risk.THEFT));

        policy.addPolicyObject(policyObject);

        BigDecimal premium = calculatePremium.calculate(policy);

        assertEquals(new BigDecimal("19.25"), premium);
    }

    @Test
    public void test_FireAndTheftRisk_twoPolicies() {
        policyObject.addSubObject(new PolicySubObject("Item_1", new BigDecimal("98.55"), Risk.FIRE));
        policyObject.addSubObject(new PolicySubObject("Item_2", new BigDecimal("123.14"), Risk.FIRE));
        policyObject.addSubObject(new PolicySubObject("Item_3", new BigDecimal("78.98"), Risk.FIRE));
        policy.addPolicyObject(policyObject);

        PolicyObject nextPolicyObject = new PolicyObject();
        nextPolicyObject.addSubObject(new PolicySubObject("Item_4", new BigDecimal("5.78"), Risk.THEFT));
        nextPolicyObject.addSubObject(new PolicySubObject("Item_5", new BigDecimal("85.36"), Risk.THEFT));
        nextPolicyObject.addSubObject(new PolicySubObject("Item_6", new BigDecimal("149.44"), Risk.THEFT));
        policy.addPolicyObject(nextPolicyObject);

        BigDecimal premium = calculatePremium.calculate(policy);

        assertEquals(new BigDecimal("19.25"), premium);
    }

    @Test
    public void test_FireEmptyAndTheftRisk_twoPolicies() {
        policy.addPolicyObject(policyObject);

        PolicyObject nextPolicyObject = new PolicyObject();
        nextPolicyObject.addSubObject(new PolicySubObject("Item_4", new BigDecimal("5.78"), Risk.THEFT));
        nextPolicyObject.addSubObject(new PolicySubObject("Item_5", new BigDecimal("85.36"), Risk.THEFT));
        nextPolicyObject.addSubObject(new PolicySubObject("Item_6", new BigDecimal("149.44"), Risk.THEFT));
        policy.addPolicyObject(nextPolicyObject);

        BigDecimal premium = calculatePremium.calculate(policy);

        assertEquals(new BigDecimal("12.03"), premium);
    }

    @Test
    public void test_FireAndEmptyTheftRisk_twoPolicies() {
        policyObject.addSubObject(new PolicySubObject("Item_1", new BigDecimal("98.55"), Risk.FIRE));
        policyObject.addSubObject(new PolicySubObject("Item_2", new BigDecimal("123.14"), Risk.FIRE));
        policyObject.addSubObject(new PolicySubObject("Item_3", new BigDecimal("78.98"), Risk.FIRE));

        policy.addPolicyObject(policyObject);
        policy.addPolicyObject(new PolicyObject());
        policy.setPolicyStatus(PolicyStatus.REGISTERED);

        BigDecimal premium = calculatePremium.calculate(policy);

        assertEquals(new BigDecimal("7.22"), premium);
    }

    @Test
    public void test_OneFireRisk_OneTheftRisk_ThreePolicies() {
        policyObject.addSubObject(new PolicySubObject("Item_1", new BigDecimal("100.00"), Risk.FIRE));
        policyObject.addSubObject(new PolicySubObject("Item_2", new BigDecimal("8.00"), Risk.THEFT));
        policy.addPolicyObject(policyObject);

        PolicyObject policyObject_2 = new PolicyObject();
        policyObject_2.addSubObject(new PolicySubObject("Item_1", new BigDecimal("100.00"), Risk.FIRE));
        policyObject_2.addSubObject(new PolicySubObject("Item_2", new BigDecimal("8.00"), Risk.THEFT));
        policy.addPolicyObject(policyObject_2);

        PolicyObject policyObject_3 = new PolicyObject();
        policyObject_3.addSubObject(new PolicySubObject("Item_1", new BigDecimal("100.00"), Risk.FIRE));
        policyObject_3.addSubObject(new PolicySubObject("Item_2", new BigDecimal("8.00"), Risk.THEFT));
        policy.addPolicyObject(policyObject_3);
        policy.setPolicyStatus(PolicyStatus.APPROVED);

        BigDecimal premium = calculatePremium.calculate(policy);

        assertEquals(new BigDecimal("6.84"), premium);
    }
}
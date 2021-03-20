package premium.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import premium.domen.Policy;
import premium.domen.PolicyObject;
import premium.domen.PolicySubObject;
import premium.domen.Risk;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(classes = {CalculatePremiumService.class,
        FireRiskCalculateService.class, TheftRiskCalculateService.class})
public class CalculatePremiumServiceTest {
    @Autowired
    private CalculatePremiumService calculatePremiumService;
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
        System.out.println(context);
    }

    @Test
    public void test_1() {
        PolicySubObject policySubObject_1 = new PolicySubObject("Item_1",
                new BigDecimal("100.00"), Risk.FIRE);
        PolicySubObject policySubObject_2 = new PolicySubObject("Item_2",
                new BigDecimal("8.00"), Risk.THEFT);

        policyObject.addSubObject(policySubObject_1);
        policyObject.addSubObject(policySubObject_2);
        policy.addPolicyObject(policyObject);

        BigDecimal premium = calculatePremiumService.premiumCalculate(policy);

        assertEquals(new BigDecimal("2.28"), premium);
    }

    @Test
    public void test_2() {
        PolicySubObject policySubObject_1 = new PolicySubObject("Item_1",
                new BigDecimal("500.00"), Risk.FIRE);
        PolicySubObject policySubObject_2 = new PolicySubObject("Item_2",
                new BigDecimal("102.51"), Risk.THEFT);

        policyObject.addSubObject(policySubObject_1);
        policyObject.addSubObject(policySubObject_2);
        policy.addPolicyObject(policyObject);

        BigDecimal premium = calculatePremiumService.premiumCalculate(policy);

        assertEquals(new BigDecimal("17.13"), premium);
    }
}
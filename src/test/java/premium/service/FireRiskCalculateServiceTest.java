package premium.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import premium.domen.PolicyObject;
import premium.domen.PolicySubObject;
import premium.domen.Risk;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(classes = {FireRiskCalculateService.class, PolicySubObjectsRisk.class, CalculateRiskSum.class})
class FireRiskCalculateServiceTest {
    @Autowired
    private FireRiskCalculateService fireRiskCalculateService;
    @Autowired
    private ApplicationContext context;
    private PolicyObject policyObject;

    @BeforeEach
    public void startUp() {
        policyObject = new PolicyObject();
    }

    @Test
    public void contextStarUp() {
        assertNotNull(context);
    }

    @Test
    public void fireRiskLess_100() {
        PolicySubObject policySubObject = new PolicySubObject("Item_1",
                new BigDecimal("85.55"), Risk.FIRE);

        policyObject.addSubObject(policySubObject);

        BigDecimal fireRiskPremium = fireRiskCalculateService.calculateFireRisk(policyObject);

        assertEquals(new BigDecimal("1.20"), fireRiskPremium);
    }

    @Test
    public void fireRiskEqual_100() {
        PolicySubObject policySubObject = new PolicySubObject("Item_1",
                new BigDecimal("100.00"), Risk.FIRE);

        policyObject.addSubObject(policySubObject);

        BigDecimal fireRiskPremium = fireRiskCalculateService.calculateFireRisk(policyObject);

        assertEquals(new BigDecimal("1.40"), fireRiskPremium);
    }

    @Test
    public void fireRiskMore_100() {
        PolicySubObject policySubObject = new PolicySubObject("Item_1",
                new BigDecimal("150.55"), Risk.FIRE);

        policyObject.addSubObject(policySubObject);

        BigDecimal fireRiskPremium = fireRiskCalculateService.calculateFireRisk(policyObject);

        assertEquals(new BigDecimal("3.61"), fireRiskPremium);
    }

    @Test
    public void noFireRisk() {
        PolicySubObject policySubObject = new PolicySubObject("Item_1",
                new BigDecimal("150.55"), Risk.THEFT);

        policyObject.addSubObject(policySubObject);

        BigDecimal fireRiskPremium = fireRiskCalculateService.calculateFireRisk(policyObject);

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

        BigDecimal fireRiskPremium = fireRiskCalculateService.calculateFireRisk(policyObject);

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

        BigDecimal fireRiskPremium = fireRiskCalculateService.calculateFireRisk(policyObject);

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

        BigDecimal fireRiskPremium = fireRiskCalculateService.calculateFireRisk(policyObject);

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

        BigDecimal fireRiskPremium = fireRiskCalculateService.calculateFireRisk(policyObject);

        assertEquals(new BigDecimal("9.44"), fireRiskPremium);
    }
}
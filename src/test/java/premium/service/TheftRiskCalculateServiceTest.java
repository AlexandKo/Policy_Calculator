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
@SpringJUnitConfig(classes = {TheftRiskCalculateService.class, PolicySubObjectsRisk.class, CalculateRiskSum.class})
class TheftRiskCalculateServiceTest {
    @Autowired
    private TheftRiskCalculateService theftRiskCalculateService;
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
    public void theftRiskLess_15() {
        PolicySubObject policySubObject = new PolicySubObject("Item_1",
                new BigDecimal("3.12"), Risk.THEFT);

        policyObject.addSubObject(policySubObject);

        BigDecimal theftRiskPremium = theftRiskCalculateService.calculateTheftRisk(policyObject);

        assertEquals(new BigDecimal("0.34"), theftRiskPremium);
    }

    @Test
    public void theftRiskEqual_15() {
        PolicySubObject policySubObject = new PolicySubObject("Item_1",
                new BigDecimal("15.00"), Risk.THEFT);

        policyObject.addSubObject(policySubObject);

        BigDecimal theftRiskPremium = theftRiskCalculateService.calculateTheftRisk(policyObject);

        assertEquals(new BigDecimal("0.75"), theftRiskPremium);
    }

    @Test
    public void theftRiskMore_15() {
        PolicySubObject policySubObject = new PolicySubObject("Item_1",
                new BigDecimal("20.44"), Risk.THEFT);

        policyObject.addSubObject(policySubObject);

        BigDecimal theftRiskPremium = theftRiskCalculateService.calculateTheftRisk(policyObject);

        assertEquals(new BigDecimal("1.02"), theftRiskPremium);
    }

    @Test
    public void noTheftRisk() {
        PolicySubObject policySubObject = new PolicySubObject("Item_1",
                new BigDecimal("20.44"), Risk.FIRE);

        policyObject.addSubObject(policySubObject);

        BigDecimal theftRiskPremium = theftRiskCalculateService.calculateTheftRisk(policyObject);

        assertEquals(new BigDecimal("0.00"), theftRiskPremium);
    }

    @Test
    public void twoTheftSubObjects_less_15() {
        PolicySubObject policySubObject_1 = new PolicySubObject("Item_1",
                new BigDecimal("3.99"), Risk.THEFT);
        PolicySubObject policySubObject_2 = new PolicySubObject("Item_2",
                new BigDecimal("4.08"), Risk.THEFT);

        policyObject.addSubObject(policySubObject_1);
        policyObject.addSubObject(policySubObject_2);

        BigDecimal theftRiskPremium = theftRiskCalculateService.calculateTheftRisk(policyObject);

        assertEquals(new BigDecimal("0.89"), theftRiskPremium);
    }

    @Test
    public void twoTheftSubObjects_equal_15() {
        PolicySubObject policySubObject_1 = new PolicySubObject("Item_1",
                new BigDecimal("8.21"), Risk.THEFT);
        PolicySubObject policySubObject_2 = new PolicySubObject("Item_2",
                new BigDecimal("6.79"), Risk.THEFT);

        policyObject.addSubObject(policySubObject_1);
        policyObject.addSubObject(policySubObject_2);

        BigDecimal theftRiskPremium = theftRiskCalculateService.calculateTheftRisk(policyObject);

        assertEquals(new BigDecimal("0.75"), theftRiskPremium);
    }

    @Test
    public void twoTheftSubObjects_more_15() {
        PolicySubObject policySubObject_1 = new PolicySubObject("Item_1",
                new BigDecimal("19.04"), Risk.THEFT);
        PolicySubObject policySubObject_2 = new PolicySubObject("Item_2",
                new BigDecimal("148.66"), Risk.THEFT);

        policyObject.addSubObject(policySubObject_1);
        policyObject.addSubObject(policySubObject_2);

        BigDecimal theftRiskPremium = theftRiskCalculateService.calculateTheftRisk(policyObject);

        assertEquals(new BigDecimal("8.39"), theftRiskPremium);
    }

    @Test
    public void twoTheftSubObjects_more_15_SumRound() {
        PolicySubObject policySubObject_1 = new PolicySubObject("Item_1",
                new BigDecimal("19.04"), Risk.THEFT);
        PolicySubObject policySubObject_2 = new PolicySubObject("Item_2",
                new BigDecimal("148.669"), Risk.THEFT);

        policyObject.addSubObject(policySubObject_1);
        policyObject.addSubObject(policySubObject_2);

        BigDecimal theftRiskPremium = theftRiskCalculateService.calculateTheftRisk(policyObject);

        assertEquals(new BigDecimal("8.39"), theftRiskPremium);
    }
}
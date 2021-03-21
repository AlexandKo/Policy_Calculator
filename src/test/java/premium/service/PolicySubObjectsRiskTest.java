package premium.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import premium.domen.PolicyObject;
import premium.domen.PolicySubObject;
import premium.domen.Risk;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PolicySubObjectsRiskTest {
    private final PolicySubObjectsRisk policySubObjectsRisk = new PolicySubObjectsRisk();
    private PolicyObject policyObject;

    @BeforeEach
    public void startUp() {
        policyObject = new PolicyObject();
    }

    @Test
    public void shouldReturnZeroRecord_NoSubObjects() {
        List<PolicySubObject> policySubObjectList = policySubObjectsRisk.getRiskSubObjects(policyObject, Risk.FIRE);

        assertEquals(0, policySubObjectList.size());
    }

    @Test
    public void shouldReturnZeroRecord_FireRisk() {
        PolicySubObject policySubObject_1 = new PolicySubObject("Item_1",
                new BigDecimal("45.15"), Risk.THEFT);

        policyObject.addSubObject(policySubObject_1);

        List<PolicySubObject> policySubObjectList = policySubObjectsRisk.getRiskSubObjects(policyObject, Risk.FIRE);

        assertEquals(0, policySubObjectList.size());
    }

    @Test
    public void shouldReturnOneRecord_FireRisk() {
        PolicySubObject policySubObject_1 = new PolicySubObject("Item_1",
                new BigDecimal("45.15"), Risk.FIRE);
        PolicySubObject policySubObject_2 = new PolicySubObject("Item_2",
                new BigDecimal("54.85"), Risk.THEFT);

        policyObject.addSubObject(policySubObject_1);
        policyObject.addSubObject(policySubObject_2);

        List<PolicySubObject> policySubObjectList = policySubObjectsRisk.getRiskSubObjects(policyObject, Risk.FIRE);

        assertEquals(1, policySubObjectList.size());
    }

    @Test
    public void shouldReturnTwoRecord_FireRisk() {
        PolicySubObject policySubObject_1 = new PolicySubObject("Item_1",
                new BigDecimal("45.15"), Risk.FIRE);
        PolicySubObject policySubObject_2 = new PolicySubObject("Item_2",
                new BigDecimal("54.85"), Risk.FIRE);

        policyObject.addSubObject(policySubObject_1);
        policyObject.addSubObject(policySubObject_2);

        List<PolicySubObject> policySubObjectList = policySubObjectsRisk.getRiskSubObjects(policyObject, Risk.FIRE);

        assertEquals(2, policySubObjectList.size());
    }

    @Test
    public void shouldReturnOneRecord_TheftRisk() {
        PolicySubObject policySubObject_1 = new PolicySubObject("Item_1",
                new BigDecimal("45.15"), Risk.FIRE);
        PolicySubObject policySubObject_2 = new PolicySubObject("Item_2",
                new BigDecimal("54.85"), Risk.THEFT);

        policyObject.addSubObject(policySubObject_1);
        policyObject.addSubObject(policySubObject_2);

        List<PolicySubObject> policySubObjectList = policySubObjectsRisk.getRiskSubObjects(policyObject, Risk.THEFT);

        assertEquals(1, policySubObjectList.size());
    }

    @Test
    public void shouldReturnTwoRecord_TheftRisk() {
        PolicySubObject policySubObject_1 = new PolicySubObject("Item_1",
                new BigDecimal("45.15"), Risk.THEFT);
        PolicySubObject policySubObject_2 = new PolicySubObject("Item_2",
                new BigDecimal("54.85"), Risk.THEFT);

        policyObject.addSubObject(policySubObject_1);
        policyObject.addSubObject(policySubObject_2);

        List<PolicySubObject> policySubObjectList = policySubObjectsRisk.getRiskSubObjects(policyObject, Risk.THEFT);

        assertEquals(2, policySubObjectList.size());
    }

    @Test
    public void shouldReturnTwoRecord_TheftRisk_2() {
        PolicySubObject policySubObject_1 = new PolicySubObject("Item_1",
                new BigDecimal("45.15"), Risk.THEFT);
        PolicySubObject policySubObject_2 = new PolicySubObject("Item_2",
                new BigDecimal("54.85"), Risk.THEFT);
        PolicySubObject policySubObject_3 = new PolicySubObject("Item_3",
                new BigDecimal("54.85"), Risk.FIRE);

        policyObject.addSubObject(policySubObject_1);
        policyObject.addSubObject(policySubObject_2);
        policyObject.addSubObject(policySubObject_3);

        List<PolicySubObject> policySubObjectList = policySubObjectsRisk.getRiskSubObjects(policyObject, Risk.THEFT);

        assertEquals(2, policySubObjectList.size());
    }
}
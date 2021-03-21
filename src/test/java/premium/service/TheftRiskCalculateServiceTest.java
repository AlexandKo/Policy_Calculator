package premium.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import premium.domen.PolicyObject;
import premium.domen.PolicySubObject;
import premium.domen.Risk;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class TheftRiskCalculateServiceTest {
    private PolicyObject policyObject;
    @Mock
    private PolicySubObjectsRisk policySubObjectsRisk;
    @Mock
    private CalculateRiskSum calculateRiskSum;
    @InjectMocks
    private TheftRiskCalculateService theftRiskCalculateService;

    @BeforeEach
    public void startUp() {
        MockitoAnnotations.openMocks(this);
        policyObject = new PolicyObject();
    }

    @Test
    public void theftRiskLess_15() {
        policyObject.addSubObject(new PolicySubObject("Item_1", new BigDecimal("3.82"), Risk.THEFT));
        List<PolicySubObject> fireRiskSubObject = new ArrayList<>();
        fireRiskSubObject.add(new PolicySubObject("Item_1", new BigDecimal("3.82"), Risk.THEFT));

        Mockito.when(policySubObjectsRisk.getRiskSubObjects(policyObject, Risk.THEFT)).thenReturn(fireRiskSubObject);
        Mockito.when(calculateRiskSum.calculateRiskSum(fireRiskSubObject)).thenReturn(new BigDecimal("3.82"));

        BigDecimal theftRiskPremium = theftRiskCalculateService.calculateTheftRisk(policyObject);

        assertEquals(new BigDecimal("0.42"), theftRiskPremium);
    }

    @Test
    public void theftRiskEqual_15() {
        policyObject.addSubObject(new PolicySubObject("Item_1", new BigDecimal("15.00"), Risk.THEFT));
        List<PolicySubObject> fireRiskSubObject = new ArrayList<>();
        fireRiskSubObject.add(new PolicySubObject("Item_1", new BigDecimal("15.00"), Risk.THEFT));

        Mockito.when(policySubObjectsRisk.getRiskSubObjects(policyObject, Risk.THEFT)).thenReturn(fireRiskSubObject);
        Mockito.when(calculateRiskSum.calculateRiskSum(fireRiskSubObject)).thenReturn(new BigDecimal("15.00"));

        BigDecimal theftRiskPremium = theftRiskCalculateService.calculateTheftRisk(policyObject);

        assertEquals(new BigDecimal("0.75"), theftRiskPremium);
    }

    @Test
    public void theftRiskMore_15() {
        policyObject.addSubObject(new PolicySubObject("Item_1", new BigDecimal("254.33"), Risk.THEFT));
        List<PolicySubObject> fireRiskSubObject = new ArrayList<>();
        fireRiskSubObject.add(new PolicySubObject("Item_1", new BigDecimal("254.33"), Risk.THEFT));

        Mockito.when(policySubObjectsRisk.getRiskSubObjects(policyObject, Risk.THEFT)).thenReturn(fireRiskSubObject);
        Mockito.when(calculateRiskSum.calculateRiskSum(fireRiskSubObject)).thenReturn(new BigDecimal("254.33"));

        BigDecimal theftRiskPremium = theftRiskCalculateService.calculateTheftRisk(policyObject);

        assertEquals(new BigDecimal("12.72"), theftRiskPremium);
    }
}
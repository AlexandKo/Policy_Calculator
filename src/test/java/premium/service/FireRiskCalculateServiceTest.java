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
class FireRiskCalculateServiceTest {
    private PolicyObject policyObject;
    @Mock
    private PolicySubObjectsRisk policySubObjectsRisk;
    @Mock
    private CalculateRiskSum calculateRiskSum;
    @InjectMocks
    private FireRiskCalculateService fireRiskCalculateService;

    @BeforeEach
    public void startUp() {
        MockitoAnnotations.openMocks(this);
        policyObject = new PolicyObject();
    }

    @Test
    public void fireRiskLess_100() {
        policyObject.addSubObject(new PolicySubObject("Item_1", new BigDecimal("85.55"), Risk.FIRE));
        List<PolicySubObject> fireRiskSubObject = new ArrayList<>();
        fireRiskSubObject.add(new PolicySubObject("Item_1", new BigDecimal("85.55"), Risk.FIRE));

        Mockito.when(policySubObjectsRisk.getRiskSubObjects(policyObject, Risk.FIRE)).thenReturn(fireRiskSubObject);
        Mockito.when(calculateRiskSum.calculateRiskSum(fireRiskSubObject)).thenReturn(new BigDecimal("85.55"));

        BigDecimal fireRiskPremium = fireRiskCalculateService.calculateFireRisk(policyObject);

        assertEquals(new BigDecimal("1.20"), fireRiskPremium);
    }

    @Test
    public void fireRiskEquals_100() {
        policyObject.addSubObject(new PolicySubObject("Item_1", new BigDecimal("100.00"), Risk.FIRE));
        List<PolicySubObject> fireRiskSubObject = new ArrayList<>();
        fireRiskSubObject.add(new PolicySubObject("Item_1", new BigDecimal("100.00"), Risk.FIRE));

        Mockito.when(policySubObjectsRisk.getRiskSubObjects(policyObject, Risk.FIRE)).thenReturn(fireRiskSubObject);
        Mockito.when(calculateRiskSum.calculateRiskSum(fireRiskSubObject)).thenReturn(new BigDecimal("100.00"));

        BigDecimal fireRiskPremium = fireRiskCalculateService.calculateFireRisk(policyObject);

        assertEquals(new BigDecimal("1.40"), fireRiskPremium);
    }

    @Test
    public void fireRiskMore_100() {
        policyObject.addSubObject(new PolicySubObject("Item_1", new BigDecimal("455.00"), Risk.FIRE));
        List<PolicySubObject> fireRiskSubObject = new ArrayList<>();
        fireRiskSubObject.add(new PolicySubObject("Item_1", new BigDecimal("455.00"), Risk.FIRE));

        Mockito.when(policySubObjectsRisk.getRiskSubObjects(policyObject, Risk.FIRE)).thenReturn(fireRiskSubObject);
        Mockito.when(calculateRiskSum.calculateRiskSum(fireRiskSubObject)).thenReturn(new BigDecimal("455.00"));

        BigDecimal fireRiskPremium = fireRiskCalculateService.calculateFireRisk(policyObject);

        assertEquals(new BigDecimal("10.92"), fireRiskPremium);
    }
}
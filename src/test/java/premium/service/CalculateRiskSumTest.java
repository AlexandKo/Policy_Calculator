package premium.service;

import org.junit.jupiter.api.Test;
import premium.domen.PolicySubObject;
import premium.domen.Risk;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculateRiskSumTest {
    private final CalculateRiskSum calculateRiskSum = new CalculateRiskSum();

    @Test
    public void noSubObjects_EmptyList() {
        List<PolicySubObject> subObjects = new ArrayList<>();
        BigDecimal sum = calculateRiskSum.calculateRiskSum(subObjects);

        assertEquals(new BigDecimal("0.00"), sum);
    }

    @Test
    public void oneSubObjects() {
        List<PolicySubObject> subObjects = new ArrayList<>();
        subObjects.add(new PolicySubObject("Item_1", new BigDecimal("0.50"), Risk.FIRE));
        BigDecimal sum = calculateRiskSum.calculateRiskSum(subObjects);

        assertEquals(new BigDecimal("0.50"), sum);
    }

    @Test
    public void twoSubObjects() {
        List<PolicySubObject> subObjects = new ArrayList<>();
        subObjects.add(new PolicySubObject("Item_1", new BigDecimal("0.59"), Risk.FIRE));
        subObjects.add(new PolicySubObject("Item_2", new BigDecimal("3.63"), Risk.FIRE));

        BigDecimal sum = calculateRiskSum.calculateRiskSum(subObjects);

        assertEquals(new BigDecimal("4.22"), sum);
    }

    @Test
    public void threeSubObjects() {
        List<PolicySubObject> subObjects = new ArrayList<>();
        subObjects.add(new PolicySubObject("Item_1", new BigDecimal("0.59"), Risk.FIRE));
        subObjects.add(new PolicySubObject("Item_2", new BigDecimal("3.63"), Risk.FIRE));
        subObjects.add(new PolicySubObject("Item_3", new BigDecimal("12.78"), Risk.FIRE));

        BigDecimal sum = calculateRiskSum.calculateRiskSum(subObjects);

        assertEquals(new BigDecimal("17.00"), sum);
    }

    @Test
    public void threeSubObjects_RoundTest_1() {
        List<PolicySubObject> subObjects = new ArrayList<>();
        subObjects.add(new PolicySubObject("Item_1", new BigDecimal("9.9999"), Risk.FIRE));
        subObjects.add(new PolicySubObject("Item_2", new BigDecimal("4.4587"), Risk.FIRE));
        subObjects.add(new PolicySubObject("Item_3", new BigDecimal("3.0147"), Risk.FIRE));

        BigDecimal sum = calculateRiskSum.calculateRiskSum(subObjects);

        assertEquals(new BigDecimal("17.47"), sum);
    }

    @Test
    public void threeSubObjects_RoundTest_2() {
        List<PolicySubObject> subObjects = new ArrayList<>();
        subObjects.add(new PolicySubObject("Item_1", new BigDecimal("9.9999"), Risk.FIRE));
        subObjects.add(new PolicySubObject("Item_2", new BigDecimal("4.4587"), Risk.FIRE));
        subObjects.add(new PolicySubObject("Item_3", new BigDecimal("3.897"), Risk.FIRE));

        BigDecimal sum = calculateRiskSum.calculateRiskSum(subObjects);

        assertEquals(new BigDecimal("18.36"), sum);
    }
}
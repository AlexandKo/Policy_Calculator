package premium.domen;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PolicySubObject {
    private String subObjectName;
    private BigDecimal insuredSum;
    private Risk risk;

    public PolicySubObject(String subObjectName, BigDecimal insuredSum, Risk risk) {
        this.subObjectName = subObjectName;
        this.insuredSum = insuredSum;
        this.risk = risk;
    }
}
package premium.domen;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class PolicyObject {
    @Getter
    @Setter
    private String objectName;
    @Getter
    private final List<PolicySubObject> subObjectList = new ArrayList<>();

    public void addSubObject(PolicySubObject subObject) {
        subObjectList.add(subObject);
    }
}
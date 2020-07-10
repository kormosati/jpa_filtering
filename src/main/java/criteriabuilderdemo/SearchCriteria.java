package criteriabuilderdemo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchCriteria {

    private String key;
    private Operation operation;
    private Object value;

}

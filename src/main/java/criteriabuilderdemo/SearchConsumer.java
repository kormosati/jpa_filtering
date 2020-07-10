package criteriabuilderdemo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.TestEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.function.Consumer;

import static criteriabuilderdemo.Operation.*;

@AllArgsConstructor
@Getter
public class SearchConsumer<T> implements Consumer<SearchCriteria> {

    private Predicate predicate;
    private Root root;
    private CriteriaBuilder criteriaBuilder;

    @Override
    public void accept(SearchCriteria criteria) {
        Operation operation = criteria.getOperation();
        String key = criteria.getKey();
        Object value = criteria.getValue();

        if (operation.equals(GREATER_THAN)) {
            predicate = criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.greaterThan(root.get(key), value.toString()));
        } else if (operation.equals(LESS_THAN)) {
            predicate = criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.lessThan(root.get(key), value.toString()));
        } else if (operation.equals(EQUAL_TO)) {
            predicate = criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.equal(root.get(key), value));
        }
    }

}

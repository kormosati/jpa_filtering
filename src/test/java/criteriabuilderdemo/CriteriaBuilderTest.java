package criteriabuilderdemo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import model.TestEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class CriteriaBuilderTest {

    private static SessionFactory sessionFactory;

    @BeforeAll
    static void init() {
        List<TestEntity> entities = new LinkedList<>();

        entities.add(TestEntity.builder()
                .email("kormosati@gmail.com")
                .firstName("Attila")
                .lastName("Kormos")
                .title("Senior Software Engineer")
                .age(30).build());

        entities.add(TestEntity.builder()
                .email("johndoe@gmail.com")
                .firstName("John")
                .lastName("Doe")
                .title("Architect")
                .age(40).build());

        sessionFactory = HibernateUtil.getSessionFactory();

        Session session = sessionFactory.openSession();

        entities.forEach(session::save);

        session.close();
    }

    @Test
    public void testCriteriaBuilder() {
        Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        Predicate predicate = cb.conjunction();
        CriteriaQuery<TestEntity> query = cb.createQuery(TestEntity.class);

        SearchConsumer searchConsumer = new SearchConsumer(predicate, query.from(TestEntity.class), cb);

        List<SearchCriteria> searchCriteriaList = new LinkedList<>();

        searchCriteriaList.add(
                SearchCriteria.builder()
                .key("age")
                .operation(Operation.EQUAL_TO)
                .value(30).build()
        );

        searchCriteriaList.forEach(searchConsumer);

        query.where(searchConsumer.getPredicate());

        List<TestEntity> testEntities = session.createQuery(query).list();

        assertTrue(testEntities.size() == 1);
        assertTrue(testEntities.get(0).getAge() == 30);

        session.close();
    }

    @AfterAll
    public static void afterAll() {
        HibernateUtil.closeSessionFactory();
    }

}

package util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import model.TestEntity;

import java.util.Properties;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();

            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "org.h2.Driver");
            settings.put(Environment.URL, "jdbc:h2:mem:test");
            settings.put(Environment.USER, "user");
            settings.put(Environment.PASS, "");
            settings.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            settings.put(Environment.HBM2DDL_AUTO, "create-drop");

            configuration.setProperties(settings);

            configuration.addAnnotatedClass(TestEntity.class);

            ServiceRegistry serviceRegistry =
                    new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }

        return sessionFactory;
    }

    public static void closeSessionFactory() {
        sessionFactory.close();
    }

}

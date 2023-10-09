import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.SQLException;

public class Main {
    private static final SessionFactory factory ;

    static{
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Session getSession() throws HibernateException{
        return factory.openSession();
    }

    public static void main(String[] args) throws SQLException{
        testFetch();
    }

    private static void testFetch() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] check de database voor alle " + entityType.getName() + " types: ");
                for (Object o : query.list()) {
                    System.out.println(o);
                }
                System.out.println();
            }
        }   finally {
            session.close();
        }
    }



}

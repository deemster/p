
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;


public class Main {
    private static final SessionFactory factory ;

    static{
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) throws SQLException {
//        testFetch();
        testDAOHibernate();
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

    public static void testDAOHibernate() {
        AdresDAOHibernate adaoSQL = new AdresDAOHibernate(getSession());
        ReizigerDAOHibernate rdaoSQL = new ReizigerDAOHibernate(getSession());
        OVChipkaartDAOHibernate odaoSQL = new OVChipkaartDAOHibernate(getSession());
        ProductDAOHibernate pdaoSQL = new ProductDAOHibernate(getSession());

        Reiziger reiziger = new Reiziger(8000, "D", "", "Hopman", java.sql.Date.valueOf("2000-08-29"));
        Adres adres = new Adres(8000, "1541AB", "65", "Breedweer", "Koog aan de Zaan", reiziger.getId());
        OVChipkaart ovChipkaart = new OVChipkaart(88899, java.sql.Date.valueOf("2030-09-22"), 1, 2000, reiziger.getId());
        Product product = new Product(8000, "P7 OPDRACHT", "DIT IS DUS VOOR DE P7 OPDRACHT", 40.50f);


        Reiziger updated_reiziger = new Reiziger(8010, "T", "", "Bolhoeve", java.sql.Date.valueOf("2001-03-29"));
        Adres updated_adres = new Adres(8010, "17SS", "14", "molenweg", "Koog aan de Zaan", updated_reiziger.getId());
        OVChipkaart updated_ovChipkaart = new OVChipkaart(88899, java.sql.Date.valueOf("2030-09-30"), 2, 4000, updated_reiziger.getId());
        Product updated_product = new Product(8000, "P7 OPDRACHT UPDATE", "DIT IS DUS DE UPDATE VOOR DE P7 OPDRACHT", 20.50f);


        System.out.println("---------- Hieronder staat alles wat met Reiziger te maken heeft (behalve de delete) ----------");
        System.out.println("----------  find all ----------");
        System.out.println(rdaoSQL.findAll());

        System.out.println("----------  save + findById ----------");
        rdaoSQL.save(reiziger);
        System.out.println(rdaoSQL.findById(reiziger.getId()));

        System.out.println("---------- update + findByGbDatum ----------");
        rdaoSQL.update(reiziger);
        System.out.println(rdaoSQL.findByGbDatum(updated_reiziger.getGeboortedatum()));

        System.out.println("---------- hieronder staat alles wat met Adres te maken heeft ----------");
        System.out.println("---------- find all ----------");
        System.out.println(adaoSQL.findAll());



    }



}

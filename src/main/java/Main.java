
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


        System.out.println("------ alles omtrent Reiziger -----");
        System.out.println("--- save en findAll methodes ---");
        System.out.println(rdaoSQL.findAll());
        rdaoSQL.save(reiziger);
        System.out.println("--- na de save ---");
        System.out.println(rdaoSQL.findAll());


        System.out.println("--- update en findById methodes ---");
        reiziger.setAchternaam("berg");
        rdaoSQL.update(reiziger);
        System.out.println(rdaoSQL.findById(reiziger.getId()));

        System.out.println("\n------ alles omtrent Adres -----");
        System.out.println("--- save en findAll methodes---");
        System.out.println(adaoSQL.findAll());
        adaoSQL.save(adres);
        System.out.println("--- na de save ---");
        System.out.println(adaoSQL.findAll());

        System.out.println("--- update + findByReiziger methodes ---");
        adres.setHuisnummer("66");
        adaoSQL.update(adres);
        System.out.println(adaoSQL.findByReiziger(reiziger));

        System.out.println("--- delete ---");
        adaoSQL.delete(adres);
        System.out.println(adaoSQL.findAll());


        System.out.println("\n------ alles omtrent Product -----");
        System.out.println("--- save + findAll ---");
        System.out.println(pdaoSQL.findAll());
        pdaoSQL.save(product);
        System.out.println("--- na de save ---");
        System.out.println(pdaoSQL.findAll());

        System.out.println("--- na de update ---");
        product.setPrijs(15.50f);
        System.out.println(pdaoSQL.findAll());


        System.out.println("\n\n------ alles wat met OVChipkaart heeft te maken -----");
        System.out.println("--- na de save ---");
        odaoSQL.save(ovChipkaart);
        System.out.println(odaoSQL.findByReiziger(reiziger));

        System.out.println("--- na de update ---");
        ovChipkaart.setSaldo(1500);
        odaoSQL.update(ovChipkaart);
        System.out.println(odaoSQL.findByReiziger(reiziger));

        System.out.println("--- voeg product aan ov toe ---");
        ovChipkaart.getProducten().add(product);
        odaoSQL.update(ovChipkaart);
        System.out.println(odaoSQL.findByReiziger(reiziger));


        System.out.println("\n\n----- DELETE EVERYTHING -----");

        System.out.println("--- ovchipkaart gedelete ---");
        odaoSQL.delete(ovChipkaart);

        System.out.println("---- voordat het product is gedeleted ----");
        System.out.println(pdaoSQL.findAll());
        pdaoSQL.delete(product);
        System.out.println("---- nadat het product is gedeleted ----");
        System.out.println(pdaoSQL.findAll());

        System.out.println("---- voordat de reiziger is gedeleted ----");
        System.out.println(rdaoSQL.findById(reiziger.getId()));
        rdaoSQL.delete(reiziger);
        System.out.println("---- nadat de reiziger is gedelete ----");
        System.out.println(rdaoSQL.findById(reiziger.getId()));


    }



}

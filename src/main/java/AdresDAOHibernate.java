import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class AdresDAOHibernate  implements AdresDAO{
    private Session session;

    public AdresDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Adres adres){
        boolean uitslag;
        try {
            Transaction transaction = this.session.beginTransaction();
            session.save(adres);
            transaction.commit();
            uitslag = true;
        } catch (Exception e) {
            System.out.println("de error luid: " + e.getMessage());
            uitslag = false;
        }
        return uitslag;
    }

    @Override
    public boolean update(Adres adres) {
        boolean uitslag;
        try {
            Transaction transaction = this.session.beginTransaction();
            session.update(adres);
            transaction.commit();
            uitslag = true;
        }catch (Exception e) {
            System.out.println("de error luid: " + e.getMessage());
            uitslag = false;
        }
        return uitslag;
    }

    @Override
    public boolean delete(Adres adres) {
        boolean uitslag;
        try {
            Transaction transaction = this.session.beginTransaction();
            session.delete(adres);
            transaction.commit();
            uitslag = true;
        }catch (Exception e) {
            System.out.println("de error luid: " + e.getMessage());
            uitslag = false;
        }
        return uitslag;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            Transaction transaction = this.session.beginTransaction();
            Adres adres = session.createQuery("FROM Adres WHERE reiziger_id = " + reiziger.getId(), Adres.class).getSingleResult();
            transaction.commit();
            return adres;
        }catch (Exception e) {
            System.out.println("de error luid: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Adres> findAll() {
        try {
            Transaction transaction = this.session.beginTransaction();
            List<Adres> adressen = session.createQuery(" FROM Adres", Adres.class).getResultList();
            List<Adres> adressen1 = new ArrayList<>(adressen);
            transaction.commit();
            return adressen1;
        } catch (Exception e) {
            System.out.println("de error luid: " + e.getMessage());
            return null;
        }
    }

}

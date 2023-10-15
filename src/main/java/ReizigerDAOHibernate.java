import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO{
    private final Session session;

    public ReizigerDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        boolean uitslag;
        try {
            Transaction transaction = this.session.beginTransaction();
            session.save(reiziger);
            transaction.commit();
            uitslag = true;
        } catch (Exception e) {
            System.out.println("de error luid: " + e.getMessage());
            uitslag = false;
        }
        return uitslag;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        boolean uitslag;
        try {
            Transaction transaction = this.session.beginTransaction();
            session.update(reiziger);
            transaction.commit();
            uitslag = true;
        } catch (Exception e) {
            System.out.println("de error luid: " + e.getMessage());
            uitslag = false;
        }
        return uitslag;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        boolean uitslag;
        try {
            Transaction transaction = this.session.beginTransaction();
            session.delete(reiziger);
            transaction.commit();
            uitslag = true;
        } catch (Exception e) {
            System.out.println("de error luid: " + e.getMessage());
            uitslag = false;
        }
        return uitslag;
    }

    @Override
    public Reiziger findById(int id) {
        try {
            Transaction transaction = this.session.beginTransaction();
            Reiziger reiziger = session.createQuery(" FROM Reiziger WHERE id = " + id, Reiziger.class).getSingleResult();
            transaction.commit();
            return reiziger;
        }catch (Exception e) {
            System.out.println("de error luid: " + e.getMessage());
            return null;
        }
    }



    @Override
    public List<Reiziger> findByGbDatum(Date datum) {
        try {
            Transaction transaction = this.session.beginTransaction();
            List<Reiziger> reizigers = session.createQuery("FROM Reiziger WHERE geboortedatum  = " + datum, Reiziger.class).getResultList();
            transaction.commit();
            return reizigers;
        } catch (Exception e) {
            System.out.println("de error luid: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() {
        try {
            Transaction transaction = this.session.beginTransaction();
            List<Reiziger> reizigers = session.createQuery("FROM Reiziger", Reiziger.class).getResultList();
            transaction.commit();
            return reizigers;
        } catch (Exception e) {
            System.out.println("de error luid: " + e.getMessage());
            return null;
        }
    }

}

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO{
    private Session session;

    public OVChipkaartDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        boolean uitslag;
        try {
            Transaction transaction = this.session.beginTransaction();
            session.save(ovChipkaart);
            transaction.commit();
            uitslag = true;
        } catch (Exception e) {
            System.out.println("de error luid: " + e.getMessage());
            uitslag = false;
        }
        return uitslag;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        boolean uitslag;
        try {
            Transaction transaction = this.session.beginTransaction();
            session.update(ovChipkaart);
            transaction.commit();
            uitslag = true;
        } catch (Exception e) {
            System.out.println("de error luid: " + e.getMessage());
            uitslag = false;
        }
        return uitslag;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart){
        boolean uitslag;
        try {
            Transaction transaction = this.session.beginTransaction();
            session.delete(ovChipkaart);
            transaction.commit();
            uitslag = true;
        } catch (Exception e) {
            System.out.println("de error luid: " + e.getMessage());
            uitslag = false;
        }
        return uitslag;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        try {
            Transaction transaction = this.session.beginTransaction();
            List<OVChipkaart> ovChipkaarten = session.createQuery("FROM OVChipkaart WHERE reiziger_Id = " + reiziger.getId(), OVChipkaart.class).getResultList();
            transaction.commit();
            return ovChipkaarten;
        } catch (Exception e) {
            System.out.println("de error luid: " + e.getMessage());
            return null;
        }
    }
}

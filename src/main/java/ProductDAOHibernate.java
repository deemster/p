import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO{
    private Session session;

    public ProductDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Product product) {
        boolean uitslag;
        try {
            Transaction transaction = this.session.beginTransaction();
            session.save(product);
            transaction.commit();
            uitslag = true;
        } catch (Exception e) {
            System.out.println("de error luid: " + e.getMessage());
            uitslag = false;
        }
        return uitslag;
    }

    @Override
    public boolean update(Product product) {
        boolean uitslag;
        try {
            Transaction transaction = this.session.beginTransaction();
            session.update(product);
            transaction.commit();
            uitslag = true;
        } catch (Exception e) {
            System.out.println("de error luid: " + e.getMessage());
            uitslag = false;
        }
        return uitslag;
    }

    @Override
    public boolean delete(Product product) {
        boolean uitslag;
        try {
            Transaction transaction = this.session.beginTransaction();
            session.delete(product);
            transaction.commit();
            uitslag = true;
        } catch (Exception e) {
            System.out.println("de error luid: " + e.getMessage());
            uitslag = false;
        }
        return uitslag;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        try {
            Transaction transaction = this.session.beginTransaction();
            List<Product> producten = session.createQuery("FROM Product", Product.class).getResultList();
            List<Product> ovProducten = new ArrayList<>();

            for (Product p : producten) {
                if (p.getOvChipkaarten().contains(ovChipkaart)) {
                    ovProducten.add(p);
                }
            }

            transaction.commit();
            return ovProducten;
        } catch (Exception e) {
            System.out.println("de error luid: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Product> findAll(){
        try {
            Transaction transaction = this.session.beginTransaction();
            List<Product> producten = session.createQuery("FROM Product", Product.class).getResultList();
            transaction.commit();
            return producten;
        }catch (Exception e) {
            System.out.println("de error luid: " + e.getMessage());
            return null;
        }
    }
}

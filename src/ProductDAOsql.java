import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOsql implements ProductDAO{
    Connection conn;

    public ProductDAOsql(Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean save(Product product) throws SQLException{
        boolean uitvoer = false;

        try {
            PreparedStatement save = conn.prepareStatement("INSERT INTO product VALUES(?, ?, ?, ?)");
            save.setInt(1, product.getId());
            save.setString(2, product.getNaam());
            save.setString(3, product.getBeschrijving());
            save.setDouble(4, product.getPrijs());
            save.executeUpdate();

            PreparedStatement saveovprod = conn.prepareStatement("INSERT INTO ov_chipkaart_product VALUES(?, ?, ?, )");
            System.out.println(product.getOvChipkaarten());
            for (OVChipkaart ovChipkaart: product.getOvChipkaarten()){
                saveovprod.setInt(1, ovChipkaart.getKaartNummer());
                saveovprod.setInt(2, product.getId());
                saveovprod.setString(3,"gekocht");
                saveovprod.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
                saveovprod.executeUpdate();
            }
            uitvoer = true;

        }  catch (SQLException sq){
            System.err.println("verkeerde sql " + sq.getMessage());
        }
        return uitvoer;
    }

    @Override
    public boolean update(Product product) throws SQLException{
        boolean uitvoer = false;

        try {
            PreparedStatement update = conn.prepareStatement("UPDATE product SET product_nummer=?, naam=?, beschrijving=?, prijs=?");
            update.setInt(1, product.getId());
            update.setString(2, product.getNaam());
            update.setString(3, product.getBeschrijving());
            update.setDouble(4, product.getPrijs());
            update.executeUpdate();

            PreparedStatement delete = conn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE product_nummer=?");
            delete.setInt(1, product.getId());
            delete.executeUpdate();

            PreparedStatement insert = conn.prepareStatement("INSERT INTO  Ov_chipkaart_product VALUES (?, ?, ?, ?)");
            for (OVChipkaart ovChipkaart : product.getOvChipkaarten()) {
                insert.setInt(1, ovChipkaart.getKaartNummer());
                insert.setInt(2, product.getId());
                insert.setString(3, "gekocht");
                insert.setDate(4, Date.valueOf(LocalDate.now()));
                insert.executeUpdate();
            }
            uitvoer = true;
        }catch (SQLException sq){
            System.err.println("verkeerde sql " + sq.getMessage());
        }
        return uitvoer;
    }

    @Override
    public boolean delete(Product product) throws SQLException {
        boolean uitvoer = false;
        try {
            PreparedStatement delete = conn.prepareStatement("DELETE FROM product WHERE product_nummer=?");
            delete.setObject(1, product.getId());
            delete.executeUpdate();

            PreparedStatement delovprod = conn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE product_nummer=?");
            delete.setObject(1, product.getId());
            delete.executeUpdate();
            delovprod.executeUpdate();
            uitvoer = true;

        } catch (SQLException sq){
            System.err.println("verkeerde sql " + sq.getMessage());
        }
        return uitvoer;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart){
        try {
            PreparedStatement find = conn.prepareStatement("SELECT p.product_nummer, naam, beschrijving, prijs FROM ov_chipkaart_product ocp JOIN product p ON ocp.product_nummer = p.product_nummer WHERE ocp.kaart_nummer = ?");
            find.setInt(1, ovChipkaart.getKaartNummer());
            ResultSet resultSet = find.executeQuery();

            ArrayList<Product> alles = new ArrayList<>();

            while (resultSet.next()) {
                int nummer =resultSet.getInt("product_nummer");
                String naam = resultSet.getString("naam");
                String beschrijving = resultSet.getString("beschrijving");
                float prijs = resultSet.getFloat("prijs");
                Product product = new Product(nummer, naam, beschrijving, prijs);
                alles.add(product);
            }
            return alles;
        } catch (SQLException sq){
            System.err.println("verkeerde sql " + sq.getMessage());
            return null;
        }
    }

    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> producten = new ArrayList<>();
        PreparedStatement product = conn.prepareStatement("SELECT * FROM product");
        ResultSet resultSet = product.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt("product_nummer");
            String naam = resultSet.getString("naam");
            String beschrijving = resultSet.getString("beschrijving");
            double prijs = resultSet.getDouble("prijs");

            Product productje = new Product(id, naam, beschrijving, prijs);
            producten.add(productje);
        }
        return  producten;
    }


}

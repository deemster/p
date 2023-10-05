import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            uitvoer = true;
        } catch (SQLException sq){
            System.err.println("verkeerde sql " + sq.getMessage());
        }
        return uitvoer;
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

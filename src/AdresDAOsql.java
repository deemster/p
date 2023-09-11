import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOsql implements AdresDAO{
    Connection conn;

    public AdresDAOsql(Connection conn) throws SQLException{
        this.conn = conn;
    }

    @Override
    public boolean save(Adres adres) throws SQLException{
        boolean uitvoer = false;

        try {
            PreparedStatement save = conn.prepareStatement("INSERT INTO adres VALUES(?, ?, ?, ?, ?, ?)");
            save.setInt(1, adres.getId());
            save.setString(2, adres.getPostcode());
            save.setString(3, adres.getHuisnummer());
            save.setString(4, adres.getStraat());
            save.setString(5, adres.getWoonplaats());
            save.setInt(6, adres.getReizigerId());
            save.executeUpdate();
            uitvoer = true;
        } catch (SQLException sq){
            System.err.println("verkeerde sql " + sq.getMessage());
        }
        return uitvoer;
    }

    @Override
    public boolean update(Adres adres) throws SQLException{
        boolean uitvoer = false;

        try {
            PreparedStatement update = conn.prepareStatement("UPDATE adres SET postcode=?, huisnummer=?, straat=?, woonplaats=? WHERE adres_id=?");
            update.setString(1, adres.getPostcode());
            update.setString(2, adres.getHuisnummer());
            update.setObject(3, adres.getStraat());
            update.setString(4, adres.getWoonplaats());
            update.setInt(5, adres.getId());
            update.executeUpdate();
            uitvoer = true;
        }catch (SQLException sq){
            System.err.println("verkeerde sql " + sq.getMessage());
        }
        return uitvoer;
    }

    @Override
    public boolean delete(Adres adres) throws SQLException{
        boolean uitvoer = false;
        try {
            PreparedStatement delete = conn.prepareStatement("DELETE FROM adres WHERE adres_id=?");
            delete.setObject(1, adres.getId());
            delete.executeUpdate();
            uitvoer = true;
        } catch (SQLException sq){
            System.err.println("verkeerde sql " + sq.getMessage());
        }
        return uitvoer;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        try{
            PreparedStatement findbyreiziger = conn.prepareStatement("SELECT * FROM adres WHERE reiziger_id = ?;");
            findbyreiziger.setInt(1, reiziger.getId());
            ResultSet resultSet = findbyreiziger.executeQuery();

            Adres adres = null;

            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String postcode = resultSet.getString(2);
                String huisnummer = resultSet.getString(3);
                String straat = resultSet.getString(4);
                String woonplaats = resultSet.getString(5);
                int reizigerid = resultSet.getInt(6);

                adres = new Adres(id, postcode, huisnummer, straat, woonplaats, reizigerid);
            }
            resultSet.close();
            findbyreiziger.close();

            return adres;
        } catch (SQLException sq) {
            System.err.println("verkeerde sql " + sq.getMessage());
        }
        return null;
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        List<Adres> adressen = new ArrayList<>();
        PreparedStatement adres = conn.prepareStatement("Select * from adres");
        ResultSet all = adres.executeQuery();
        while (all.next()){
            int id = all.getInt("adres_id");
            String postcode = all.getString("postcode");
            String huisnummer = all.getString("huisnummer");
            String straat = all.getString("straat");
            String woonplaats = all.getString("woonplaats");
            int reizigerId = all.getInt("reiziger_id");

            Adres adresjes = new Adres(id, postcode, huisnummer, straat, woonplaats, reizigerId);
            adressen.add(adresjes);
        }
        return adressen;
    }



}

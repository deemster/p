import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOsql implements  ReizigerDAO{
    private Connection conn;



    public ReizigerDAOsql(Connection conn) throws SQLException {
        this.conn = conn;
    }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        boolean uitvoer = false;
        try {
            PreparedStatement save = conn.prepareStatement("INSERT INTO reiziger VALUES(?, ?, ?, ?, ?)");
            save.setInt(1, reiziger.getId());
            save.setString(2, reiziger.getVoorletters());
            save.setString(3, reiziger.getTussenvoegsel());
            save.setString(4, reiziger.getAchternaam());
            save.setObject(5, reiziger.getGeboortedatum());
            save.executeUpdate();
            uitvoer = true;
        }catch (SQLException sq){
            System.err.println("verkeerde sql " + sq.getMessage());
        }
        return uitvoer;
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        boolean uitvoer = false;

        try {
            PreparedStatement update = conn.prepareStatement("UPDATE reiziger SET voorletters=?, tussenvoegsel=?, achternaam=?, geboortedatum=? WHERE reiziger_id=?");
            update.setString(1, reiziger.getVoorletters());
            update.setString(2, reiziger.getTussenvoegsel());
            update.setString(3, reiziger.getAchternaam());
            update.setObject(4, reiziger.getGeboortedatum());
            update.setInt(5, reiziger.getId());
            update.executeUpdate();
            uitvoer = true;
        }catch (SQLException sq){
            System.err.println("verkeerde sql " + sq.getMessage());
        }
        return uitvoer;
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        boolean uitvoer = false;

        try {
            PreparedStatement delete = conn.prepareStatement("DELETE FROM reiziger WHERE reiziger_id=?");
            delete.setObject(1, reiziger.getId());
            delete.executeUpdate();
            uitvoer = true;
        }catch (SQLException sq){
            System.err.println("verkeerde sql " + sq.getMessage());
        }
        return uitvoer;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        Reiziger reis = null;
        PreparedStatement reizigers = conn.prepareStatement("select * from reiziger WHERE reiziger_id = ?");
        reizigers.setInt(1, id);
        ResultSet alles = reizigers.executeQuery();
        while (alles.next()) {
            int ide = alles.getInt("reiziger_id");
            String voorletters = alles.getString("voorletters");
            String tussenvoegsel = alles.getString("tussenvoegsel");
            String achternaam = alles.getString("achternaam");
            Date geboortedatum = alles.getDate("geboortedatum");

            reis = new Reiziger(ide, voorletters, tussenvoegsel, achternaam, geboortedatum);
        }

        return reis;
    }



    @Override
    public List<Reiziger> findByGbDatum(String datum) throws SQLException {
        List<Reiziger> personen = new ArrayList<>();
        PreparedStatement reizigers = conn.prepareStatement("select * from reiziger WHERE geboortedatum = ?");
        reizigers.setDate(1, Date.valueOf(datum));
        ResultSet alles = reizigers.executeQuery();
        while (alles.next()){
            int id = alles.getInt("reiziger_id");
            String voorletters = alles.getString("voorletters");
            String tussenvoegsel = alles.getString("tussenvoegsel");
            String achternaam = alles.getString("achternaam");
            Date geboortedatum = alles.getDate("geboortedatum");

            Reiziger reiziger = new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum);
            personen.add(reiziger);
        }
        return personen;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        List<Reiziger> personen = new ArrayList<>();
        PreparedStatement reizigers = conn.prepareStatement("select * from reiziger");
        ResultSet alles = reizigers.executeQuery();
        while (alles.next()){
            int id = alles.getInt("reiziger_id");
            String voorletters = alles.getString("voorletters");
            String tussenvoegsel = alles.getString("tussenvoegsel");
            String achternaam = alles.getString("achternaam");
            Date geboortedatum = alles.getDate("geboortedatum");

            Reiziger reiziger = new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum);
            personen.add(reiziger);
        }
        return personen;
    }

    public Adres findByReiziger(Reiziger reiziger) {
        try {
            // find traveler by id using a prepareStatement
            String q = "SELECT * FROM adres WHERE reiziger_id = ?;";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, reiziger.getId());
            ResultSet rs = pst.executeQuery();

            Adres adres = null;

            // add the address
            while (rs.next())
            {
                int adresId = rs.getInt(1);
                String postcode = rs.getString(2);
                String huisnummer = rs.getString(3);
                String straat = rs.getString(4);
                String woonplaats = rs.getString(5);
                int reizigerId = rs.getInt(6);

                adres = new Adres(adresId, postcode, huisnummer, straat, woonplaats,reizigerId);
            }

            // close connections
            rs.close();
            pst.close();

            return adres;
        }
        catch (SQLException sqlex) {
            System.err.println("An error occurred while searching by id: " + sqlex.getMessage());
        }
        return null;
    }

}








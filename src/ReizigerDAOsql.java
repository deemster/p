import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOsql implements  ReizigerDAO{
    private Connection conn;
    private final AdresDAOsql adao;
//    private final OVChipkaartDAOsql odao;



    public ReizigerDAOsql(Connection conn) throws SQLException {
        this.conn = conn;
        this.adao = new AdresDAOsql(conn);
//        this.odao = new OVChipkaartDAOsql(conn);
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
            int kaartnum = alles.getInt("kaartnummer");

            reis = new Reiziger(ide, voorletters, tussenvoegsel, achternaam, geboortedatum, kaartnum);
            reis.setAdres(adao.findByReiziger(reis));
//            reis.setOvChipkaart(odao.findByReiziger(reis));
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
            int kaartnum = alles.getInt("kaartnummer");

            Reiziger reiziger = new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum, kaartnum);
            reiziger.setAdres(adao.findByReiziger(reiziger));
//            reiziger.setOvChipkaart(odao.findByReiziger(reiziger));
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
            int kaartnum = alles.getInt("kaartnummer");

            Reiziger reiziger = new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum, kaartnum);
            reiziger.setAdres(adao.findByReiziger(reiziger));
//            reiziger.setOvChipkaart(odao.findByReiziger(reiziger));
            personen.add(reiziger);
        }
        return personen;
    }

    @Override
    public Reiziger findByChipkaart(OVChipkaart ovChipkaart) throws SQLException{
        try {
            PreparedStatement findbyovchip = conn.prepareStatement("SELECT * FROM  ov_chipkaart WHERE reiziger_id=?");
            findbyovchip.setInt(1, ovChipkaart.getReizigerId());
            ResultSet resultSet = findbyovchip.executeQuery();
            Reiziger reiziger = null;
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String voorletter = resultSet.getString(2);
                String tussenvoegsel = resultSet.getString(3);
                String achternaam = resultSet.getString(4);
                Date geboortedatum = resultSet.getDate(5);
                int kaartnummer = resultSet.getInt(6);

                reiziger = new Reiziger(id, voorletter, tussenvoegsel, achternaam, geboortedatum, kaartnummer);
            }
            resultSet.close();
            findbyovchip.close();

            return reiziger;
        } catch (SQLException sq) {
            System.err.println("verkeerde sql " + sq.getMessage());
        }
        return null;
    }

}
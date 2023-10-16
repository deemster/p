import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOsql implements OVChipkaartDAO{
    Connection conn;


    public OVChipkaartDAOsql(Connection conn) throws SQLException{
        this.conn = conn;
    }
    @Override
    public boolean save(OVChipkaart ovchipkaart) throws SQLException{
        boolean uitvoer = false;

        try {
            PreparedStatement save = conn.prepareStatement("INSERT INTO ov_chipkaart VALUES(?, ?, ?, ?, ?)");
            save.setInt(1, ovchipkaart.getKaartNummer());
            save.setObject(2, ovchipkaart.getGeldigTot());
            save.setInt(3, ovchipkaart.getKlasse());
            save.setInt(4, ovchipkaart.getSaldo());
            save.setInt(5, ovchipkaart.getReizigerId());
            save.executeUpdate();
            uitvoer = true;

        } catch (SQLException sq){
            System.err.println("verkeerde sql " + sq.getMessage());
        }
        return uitvoer;
    }

    @Override
    public boolean update(OVChipkaart ovchipkaart) throws SQLException {
        boolean uitvoer = false;

        try {
            PreparedStatement update = conn.prepareStatement("UPDATE ov_chipkaart SET geldig_tot=?, klasse=?, saldo=? WHERE kaart_nummer=?");
            update.setObject(1, ovchipkaart.getGeldigTot());
            update.setInt(2, ovchipkaart.getKlasse());
            update.setInt(3, ovchipkaart.getSaldo());
            update.setInt(4, ovchipkaart.getKaartNummer());
            update.executeUpdate();
            uitvoer = true;
        } catch (SQLException sq){
            System.err.println("verkeerde sql " + sq.getMessage());
        }
        return uitvoer;
    }

    @Override
    public boolean delete(OVChipkaart ovchipkaart) throws SQLException{
        boolean uitvoer = false;
        try {
            PreparedStatement delete = conn.prepareStatement("DELETE FROM ov_chipkaart WHERE kaart_nummer=?");
            delete.setObject(1, ovchipkaart.getKaartNummer());
            delete.executeUpdate();
            uitvoer = true;
        }catch (SQLException sq){
            System.err.println("verkeerde sql " + sq.getMessage());
        }
        return uitvoer;
    }

    @Override
    public OVChipkaart findByReiziger(Reiziger reiziger) throws SQLException{
        try {
            PreparedStatement findbyreiziger = conn.prepareStatement("SELECT * FROM ov_chipkaart WHERE reiziger_id = ?;");
            findbyreiziger.setInt(1, reiziger.getId());
            ResultSet resultSet = findbyreiziger.executeQuery();
            ReizigerDAOsql rdao = new ReizigerDAOsql(conn);
            OVChipkaart ovchipkaart = null;
            while (resultSet.next()){
                int kaartnummer = resultSet.getInt(1);
                Date geldigtot = resultSet.getDate(2);
                int klasse = resultSet.getInt(3);
                int saldo = resultSet.getInt(4);
                int reizigerid = resultSet.getInt(5);

                ovchipkaart = new OVChipkaart(kaartnummer, geldigtot, klasse, saldo, reizigerid);
                ovchipkaart.setReiziger(rdao.findById(reizigerid));
            }
            resultSet.close();
            findbyreiziger.close();

            return ovchipkaart;
        }catch (SQLException sq) {
            System.err.println("verkeerde sql " + sq.getMessage());
        }
        return null;
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        List<OVChipkaart> ovchipkaarten = new ArrayList<>();
        PreparedStatement ovchipkaart = conn.prepareStatement("SELECT * from ov_chipkaart");
        ResultSet resultSet = ovchipkaart.executeQuery();
        ReizigerDAOsql rdao = new ReizigerDAOsql(conn);
        while (resultSet.next()){
            int kaartnummer = resultSet.getInt(1);
            Date geldigtot = resultSet.getDate(2);
            int klasse = resultSet.getInt(3);
            int saldo = resultSet.getInt(4);
            int reizigerid = resultSet.getInt(5);

            OVChipkaart ovchip = new OVChipkaart(kaartnummer, geldigtot, klasse, saldo, reizigerid);
            ovchip.setReiziger(rdao.findById(reizigerid));

            ovchipkaarten.add(ovchip);
        }
        return ovchipkaarten;
    }

}

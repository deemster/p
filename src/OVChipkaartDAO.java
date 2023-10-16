import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    boolean save(OVChipkaart ovchipkaart) throws SQLException;

    boolean update(OVChipkaart ovchipkaart) throws SQLException;

    boolean delete(OVChipkaart ovchipkaart) throws SQLException;

    OVChipkaart findByReiziger(Reiziger reiziger) throws SQLException;

    List<OVChipkaart> findAll() throws SQLException;
}

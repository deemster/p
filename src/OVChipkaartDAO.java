import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {

    public boolean save(OVChipkaart ovchipkaart) throws SQLException;
    public boolean update(OVChipkaart ovchipkaart) throws SQLException;
    public boolean delete(OVChipkaart ovchipkaart) throws SQLException;
    public OVChipkaart findByReiziger(Reiziger reiziger) throws SQLException;
    public List<OVChipkaart> findAll() throws SQLException;
}

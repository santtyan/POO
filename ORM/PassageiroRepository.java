import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class PassageiroRepository {
    private static Database database;
    private static Dao<Passageiro, Integer> dao;
    private List<Passageiro> loadedPassageiros;
    private Passageiro loadedPassageiro;

    public PassageiroRepository(Database database) {
        PassageiroRepository.setDatabase(database);
        loadedPassageiros = new ArrayList<>();
    }

    public static void setDatabase(Database database) {
        PassageiroRepository.database = database;
        try {
            dao = DaoManager.createDao(database.getConnection(), Passageiro.class);
            TableUtils.createTableIfNotExists(database.getConnection(), Passageiro.class);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Passageiro create(Passageiro passageiro) {
        int nrows = 0;
        try {
            nrows = dao.create(passageiro);
            if (nrows == 0)
                throw new SQLException("Error: object not saved");
            this.loadedPassageiro = passageiro;
            loadedPassageiros.add(passageiro);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return passageiro;
    }

    public void update(Passageiro passageiro) {
        try {
            dao.update(passageiro);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void delete(Passageiro passageiro) {
        try {
            dao.delete(passageiro);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Passageiro loadFromId(int id) {
        try {
            this.loadedPassageiro = dao.queryForId(id);
            if (this.loadedPassageiro != null)
                this.loadedPassageiros.add(this.loadedPassageiro);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.loadedPassageiro;
    }

    public List<Passageiro> loadAll() {
        try {
            this.loadedPassageiros = dao.queryForAll();
            if (this.loadedPassageiros.size() != 0)
                this.loadedPassageiro = this.loadedPassageiros.get(0);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.loadedPassageiros;
    }
}

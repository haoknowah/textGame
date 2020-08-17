import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
public class EnemyDAO {
    public Enemy initEnemmy(String dungeon){
        String sql = "SELECT race FROM enemies WHERE env=" + dungeon +";";

        return enemy;
    }
}
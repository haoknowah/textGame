import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.sql.ResultSet;
import java.sql.Connection;
public class EnemyDAO {
    public String dungeon;
    public EnemyDAO(String dungeon){
        this.dungeon=dungeon;
    }
    public Enemy initEnemy(int dist){
        String sql = "SELECT * FROM enemies WHERE env=?";
        Random rand = new Random();
        Enemy enemy;
        try (Connection connection = ConnectionUtil.getConnection()){
            String dun = "SELECT * FROM dungeons WHERE id=?";
            PreparedStatement locState = connection.prepareStatement(dun);
            locState.setString(1, dungeon);
            ResultSet locInfo = locState.executeQuery();
            locInfo.next();
            int min = locInfo.getInt("lmin");
            int max = locInfo.getInt("lmax");
            int leng = locInfo.getInt("leng");
            System.out.println(min + max + leng);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, dungeon);
            ResultSet rs = statement.executeQuery();
            ArrayList<Enemy> poss = new ArrayList<Enemy>();
            int lev = rand.nextInt(max - min) + min;
            while(rs.next()){
                String race = rs.getString("race");
                int rhp = rs.getInt("rhp");
                int ratk = rs.getInt("ratk");
                int rdef = rs.getInt("rdef");
                int rmag = rs.getInt("rmag");
                boolean boss = rs.getBoolean("boss");
                if(dist == leng){
                    boss = true;
                }
                 Enemy creature = new Enemy(lev, race, boss, rhp, ratk, rdef, rmag);
                 poss.add(creature);
             }
            enemy = poss.get(rand.nextInt(poss.size()-1)+1);
        }
        catch (SQLException e) {
            System.err.println("Error getting monster");
            e.printStackTrace();
            enemy = new Enemy(1, "blank", false, 0, 0, 0, 0);
        }
        return enemy;
    }
    public int getLeng(){
        int leng;
        try (Connection connection = ConnectionUtil.getConnection()){
            String dun = "SELECT * FROM dungeons WHERE id=?";
            PreparedStatement locState = connection.prepareStatement(dun);
            locState.setString(1, dungeon);
            ResultSet locInfo = locState.executeQuery();
            locInfo.next();
            leng = locInfo.getInt("leng");
        }
        catch (SQLException e) {
            System.err.println("Error getting length");
            e.printStackTrace();
            leng = 5;
        }
        return leng;
    }
}
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Connection;
public class Grimoire {
    public ArrayList<Spells> spellList;
    public Grimoire(){
        spellList = getSpellList();
    }
    public ArrayList<Spells> getSpellList(){
        ArrayList<Spells> grim = new ArrayList<Spells>();
        String sql = "SELECT * FROM grimoire";
        try (Connection connection = ConnectionUtil.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                int dmg = rs.getInt("dmg");
                boolean buff = rs.getBoolean("buff");
                boolean debuff = rs.getBoolean("debuff");
                String name = rs.getString("name");
                grim.add(new Spells(dmg, name, buff, debuff));
            }
        }
        catch (SQLException e) {
            System.err.println("Error getting spell list");
            e.printStackTrace();
            grim.add(new Spells());
        }
        return grim;
    }
    public void printList(){
        for (Spells spells : spellList) {
            System.out.println("Name: " + spells.getName() + " Power: " + spells.getDmg() + " Buff: " + 
                spells.getBuff() + " Debuff: " + spells.getDebuff());
        }
    }
    public boolean spellCheck(String spell, Character player, int sl){
        boolean good = false;
        for (Spells spells : spellList) {
            if(spell.equalsIgnoreCase(spells.getName())){
                good = true;
                player.setSlots(spells, sl);
            }
        }
        return good;
    }
}
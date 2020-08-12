import java.util.HashMap;
import java.util.logging.Logger;
public class Character extends Alive{
    
    public HashMap<String, Integer> choices;
    private Character(){
        this.setLvl(1);
        this.setHp(10 + (2 * this.lvl));
        this.setMhp(10 + (2 * this.lvl));
        this.setAtk(1 + this.lvl);
        this.setDef(this.lvl);
        this.setMag((int)(0.5 * this.lvl));
        this.setXp(0, false);
        this.setSlots((int)(this.lvl / 5) + 1);
        this.choices = new HashMap<>();
        this.choices.put("atk", 0);
        this.choices.put("def", 0);
        this.choices.put("mag", 0);
    }
    public void lvlup(){
        this.setLvl(this.lvl + 1);
        this.setXp(0, true);
    }
    public boolean statUp(String stat){
        if(stat.equalsIgnoreCase("atk")){
            int yub = this.choices.get("atk") + 1;
            this.choices.replace("atk", yub);
            return true;
        }
        if(stat.equalsIgnoreCase("def")){
            int yub = this.choices.get("def") + 1;
            this.choices.replace("def", yub);
            return true;
        }
        if(stat.equalsIgnoreCase("mag")){
            int yub = this.choices.get("mag");
            this.choices.replace("mag", yub);
            return true;
        }
        else{
            System.out.println("Invalid Stat");
            return false;
        }
    } 
    public void updateChar(){
        this.setMhp(10 + (2 * this.lvl));
        this.setHp(this.getMhp());
        this.setAtk(1 + this.lvl + this.choices.get("atk"));
        this.setDef(this.lvl + this.choices.get("def"));
        this.setMag((int)(0.5 * this.lvl) + this.choices.get("mag"));
        this.setSlots((int)(this.lvl / 5) + 1);
    }
}
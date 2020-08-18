import java.util.HashMap;
import java.util.logging.FileHandler;

public class Character extends Alive{
    
    public HashMap<String, Integer> choices;
    java.util.logging.Logger logger =  java.util.logging.Logger.getLogger(this.getClass().getName());

    public Character(){
        this.setLvl(1);
        this.setMhp(10 + (2 * this.getLvl()));
        this.setHp(10 + (2 * this.getLvl()));
        this.setAtk(1 + this.lvl);
        this.setDef(this.lvl);
        this.setMag((int)(0.5 * this.lvl));
        this.setXp(0, false);
        this.initSlots(this.getLvl());
        this.choices = new HashMap<>();
        this.choices.put("atk", 0);
        this.choices.put("def", 0);
        this.choices.put("mag", 0);
        try{
            logger.setUseParentHandlers(false);
            FileHandler fileHandle = new FileHandler("status.log");
            logger.addHandler(fileHandle);
        }
        catch(Exception e){
            logger.fine("Unable to build logger.");
        }
    }
    public void lvlup(){
        this.setLvl(this.lvl + 1);
        this.setXp(0, true);
        logger.info("This is for the log, not that one you perv.");
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
        if(this.getTotalSlots() < (int)(this.lvl / 5) + 1){
            System.out.println("New spell slot gained.");
            Spells[] nw = new Spells[((int)(this.lvl / 5) + 1)];
            for (int i = 0; i < this.getTotalSlots() - 1; i++) {
                nw[i] = this.getSlot(i);
            }
            nw[this.getTotalSlots() - 1] = new Spells();
            this.slots = nw;
        }
    }
}
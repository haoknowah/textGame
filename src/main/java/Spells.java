public class Spells {
    public int dmg;
    public String name;
    public boolean buff;
    public boolean debuff;
    public Spells(){
        //empty spell slot spell
        name = "Empty";
    }    
    public Spells(int dam, String nam, boolean buf, boolean debuf){
        dmg = dam;
        name = nam;
        buff = buf;
        debuff = debuf;
    }
    public int getDmg(){
        return this.dmg;
    }
    public String getName(){
        return this.name;
    }
    public boolean getBuff(){
        return this.buff;
    }
    public boolean getDebuff(){
        return this.debuff;
    }
}
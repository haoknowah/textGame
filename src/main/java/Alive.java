public abstract class Alive {
    protected int hp;
    protected int mhp;
    protected int atk;
    protected int def;
    protected int lvl;
    protected int mag;
    protected int xp;
    protected int slots;
    public void lvlup(){
        this.lvl = this.lvl + 1;
        this.setXp(0, true);
    }
    public int getAtk(){
        return this.atk;
    }
    public int getDef(){
        return this.def;
    }
    public int getHp(){
        return this.hp;
    }
    public int getMhp(){
        return this.mhp;
    }
    public int getMag(){
        return this.mag;
    }
    public int getXp(){
        return this.xp;
    }
    public int getLvl(){
        return this.lvl;
    }
    public void setXp(int exp, boolean reset){
        if(reset == true){
            this.xp = 0;
        }
        else{
            this.xp = this.xp + exp;
        }
    }
    public boolean setHp(int dmg){
        this.hp = this.hp + dmg;
        if(hp <= 0){
            System.out.println("Game Over");
            return false;
        }
        if(hp > this.mhp){
            this.hp = mhp;
            return true;
        }
        else{
            return true;
        }
    }
    public void setMhp(int x){
        this.mhp = x;
    }
    public void setAtk(int x){
        this.atk = x;
    }
    public void setDef(int x){
        this.def = x;
    }
    public void setMag(int x){
        this.mag = x;
    }
    public void setSlots(int x){
        this.slots = x;
    }
    public void setLvl(int x){
        this.lvl = x;
    }
}
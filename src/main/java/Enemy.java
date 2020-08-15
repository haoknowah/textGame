public class Enemy extends Alive{
    protected boolean boss;
    protected int regen;
    public Enemy(int lv, boolean bos){
        this.setLvl(lv);
        this.boss = bos;
        this.regen = 0;
        this.setMhp(10 + (2 * this.lvl));
        this.setHp(10 + (2 * this.lvl));
        this.setAtk(1 + this.lvl);
        this.setDef(this.lvl);
        this.setMag((int)(0.5 * this.lvl));
        this.setXp(0, false);
        this.initSlots(this.getLvl());
    }    
    public boolean bossStat(){
        return this.boss;
    }
    public void setRegen(int x){
        this.regen = x;
    }
    public int getRegen(){
        return regen;
    }
    public void mkBoss(){
        if(this.boss != true){
            this.boss = true;
        }
        this.setMhp(this.getMhp() * 2);
        this.setHp(this.getMhp());
        this.setAtk((int)(this.getAtk() * 1.2));
        this.setDef((int)(this.getDef() * 1.1));
        this.setMag((int)(this.getMag() * 1.1));
        this.regen = (int)(this.getLvl() / 4);
    }
}
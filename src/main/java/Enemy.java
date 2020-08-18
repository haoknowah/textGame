public class Enemy extends Alive{
    protected boolean boss;
    protected int regen;
    protected String name;
    public Enemy(int lv, String name, boolean bos, int rhp, int ratk, int rdef, int rmag){
        this.setLvl(lv);
        this.boss = bos;
        this.name = name;
        this.regen = 0;
        this.setMhp(10 + (2 * this.lvl) + rhp);
        this.setHp(10 + (2 * this.lvl) + rhp);
        this.setAtk(1 + this.lvl + ratk);
        this.setDef(this.lvl + rdef);
        this.setMag((int)(0.5 * this.lvl) + rmag);
        if(this.getMag() < 0){
            this.setMag(0);
        }
        this.setXp(0, false);
        this.initSlots(this.getLvl());
    }    
    public boolean bossStat(){
        return this.boss;
    }
    public String getName(){
        return this.name;
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
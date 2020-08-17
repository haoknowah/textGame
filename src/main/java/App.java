import java.util.Random;
import java.util.Scanner;
import java.util.Properties;
public class App {

    protected static Character player;
    private boolean play;
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        Properties config = setupGameConfig(in);
        play(in, config);
    }
    static public Properties setupGameConfig(Scanner in){
        Properties gameConfig = new Properties();
        gameConfig.setProperty("dungeon", "begin");
        int menuSelect = -1;
        System.out.print("Enter character name: ");
        String name = in.nextLine();
        gameConfig.setProperty("player", name);
        player = new Character();
        while(menuSelect != 0){
            System.out.println("1: Change dungeon (Current == " +
                gameConfig.getProperty("dungeon") + ")");
            System.out.println("2: Spell selection");
            System.out.println("0: Begin game");
            try {
                menuSelect = Integer.parseInt(in.nextLine());
            } catch (Exception InputMismatchException) {
                System.out.println("Enter a number you dolt.");
            }
            if(menuSelect <0 || menuSelect > 2){
                System.out.println("Invalid choice.");
            }
            System.out.println(menuSelect);
            switch(menuSelect){
                case 1:
                int dun = 0;
                System.out.println("1: begin");
                try {
                    dun = Integer.parseInt(in.nextLine());
                } catch (Exception InputMismatchException) {
                    System.out.println("Enter a number you dolt.");
                }
                if(dun < 1 || dun > 1){
                    System.out.println("Invalid choice");
                }
                break;
                case 2:
                
                break;
            }
        }
        //save character info to database or file
        return gameConfig;
    }

    /**
     * Returns the damage the user's attack would inflict on the target.
     * @param user the user of the attack
     * @param target the target of the attack
     * @param atkRoll a random value added to the damage
     * @return an int representing the damage in negatives, no greater than 0
     */
    public static int generateAttackDamage(Alive user, Alive target, int atkRoll)
    {
        int dmg = (user.getAtk() + atkRoll - target.getDef());
        return dmg >= 0? -dmg : 0;
    }

    /**
     * Returns the damage the user's spell would inflict on the target.
     * @param user the user of the spell
     * @param target the target of the spell
     * @param spell the spell used
     * @param atkRoll a random value added to the damage
     * @return an int representing the damage in negatives, no greater than 0
     */
    public static int generateSpellDamage(Alive user, Alive target, Spells spell, int atkRoll)
    {
        int mDmg = ((int) (user.getMag() * 0.75) + atkRoll - 
            target.getDef() + spell.getDmg());
        return mDmg >= 0? -mDmg : 0;
    }

    /**
     * Returns the amount that the user's spell would heal.
     * @param user the user of the spell
     * @param spell the healing spell
     * @return an int representing the amount healed, no less than 0.
     */
    public static int generateHealAmount(Alive user, Spells spell)
    {
        int heal = (int) (user.getMag() * 0.75) +
        spell.getDmg();
        return heal >= 0? heal : 0;
    }

    static public void play(Scanner in, Properties config){
        Random rand = new Random();
        boolean playerAlive = true;
        String dungeon = config.getProperty("dungeon");
        String name = config.getProperty("player");
        int menuSelect = 0;
        boolean cont = true;
        Enemy npc = new Enemy(1, false);
        boolean npcAlive = true;
        System.out.println("-----------Player----------");
        System.out.println(name + " LVL:" + player.getLvl());
        System.out.println(" HP:" + player.getHp() + "/" + 
            player.getMhp() + "| ATK: " + player.getAtk()
            + "| DEF: " + player.getDef() + "| MAG: " +
            player.getMag());
        System.out.println("1: attack");
        System.out.println("2: magic");
        System.out.println("3: observe");
        System.out.println("4: flee");
        try {
            menuSelect = Integer.parseInt(in.nextLine());
        }  catch (Exception InputMismatchException) {
            System.out.println("Enter a number you dolt.");
        }
        switch(menuSelect){
            case 1:
            int atkRoll = rand.nextInt(20);
            int dmg = generateAttackDamage(player, npc, atkRoll);
            npcAlive = npc.damageHp(dmg);
            System.out.println("Dealt " + -dmg + " damage.");
            break;
            case 2:
            int spellSelect = -1;
            for(int i = 0; i < player.getTotalSlots() - 1; i++){
                System.out.println(i + ": " + player.getSlot(i).getName());
            }
            try {
                spellSelect = Integer.parseInt(in.nextLine());
            }  catch (Exception InputMismatchException) {
                System.out.println("Enter a number you dolt.");
            }
            Spells chose = player.getSlot(spellSelect);
            if(chose.getBuff() == false)
            {
                int mAtkRoll = rand.nextInt(20);
                int mDmg = generateSpellDamage(player, npc, chose, mAtkRoll);
                npcAlive = npc.damageHp(mDmg);
                System.out.println("Dealt " + -mDmg + " damage.");
            }
            else{
                int heal = generateHealAmount(player, chose);
                player.damageHp(heal);
                System.out.println("Healed " + heal + " damage.");
            }
            break;
            case 3:
            System.out.println("LVL: " + npc.getLvl() + "| HP: "+ 
                npc.getHp() + "/" + npc.getMhp() + "| ATK: " +
                npc.getAtk() +  "| DEF: " + npc.getDef() +
                "| MAG: " + npc.getMag());
            break;
            case 4:
            if(npc.bossStat() == false){
                cont = false;
            }
            else{
                System.out.println("Can't escape.");
            }
            break;
        }
        if(npcAlive == true){   
            int eAtkRoll = rand.nextInt(20);
            int eDmg = generateAttackDamage(npc, player, eAtkRoll);
            if(npc.getRegen() > 0){
                npc.damageHp(npc.getRegen());
            }
            playerAlive = player.damageHp(eDmg);
            System.out.println("Took " + -eDmg + " damage.");
        }
    }
}
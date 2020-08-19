import java.util.Random;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public class App {
    //@param player = player character object thatt contains player stats
    //@param gameOn = boolean that determines if the game
    //@param nme = EnemyDAO object that accesses enemies from database and initiallizes one for combat
    //@param goetia = object that holds the spell list
    //@param threadPool = for multithreading and closing the program
    protected static Character player;
    private static boolean gameOn;
    protected static EnemyDAO nme;
    protected static Grimoire goetia;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(10);
    java.util.logging.Logger logger =  java.util.logging.Logger.getLogger(this.getClass().getName());
    public App(){
        try{
            logger.setUseParentHandlers(false);
            FileHandler fileHandle = new FileHandler("status.log");
            logger.addHandler(fileHandle);
        }
        catch(Exception e){
            logger.fine("Unable to build logger.");
        }
    }
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdown()));
        Properties config = setupGameConfig(in);
        play(in, config);
    }
    static public Properties setupGameConfig(Scanner in){
        //@param gameConfig = initiallizes player and selects dungeon and spells
        Properties gameConfig = new Properties();
        goetia = new Grimoire();
        gameConfig.setProperty("dungeon", "begin");
        int menuSelect = -1;
        System.out.print("Enter character name: ");
        String name = in.nextLine();
        gameConfig.setProperty("player", name);
        player = new Character();
        player.setSlots(new Spells(), 0);
        //following is the menu for the setup
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
                spellSelect(in);
                break;
            }
            nme = new EnemyDAO("begin");
        }
        //save character info to database or file
        return gameConfig;
    }
    static public void play(Scanner in, Properties config){
        //@param rand = creates random value to add some rng to damage calculation
        //@param menuSelect = selects what combat option to take
        //@param cont = boolean that determines whether to continue fighting due will, ie whether or not the player tries to flee
        //@param playerAlive = boolean that says if player hp is still positive
        //@param npcAlive = same as above for enemy
        //@param npc = object that holds stats for current opponent
        Random rand = new Random();
        gameOn = true;
        int dist = 1;
        while(gameOn == true){
            boolean playerAlive = true;
            String name = config.getProperty("player");
            int menuSelect = 0;
            boolean cont = true;
            Enemy npc = nme.initEnemy(dist);
            boolean npcAlive = true;
            while(cont == true && playerAlive == true && npcAlive == true){
                System.out.println("Enemy: " + npc.getName() + " " +
                    + (int)((float)npc.getHp()/(float)npc.getMhp()
                    * 100) + "%");
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
                    int dmg = -(player.getAtk() + atkRoll - npc.getDef());
                    if(dmg > 0){
                        dmg = 0;
                    }
                    npcAlive = npc.damageHp(dmg);
                    System.out.println("Dealt " + -dmg + " damage.");
                    break;
                    case 2:
                    int spellSelect = -1;
                    for(int i = 0; i <= player.getTotalSlots() - 1; i++){
                        System.out.println(i + ": " + player.getSlot(i).getName());
                    }
                    try {
                        spellSelect = Integer.parseInt(in.nextLine());
                    }  catch (Exception InputMismatchException) {
                        System.out.println("Enter a number you dolt.");
                    }
                    Spells chose = player.getSlot(spellSelect);
                    if(chose.getBuff() == false){
                        int mAtkRoll = rand.nextInt(20);
                        int mDmg = -((int) (player.getMag() * 0.75) + mAtkRoll - 
                            npc.getDef() + chose.getDmg());
                        if(mDmg > 0){
                            mDmg = 0;
                        }
                        if(chose.getName().equalsIgnoreCase("empty")){
                            mDmg = 0;
                        }
                        npcAlive = npc.damageHp(mDmg);
                        System.out.println("Dealt " + -mDmg + " damage.");
                    }
                    else{
                        int heal = (int) (player.getMag() * 0.75) +
                            chose.getDmg();
                        player.damageHp(heal);
                        System.out.println("Healed " + heal + " damage.");
                    }
                    player.setSlots(new Spells(), spellSelect);
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
                if(npcAlive == true && cont == true){   
                    int eAtkRoll = rand.nextInt(20);
                    int eDmg = -(npc.getAtk() + eAtkRoll - player.getDef());
                    if(eDmg > 0){
                        eDmg = 0;
                    }
                    if(npc.getRegen() > 0){
                        npc.damageHp(npc.getRegen());
                    }
                    playerAlive = player.damageHp(eDmg);
                    System.out.println("Took " + -eDmg + " damage.");
                }
                if(npcAlive == false){
                    dist = dist + 1;
                    int xp = npc.getLvl() * 5 / player.getLvl();
                    player.setXp(xp, false);
                    if(player.getXp() >= 10 * player.getLvl()){
                        levelUp(in);
                    }
                    System.out.println(npc.getName() + " has been defeated.");
                    System.out.println("Type c to continue.");
                    String c = in.nextLine();
                    if(c.equalsIgnoreCase("c") != true){
                        cont = false;
                    }
                }
            }
            //@param yub = continues the loop until proper input is recieved
            //upcoming loop activates if player beat the dungeon, quit, or died and asks if they want to go again
            boolean yub = false;
            while(yub == false && (dist > nme.getLeng() || cont == false || playerAlive == false)){
                System.out.println("Go again? y/n");
                String input = in.nextLine();
                if(input.equalsIgnoreCase("y")){
                    yub = true;
                    System.out.println("Type r to reprep spells");
                    String r = in.nextLine();
                    dist = 1;
                    if(r.equalsIgnoreCase("y")){
                        spellSelect(in);
                    }
                }
                if(input.equalsIgnoreCase("n")){
                    yub = true;
                    gameOn = false;
                    System.out.println("Thank you for playing.");
                }
                else{
                    System.out.println("Improper input.");
                }
            }
        }
    }
    public static void levelUp(Scanner in){
        //this function levels up the player
        player.lvlup();
        boolean up = false;
        while(up == false){
            System.out.println("Enter stat to increase: atk/def/mag");
            up = player.statUp(in.nextLine());
        }
        player.updateChar();
    }
    public static void spellSelect(Scanner in){
        for(int i = 0; i <= player.getTotalSlots() - 1; i++){
            System.out.println(player.getTotalSlots() - i
                + ":slots remain.");
            goetia.printList();
            String select;
            try {
                select = in.nextLine();
                if(goetia.spellCheck(select, player, i) == false){
                    i = i - 1;
                }
            } catch (Exception InputMismatchException) {
                System.out.println("Wrong input.");
            }
        }
    }
    private static Object shutdown() {
         // scan.close(); // Does Java do this on its own when ^C is pressed?
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.err.println("Failed to pause for 1 seconds.");
            e.printStackTrace();
        }
        threadPool.shutdown();
        return null;
    }
}
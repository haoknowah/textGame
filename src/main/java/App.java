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
    static public void play(Scanner in, Properties config){
        Random rand = new Random();
        boolean playerAlive = true;
        String dungeon = config.getProperty("dungeon");
        String name = config.getProperty("player");
        int menuSelect = 0;
        Enemy npc = new Enemy(1, false);
        boolean npcAlive = true;
        System.out.println("-----------Player----------");
        System.out.println(name + " LVL:" + player.getLvl());
        System.out.println(" HP:" + player.getHp() + "/" + 
            player.getMhp() + "| ATK: " + player.getAtk()
            + "| DEF: " + player.getDef() + "| MAG: " +
            player.getMag());
        System.out.println("1: attack");
        System.out.println("2: magic/skill");
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
            npcAlive = npc.damageHp(dmg);
            System.out.println("Dealt " + -dmg + " damage.");
            System.out.println(npc.getHp());
            break;
            case 2:
            break;
            case 3:
            break;
            case 4:
            break;
        }
    }
}
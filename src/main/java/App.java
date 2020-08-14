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
        int menuSelect = -1;
        System.out.print("Enter character name: ");
        String name = in.nextLine();
        while(menuSelect != 0){
            System.out.println("1: Change dungeon (Current == begin)");
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
                break;
                case 2:
                break;
            }
        }
        //save character info to database or file
        return gameConfig;
    }
    static public void play(Scanner in, Properties config){
        
    }
}

import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main {

    JFrame frame = new JFrame("Jep");
    JPanel panel = new JPanel();

    public static void main(String[] args) {
        Randomizer satun = new Randomizer();
        Scanner s = new Scanner(System.in);

        // KENTÄN KOKOJA JA SOLUJEN KOKOJA
//        int levelSize = 168; // Suurin
//        int levelSize = 120; // Suurempi
//        int levelSize = 72; // Suuri
        int levelSize = 48; // keski
//        int levelSize = 24; // pieni
//        int levelSize = 18; // pienin

//        int cellSize = levelSize/2; // 4 solua eli max koko ( 2 x 2 )
//        int cellSize = ( levelSize/3 ) ; //  9 solua ( 3 x 3 )
        int cellSize = (levelSize / 4); //  16 solua ( 4 x 4 )
//        int cellSize = ( levelSize/6 ) ; //  36 solua ( 6 x 6 ) | vain keskikoko tai suurempaan

        // KUINKA PALJON HUONEITA
        int maxRooms = (levelSize / cellSize) * (levelSize / cellSize) + 1; // Tätä enempää huoneita ei voi olla ( paitsi huoneiden sisäisiä ( WIP ) )
        int rooms = satun.randomBetween(4, maxRooms); // 4 - maximi määrä huoneita
//        int rooms = maxRooms-1; // Aina max

        // HUONEIDEN KOOT
//        int huoneKoko = 4; // Pienin koko
        int huoneKoko = cellSize / 2; // Puolikas koko maximista
//        int huoneKoko = cellSize; // Maximikoko

        int biome = 1;

        // LUO KENTÄN, LISÄÄ HUONEET
        Floor f1 = new Floor(levelSize, cellSize, true, false, biome);
        f1.addRooms(rooms, huoneKoko, 0);

        // KÄYTÄVIEN KOKO
        int corridorSize = 2;

        // MILLÄ TAVALLA KÄYTÄVÄT
        Corridor c = new Corridor(f1, corridorSize, true, biome);
//        c.findCellsFromMiddleAndGenerate(); // Varmaan paras
//        c.findCellsClosestAndGenerate();
//        c.findCellsTopToBottomAndGenerate(); // Tylsin
//        c.doorways();
        c.allToOneCoord(levelSize / 2, levelSize / 2);

//        c.ConnectTwo(0, 0, 0, 3);
//        c.ConnectTwo(0, 0, 3, 0);
//        c.ConnectTwo(3, 0, 3, 3);
//        c.ConnectTwo(0, 3, 3, 3);
//        c.ConnectTwo(3, 0, 0, 3);
//        c.ConnectTwo(0, 0, 3, 3);
        //f1.printFloor();
        int eka = 5;
        int toka = 9;
        Randomizer rand = new Randomizer();
        int lol = (rand.randomBetween(eka, toka));
        System.out.println(lol);
        
        World w = new World();
//        w.createCave();
//        w.createCaveDia();
//        w.createPlain();
//        w.firstFloor();
//        w.createCity();
        GUI gui = new GUI(w);

    }

}

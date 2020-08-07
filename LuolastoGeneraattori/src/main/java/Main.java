
import java.util.concurrent.ThreadLocalRandom;


public class Main {

    public static void main(String[] args) {
        Floor[] floors = new Floor[10];
        int levelSize = 20;
        int cellSize = 10;
        
        int maxRooms = (levelSize/cellSize) * (levelSize/cellSize)+1;
        int rooms = ThreadLocalRandom.current().nextInt(2, maxRooms);
        
        Floor f1 = new Floor(levelSize, cellSize);
        f1.addRooms(rooms);
        
        Corridor c = new Corridor(f1);
        c.findMiddleOfCell();
        
        floors[0] = f1;
        f1.printFloor();

    }
    
}

import java.util.concurrent.ThreadLocalRandom;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author santt
 */
public class JTesting {
    
    int levelSize = 30;
    int cellSize = 10;
    
    int maxRooms = (levelSize/cellSize) * (levelSize/cellSize)+1;
    int rooms = ThreadLocalRandom.current().nextInt(4, maxRooms);
        
    private Floor f1;
    
    private Room room;
    
    private Tile t1;
    private Tile t2;
    private Tile t3;
    private Tile t4;
    private Tile t5;
    
    public JTesting() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        t1 = new Tile(0);
        t2 = new Tile(1);
        t3 = new Tile(2);
        t4 = new Tile(3);
        t5 = new Tile(4);
    }
    
    @After
    public void tearDown() {
    }

    
    
    @Test
    public void tileCreation() {
        assertEquals(0,t1.getType());
        assertEquals(1,t2.getType());
        assertEquals(2,t3.getType());
        assertEquals(3,t4.getType());
        assertEquals(4,t5.getType());
        
        assertEquals("X",t1.getVisual());
        assertEquals(" ",t2.getVisual());
        assertEquals("*",t3.getVisual());
        assertEquals("m",t4.getVisual());
        assertEquals(" ",t5.getVisual());
    }
    
    @Test
    public void customTile() {
        t1 = new Tile("Testi");
        assertEquals(10,t1.getType());
        assertEquals("Testi",t1.getVisual());
    }
    
    @Test
    public void roomCreation() {
        room = new Room(16,16);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (i == 16/2 & j == 16/2) {
                    assertEquals(room.getTilesh()[i][j].getType(), 3);
                } else if (i == 0 | j == 0 | i == 15 | j == 15) {
                    assertEquals(room.getTilesh()[i][j].getType(), 0);
                } else {
                    assertEquals(room.getTilesh()[i][j].getType(), 1);
                }
            }
        }
    }
    
    @Test
    public void floorCreation() {
        f1 = new Floor(16, 8);
        Room r = new Room(16,16,1);
        Cell c = new Cell(r,17);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                assertEquals(c.getRoom().getTilesh()[i][j].getType(), f1.getStage().getTilesh()[i][j].getType());
            }
        }
        assertEquals(f1.getLevel().length, 2);
    }
    
    @Test
    public void floor() {
        f1 = new Floor(levelSize, cellSize);
        
        f1.addRooms(rooms);
        
        Corridor c = new Corridor(f1);
        c.findCellsAndGenerate();
        
        f1.printFloor();
        t1 = new Tile();
    }
}

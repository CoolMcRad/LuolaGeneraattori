
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Rule;
import static org.junit.Assert.*;
import org.junit.rules.Timeout;

/**
 *
 * @author santt
 */
public class JTesting {

    Randomizer satunnaisuus = new Randomizer();
    int levelSize = 48;
    int cellSize = levelSize / 4;

    int maxRooms = (levelSize / cellSize) * (levelSize / cellSize) + 1;
    int rooms = satunnaisuus.randomBetween(4, maxRooms);

    private Floor f1;

    private Room room;

    private Tile t1;

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
    }

    @After
    public void tearDown() {
    }

    @Rule
    public Timeout globalTimeout = new Timeout(3000);

    @Test
    public void tileCreation() {

        assertEquals(0, t1.getType());
        assertEquals("X", t1.getVisual());

        t1 = new Tile(1);
        assertEquals(1, t1.getType());
        assertEquals(" ", t1.getVisual());

        t1 = new Tile(2);
        assertEquals(2, t1.getType());
        assertEquals("*", t1.getVisual());

        t1 = new Tile(3);
        assertEquals(3, t1.getType());
        assertEquals("m", t1.getVisual());

        t1 = new Tile(4);
        assertEquals(4, t1.getType());
        assertEquals(" ", t1.getVisual());
    }

    @Test
    public void customTile() {
        t1 = new Tile("Testi");
        assertEquals(10, t1.getType());
        assertEquals("Testi", t1.getVisual());
    }

    @Test
    public void roomCreation() {
        room = new Room(16, 16, false, true);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (i == 16 / 2 & j == 16 / 2) {
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
        f1 = new Floor(16, 8, false, false);
        Room r = new Room(18, 18, false, true);
        Cell c = new Cell(r, 17);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                assertEquals(c.getRoom().getTilesh()[i][j].getType(), f1.getStage().getTilesh()[i][j].getType());
            }
        }
        assertEquals(f1.getLevel().length, 2);
    }

    @Test
    public void floor() {
        f1 = new Floor(levelSize, cellSize, false, false);

        f1.addRooms(rooms, 4);

        Corridor c = new Corridor(f1, 1, false);
        c.findCellsFromMiddleAndGenerate();

    }
    
    @Test
    public void semiTopToBottom() {
        f1 = new Floor(levelSize, cellSize, true, false);

        f1.addRooms(rooms, cellSize/2);

        Corridor c = new Corridor(f1, 1, true);
        c.findCellsTopToBottomAndGenerate();
        f1.printFloor();
    }
    
    @Test
    public void eriCorridor() {
        f1 = new Floor(levelSize, cellSize, true, false);

        f1.addRooms(rooms, cellSize/2);

        Corridor c = new Corridor(f1, 1, true);
        c.findCellsFromMiddleAndGenerate();
        f1.printFloor();
    }

    @Test
    public void ulkoTilaYksiTalo() {
        f1 = new Floor(levelSize, cellSize, true, true);

        f1.addRooms(1, cellSize / 2);

        Corridor c = new Corridor(f1, 2, false);
        c.doorways();

        f1.printFloor();
        t1 = new Tile();
    }

    @Test
    public void luola() {
        f1 = new Floor(levelSize, cellSize, true, false);

        f1.addRooms(rooms, cellSize / 2);

        Corridor c = new Corridor(f1, 3, false);
        c.findCellsFromMiddleAndGenerate();

        f1.printFloor();
        t1 = new Tile();
    }

    @Test
    public void linna() { // Tässä bugi, kun huone määrä vähän liian suuri, tapahtuu loputon looppi.
        f1 = new Floor(levelSize, cellSize, false, false);

        int k = f1.getLevel().length - 1;
        Room r = new Room(cellSize, cellSize, false, true);
        f1.getLevel()[0][0].setRoom(r, 0, 0, cellSize);
        f1.getLevel()[k][k].setRoom(r, k, k, cellSize);
        f1.getLevel()[k][0].setRoom(r, k, 0, cellSize);
        f1.getLevel()[0][k].setRoom(r, 0, k, cellSize);

        f1.addRooms(rooms, cellSize / 2);

        Corridor c = new Corridor(f1, 3, false);
        c.findCellsFromMiddleAndGenerate();
        c.ConnectTwo(0, 0, 0, k);
        c.ConnectTwo(0, 0, k, 0);
        c.ConnectTwo(k, 0, k, k);
        c.ConnectTwo(0, k, k, k);

        f1.printFloor();
        t1 = new Tile();
    }

    @Test
    public void luolaDiagonal() {
        f1 = new Floor(levelSize, cellSize, true, false);

        f1.addRooms(rooms, cellSize / 2);

        Corridor c = new Corridor(f1, 3, true);
        c.findCellsFromMiddleAndGenerate();

        f1.printFloor();
        t1 = new Tile();
    }

    @Test
    public void crossRoom() {

        Room cross = new Room(10, 10, false, true, true, 1);
        cross.setType(1);
        cross.printRoom();
    }
    @Test
    public void crossLuola() {

        f1 = new Floor(levelSize, cellSize, false, false);
        int k = f1.getLevel().length - 1;
        f1.addRooms(rooms, cellSize - 3);

        Corridor c = new Corridor(f1, 3, true);
        c.ConnectTwo(0, 0, 0, k);
        c.ConnectTwo(0, 0, k, 0);
        c.ConnectTwo(k, 0, k, k);
        c.ConnectTwo(0, k, k, k);
        c.ConnectTwo(0, 0, k, k);
        c.ConnectTwo(0, k, k, 0);

        f1.printFloor();
    }
    
    @Test
    public void crossLuola2() {

        f1 = new Floor(72, 72/6, false, false);
        int k = f1.getLevel().length - 1;
        f1.addRooms(rooms, cellSize - 3);

        Corridor c = new Corridor(f1, 2, true);
        c.ConnectTwo(0, 0, 0, k);
        c.ConnectTwo(0, 0, k, 0);
        c.ConnectTwo(k, 0, k, k);
        c.ConnectTwo(0, k, k, k);
        c.ConnectTwo(k/2, 0, k/2, k);
        c.ConnectTwo(k/2+1, 0, k/2+1, k);
        c.ConnectTwo(0, k/2+1, k, k/2+1);
        c.ConnectTwo(0, k/2, k, k/2);

        f1.printFloor();
    }
    
    @Test
    public void semiDiagonallyYay() {

        f1 = new Floor(levelSize, cellSize, false, false);

        int k = f1.getLevel().length - 1;
        Room r = new Room(cellSize, cellSize, 0);
        f1.getLevel()[0][1].setRoom(r, 0, 1, cellSize);
        f1.getLevel()[k][0].setRoom(r, k, 0, cellSize);
        f1.getLevel()[0][3].setRoom(r, 0, 3, cellSize);
        f1.getLevel()[k][2].setRoom(r, k, 2, cellSize);
        
        f1.addRooms(rooms, cellSize / 2);
        
        Corridor c = new Corridor(f1, 3, true);
        c.ConnectTwo(0, 1, k, 0);
        c.ConnectTwo(0, 3, k, 2);
        c.ConnectTwo(0, 1, k, k);

        f1.printFloor();
    }
}

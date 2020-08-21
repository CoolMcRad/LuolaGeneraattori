
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
    
    int biome = 0;

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
        t1 = new Tile(0,biome);
    }

    @After
    public void tearDown() {
    }

    @Rule
    public Timeout globalTimeout = new Timeout(3000);

    @Test
    public void tileCreation() {
        biome = 0;
        assertEquals(0, t1.getType());
        assertEquals("m", t1.getVisual());

        t1 = new Tile(1,biome);
        assertEquals(1, t1.getType());
        assertEquals(" ", t1.getVisual());

        t1 = new Tile(2,biome);
        assertEquals(2, t1.getType());
        assertEquals("D", t1.getVisual());

        t1 = new Tile(3,biome);
        assertEquals(3, t1.getType());
        assertEquals(" ", t1.getVisual());

        t1 = new Tile(4,biome);
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
        room = new Room(16, 16, false, true, biome);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (i == 16 / 2 & j == 16 / 2) {
                    assertEquals(room.getTilesh()[i][j].getType(), 0);
                } else if (i == 0 | j == 0 | i == 15 | j == 15) {
                    assertEquals(room.getTilesh()[i][j].getType(), 5);
                } else {
                    assertEquals(room.getTilesh()[i][j].getType(), 3);
                }
            }
        }
    }

    @Test
    public void floorCreation() {
        f1 = new Floor(16, 8, false, false,biome);
        Room r = new Room(18, 18, false, true,biome);
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
        f1 = new Floor(levelSize, cellSize, false, false, biome);

        f1.addRooms(rooms, 4,0);

        Corridor c = new Corridor(f1, 1, false,biome);
        c.findCellsFromMiddleAndGenerate();

    }
    
    @Test
    public void semiTopToBottom() {
        System.out.println("semiTopToBottom");
        f1 = new Floor(levelSize, cellSize, true, false,biome);

        f1.addRooms(rooms, cellSize/2,0);

        Corridor c = new Corridor(f1, 1, true,biome);
        c.findCellsTopToBottomAndGenerate();
        f1.printFloor();
    }
    
    @Test
    public void eriCorridor() {
        System.out.println("eriCorridor");
        f1 = new Floor(levelSize, cellSize, true, false,biome);

        f1.addRooms(rooms, cellSize/2,0);

        Corridor c = new Corridor(f1, 1, true,biome);
        c.findCellsFromMiddleAndGenerate();
        f1.printFloor();
    }

    @Test
    public void ulkoTilaYksiTalo() {
        System.out.println("ulkoTilaYksiTalo");
        biome = 1;
        f1 = new Floor(levelSize, cellSize, true, true, biome);

        f1.addRooms(5, cellSize / 2,0);

        Corridor c = new Corridor(f1, 2, false, biome);
        c.doorways();

        f1.printFloor();
    }
    
    @Test
    public void ulkoTilaDoorwaysTest() {
        System.out.println("ulkoTilaDoorwaysTest");
        cellSize = levelSize/3;
        biome = 1;
        f1 = new Floor(levelSize, cellSize, true, true, biome);

        int k = f1.getLevel().length - 1;
        Room r = new Room(cellSize-2, cellSize-2, false, true, biome);
        f1.placeRoom(r,0,0,cellSize);
        f1.placeRoom(r,k,k,cellSize);
        f1.placeRoom(r,k,0,cellSize);
        f1.placeRoom(r,0,k,cellSize);
        f1.placeRoom(r,k/2,0,cellSize);
        f1.placeRoom(r,0,k/2,cellSize);
        f1.placeRoom(r,k/2,k,cellSize);
        f1.placeRoom(r,k,k/2,cellSize);
        
        f1.addRooms(0, 0,0);

        Corridor c = new Corridor(f1, 2, false, biome);
        c.doorways();

        f1.printFloor();
    }
    
    @Test
    public void ulkoTilaDoorwaysTest2() {
        System.out.println("ulkoTilaDoorwaysTest2");
        cellSize = levelSize/3;
        biome = 1;
        f1 = new Floor(levelSize, cellSize, true, true, biome);

        int k = f1.getLevel().length - 1;
        Room r = new Room(cellSize-2, cellSize-2, false, true, biome);
        f1.placeRoom(r,k,k,cellSize);
        f1.placeRoom(r,k,0,cellSize);
        f1.placeRoom(r,0,k,cellSize);
        f1.placeRoom(r,k/2,0,cellSize);
        f1.placeRoom(r,0,k/2,cellSize);
        f1.placeRoom(r,k,k/2,cellSize);
        
        f1.addRooms(0, 0,0);

        Corridor c = new Corridor(f1, 2, false, biome);
        c.doorways();

        f1.printFloor();
    }

    @Test
    public void luola() {
        System.out.println("luola");
        biome = 0;
        f1 = new Floor(levelSize, cellSize, true, false, biome);

        f1.addRooms(rooms, cellSize / 2,0);

        Corridor c = new Corridor(f1, 3, false, biome);
        c.findCellsFromMiddleAndGenerate();

        f1.printFloor();
    }

    @Test
    public void linna() {
        System.out.println("linna");
        biome = 2;
        f1 = new Floor(levelSize, cellSize, false, false, biome);
        
        rooms = satunnaisuus.randomBetween(4, maxRooms);
        
        int k = f1.getLevel().length - 1;
        Room r = new Room(cellSize, cellSize, false, true, biome);
        f1.placeRoom(r,0,0,cellSize);
        f1.placeRoom(r,k,k,cellSize);
        f1.placeRoom(r,k,0,cellSize);
        f1.placeRoom(r,0,k,cellSize);

        f1.addRooms(rooms, cellSize / 2,0);

        Corridor c = new Corridor(f1, 3, false, biome);
        c.findCellsFromMiddleAndGenerate();
        c.ConnectTwo(0, 0, 0, k);
        c.ConnectTwo(0, 0, k, 0);
        c.ConnectTwo(k, 0, k, k);
        c.ConnectTwo(0, k, k, k);

        f1.printFloor();
    }

    @Test
    public void luolaDiagonal() {
        System.out.println("luolaDiagonal");
        biome = 0;
        f1 = new Floor(levelSize, cellSize, true, false, biome);

        f1.addRooms(rooms, cellSize / 2, 0);

        Corridor c = new Corridor(f1, 3, true, biome);
        c.findCellsFromMiddleAndGenerate();

        f1.printFloor();
    }

    @Test
    public void crossRoom() {
        System.out.println("crossRoom");
        biome = 2;

        Room cross = new Room(10, 10, false, false, 1, biome);
        cross.setType(1);
        cross.printRoom();
    }
    
    @Test
    public void crossRooms() {
        System.out.println("crossRooms");
        biome = 2;

        f1 = new Floor(levelSize, cellSize, false, false, biome);
        f1.addRooms(rooms, cellSize/2,1);

        Corridor c = new Corridor(f1, 2, false, biome);
        c.findCellsFromMiddleAndGenerate();
        f1.printFloor();
    }
    
    @Test
    public void crossLuola() {
        System.out.println("crossLuola");
        biome = 0;
        f1 = new Floor(levelSize, cellSize, true, false, biome);
        int k = f1.getLevel().length - 1;
        f1.addRooms(rooms, cellSize - 3,0);

        Corridor c = new Corridor(f1, 3, true, biome);
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
        System.out.println("crossLuola2");
        biome = 0;
        f1 = new Floor(72, 72/6, false, false, biome);
        int k = f1.getLevel().length - 1;
        f1.addRooms(rooms, cellSize - 3,0);

        Corridor c = new Corridor(f1, 2, true, biome);
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
        System.out.println("semiDiagonallyYay");
        biome = 1;
        f1 = new Floor(levelSize, cellSize, false, false, biome);
        rooms = satunnaisuus.randomBetween(4, maxRooms);
        int k = f1.getLevel().length - 1;
        Room r = new Room(cellSize, cellSize, false, true, biome);
        f1.placeRoom(r,0,1,cellSize);
        f1.placeRoom(r,k,0,cellSize);
        f1.placeRoom(r,0,3,cellSize);
        f1.placeRoom(r,k,2,cellSize);
        
        f1.addRooms(rooms, cellSize / 2,0);
        
        Corridor c = new Corridor(f1, 3, true, biome);
        c.ConnectTwo(0, 1, k, 0);
        c.ConnectTwo(0, 3, k, 2);
        c.ConnectTwo(0, 1, k, k);

        f1.printFloor();
    }
}

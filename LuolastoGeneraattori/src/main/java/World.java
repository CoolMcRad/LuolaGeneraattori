
public class World {

    Floor[] floors = new Floor[100];
    Player p;
    int currentFloor = 0;
    int nextFloor = 0;
    Randomizer satun = new Randomizer();
    int levelSize = 48;
    int cellSize = (levelSize / 4);
    int maxRooms = (levelSize / cellSize) * (levelSize / cellSize) + 1;
    int rooms = satun.randomBetween(4, maxRooms);
    ;
    int huoneKoko = cellSize / 2;
    int corridorSize = 2;
    Corridor c;

    public World() {
    }

    public World(int levelSize, int cellSize) {
        this.levelSize = levelSize;
        this.cellSize = levelSize / cellSize;
        maxRooms = (levelSize / cellSize) * (levelSize / cellSize) + 1;
        rooms = satun.randomBetween(4, maxRooms);
        huoneKoko = cellSize / 2;
    }

    public void createFloor(int biome) {
        Floor f1 = new Floor(levelSize, cellSize, true, false, biome);
        f1.addRooms(rooms, huoneKoko, 0);
        Corridor c = new Corridor(f1, corridorSize, true, biome);
        c.allToOneCoord(levelSize / 2, levelSize / 2);
        addFloor(f1);
    }

    public void createCaveDia() {
        int biome = 0;
        Floor f = new Floor(levelSize, cellSize, true, false, biome);
        f.addRooms(rooms, huoneKoko, 0);
        c = new Corridor(f, corridorSize, true, biome);
        c.findCellsClosestAndGenerate();
        addFloor(f);
    }

    public void createCave() {
        int biome = 0;
        Floor f = new Floor(levelSize, cellSize, true, false, biome);
        f.addRooms(rooms, huoneKoko, 0);
        c = new Corridor(f, corridorSize, false, biome);
        c.findCellsFromMiddleAndGenerate();
        addFloor(f);
    }

    public void createPlain() {
        int biome = 1;
        Floor f = new Floor(levelSize, cellSize, true, true, biome);
        f.addRooms(rooms, huoneKoko, 0);
        c = new Corridor(f, corridorSize, false, biome);
        c.doorways();
        addFloor(f);
    }

    public void addFloor(Floor f) {
        floors[nextFloor] = f;
        nextFloor++;
    }

    public void printFloor(int index) {
        floors[index].printFloor();
    }

    public void playerTest() {
        Tile lol = floors[currentFloor].getStage().tilesh[p.getPositionx()][p.getPositiony()];
        floors[currentFloor].getStage().tilesh[p.getPositionx()][p.getPositiony()] = p.t;
        p.setO(lol);
    }

    public void playerSpawn() {
        for (int i = 1; i < levelSize; i++) {
            for (int j = 1; j < levelSize; j++) {
                if (checkWalkable(i, j)) {
                    p = new Player(i, j);
                    break;
                }
            }
        }
    }

    public void playerTestMove(String direction) {
        floors[currentFloor].getStage().tilesh[p.getPositionx()][p.getPositiony()] = p.getO();
        int x = p.positionx;
        int y = p.positiony;
        if (direction.equals("l") & checkWalkable(x, y - 1)) {
            p.moveLeft();
        } else if (direction.equals("r") & checkWalkable(x, y + 1)) {
            p.moveRight();
        } else if (direction.equals("u") & checkWalkable(x - 1, y)) {
            p.moveUp();
        } else if (direction.equals("d") & checkWalkable(x + 1, y)) {
            p.moveDown();
        }
        playerTest();
    }

    public boolean checkWalkable(int x, int y) {
        if (!floors[currentFloor].getStage().tilesh[x][y].isWalkable()) {
            return false;
        }
        return true;
    }
}

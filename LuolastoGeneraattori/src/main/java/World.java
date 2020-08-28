
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
        firstFloor();
    }

    public World(int levelSize, int cellSize) {
        this.levelSize = levelSize;
        this.cellSize = levelSize / cellSize;
        maxRooms = (levelSize / cellSize) * (levelSize / cellSize) + 1;
        rooms = satun.randomBetween(4, maxRooms);
        huoneKoko = cellSize / 2;
        firstFloor();
    }

    private void ini() {
        playerSpawn();
        playerTest();
    }

    private void firstFloor() {
        int bio = 0;
        int ls = 24;
        int cs = (ls / 2);
        int mr = (ls / cs) * (ls / cs) + 1;
        int r = mr - 1;
        int hk = cs;
        Floor f = new Floor(ls, cs, true, false, bio);
        f.addRooms(r, hk, 0);
        Corridor c = new Corridor(f, 3, true, bio);
        c.allToOneCoord(ls / 2, ls / 2);
        f.getStage().getTilesh()[ls / 2][ls / 2] = new Tile(2, bio, currentFloor + 1);
        addFloor(f);
        p = new Player((ls / 2) - 3, (ls / 2) - 3);
        playerTest();
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

    public void createCity() {
        int biome = 2;
        Floor f = new Floor(levelSize, cellSize, true, true, biome);
        f.addRooms(rooms, huoneKoko, 0);
        c = new Corridor(f, corridorSize, false, biome);
        c.findCellsFromMiddleAndGenerate();
        addFloor(f);
    }

    public void addFloor(Floor f) {
        floors[nextFloor] = f;
        nextFloor++;
    }

    public Tile[][] getFloor() {
        return floors[currentFloor].getStage().getTilesh();
    }

    public void printFloor(int index) {
        floors[index].printFloor();
    }

    public void playerTest() {
        Tile lol = floors[currentFloor].getStage().tilesh[p.getPositionx()][p.getPositiony()];
        if (lol.getType() == 2) {
            if (lol.doorTo >= nextFloor) {
                int uusbiome = this.satun.nano(0,5);
                System.out.println(uusbiome);
                switch (uusbiome) {
                    case 1:
                        createCaveDia();
                        break;
                    case 2:
                        createPlain();
                        break;
                    case 3:
                        createCity();
                        break;
                    
                    default:
                        createCave();
                        break;
                }
                currentFloor = lol.doorTo;
                createDoor();
            } else {
                currentFloor = lol.doorTo;
            }
            ini();
        } else {
            floors[currentFloor].getStage().tilesh[p.getPositionx()][p.getPositiony()] = p.t;
            p.setO(lol);
        }
    }

    public void playerSpawn() {
        boolean jep = false;
        for (int i = 1; i < this.floors[currentFloor].getStage().getHeight(); i++) {
            for (int j = 1; j < this.floors[currentFloor].getStage().getWidth(); j++) {
                if (checkWalkable(j, i)) {
                    p = new Player(j, i);
                    if (currentFloor != 0) {
                        this.floors[currentFloor].getStage().getTilesh()[j][i - 1] = new Tile(2, this.floors[currentFloor].biome, currentFloor - 1);
                    }
                    jep = true;
                    break;
                }
            }
            if (jep) {
                break;
            }
        }
    }

    public void playerTestMove(String direction) {
        floors[currentFloor].getStage().tilesh[p.getPositionx()][p.getPositiony()] = p.getO();
        int x = p.positionx;
        int y = p.positiony;
        if (direction.equals("a") & checkWalkable(x - 1, y)) {
            p.moveLeft();
        } else if (direction.equals("d") & checkWalkable(x + 1, y)) {
            p.moveRight();
        } else if (direction.equals("w") & checkWalkable(x, y - 1)) {
            p.moveUp();
        } else if (direction.equals("s") & checkWalkable(x, y + 1)) {
            p.moveDown();
        }
        playerTest();
    }

    public boolean checkWalkable(int x, int y) {
        return floors[currentFloor].getStage().tilesh[x][y].isWalkable();
    }

    private void createDoor() {
        boolean jep = false;
        for (int i = this.floors[currentFloor].getStage().getHeight()-1; i > 1; i--) {
            for (int j = this.floors[currentFloor].getStage().getWidth()-1; j > 1; j--) {
                if (this.floors[currentFloor].getStage().getTilesh()[j][i].inRoom) {
                    this.floors[currentFloor].getStage().getTilesh()[j][i] = new Tile(2, this.floors[currentFloor].biome, currentFloor + 1);
                    jep = true;
                    break;
                }
            }
            if (jep) {
                break;
            }
        }
    }
}

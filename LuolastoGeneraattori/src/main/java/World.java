
import java.util.Arrays;

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
    int huoneKoko = cellSize / 2;
    int corridorSize = 2;
    Corridor c;
    boolean dia = false;

    int[] nls = {48, 72, 120, 168};
    int[] ncs = {2, 3, 4, 6};
    int[] cors = {2, 3, 4, 5};
    int[] nhk = {0, 2, 3};
    int[] ndia = {0, 1};
    int[] tieStyle = {0, 1, 2};

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
        f.setExit(ls / 2, ls / 2);
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

    public void doorEnter() {
        Tile lol = p.getO();
        System.out.println("lol");
        if (lol.getType() == 2) {
            if (lol.doorTo >= nextFloor) {
                randomCreation();
                floors[currentFloor].getStage().tilesh[p.getPositionx()][p.getPositiony()] = lol;
                currentFloor = lol.doorTo;
                createDoor();
            } else {
                floors[currentFloor].getStage().tilesh[p.getPositionx()][p.getPositiony()] = lol;
                currentFloor = lol.doorTo;
            }
            ini();
        }
    }

    public void playerTest() {
        Tile lol = floors[currentFloor].getStage().tilesh[p.getPositionx()][p.getPositiony()];
        floors[currentFloor].getStage().tilesh[p.getPositionx()][p.getPositiony()] = p.t;
        p.setO(lol);

    }

    public void playerSpawn() {
        boolean jep = false;
        if (currentFloor == nextFloor - 1) {
            for (int i = 1; i < this.floors[currentFloor].getStage().getHeight(); i++) {
                for (int j = 1; j < this.floors[currentFloor].getStage().getWidth(); j++) {
                    if (checkWalkable(j, i) & floors[currentFloor].getStage().tilesh[i][j].isInRoom() & this.floors[currentFloor].getStage().getTilesh()[j][i].type == 3) {
                        this.p.setCoordinates(j, i);
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
        } else {
            int[] ex = this.floors[currentFloor].getExit();
            this.p.setCoordinates(ex[0], ex[1]);
        }
        spawnEnemies();
    }

    public void playerTestMove(String direction) {
        if (!direction.equals("") & !p.dead) {
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
    }

    public boolean checkWalkable(int x, int y) {
        return floors[currentFloor].getStage().tilesh[x][y].isWalkable();
    }

    private void createDoor() {
        boolean jep = false;
        for (int i = this.floors[currentFloor].getStage().getHeight() - 1; i > 1; i--) {
            for (int j = this.floors[currentFloor].getStage().getWidth() - 1; j > 1; j--) {
                if (this.floors[currentFloor].getStage().getTilesh()[j][i].isInRoom() & this.floors[currentFloor].getStage().getTilesh()[j][i].type == 3) {
                    this.floors[currentFloor].getStage().getTilesh()[j][i] = new Tile(2, this.floors[currentFloor].biome, currentFloor + 1);
                    this.floors[currentFloor].setExit(j, i);
                    jep = true;
                    break;
                }
            }
            if (jep) {
                break;
            }
        }
    }

    public Floor generateNewFloor(int biome, int ls, int cs, int r, int hk, boolean dia, int corSize) {

        boolean randOutside = true;
        if (biome == 0) {
            randOutside = false;
        }
        Floor f = new Floor(ls, cs, true, randOutside, biome);
        f.addRooms(r, hk, 0);
        c = new Corridor(f, corSize, dia, biome);
        if (biome == 1) {
            c.doorways();
        } else {
            c.findCellsFromMiddleAndGenerate();
        }
        return f;
    }

    public void randomCreation() {
        int biome = satun.randomBetween(0, 3);
        int ls = satun.randomBetween(0, 4);
//        ls = nls[ls];
        ls = 48;
        int cs = satun.randomBetween(0, 4);
        cs = ncs[cs];
        int cor = satun.randomBetween(0, 3);
        cor = cors[cor];
        cs = ls / cs;
        int mr = (ls / cs) * (ls / cs) + 1;
        int hk = satun.randomBetween(0, 3);
        switch (hk) {
            case 0:
                hk = 5;
                break;
            case 3:
                hk = cs - 1;
                break;
            default:
                hk = cs / 2;
                break;
        }
        boolean ran = false;
        if (biome != 0) {
            ran = true;
        }
        boolean diako;
        if (biome != 0) {
            diako = false;
        } else {
            diako = satun.randomBetween(0, 2) != 0;
        }
        int ro = satun.randomBetween(2, mr);
        Floor f = new Floor(ls, cs, true, ran, biome);
        f.addRooms(ro, hk, 0);
        c = new Corridor(f, cor, diako, biome);
        int style = satun.randomBetween(0, 3);
        if (biome == 1) {
            c.doorways();
        } else if (style == 0) {
            c.findCellsFromMiddleAndGenerate();
        } else if (style == 1) {
            c.findCellsClosestAndGenerate();
        } else if (style == 2) {
            c.findCellsTopToBottomAndGenerate();
        }
        addFloor(f);
    }

    private void spawnEnemies() {
        for (int i = 1; i < this.floors[currentFloor].getStage().getHeight(); i++) {
            for (int j = 1; j < this.floors[currentFloor].getStage().getWidth(); j++) {
                if (!this.floors[currentFloor].getStage().getTilesh()[i][j].inRoom | !checkWalkable(i, j) | this.floors[currentFloor].getStage().getTilesh()[i][j].getType() != 3) {
                    continue;
                }
                if (satun.enemyChance()) {
                    Enemy e = new Enemy(i, j);
                    this.floors[currentFloor].addEnemy(e);
                    Tile lol = floors[currentFloor].getStage().tilesh[e.getPositionx()][e.getPositiony()];
                    floors[currentFloor].getStage().tilesh[e.getPositionx()][e.getPositiony()] = e.t;
                    e.setO(lol);
                }
            }
        }
    }

    public void enemyBehaviour() {
        Enemy[] enemies = this.floors[currentFloor].getE();
        for (int i = 0; i < floors[currentFloor].getEcount() - 1; i++) {
            if (i < 0) {
                break;
            }
            Enemy e = enemies[i];
            if (!e.dead) {
                if (e.timer == 0) {
                    if (!p.dead & (p.positiony <= e.positiony + e.sight & p.positiony >= e.positiony - e.sight) & (p.positionx <= e.positionx + e.sight & p.positionx >= e.positionx - e.sight)) {
                        e.setAlert(true);
                        e.setIdle(20);
                        if (enemyAttack(e)) {

                        } else {
                            enemyMovement(e, p.positionx, p.positiony);
                        }
                        e.lastSeenPlayer(p.positionx, p.positiony);
                    } else {
                        e.setAlert(false);
                        enemyMovement(e, e.originalPositiion[0], e.originalPositiion[1]);
                    }
                } else {
                    e.prepareToAttack();
                }
                if (e.attack) {
                    enat(e);
                    e.attack = false;
                }
            }
        }
    }

    private void enat(Enemy e) {
        int x = e.getPositionx();
        int y = e.getPositiony();
        int a1 = e.attackRange[0];
        int a2 = e.attackRange[1];
        if (e.nextAttack.equals("up")) {
            System.out.println(e.name + " hits up");
            for (int i = y - a1; i < y; i++) {
                for (int j = x - a2; j <= x + a2; j++) {
                    helpEnat(e, i, j);
                }
            }
        } else if (e.nextAttack.equals("down")) {
            System.out.println(e.name + " hits down");
            for (int i = y + 1; i <= y + a1; i++) {
                for (int j = x - a2; j <= x + a2; j++) {
                    helpEnat(e, i, j);
                }
            }
        } else if (e.nextAttack.equals("left")) {
            System.out.println(e.name + " hits left");
            for (int i = x - a1; i < x; i++) {
                for (int j = y - a2; j <= y + a2; j++) {
                    helpEnat(e, j, i);
                }
            }
        } else if (e.nextAttack.equals("right")) {
            System.out.println(e.name + " hits right");
            for (int i = x+1; i <= x + a1; i++) {
                for (int j = y - a2; j <= y + a2; j++) {
                    helpEnat(e, j, i);
                }
            }
        }
        e.nextAttack = "";
        e.attack = false;
    }

    public void helpEnat(Enemy e, int i, int j) {
        if (i < 0 | j < 0 | i >= floors[currentFloor].stage.height | j >= floors[currentFloor].stage.width) {

        } else {
            if (floors[currentFloor].getStage().tilesh[j][i].type == 10) {
                p.hit(e.damage);
                System.out.println("PELAAJAN TERVEYS: " + p.health);
                if (p.dead) {
                    floors[currentFloor].getStage().tilesh[p.getPositionx()][p.getPositiony()] = new Tile("D");
                }
            }
            floors[currentFloor].getStage().tilesh[j][i].hit = true;
        }
    }

    public boolean enemyAttack(Enemy e) {
        int x = e.getPositionx();
        int y = e.getPositiony();
        int a1 = e.attackRange[0];
        int a2 = e.attackRange[1];
        if (e.inRangeUp(p.positionx, p.positiony)) {
            for (int i = y - a1; i < y; i++) {
                for (int j = x - a2; j <= x + a2; j++) {
                    ea(e, i, j);
                    e.nextAttack = "up";
                }
            }
            return true;
        } else if (e.inRangeDown(p.positionx, p.positiony)) {
            for (int i = y + 1; i <= y + a1; i++) {
                for (int j = x - a2; j <= x + a2; j++) {
                    ea(e, i, j);
                    e.nextAttack = "down";
                }
            }
            return true;
        } else if (e.inRangeLeft(p.positionx, p.positiony)) {
            for (int i = x - a1; i < x; i++) {
                for (int j = y - a2; j <= y + a2; j++) {
                    ea(e, j, i);
                    e.nextAttack = "left";
                }
            }
            return true;
        } else if (e.inRangeRight(p.positionx, p.positiony)) {
            for (int i = x+1; i <= x + a1; i++) {
                for (int j = y - a2; j <= y + a2; j++) {
                    ea(e, j, i);
                    e.nextAttack = "right";
                }
            }
            return true;
        }
        return false;
    }

    public void ea(Enemy e, int i, int j) {
        if (i < 0 | j < 0 | i >= floors[currentFloor].stage.height | j >= floors[currentFloor].stage.width) {

        } else {
            if (floors[currentFloor].getStage().tilesh[j][i].walkable & floors[currentFloor].getStage().tilesh[j][i].type != 11) {
                if (floors[currentFloor].getStage().tilesh[j][i].type == 10) {
                    p.o.hit = false;
                    p.o.toBeHit = true;
                } else {
                    floors[currentFloor].getStage().tilesh[j][i].hit = false;
                    floors[currentFloor].getStage().tilesh[j][i].toBeHit = true;
                }
            }
            e.timer = e.attackTime;
        }
    }

    public void enemyMovement(Enemy e, int toX, int toY) {
        floors[currentFloor].getStage().tilesh[e.getPositionx()][e.getPositiony()] = e.o;
        if (checkWalkable(e.getPositionx() - 1, e.getPositiony()) & toX < e.positionx) {
            e.moveLeft();
        } else if (checkWalkable(e.getPositionx() + 1, e.getPositiony()) & toX > e.positionx) {
            e.moveRight();
        } else if (checkWalkable(e.getPositionx(), e.getPositiony() - 1) & toY < e.positiony) {
            e.moveUp();
        } else if (checkWalkable(e.getPositionx(), e.getPositiony() + 1) & toY > e.positiony) {
            e.moveDown();
        }
        Tile lol = floors[currentFloor].getStage().tilesh[e.getPositionx()][e.getPositiony()];
        floors[currentFloor].getStage().tilesh[e.getPositionx()][e.getPositiony()] = e.t;
        e.setO(lol);
    }

    public void attack() {
        Enemy[] enemies = this.floors[currentFloor].getE();
        floors[currentFloor].getStage().tilesh[p.getFacing()[1]][p.getFacing()[0]].hit = true;
        for (int i = 0; i < floors[currentFloor].getEcount() - 1; i++) {
            if (i < 0) {
                break;
            }
            Enemy e = enemies[i];
            if (e.getPositionx() == p.getFacing()[1] & e.getPositiony() == p.getFacing()[0]) {
                e.hit(p.getDamage());
                if (e.dead) {
                    floors[currentFloor].getStage().tilesh[e.getPositionx()][e.getPositiony()] = new Tile("D");
                    p.level();
                }
            }
        }

    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public void setCorridorSize(int corridorSize) {
        this.corridorSize = corridorSize;
    }

    public void setDia(boolean dia) {
        this.dia = dia;
    }

    public void setHuoneKoko(int huoneKoko) {
        this.huoneKoko = huoneKoko;
    }

    public void setLevelSize(int levelSize) {
        this.levelSize = levelSize;
    }

    public void setMaxRooms(int maxRooms) {
        this.maxRooms = maxRooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public Player getP() {
        return p;
    }

}

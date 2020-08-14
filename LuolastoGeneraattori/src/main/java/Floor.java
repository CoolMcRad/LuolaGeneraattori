
/**
 * Floor on kenttä tai kerros luolastossa.
 * Se on kokonaisuus huoneita/soluja.
 *
 * @author santt
 */
public class Floor {

    Cell[][] level; // Kentän kaikki solut
    Room stage; // Kentän huone kokonaisuudessaan
    int size; // Kentän koko
    int cellSize; // Solujen koot
    boolean randomHuoneKoko; // Ovatko huoneiden koot annettu koko - maximikoko vai aina annettu koko
    boolean randomOutside;
    Randomizer satunnaisuus = new Randomizer();

    /**
     * generoi kentän annetulla solukoolla ja kentän koolla.
     *
     * @param size Kentän koko. size x size.
     * @param cellSize Solujen koko. (size/cellSize) x (size/cellSize)
     */
    public Floor(int size, int cellSize, boolean randomRoomKoko, boolean randomOutside) {
        this.randomHuoneKoko = randomRoomKoko;
        this.size = (size / cellSize);
        this.cellSize = cellSize;
        this.stage = new Room(size + 2, size + 2, false, true);
        this.randomOutside = randomOutside;
        generateFloor();
    }

    private void generateFloor() {
        this.level = new Cell[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.randomOutside) {
                    level[i][j] = new Cell(cellSize, true);
                } else {
                    level[i][j] = new Cell(cellSize);
                }
            }
        }
    }

    /**
     * Lisää huoneita kenttään annetun määrän. Määrä ei saa ylittää tiettyä
     * määrää.
     *
     * @param amount Huoneiden määrä
     */
    public void addRooms(int amount, int koko) {
        int width;
        int height;
        if (amount > level.length*level.length) amount = level.length*level.length;
        while (amount != 0) {
            int x;
            int y;
            while (true) {
                x = satunnaisuus.randomBetween(0, this.size);
                y = satunnaisuus.randomBetween(0, this.size);
                if (!level[x][y].isActive()) {
                    break;
                }

            }

            if (this.randomHuoneKoko) {
                width = satunnaisuus.randomBetween(koko, this.cellSize + 1);
                height = satunnaisuus.randomBetween(koko, this.cellSize + 1);
            } else {
                width = koko;
                height = koko;
            }
            level[x][y].setRoom(new Room(width, height, false, true), x, y, size);

            amount--;
        }
        createStage();
    }

    /**
     * Kokoaa kentän gridiin
     */
    public void createStage() {
        int x = 1;
        int y = 1;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                stage.insertRoomNotRandom(level[i][j].getRoom(), y, x);
                y += this.cellSize;
            }
            x += this.cellSize;
            y = 1;
        }
    }

    public void printFloor() {
        stage.printRoom();
    }

    public Cell[][] getLevel() {
        return level;
    }

    public Room getStage() {
        return stage;
    }

    public void setStage(Room stage) {
        this.stage = stage;
    }
}

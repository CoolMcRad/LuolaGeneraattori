import java.util.concurrent.ThreadLocalRandom;

public class Floor {
    Cell[][] level;
    Room stage;
    int size;
    int cellSize;

    public Floor(int size, int cellSize) {
        this.size = (size/cellSize);
        this.cellSize = cellSize;
        this.stage = new Room(size,size,1);
        generateFloor();
    }
    
    private void generateFloor() {
        this.level = new Cell[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                level[i][j] = new Cell(cellSize);
            }
        }
    }
    
    public void addRooms(int amount) {
        while(amount != 0) {
            int x;
            int y;
            while(true) {
                x = ThreadLocalRandom.current().nextInt(0, this.size);
                y = ThreadLocalRandom.current().nextInt(0, this.size);
                if (!level[x][y].isActive()) {
                    break;
                }
            }
            int width = ThreadLocalRandom.current().nextInt(4, this.cellSize);
            int height = ThreadLocalRandom.current().nextInt(4, this.cellSize);
            level[x][y].setRoom(new Room(width,height));
            
            amount--;
        }
        createStage();
    }
    
    public void createStage() {
        int x = 0;
        int y = 0;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                stage.insertRoomNotRandom(level[i][j].getRoom(),y,x);
                y += this.cellSize;
            }
            x += this.cellSize;
            y = 0;
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

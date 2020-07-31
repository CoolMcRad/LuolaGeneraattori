
public class Room {
    int width;
    int hight;
    Tile[][] tilesh;

    public Room(int width, int hight) {
        this.width = width + 2;
        this.hight = hight + 2;
        generate();
    }

    public int getHight() {
        return hight;
    }

    public int getWidth() {
        return width;
    }
    
    public void generate() {
        this.tilesh = new Tile[this.hight][this.width];
        for (int i = 0; i <= this.hight - 1; i++) {
            for (int j = 0; j <= this.width - 1; j++) {
                if (i == 0 | j == 0 | i == this.hight-1 | j == this.width-1) {
                    this.tilesh[i][j] = new Tile(0);
                    continue;
                }
                this.tilesh[i][j] = new Tile();
            }
            
        }
    }
    
    public void printRoom() {
        for (int i = 0; i < this.hight; i++) {
            for (int j = 0; j < this.width; j++) {
                System.out.print(tilesh[i][j].getVisual());
            }
            System.out.println("");
        }
    }
}

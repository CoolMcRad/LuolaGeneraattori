import java.util.concurrent.ThreadLocalRandom;

public class Room {
    int width;
    int height;
//    int maxw;
//    int maxh;
    int[] middle; // huoneen keskikohta
    int[] middleInStage; // huoneen keskikohta, kun huone on sijoitettu kenttään/leveliin
    Tile[][] tilesh;

    public Room(int width, int height) {
        this.width = width;
        this.height = height;
//        this.maxh = 0;
//        this.maxw = 0;
        this.middle = new int[2];
        this.middleInStage = new int[2];
        generate();
    }
    
    public Room(int width, int height, int type) {
        this.width = width;
        this.height = height;
//        this.maxh = 0;
//        this.maxw = 0;
        this.middle = new int[2];
        this.middleInStage = new int[2];
        generate(type);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Tile[][] getTilesh() {
        return tilesh;
    }

    public int[] getMiddleInStage() {
        return this.middleInStage;
    }

    public void setMiddleInStage(int f1, int f2) {
        this.middleInStage[0] = f1;
        this.middleInStage[1] = f2;
    }
    
    
    
    private void generate() {
        this.tilesh = new Tile[this.height][this.width]; // Ruudukko jossa käytössä Tile luokka
        for (int i = 0; i <= this.height - 1; i++) {
            for (int j = 0; j <= this.width - 1; j++) {
                if (i == 0 | j == 0 | i == this.height-1 | j == this.width-1) {
                    this.tilesh[i][j] = new Tile(0); // Huoneen seinät generoidaan tässä
                    continue;
                }
                if (i == this.height/2 & j == this.width/2) {
                    middle[0] = i;
                    middle[1] = j;
                    this.tilesh[i][j] = new Tile(2); // Merkataan huoneen keskipiste
                } else {
                    this.tilesh[i][j] = new Tile(1); // Huoneen sisältö generoidaan tässä
                }
            }
        }
    }
    
        private void generate(int type) { // Generoi huoneen jossa vain yhtä tyyppiä kuten seinää tai tyhjää
        this.tilesh = new Tile[this.height][this.width]; // Ruudukko jossa käytössä Tile luokka
        for (int i = 0; i <= this.height - 1; i++) {
            for (int j = 0; j <= this.width - 1; j++) {
                if (i == 0 | j == 0 | i == this.height-1 | j == this.width-1) {
                    this.tilesh[i][j] = new Tile(type);
                    continue;
                }
                this.tilesh[i][j] = new Tile(type);
            }
            
        }
    }
    
    public void insertRoom(Room r) {
        int x = ThreadLocalRandom.current().nextInt(0, this.width-r.getWidth()+1);
        int y = ThreadLocalRandom.current().nextInt(0, this.height-r.getHeight()+1);
//        int rh = r.getHeight();
//        if (this.maxh >= this.height-rh+1) {
//            this.maxh = 0;
//            this.maxw += rh;
//        }
        //int x = this.maxw;
        //int y = this.maxh;
        Tile[][] t = r.getTilesh();
        int h = 0;
        int w = 0;
        for (int i = y; i <= r.getHeight()+y-1; i++) {
            for (int j = x; j <= r.getWidth()+x-1; j++) {
                this.tilesh[i][j] = t[h][w];
                w++;
            }
            w = 0;
            h++;
        }

        //this.maxh += h;
    }
    
    public void insertRoomNotRandom(Room r, int y, int x) {
        //x *= r.getWidth();
        //y *= r.getHeight();
//        int rh = r.getHeight();
//        if (this.maxh >= this.height-rh+1) {
//            this.maxh = 0;
//            this.maxw += rh;
//        }
        //int x = this.maxw;
        //int y = this.maxh;
        Tile[][] t = r.getTilesh();
        int h = 0;
        int w = 0;
        for (int i = y; i <= r.getHeight()+y-1 & i < this.height; i++) {
            for (int j = x; j <= r.getWidth()+x-1 & j < this.width; j++) {
                this.tilesh[i][j] = t[h][w];
                if (t[h][w].getType() == 2) {
                    r.setMiddleInStage(i, j);
                }
                w++;
            }
            w = 0;
            h++;
        }
        //this.maxh += h;
    }
    
    public void printRoom() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                System.out.print(tilesh[i][j].getVisual());
            }
            System.out.println("");
        }
    }

    void printPart(int i, int j) {
        for (int k = 0; k < this.width; k++) {
            System.out.print(tilesh[i][j].getVisual());
            
        }
    }
}

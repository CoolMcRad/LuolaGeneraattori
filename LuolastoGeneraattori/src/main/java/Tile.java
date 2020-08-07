import java.util.concurrent.ThreadLocalRandom;

public class Tile {
    int type;
    String visual;
    String[] types = {"X", " ", "m", "*", " "}; // "X" on muuri tai seinä, " " on tyhjä ruutu, "m" on huoneen keskipiste, "*" on jokin esine, "O" on huoneiden ulkopuolinen tila.

    public Tile(int type) {
        this.type = type;
        this.visual = this.types[type];
    }

    public Tile() { // Satunnaisen tiilen konstruktori
        this.type =  ThreadLocalRandom.current().nextInt(0, 3 + 1);
        this.visual = this.types[this.type];
    }
    
    public Tile(String type) {
        this.type = 10;
        this.visual = type;
    }

    public void checkTile() {
        if (this.type == 4) {
            this.type = 0;
            this.visual = this.types[0];
        }
    }
    
    public String getVisual() {
        return visual;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return this.visual + " " + this.type;
    }
    
    
}

import java.util.concurrent.ThreadLocalRandom;

public class Tile {
    int type;
    String visual;
    String[] types = {"X", " ", "O", "*"}; // "X" on muuri tai seinä, " " on tyhjä ruutu, "O" on este kuten kuoppa, "*" on jokin esine.

    public Tile(int type) {
        this.type = type;
        this.visual = this.types[type];
    }

    public Tile() { // Satunnaisen tiilen konstruktori
        this.type =  ThreadLocalRandom.current().nextInt(0, 3 + 1);
        this.visual = this.types[this.type];
    }

    public String getVisual() {
        return visual;
    }

    @Override
    public String toString() {
        return this.visual + " " + this.type;
    }
    
    
}

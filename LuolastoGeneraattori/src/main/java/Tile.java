
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Tile olio on luolaston tiiliskivi. Kaikki huoneet rakentuu tiileistä joilla
 * on oma tyyppi.
 *
 * @author santt
 */
public class Tile {

    int type; // Tilen tyyppi numerona, esimerkiksi numero 1 on tyyppiä seinä
    String visual; // Miltä Tile näyttää, kuten X, O ... jne
    int doorTo;
    boolean walkable = true;
    boolean inRoom = false;
    int biome;
    Image image;

    String[] cave = {" ", " ", "D", "-", "|", "X", " ", "X", "X"};
    String[] plains = {" ", " ", "D", "-", "|", "W", " ", "C", "T"};
    String[] city = {" ", " ", "D", "-", "|", "X", " ", "C", "T"};
    String[][] biomes = {cave, plains, city};
    Randomizer satunnaisuus = new Randomizer();
    String path = "c1.png";

    public Tile(int type, int biome) {
        this.type = type;
        if (type > 6 | type == 1 | type == 5) {
            this.walkable = false;
        }
        if (type == 3 | type == 2) {
            this.inRoom = true;
        }
        this.biome = biome;
        this.visual = this.biomes[biome][this.type];
        setImage();
    }
    
        public Tile(int type, int biome, int doorTo) {
        this.type = type;
        this.walkable = true;
        this.inRoom = true;
        this.doorTo = doorTo;
        this.biome = biome;
        this.visual = this.biomes[biome][this.type];
        setImage();
    }

    /**
     * Satunnaisten tiilen konstruktori. Luo tiilin joka voi olla mitävain
     * tyyppiä
     */
    public Tile(int biome) {
        this.biome = biome;
        this.type = satunnaisuus.randomBetween2(6, 8 + 1);
        if (type > 6 | type == 1 | type == 5) {
            this.walkable = false;
        }
        if (type == 3 | type == 2) {
            this.inRoom = true;
        }
        this.inRoom = false;
        this.visual = this.biomes[biome][this.type]; // Antaa tiilelle oikean visuaalin sen tyypin mukaan
        setImage();
    }

    /**
     * Custom tiilen luominen, lähinnä testaukseen
     *
     * @param type Täytyy antaa olion visuaali
     */
    public Tile(String type) {
        this.type = 10;
        this.visual = type;
        this.walkable = false;
        this.biome = 20;
        setImage();
    }

    public Tile(int type, int biome, boolean walkable) {
        this.type = type;
        this.biome = biome;
        this.visual = this.biomes[biome][this.type];
        this.walkable = walkable;
        if (type == 3 | type == 2) {
            this.inRoom = true;
        }
        setImage();
        this.type = 0;
    }

    /**
     * Tarkistaa onko tiili tyhjyyttä/kentän ulkopuolista tilaa ja jos on
     * muuttaa sen seinäksi Käytössä lähinnä Corridor/käytävien seinien
     * huoneiden välillä luomiseen. Metodi ei muuta huoneiden sisältö,
     * pelkästään niiden ulkopuolista tilaa
     */
    public void checkTile(int[] oldt, int newt) {
        for (int i : oldt) {
            if (this.type == i) { // Katsoo onko tiili kysyttyä
                this.type = newt; // Korvaa tiilen annetulla tiilellä
                if (type > 6 | type == 1 | type == 5) {
                    this.walkable = false;
                }
                this.visual = this.biomes[this.biome][newt];
            }
        }
        setImage();
    }

    private void setImage() {
        String p;
        if (this.type == 0 && this.inRoom) {
            p = Integer.toString(3) + ".png";
            this.walkable = true;
        } else if (this.type == 0 && this.biome == 0) {
            p = Integer.toString(5) + ".png";
            this.walkable = false;
        } else {
            p = Integer.toString(type) + ".png";
        }
        switch (biome) {
            case 0:
                p = "c" + p;
                break;
            case 1:
                p = "p" + p;
                break;
            case 2:
                p = "ci" + p;
                break;
            case 20:
                p = "player" + p;
                break;
            default:
                break;
        }
        path = p;
//        ImageIcon ima = new ImageIcon(getClass().getResource(path));
        Image imag = new ImageIcon(this.getClass().getResource(path)).getImage();
        this.image = imag;
    }

    public Image getImage() {
        return image;
    }

    public void setInRoom(boolean inRoom) {
        this.inRoom = inRoom;
    }

    public boolean isInRoom() {
        return inRoom;
    }

    public String getVisual() {
        return visual;
    }

    public int getType() {
        return type;
    }

    public boolean isWalkable() {
        return walkable;
    }
}

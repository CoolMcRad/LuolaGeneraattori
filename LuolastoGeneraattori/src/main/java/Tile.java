
/**
 * Tile olio on luolaston tiiliskivi.
 * Kaikki huoneet rakentuu tiileistä joilla on oma tyyppi.
 *
 * @author santt
 */
public class Tile {

    int type; // Tilen tyyppi numerona, esimerkiksi numero 1 on tyyppiä seinä
    String visual; // Miltä Tile näyttää, kuten X, O ... jne

    boolean walkable = true;
    int biome;

    String[] cave = {"m", " ", "D", " ", " ", "X", " ", "X", "X"};
    String[] plains = {"m", " ", "D", "-", " ", "W", " ", "C", "T"};
    String[] city = {"m", " ", "D", "-", "|", "X", " ", "C", "T"};
    String[][] biomes = {cave, plains, city};
    Randomizer satunnaisuus = new Randomizer();

    public Tile(int type, int biome) {
        this.type = type;
        if (type > 6 | type == 1 | type == 5) {
            this.walkable = false;
        }
        this.biome = biome;
        this.visual = this.biomes[biome][this.type];
    }

    /**
     * Satunnaisten tiilen konstruktori. Luo tiilin joka voi olla mitävain
     * tyyppiä
     */
    public Tile(int biome) {
        this.biome = biome;
        this.type = satunnaisuus.randomBetween2(6, 8+1);
        if (type > 6 | type == 1 | type == 5) {
            this.walkable = false;
        }
        this.visual = this.biomes[biome][this.type]; // Antaa tiilelle oikean visuaalin sen tyypin mukaan
    }

    /**
     * Custom tiilen luominen, lähinnä testaukseen
     *
     * @param type Täytyy antaa olion visuaali
     */
    public Tile(String type) {
        this.type = 10;
        this.visual = type;
    }

    /**
     * Tarkistaa onko tiili tyhjyyttä/kentän ulkopuolista tilaa ja jos on
     * muuttaa sen seinäksi Käytössä lähinnä Corridor/käytävien seinien
     * huoneiden välillä luomiseen. Metodi ei muuta huoneiden sisältö,
     * pelkästään niiden ulkopuolista tilaa
     */
    public void checkTile(int[] oldt, int newt) {
        for (int i : oldt) {
            if (this.type == i ) { // Katsoo onko tiili kysyttyä
            this.type = newt; // Korvaa tiilen annetulla tiilellä
            if (type > 6 | type == 1 | type == 5) {
                this.walkable = false;
            }
            this.visual = this.biomes[this.biome][newt];
        }
        }
    }

    public String getVisual() {
        return visual;
    }

    public int getType() {
        return type;
    }

}

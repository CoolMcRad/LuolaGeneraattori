import java.util.concurrent.ThreadLocalRandom;

/**
 * Room luokka on graph joka käyttää Tile luokkaa, sillä on keskikohta jolla lasketaan käytävät(Corridor luokka).
 * @author santt
 */
public class Room {
    int width;
    int height;
    int[] middle; // huoneen keskikohta. x ja y koordinaatti
    int[] middleInStage; // huoneen keskikohta, kun huone on sijoitettu kenttään/leveliin
    Tile[][] tilesh; // Tämä on periaatteessa itse huone. Se siältää kaikki huoneen osat Tile luokkaa käyttäen

    public Room(int width, int height) {
        this.width = width;
        this.height = height;
        this.middle = new int[2];
        this.middleInStage = new int[2];
        generate();
    }
    
    public Room(int width, int height, int type) {
        this.width = width;
        this.height = height;
        this.middle = new int[2];
        this.middleInStage = new int[2];
        generate(type);
    }
    
    /**
     * Metodi generoi uuden huoneen lisäämällä sen gridiin oikeanlaista tyyppiä olevia Tile luokkaa olevia olioita
     * Tämä generate luo huoneen jonka sisältö on satunnainen (toistaiseksi pois käytöstä)
     */
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
                    this.tilesh[i][j] = new Tile(3); // Merkataan huoneen keskipiste
                } else {
                    this.tilesh[i][j] = new Tile(1); // Huoneen sisältö generoidaan tässä
                }
            }
        }
    }
    
     /**
     * Metodi generoi uuden huoneen lisäämällä sen gridiin oikeanlaista tyyppiä olevia Tile luokkaa olevia olioita
     * Tämä generate luo huoneen jonka sisältö on pelkästään yhtä tyyppiä
     */
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
    
    /**
     * insertRoom lisää nykyisen huoneen päälle annetun huoneen korvaamalla aiemmat Tile oliot gridissä.
     * @param r 
     * Huone olio
     */
    public void insertRoom(Room r) {
        // Satunnaiset x ja y koordinaatit
        int x = ThreadLocalRandom.current().nextInt(0, this.width-r.getWidth()+1);
        int y = ThreadLocalRandom.current().nextInt(0, this.height-r.getHeight()+1);
        
        // Annettu huone sijoitetaan tässä huoneen päälle, korvaamalla Tilet ruudukossa
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
    }
    
    /**
     * Tätä metodia käytetään solujen lisäykseen kenttään.
     * Huoneet solujen sisällä ovat satunnaisia, mutta solut täyttävät koko kentän; ei satunnaisesti.
     * @param r
     * Huone olio
     * @param y
     * Apu muuntuja huoneen oikealle paikalle pistämiseen
     * @param x 
     * Apu muuntuja huoneen oikealle paikalle pistämiseen
     */
    public void insertRoomNotRandom(Room r, int y, int x) {

        Tile[][] t = r.getTilesh();
        int h = 0;
        int w = 0;
        for (int i = y; i <= r.getHeight()+y-1 & i < this.height; i++) {
            for (int j = x; j <= r.getWidth()+x-1 & j < this.width; j++) {
                this.tilesh[i][j] = t[h][w];
                if (t[h][w].getType() == 3) { // Tarkistaa onko tiili solun huoneen keskikohta ja merkitsee sen
                    r.setMiddleInStage(i, j);
                }
                w++;
            }
            w = 0;
            h++;
        }
    }
    
    public void printRoom() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                System.out.print(tilesh[i][j].getVisual());
            }
            System.out.println("");
        }
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
}


/**
 * Room luokka on graph joka käyttää Tile luokkaa, sillä on keskikohta jolla
 * lasketaan käytävät(Corridor luokka).
 *
 * @author santt
 */
public class Room {

    int width;
    int height;
    int[] middle; // huoneen keskikohta. x ja y koordinaatti
    int[] middleInStage; // huoneen keskikohta, kun huone on sijoitettu kenttään/leveliin
    Tile[][] tilesh; // Tämä on periaatteessa itse huone. Se siältää kaikki huoneen osat Tile luokkaa käyttäen
    boolean rand;
    boolean border;
    Randomizer satunnaisuus = new Randomizer();
    int gravity = 2;
    int roomType = 0; // Neliö, ympyrä tai ruksi
    int biome;

    public Room(int width, int height, boolean rand, boolean border, int biome) {
        this.biome = biome;
        this.rand = rand;
        this.border = border;
        this.width = width;
        this.height = height;
        this.middle = new int[2];
        this.middleInStage = new int[2];
        generate();
    }

    public Room(int width, int height, int type, int biome) {
        this.biome = biome;
        this.width = width;
        this.height = height;
        this.middle = new int[2];
        this.middleInStage = new int[2];
        generate(type);
    }

    public Room(int width, int height, boolean rand, boolean border, int roomType, int biome) {
        this.biome = biome;
        this.rand = rand;
        this.border = border;
        this.width = width;
        this.height = height;
        this.middle = new int[2];
        this.middleInStage = new int[2];
        this.roomType = roomType;
        generate();
    }

    /**
     * Metodi generoi uuden huoneen lisäämällä sen gridiin oikeanlaista tyyppiä
     * olevia Tile luokkaa olevia olioita Tämä generate luo huoneen jonka
     * sisältö on satunnainen (toistaiseksi pois käytöstä)
     */
    private void generate() {
        int h = (this.height) / 3;
        int w = (this.width) / 3;

        this.tilesh = new Tile[this.height][this.width]; // Ruudukko jossa käytössä Tile luokka
        for (int i = 0; i <= this.height - 1; i++) {
            for (int j = 0; j <= this.width - 1; j++) {
                if (this.roomType == 1) { // Jos tyyppi 1 niin muodostuu ruksi
                    if (j < w & i < h & (j == w - 1 | i == h - 1) | j > w + w & i > h + h & (j == w + w + 1 | i == h + h + 1) | j > w + w & i < h & (j == w + w + 1 | i == h - 1) | j < w & i > h + h & (j == w - 1 | i == h + h + 1)) {
                        this.tilesh[i][j] = new Tile(5, biome);
                        continue;
                    }
                }
                if (i == 0 | j == 0 | i == this.height - 1 | j == this.width - 1) {
                    if (border) {
                        this.tilesh[i][j] = new Tile(5, biome); // Huoneen seinät generoidaan tässä
//                        this.tilesh[i][j].setInRoom(true);
                    } else {
                        this.tilesh[i][j] = new Tile(6, biome); // Huoneen sisältö generoidaan tässä
                    }
                    continue;
                }
                if (i == this.height / 2 & j == this.width / 2) {
                    middle[0] = i;
                    middle[1] = j;
                    this.tilesh[i][j] = new Tile(0, biome); // Merkataan huoneen keskipiste
                    this.tilesh[i][j].setInRoom(true);
                } else {
                    if (this.rand) {
                        if (checkIfObstacles(i, j)) {
                            this.tilesh[i][j] = new Tile(6, biome);
                        } else {
                            this.tilesh[i][j] = new Tile(biome);
                        }
                    } else {
                        this.tilesh[i][j] = new Tile(3, biome); // Huoneen sisältö generoidaan tässä
                    }
                }
            }
        }
    }

    /**
     * Metodi generoi uuden huoneen lisäämällä sen gridiin oikeanlaista tyyppiä
     * olevia Tile luokkaa olevia olioita Tämä generate luo huoneen jonka
     * sisältö on pelkästään yhtä tyyppiä
     */
    private void generate(int type) { // Generoi huoneen jossa vain yhtä tyyppiä kuten seinää tai tyhjää
        this.tilesh = new Tile[this.height][this.width]; // Ruudukko jossa käytössä Tile luokka
        for (int i = 0; i <= this.height - 1; i++) {
            for (int j = 0; j <= this.width - 1; j++) {
                if (i == 0 | j == 0 | i == this.height - 1 | j == this.width - 1) {
                    this.tilesh[i][j] = new Tile(type, biome);
                    continue;
                }
                if (i == this.height / 2 & j == this.width / 2) {
                    middle[0] = i;
                    middle[1] = j;
                    this.tilesh[i][j] = new Tile(0, biome);
                } else {
                    this.tilesh[i][j] = new Tile(type, biome);
                }
            }
        }
    }

    // WIP
    private void generateRound() {
        int h = (this.height - 1) / 3;
        int w = (this.width - 1) / 3;
        this.tilesh = new Tile[this.height][this.width]; // Ruudukko jossa käytössä Tile luokka
        for (int i = 0; i <= this.height - 1; i++) {
            for (int j = 0; j <= this.width - 1; j++) {
                if (j < w & i < h | j > w + w & i > h + h) {
                    this.tilesh[i][j] = new Tile(5, biome);
                    continue;
                }
                if (i == 0 | j == 0 | i == this.height - 1 | j == this.width - 1) {
                    if (border) {
                        this.tilesh[i][j] = new Tile(5, biome); // Huoneen seinät generoidaan tässä
                    } else {
                        if (this.rand) {
                            this.tilesh[i][j] = new Tile(biome);
                        } else {
                            this.tilesh[i][j] = new Tile(3, biome); // Huoneen sisältö generoidaan tässä
                        }
                    }
                    continue;
                }
                if (i == this.height / 2 & j == this.width / 2) {
                    middle[0] = i;
                    middle[1] = j;
                    this.tilesh[i][j] = new Tile(0, biome); // Merkataan huoneen keskipiste
                } else {
                    if (this.rand) {
                        this.tilesh[i][j] = new Tile(biome);
                    } else {
                        this.tilesh[i][j] = new Tile(3); // Huoneen sisältö generoidaan tässä
                    }
                }
            }
        }
    }

    /**
     * insertRoom lisää nykyisen huoneen päälle annetun huoneen korvaamalla
     * aiemmat Tile oliot gridissä.
     *
     * @param r Huone olio
     */
    public void insertRoom(Room r, int coordx, int coordy, int s) {
        // Satunnaiset x ja y koordinaatit
        this.tilesh[middle[0]][middle[1]] = new Tile(6,r.biome);
        this.gravity = 2;
        int[] grav = gravity(0, this.width - r.getWidth() + 1, this.height - r.getHeight() + 1, coordx, coordy, s - 1);
        int x = grav[0];
        int y = grav[1];
        // Annettu huone sijoitetaan tässä huoneen päälle, korvaamalla Tilet ruudukossa
        Tile[][] t = r.getTilesh();
        int h = 0;
        int w = 0;
        for (int i = y; i <= r.getHeight() + y - 1; i++) {
            for (int j = x; j <= r.getWidth() + x - 1; j++) {
                this.tilesh[i][j] = t[h][w];
                w++;
            }
            w = 0;
            h++;
        }
    }

    public int[] gravity(int x1, int x2, int y1, int a, int b, int s) {
        int[] arvot = new int[2];
        int x = x1;
        int y = y1;
        if (this.gravity == 0) {
            x = satunnaisuus.randomBetween(x1, x2);
            y = satunnaisuus.randomBetween(x1, y1);
        }
        // IF HELL
        if (this.gravity == 1) {
            x = x2 - 1;
            y = y1 - 1;
        }

        if (this.gravity == 2) {
            if (a <= (s / 2) & b <= (s / 2)) {
                x = x2 - 1;
                y = y1 - 1;
            }

            if (a >= (s / 2) + 1 & b >= (s / 2) + 1) {
                x = x1;
                y = x1;
            }

            if (a <= (s / 2) & b >= (s / 2) + 1) {
                x = x2 - 1;
                y = x1;
            }

            if (a >= (s / 2) + 1 & b <= (s / 2)) {
                x = x1;
                y = y1 - 1;
            }

        }
        arvot[0] = x;
        arvot[1] = y;
        return arvot;
    }

    /**
     * Tätä metodia käytetään solujen lisäykseen kenttään. Huoneet solujen
     * sisällä ovat satunnaisia, mutta solut täyttävät koko kentän; ei
     * satunnaisesti.
     *
     * @param r Huone olio
     * @param y Apu muuntuja huoneen oikealle paikalle pistämiseen
     * @param x Apu muuntuja huoneen oikealle paikalle pistämiseen
     */
    public void insertRoomNotRandom(Room r, int y, int x) {
        Tile[][] t = r.getTilesh();
        int h = 0;
        int w = 0;
        for (int i = y; i <= r.getHeight() + y - 1 & i < this.height; i++) {
            for (int j = x; j <= r.getWidth() + x - 1 & j < this.width; j++) {
                this.tilesh[i][j] = t[h][w];
                if (t[h][w].getType() == 0) { // Tarkistaa onko tiili solun huoneen keskikohta ja merkitsee sen
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

    public boolean isRand() {
        return rand;
    }

    public void setType(int type) {
        this.roomType = type;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public void setMiddleInStage(int f1, int f2) {
        this.middleInStage[0] = f1;
        this.middleInStage[1] = f2;
    }

    private boolean checkIfObstacles(int i, int j) {
        return !this.tilesh[i - 1][j].walkable | !this.tilesh[i - 1][j - 1].walkable | !this.tilesh[i][j - 1].walkable;
    }
}

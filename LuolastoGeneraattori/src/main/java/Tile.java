
/**
 * Tile olio on luolaston tiiliskivi.
 * Kaikki huoneet rakentuu tiileistä joilla on oma tyyppi.
 * @author santt
 */
public class Tile {
    int type; // Tilen tyyppi numerona, esimerkiksi numero 1 on tyyppiä seinä
    String visual; // Miltä Tile näyttää, kuten X, O ... jne
    String[] types = {"X", " ", "*", "m", " "}; // "X" on muuri tai seinä, " " on tyhjä ruutu, "m" on huoneen keskipiste, "*" on jokin esine, "O" on huoneiden ulkopuolinen tila.
    Randomizer satunnaisuus = new Randomizer();
    
    public Tile(int type) {
        this.type = type;
        this.visual = this.types[type];
    }
    
    /**
     * Satunnaisten tiilen konstruktori.
     * Luo tiilin joka voi olla mitävain tyyppiä
     */
    public Tile() {
        this.type =  satunnaisuus.randomBetween2(0, 2 + 1); // Viimeinen tyyppi ei ole satunnaisuuden sisällä, koska se merkkaa tiiltä joka on huoneiden ulkopuolella
        this.visual = this.types[this.type]; // Antaa tiilelle oikean visuaalin sen tyypin mukaan
    }
    
    /**
     * Custom tiilen luominen, lähinnä testaukseen
     * @param type 
     * Täytyy antaa olion visuaali
     */
    public Tile(String type) {
        this.type = 10;
        this.visual = type;
    }

    /**
     * Tarkistaa onko tiili tyhjyyttä/kentän ulkopuolista tilaa ja jos on muuttaa sen seinäksi
     * Käytössä lähinnä Corridor/käytävien seinien huoneiden välillä luomiseen. Metodi ei muuta huoneiden sisältö, pelkästään niiden ulkopuolista tilaa
     */
    public void checkTile() {
        if (this.type == 4) { // Katsoo onko tiili kentän ulkopuolista aluetta
            this.type = 0; // Korvaa tiilen muurilla/seinällä
            this.visual = this.types[0];
        }
    }
    
    public String getVisual() {
        return visual;
    }

    public int getType() {
        return type;
    }
    
}

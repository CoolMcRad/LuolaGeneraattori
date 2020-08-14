/**
 * Solut täyttävät kentän kokonaisuudessaan.
 * Niillä on koko ja oma huone, joka on joko tyhjä/käyttämätön tai satunnaisesti asetettu solun alueelle.
 * @author santt
 */
public class Cell {
    boolean active; // Onko solussa huonetta
    boolean connected; // Onko solu yhdistetty toiseen soluun
    Room room; // Solussa oleva huone
    int size; // Solun koko
    boolean rand;

    public Cell(int size) {
        this.size = size;
        this.active = false;
        this.connected = false;
        this.room = new Room(size,size,4); // Solun huone on aluksi täynnä vain huoneiden ulkopuolisia tiilejä
    }
    
    public Cell(int size, int type) {
        this.size = size;
        this.active = false;
        this.connected = false;
        this.room = new Room(size,size,type); // Solun huone on aluksi täynnä vain annettua tyyppiä
    }

    public Cell(Room room, int size) {
        this.active = true;
        this.connected = false;
        this.room = room;
        this.size = size;
    }
    
    public Cell(int size, boolean rand) {
        this.rand = rand;
        this.active = false;
        this.connected = false;
        this.room = new Room(size,size,true,false);
        this.size = size;
    }

    public boolean isActive() {
        return active;
    }

    public void setRoom(Room room, int x, int y, int s) {
        this.room.insertRoom(room, x, y, s);
        this.active = true;
    }

    public Room getRoom() {
        return room;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}

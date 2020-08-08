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

    public Cell(int size) {
        this.size = size;
        this.active = false;
        this.connected = false;
        this.room = new Room(size,size,4); // Solun huone on aluksi täynnä vain huoneiden ulkopuolisia tiilejä
    }

    public Cell(Room room, int size) {
        this.active = true;
        this.connected = false;
        this.room = room;
        this.size = size;
    }

    public boolean isActive() {
        return active;
    }

    public void setRoom(Room room) {
        this.room.insertRoom(room);
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

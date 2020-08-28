
public class Player {

    int positionx;
    int positiony;
    Tile t = new Tile("@");
    Tile o;
    int health = 100;
    int damage = 25;

    public Player(int positionx, int positiony) {
        this.positionx = positionx;
        this.positiony = positiony;
    }

    public void moveLeft() {
        this.positionx--;
    }

    public void moveRight() {
        this.positionx++;
    }

    public void moveUp() {
        this.positiony--;
    }

    public void moveDown() {
        this.positiony++;
    }

    public int getPositionx() {
        return positionx;
    }

    public int getPositiony() {
        return positiony;
    }

    public void setO(Tile o) {
        this.o = o;
    }

    public Tile getO() {
        return o;
    }

}

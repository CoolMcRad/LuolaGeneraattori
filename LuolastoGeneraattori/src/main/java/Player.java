
public class Player {

    int positionx;
    int positiony;
    Tile t = new Tile("@");
    Tile o;
    int maxhp = 100;
    int health = 100;
    int damage = 25;
    int level = 1;
    int exp = 0;
    int[] facing = new int[2];
    boolean dead = false;

    public Player(int positionx, int positiony) {
        this.positionx = positionx;
        this.positiony = positiony;
        this.facing[0] = positiony - 1;
        this.facing[1] = positionx;
    }
    
    public void level() {
        exp += 20;
        if (exp >= 100) {
            exp = 0;
            health = maxhp;
            damage += 2;
            level++;
            System.out.println("");
            System.out.println("*****************");
            System.out.println("LEVEL UP");
            System.out.println("Level is: " + this.level);
            System.out.println("Damage up by 2 !");
            System.out.println("Health recharged !");
            System.out.println("*****************");
            System.out.println("");
            
        }
    }

    public void moveLeft() {
        this.positionx--;
        this.facing[0] = positiony;
        this.facing[1] = positionx - 1;
    }

    public void moveRight() {
        this.positionx++;
        this.facing[0] = positiony;
        this.facing[1] = positionx + 1;
    }

    public void moveUp() {
        this.positiony--;
        this.facing[0] = positiony - 1;
        this.facing[1] = positionx;
    }

    public void moveDown() {
        this.positiony++;
        this.facing[0] = positiony + 1;
        this.facing[1] = positionx;
    }

    public void hit(int dmg) {
        this.health -= dmg;
        if (this.health <= 0) {
            dead = true;
        }
    }

    public int getPositionx() {
        return positionx;
    }

    public int getPositiony() {
        return positiony;
    }

    public int[] getFacing() {
        return facing;
    }

    public void setO(Tile o) {
        this.o = o;
    }

    public Tile getO() {
        return o;
    }

    public int getDamage() {
        return damage;
    }

    public void setCoordinates(int x, int y) {
        this.positionx = x;
        this.positiony = y;
    }
}

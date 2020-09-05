
public class Enemy {

    Randomizer ran = new Randomizer();
    String name;
    int positionx;
    int positiony;
    Tile t = new Tile("E");
    Tile o;
    int health = 100;
    int damage = 25;
    boolean dead = false;
    int[] originalPositiion = new int[2];
    int[] lastSeen = new int[2];
    int idle = 0;
    boolean alert = false;
    int sight = 6;
    int[] attackRange = new int[2];
    int attackTime;
    int timer = 0;
    int type;
    boolean attack = false;
    String nextAttack = "";

    public Enemy(int positionx, int positiony) {
        this.positionx = positionx;
        this.positiony = positiony;
        this.originalPositiion[0] = positionx;
        this.originalPositiion[1] = positiony;
        lastSeen[0] = positionx;
        lastSeen[1] = positiony;
        this.type = ran.randomBetween(0, 6);
        setType();
    }

    public void moveLeft() {
        if (!idling()) {
            this.positionx--;
        }
    }

    public void moveRight() {
        if (!idling()) {
            this.positionx++;
        }
    }

    public void moveUp() {
        if (!idling()) {
            this.positiony--;
        }
    }

    public void moveDown() {
        if (!idling()) {
            this.positiony++;
        }
    }

    public int getPositionx() {
        return positionx;
    }

    public int getPositiony() {
        return positiony;
    }

    public void hit(int dmg) {
        this.health -= dmg;
        if (this.health <= 0) {
            dead = true;
        }
    }

    public void setO(Tile o) {
        this.o = o;
    }

    public Tile getO() {
        return o;
    }

    public void setCoordinates(int x, int y) {
        this.positionx = x;
        this.positiony = y;
    }

    public void lastSeenPlayer(int x, int y) {
        lastSeen[0] = x;
        lastSeen[1] = y;
    }

    public void setIdle(int idle) {
        this.idle = idle;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public boolean isAlert() {
        return alert;
    }

    public boolean idling() {
        if (this.idle > 0 & !this.alert) {
            this.idle--;
            return true;
        }
        return false;
    }

    private void setType() {
        switch (this.type) {
            case 0:
                this.name = "Daggerman";
                this.attackRange[0] = 1;
                this.attackRange[1] = 0;
                this.damage = 4;
                this.attackTime = 4;
                break;
            case 1:
                this.name = "Swordsman";
                this.attackRange[0] = 1;
                this.attackRange[1] = 3;
                this.damage = 8;
                this.attackTime = 7;
                break;
            case 2:
                this.name = "Spearman";
                this.attackRange[0] = 4;
                this.attackRange[1] = 0;
                this.damage = 8;
                this.attackTime = 7;
                break;
            case 3:
                this.name = "Hammerman";
                this.attackRange[0] = 2;
                this.attackRange[1] = 2;
                this.damage = 12;
                this.attackTime = 11;
                break;
            case 4:
                this.name = "Greatswordman";
                this.attackRange[0] = 2;
                this.attackRange[1] = 4;
                this.damage = 17;
                this.attackTime = 16;
                break;
            case 5:
                this.name = "Greathammerman";
                this.attackRange[0] = 3;
                this.attackRange[1] = 3;
                this.damage = 17;
                this.attackTime = 16;
                break;
            default:
                break;
        }
    }

    public void prepareToAttack() {
        if (timer > 0) {
            timer--;
        } 
        if (timer == 2) {
            attack = true;
        }
    }

    public boolean inRangeUp(int x, int y) {
        return (y < this.positiony & y >= this.positiony - this.attackRange[0]) & (x >= this.positionx - this.attackRange[1] & x <= this.positionx + this.attackRange[1]);
    }

    public boolean inRangeDown(int x, int y) {
        return (y > this.positiony & y <= this.positiony + this.attackRange[0]) & (x >= this.positionx - this.attackRange[1] & x <= this.positionx + this.attackRange[1]);
    }

    public boolean inRangeLeft(int x, int y) {
        return (x < this.positionx & x >= this.positionx - this.attackRange[0]) & (y >= this.positiony - this.attackRange[1] & y <= this.positiony + this.attackRange[1]);
    }

    public boolean inRangeRight(int x, int y) {
        return (x > this.positionx & x <= this.positionx + this.attackRange[0]) & (y >= this.positiony - this.attackRange[1] & y <= this.positiony + this.attackRange[1]);
    }
}

import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {

    public Randomizer() {
    }
    
    public int randomBetween(int eka, int toka) {
        int palautus = ThreadLocalRandom.current().nextInt(eka, toka);
        return palautus;
    }
    
    public int randomBetween2(int eka, int toka) {
        int palautus = ThreadLocalRandom.current().nextInt(eka, toka+20);
        if (palautus >= toka) {
            palautus = eka;
        }
        return palautus;
    }
    
    public boolean enemyChance() {
        int chance = ThreadLocalRandom.current().nextInt(0, 30);
        boolean palautus = false;
        if (chance == 0) {
            palautus = true;
        }
        return palautus;
    }
    
    public int nano(int eka,int toka) {
        int palautus = (int) System.nanoTime()%toka;
        while (palautus < eka) {
            palautus = (int) System.nanoTime()%toka;
        }

        return palautus;
    }
}

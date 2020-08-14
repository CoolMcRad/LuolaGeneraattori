import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {

    public Randomizer() {
    }
    
    public int randomBetween(int eka, int toka) {
        int palautus = ThreadLocalRandom.current().nextInt(eka, toka);
        return palautus;
    }
    
    public int randomBetween2(int eka, int toka) {
        int palautus = 0;
        if (ThreadLocalRandom.current().nextInt(eka, toka) != 0) {
            palautus = 1;
        }
        if (palautus != 1) {
            palautus = ThreadLocalRandom.current().nextInt(eka, toka);
        }
        return palautus;
    }
}

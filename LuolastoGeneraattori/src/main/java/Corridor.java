/**
 * Käytävät huoneiden välillä
 * @author santt
 */
public class Corridor {
    Cell[][] level;
    Cell currentCell;
    Room r;

    public Corridor(Floor level) {
        this.r = level.getStage();
        this.level = level.getLevel();
    }
    
    /**
     * Etsii kentän huoneiden keskikohdat ja luo käytävät niiden avulla.
     */
    public void findCellsAndGenerate() {
        currentCell = new Cell(0);
        
        for (int i = 0; i < level.length; i++) {
            for (int j = 0; j < level.length; j++) {
                Cell cell = level[i][j];
                if (cell.isActive()) {
                    if (!currentCell.isActive()) {
                        currentCell = cell;
                        continue;
                    }
                    if (cell.isConnected()) {
                        continue;
                    }
                    
                    cell.setConnected(true);
                    currentCell.setConnected(true);
                    int[] cellMiddle = cell.getRoom().getMiddleInStage();
                    int[] currentCellMiddle = currentCell.getRoom().getMiddleInStage();

                    currentCell = cell;
                    carveCorridor(currentCellMiddle[1], cellMiddle[1], currentCellMiddle[0], cellMiddle[0]);
                }
            }
        }

    }
    
    public void carveCorridor(int x1, int x2, int y1, int y2) {
        int position1 = y1;
        int position2 = x1;
        
        while(true) {
            if (position2 != x2 && position1 == y2) {
                if (position2 < x2) {
                    position2++;
                } else {
                    position2--;
                }
            }
            
            if (position1 != y2) {
                if (position1 < y2) {
                    position1++;
                } else {
                    position1--;
                }
            }
            
            checkSurroundingTiles(position1, position2);
            if (r.tilesh[position1][position2].getType() != 3) {
                r.tilesh[position1][position2] = new Tile(1);
            }
            if (position1 == y2 & position2 == x2) {
                break;
            }
        }
    }

    private void checkSurroundingTiles(int y, int x) {
        for (int i = y-1; i <= y+1; i++) {
            for (int j = x-1; j <= x+1; j++) {
                r.tilesh[i][j].checkTile();
            }
        }
    }
    
    
}


public class Corridor {
    Floor floor;
    Cell[][] level;
    int[] middlePoints;
    Cell currentCell;
    Room r;

    public Corridor(Floor level) {
        this.floor = level;
        this.level = level.getLevel();
    }
    
    public void findMiddleOfCell() {
        r = this.floor.getStage();
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
            if (r.tilesh[position1][position2].getType() != 2) {
                r.tilesh[position1][position2] = new Tile(1);
            }
            if (position1 == y2 & position2 == x2) {
                break;
            }
        }
    }

    private void checkSurroundingTiles(int yy, int äx) {
        for (int i = yy-1; i <= yy+1; i++) {
            for (int j = äx-1; j <= äx+1; j++) {
                r.tilesh[i][j].checkTile();
            }
        }
    }
    
    
}


/**
 * Käytävät huoneiden välillä
 *
 * @author santt
 */
public class Corridor {

    Cell[][] level;
    Cell currentCell;
    Room r;
    int size;
    boolean diagonal;

    public Corridor(Floor level, int size, boolean diagonal) {
        this.diagonal = diagonal;
        this.r = level.getStage();
        this.level = level.getLevel();
        this.size = size;
    }

    /**
     * Etsii kentän huoneiden keskikohdat ja luo käytävät niiden avulla.
     */
    public void findCellsTopToBottomAndGenerate() {
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

                    prepCells(cell, currentCell);

                    currentCell = cell;
                }
            }
        }
    }

    public void findCellsFromMiddleAndGenerate() {
        currentCell = new Cell(0);
        int i = (level.length / 2) - 1;
        int j = i + 1;

        while (i >= 0 & j <= this.level.length - 1) {

            apu(i, j, i, false);
            apu(i, j, i, true);
            apu(i, j, j, false);
            apu(i, j, j, true);

            i--;
            j++;
        }

    }

    public void apu(int a, int b, int con, boolean rev) {
        Cell cell;
        for (int i = a; i <= b; i++) {
            if (rev) {
                cell = level[con][i];
            } else {
                cell = level[i][con];
            }
            if (cell.isActive()) {
                if (!currentCell.isActive()) {
                    currentCell = cell;
                } else {
                    if (!cell.isConnected()) {

                        prepCells(cell, currentCell);
                        currentCell = cell;
                    }
                }
            }
        }
    }

    // WIP
    public void doorways() {
        int[] wallCoord = new int[2];
        wallCoord[0] = -1;
        boolean up;
        boolean down;
        boolean left;
        boolean right;
        for (int i = 0; i < level.length; i++) {
            for (int j = 0; j < level.length; j++) {
                up = booleanit(i, false);
                down = booleanit(i, true);
                left = booleanit(j, false);
                right = booleanit(j, true);
                Cell cell = level[i][j];
                if (cell.isActive()) {
                    int[] cellMiddle = cell.getRoom().getMiddleInStage();
                    if (wallCoord[0] == -1) {
                        wallCoord[0] = cellMiddle[0] + cell.size / 2 - 1;
                        wallCoord[1] = cellMiddle[1];
                    }
                    cell.setConnected(true);

                    carveCorridorDiagonal(wallCoord[1], cellMiddle[1], wallCoord[0], cellMiddle[0]);
                    wallCoord[0] = -1;
                }
            }
        }
    }

    public boolean booleanit(int x, boolean maxVaiMin) {
        boolean onko = true;
        if (maxVaiMin) {
            if (x == 0) {
                onko = false;
            }
        } else {
            if (x == level.length) {
                onko = false;
            }
        }
        return onko;
    }

    public void findCellsClosestAndGenerate() {
        currentCell = new Cell(0);
        int i = 0;
        int j = 0;
        boolean apu = false;
        int counter;
        int numba = 0;
        while (i < level.length & j < level.length) {
            Cell cell = level[i][j];
            if (cell.isActive()) {
                if (!currentCell.isActive()) {
                    currentCell = cell;
                } else {
                    if (!cell.isConnected()) {

                        prepCells(cell, currentCell);
                        currentCell = cell;
                    }
                }
            }
            if (i == j) {
                i = 0;
                j++;
                continue;
            }
            if (!apu) {
                counter = i;
                i = j;
                j = counter;
                numba = j;
                apu = true;
            } else {
                j = i;
                i = numba + 1;
                apu = false;
            }
        }
    }

    public void ConnectTwo(int x1, int y1, int x2, int y2) {

        Cell cell = level[x1][y1];
        Cell cell2 = level[x2][y2];

        prepCells(cell, cell2);

    }

    public void prepCells(Cell cell, Cell cell2) {
        cell.setConnected(true);
        cell2.setConnected(true);
        int[] cellMiddle = cell.getRoom().getMiddleInStage();
        int[] currentCellMiddle = cell2.getRoom().getMiddleInStage();

        if (diagonal) {
            carveCorridorDiagonal(currentCellMiddle[1], cellMiddle[1], currentCellMiddle[0], cellMiddle[0]);
        } else {
            carveCorridor(currentCellMiddle[1], cellMiddle[1], currentCellMiddle[0], cellMiddle[0]);
        }
    }

    public void carveCorridor(int x1, int x2, int y1, int y2) {
        int position1 = y1;
        int position2 = x1;

        while (true) {
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

            if (position1 == y2 & position2 == x2) {
                break;
            }
        }
    }

    public void carveCorridorDiagonal(int x1, int x2, int y1, int y2) {
        int position1 = y1;
        int position2 = x1;

        while (true) {
            if (position2 != x2 && position1 != y2) {
                if (position2 < x2) {
                    position2++;
                } else {
                    position2--;
                }
            }

            if (position1 < y2) {
                position1++;
            } else {
                position1--;
            }

            checkSurroundingTiles(position1, position2);

            if (position1 == y2 & position2 == x2) {
                break;
            }
        }
    }

    private void checkSurroundingTiles(int y, int x) {
        for (int i = y - this.size; i <= y + this.size; i++) {
            for (int j = x - this.size; j <= x + this.size; j++) {
                if (j <= 0 | i <= 0 | j >= this.r.getWidth() - 1 | i >= this.r.getHeight() - 1) {
                    continue;
                }
                if (i == y - this.size | i == y + this.size | j == x + this.size | j == x - this.size) {
                    r.tilesh[i][j].checkTile();
                } else {
                    if (r.tilesh[i][j].getType() == 0) {
                        r.tilesh[i][j] = new Tile(1);
                    }
                }
            }
        }
    }

}

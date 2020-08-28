
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
    int oldt[] = {1};
    int newt = 5;
    int roadTile = 4;
    int biome;

    public Corridor(Floor level, int size, boolean diagonal, int biome) {
        this.biome = biome;
        this.diagonal = diagonal;
        this.r = level.getStage();
        this.level = level.getLevel();
        this.size = size;
    }

    /**
     * Etsii kentän huoneiden keskikohdat ja luo käytävät niiden avulla.
     */
    public void findCellsTopToBottomAndGenerate() {
        currentCell = new Cell(0,biome);

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
        currentCell = new Cell(0,biome);
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
        boolean enclosed;
        for (int i = 0; i < level.length; i++) {
            for (int j = 0; j < level.length; j++) {

                enclosed = false;
                Cell cell = level[i][j];
                if (cell.isActive()) {
                    up = booleanitHBorder(j, true, i);
                    down = booleanitHBorder(j, false, i);
                    left = booleanitVBorder(i, true, j);
                    right = booleanitVBorder(i, false, j);
                    if (!up && !down && !left && !right) {
                        enclosed = true;
                    }
                    int[] cellMiddle = cell.getRoom().getMiddleInStage();
                    if (enclosed) {
                        int[] o = this.oldt;
                        int[] kor = {6,7,8};
                        int v = roadTile;
                        roadTile = 3;
                        oldt = kor;
                        if (booleanitHCell(j, true, i)) {
                            prepCells(cell, level[i][j - 1]);
                        } else if (booleanitHCell(j, false, i)) {
                            prepCells(cell, level[i][j + 1]);
                        } else if (booleanitVCell(i, true, j)) {
                            prepCells(cell, level[i - 1][j]);
                        } else if (booleanitVCell(i, false, j)) {
                            prepCells(cell, level[i + 1][j]);
                        }
                        roadTile = v;
                        oldt = o;
                        continue;
                    } else if (down) {
                        wallCoord[0] = cellMiddle[0] + (cell.size / 2);
                        wallCoord[1] = cellMiddle[1];
                    } else if (up) {
                        wallCoord[0] = cellMiddle[0] - (cell.size / 2);
                        wallCoord[1] = cellMiddle[1];
                    } else if (left) {
                        wallCoord[0] = cellMiddle[0];
                        wallCoord[1] = cellMiddle[1] - (cell.size / 2);
                    } else if (right) {
                        wallCoord[0] = cellMiddle[0];
                        wallCoord[1] = cellMiddle[1] + (cell.size / 2);
                    } else {
                        continue;
                    }
                    cell.setConnected(true);
                    if (diagonal) {
                        carveCorridorDiagonal(wallCoord[1], cellMiddle[1], wallCoord[0], cellMiddle[0]);
                    } else {
                        carveCorridor(wallCoord[1], cellMiddle[1], wallCoord[0], cellMiddle[0]);
                    }
                    wallCoord[0] = -1;
                }
            }
        }
    }

    public void findCellsClosestAndGenerate() {
        currentCell = new Cell(0,biome);
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

    public void allToOneCoord(int x, int y) {

        for (int i = 0; i < level.length; i++) {
            for (int j = 0; j < level.length; j++) {
                Cell cell = level[i][j];
                if (cell.isActive()) {

                    int[] cellMiddle = cell.getRoom().getMiddleInStage();

                    if (diagonal) {
                        carveCorridorDiagonal(x, cellMiddle[1], y, cellMiddle[0]);
                    } else {
                        carveCorridor(x, cellMiddle[1], y, cellMiddle[0]);
                    }
                }
            }
        }
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
                    r.tilesh[i][j].checkTile(this.oldt, this.newt);
                } else {
                    if (!r.tilesh[i][j].isInRoom()) {
                        r.tilesh[i][j] = new Tile(roadTile,biome);
                    }
                }
            }
        }
    }

    public boolean booleanitHBorder(int x, boolean min, int y) {
        boolean onko = true;
        if (min) {
            if (x == 0) {
                onko = false;
            } else if (level[y][x - 1].isActive()) {
                onko = false;
            }
        } else {
            if (x == level.length - 1) {
                onko = false;
            } else if (level[y][x + 1].isActive()) {
                onko = false;
            }
        }
        return onko;
    }

    public boolean booleanitVBorder(int x, boolean min, int y) {
        boolean onko = true;
        if (min) {
            if (x == 0) {
                onko = false;
            } else if (level[x - 1][y].isActive()) {
                onko = false;
            }
        } else {
            if (x == level.length - 1) {
                onko = false;
            } else if (level[x + 1][y].isActive()) {
                onko = false;
            }
        }
        return onko;
    }

    public boolean booleanitVCell(int x, boolean min, int y) {
        boolean onko = true;
        if (min) {
            if (x == 0) {
                onko = false;
            } else if (level[x - 1][y].isActive()) {
                onko = true;
            }
        } else {
            if (x == level.length - 1) {
                onko = false;
            } else if (level[x + 1][y].isActive()) {
                onko = true;
            }
        }
        return onko;
    }

    public boolean booleanitHCell(int x, boolean min, int y) {
        boolean onko = false;
        if (min) {
            if (x == 0) {
                onko = false;
            } else if (level[x - 1][y].isActive()) {
                onko = true;
            }
        } else {
            if (x == level.length - 1) {
                onko = false;
            } else if (level[x + 1][y].isActive()) {
                onko = true;
            }
        }
        return onko;
    }
}

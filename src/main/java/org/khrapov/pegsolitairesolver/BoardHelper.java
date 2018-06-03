package org.khrapov.pegsolitairesolver;

/**
 * BoardHelper class contains methods to perform symmetry operations on a Peg Solitaire boards and positions.
 *
 */
public class BoardHelper
{
    private int sizeX;
    private int sizeY;
    private int boardSize;
    private boolean[] pos;


    BoardHelper(int x, int y, boolean[] pos)
    {
        sizeX = x;
        sizeY = y;
        this.pos = pos;
        boardSize = x*y;

        if(boardSize != pos.length)
        {
            throw new RuntimeException("array size mismatch");
        }
    }


    @Override
    public boolean equals(Object o)
    {
        if(o == this)
        {
            return true;
        }

        if(o == null)
        {
            return false;
        }

        if(!(o instanceof BoardHelper))
        {
            return false;
        }

        BoardHelper otherBoardHelper = (BoardHelper) o;

        if(this.pos.length != otherBoardHelper.pos.length)
        {
            return false;
        }

        for(int i = 0; i < pos.length; i++)
        {
            if(pos[i] != otherBoardHelper.pos[i])
            {
                return false;
            }
        }

        return true;
    }


    @Override
    public int hashCode()
    {
        return properUniqueID().hashCode();
    }


    BoardHelper verticalFlip()
    {
        boolean[] mod_pos = new boolean[boardSize];
        boolean[][] t1 = new boolean[sizeX][sizeY];
        boolean[][] t2 = new boolean[sizeX][sizeY];

        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                t1[x][y] = pos[y*sizeX + x];
            }
        }

        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                t2[x][y] = t1[(sizeX - 1) - x][y];
            }
        }

        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                mod_pos[y*sizeX + x] = t2[x][y];
            }
        }

        return new BoardHelper(sizeX, sizeY, mod_pos);
    }


    BoardHelper horizontalFlip() {
        boolean[] mod_pos = new boolean[boardSize];
        boolean[][] t1 = new boolean[sizeX][sizeY];
        boolean[][] t2 = new boolean[sizeX][sizeY];

        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                t1[x][y] = pos[y*sizeX + x];
            }
        }

        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                t2[x][y] = t1[x][(sizeY - 1) - y];
            }
        }

        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                mod_pos[y*sizeX + x] = t2[x][y];
            }
        }

        return new BoardHelper(sizeX, sizeY, mod_pos);
    }


    BoardHelper leftDiagonalFlip() {
        boolean[] mod_pos = new boolean[boardSize];
        boolean[][] t1 = new boolean[sizeX][sizeY];
        boolean[][] t2 = new boolean[sizeX][sizeY];

        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                t1[x][y] = pos[y*sizeX + x];
            }
        }

        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                t2[x][y] = t1[(sizeY - 1) - y][(sizeX - 1) - x];
            }
        }

        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                mod_pos[y*sizeX + x] = t2[x][y];
            }
        }

        return new BoardHelper(sizeX, sizeY, mod_pos);
    }


    BoardHelper rightDiagonalFlip() {
        boolean[] mod_pos = new boolean[boardSize];
        boolean[][] t1 = new boolean[sizeX][sizeY];
        boolean[][] t2 = new boolean[sizeX][sizeY];

        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                t1[x][y] = pos[y*sizeX + x];
            }
        }

        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                t2[x][y] = t1[y][x];
            }
        }

        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                mod_pos[y*sizeX + x] = t2[x][y];
            }
        }

        return new BoardHelper(sizeX, sizeY, mod_pos);
    }


    BoardHelper rotate90() {
        boolean[] mod_pos = new boolean[boardSize];
        boolean[][] t1 = new boolean[sizeX][sizeY];
        boolean[][] t2 = new boolean[sizeX][sizeY];

        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                t1[x][y] = pos[y*sizeX + x];
            }
        }

        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                t2[x][y] = t1[y][(sizeX - 1) - x];
            }
        }

        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                mod_pos[y*sizeX + x] = t2[x][y];
            }
        }

        return new BoardHelper(sizeX, sizeY, mod_pos);
    }


    BoardHelper rotate180() {
        boolean[] mod_pos = new boolean[boardSize];
        boolean[][] t1 = new boolean[sizeX][sizeY];
        boolean[][] t2 = new boolean[sizeX][sizeY];

        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                t1[x][y] = pos[y*sizeX + x];
            }
        }

        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                t2[x][y] = t1[(sizeX - 1) - x][(sizeY - 1) - y];
            }
        }

        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                mod_pos[y*sizeX + x] = t2[x][y];
            }
        }

        return new BoardHelper(sizeX, sizeY, mod_pos);
    }


    BoardHelper rotate270() {
        boolean[] mod_pos = new boolean[boardSize];
        boolean[][] t1 = new boolean[sizeX][sizeY];
        boolean[][] t2 = new boolean[sizeX][sizeY];

        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                t1[x][y] = pos[y*sizeX + x];
            }
        }

        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                t2[x][y] = t1[(sizeY - 1) - y][x];
            }
        }

        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                mod_pos[y*sizeX + x] = t2[x][y];
            }
        }

        return new BoardHelper(sizeX, sizeY, mod_pos);
    }


    /**
     * Unique own properUniqueID for this BoardHelper position. A different
     * board position that can be converted to this position via a
     * symmetry operation will have a different properUniqueID.
     *
     * @return String
     */
    String properUniqueID() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < boardSize; i++) {
            if(pos[i]) {
                sb.append('1');
            }
            else {
                sb.append('0');
            }
        }
        return sb.toString();
    }
}

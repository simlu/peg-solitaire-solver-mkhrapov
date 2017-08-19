package org.khrapov.pegsolitairesolver;


class Board {
    private int sizeX;
    private int sizeY;
    private int boardSize;
    private boolean[] pos;


    Board(int x, int y, boolean[] pos)
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

        if(!(o instanceof Board))
        {
            return false;
        }

        Board otherBoard = (Board) o;

        if(this.pos.length != otherBoard.pos.length)
        {
            return false;
        }

        for(int i = 0; i < pos.length; i++)
        {
            if(pos[i] != otherBoard.pos[i])
            {
                return false;
            }
        }

        return true;
    }


    @Override
    public int hashCode()
    {
        return (int) properUniqueID();
    }


    Board verticalFlip()
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

        return new Board(sizeX, sizeY, mod_pos);
    }


    Board horizontalFlip() {
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

        return new Board(sizeX, sizeY, mod_pos);
    }


    Board leftDiagonalFlip() {
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

        return new Board(sizeX, sizeY, mod_pos);
    }


    Board rightDiagonalFlip() {
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

        return new Board(sizeX, sizeY, mod_pos);
    }


    Board rotate90() {
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

        return new Board(sizeX, sizeY, mod_pos);
    }


    Board rotate180() {
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

        return new Board(sizeX, sizeY, mod_pos);
    }


    Board rotate270() {
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

        return new Board(sizeX, sizeY, mod_pos);
    }


    /**
     * Unique own properUniqueID for this Board position. A different
     * board position that can be converted to this position via a
     * symmetry operation will have a different properUniqueID.
     *
     * @return long
     */
    long properUniqueID() {
        long hash = 0L;
        for(int i = 0; i < boardSize; i++) {
            if(pos[i]) {
                hash = hash << 1;
                hash += 1;
            }
            else {
                hash = hash << 1;
            }
        }
        return hash;
    }
}

package org.khrapov.pegsolitairesolver;

public class BoardConfig {
    private int sizeX;
    private int sizeY;
    private boolean[] allowedPositions;

    // symmetry
    private boolean verticalFlip = false;
    private boolean horizontalFlip = false;
    private boolean leftDiagonalFlip = false; // top left to bottom right
    private boolean rightDiagonalFlip = false; // top right to bottom left
    private boolean rotate90 = false;
    private boolean rotate180 = false;
    private boolean rotate270 = false;


    public BoardConfig(int x, int y) {
        if (x < 4 || y < 4 || x * y > 64) {
            throw new RuntimeException("Board dimentions may not be smaller than 4 " +
                "or board size may not be larger than 64 cells.");
        }

        sizeX = x;
        sizeY = y;

        allowedPositions = new boolean[x * y];
    }


    public void setAllowedPositions(int[] allowedPositions) {
        if (allowedPositions.length != this.allowedPositions.length) {
            throw new RuntimeException("array size mismatch");
        }

        for (int i = 0; i < this.allowedPositions.length; i++) {
            if (allowedPositions[i] == 1) {
                this.allowedPositions[i] = true;
            }
        }

        discoverSymmetry();
    }


    public BoardState initialState(int x, int y) {
        BoardState boardState = new BoardState(this, sizeX, sizeY);

        // in the initial Board State all positions are occupied
        // except one
        for (int i = 0; i < allowedPositions.length; i++) {
            if (allowedPositions[i]) {
                boardState.setPosition(i, true);
            }
        }

        boardState.setPosition(x, y, false);
        return boardState;
    }


    boolean positionAllowed(int i)
    {
        return allowedPositions[i];
    }


    void discoverSymmetry() {
        Board b = new Board(sizeX, sizeY, allowedPositions);
        if(b.equals(b.verticalFlip()))
        {
            verticalFlip = true;
        }

        if(b.equals(b.horizontalFlip()))
        {
            horizontalFlip = true;
        }

        if(b.equals(b.leftDiagonalFlip()))
        {
            leftDiagonalFlip = true;
        }

        if(b.equals(b.rightDiagonalFlip()))
        {
            rightDiagonalFlip = true;
        }

        if(b.equals(b.rotate90()))
        {
            rotate90 = true;
        }

        if(b.equals(b.rotate180()))
        {
            rotate180 = true;
        }

        if(b.equals(b.rotate270()))
        {
            rotate270 = true;
        }
    }


    boolean isVerticalFlip() {
        return verticalFlip;
    }


    boolean isHorizontalFlip() {
        return horizontalFlip;
    }


    boolean isLeftDiagonalFlip()
    {
        return leftDiagonalFlip;
    }


    boolean isRightDiagonalFlip()
    {
        return rightDiagonalFlip;
    }


    boolean isRotate90()
    {
        return rotate90;
    }


    boolean isRotate180()
    {
        return rotate180;
    }


    boolean isRotate270()
    {
        return rotate270;
    }
}

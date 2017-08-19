package org.khrapov.pegsolitairesolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * BoardState class represents the state of the Peg Solitaire board
 * at any particular step during the game.
 *
 * There is no public constructor. The initial BoardState is constructed
 * by the BoardConfig class.
 */
public class BoardState {
    private int sizeX;
    private int sizeY;
    private int boardSize;
    private boolean[] occupiedPositions;
    private long boardID = 0;
    private int boardScore = 0;
    private BoardConfig boardConfig;
    private List<String> history;


    // constructor is not public. Should only be used by
    // BoardConfig to return initial state.
    // may also be used by in package unit tests.
    BoardState(BoardConfig bc, int x, int y)
    {
        if(x < 4 || y < 4 || x*y > 64)
        {
            throw new RuntimeException("Board dimentions may not be smaller than 4 " +
                "or board size may not be larger than 64 cells.");
        }

        boardConfig = bc;
        sizeX = x;
        sizeY = y;
        boardSize = x*y;

        occupiedPositions = new boolean[x*y];
        history = new ArrayList<String>();
    }


    // private pseudo constructor. To be used by the children method
    private BoardState beget(int x1, int y1, int x2, int y2, int x3, int y3)
    {
        BoardState bs = new BoardState(boardConfig, sizeX, sizeY);
        for(int i = 0; i < occupiedPositions.length; i++)
        {
            bs.setPosition(i, occupiedPositions[i]);
        }

        bs.setPosition(x1, y1, false);
        bs.setPosition(x2, y2, false);
        bs.setPosition(x3, y3, true);

        bs.history = historyCopy();
        bs.historyAdd(x1, y1, x3, y3);

        return bs;
    }


    void setPosition(int x, int y, boolean state)
    {
        occupiedPositions[y*sizeX + x] = state;
    }


    void setPosition(int i, boolean state)
    {
        occupiedPositions[i] = state;
    }


    boolean isFinal()
    {
        return numOccupiedPositions() == 1;
    }


    int numOccupiedPositions()
    {
        int counter = 0;

        for(int i = 0; i < occupiedPositions.length; i++)
        {
            if(occupiedPositions[i])
            {
                counter++;
            }
        }

        return counter;
    }


    long id()
    {
        if(boardID == 0)
        {
            boardID = calculateSymmetryNormalizedID();
        }

        return boardID;
    }


    int score()
    {
        if(boardScore == 0)
        {
            boardScore = calculateScore();
        }

        return boardScore;
    }


    /**
     * This function is critical to the speed of the solver. It calculates
     * the heuristic value that allows an aggressive pruning of the search tree.
     *
     * The idea came from my manual solving of the peg solitaire puzzles. It
     * appeared that the more compact boards are more likely to produce a solution.
     *
     * To quantify compactness I calculate the length of the border of the position.
     * If an occupied cell is next to an empty cell on any side, a 1 is added to
     * the length of the border.
     *
     * @return int measure of the board position's border length. The longer the border's
     * length, the less likely the position is to yield a solution. Shorter border
     * means more compact position, that is more likely to produce a solution.
     */
    int calculateScore()
    {
        int score = 0;

        for(int i = 0; i < boardSize; i++) {
            if(boardConfig.positionAllowed(i)) {
                if(occupiedPositions[i]) {
                    int x = i % sizeX;
                    int y = i / sizeY;
                    if(empty(x, y+1)) {
                        score += 1;
                    }
                    if(empty(x, y-1)) {
                        score += 1;
                    }
                    if(empty(x+1, y)) {
                        score += 1;
                    }
                    if(empty(x-1, y)) {
                        score += 1;
                    }
                }
            }
        }

        return score;
    }


    boolean empty(int x, int y)
    {
        if(x < 0) return true;
        if(y < 0) return true;
        if(x >= sizeX) return true;
        if(y >= sizeY) return true;
        int pos = y*sizeX + x;
        if(!boardConfig.positionAllowed(pos)) return true;
        if(!occupiedPositions[pos]) return true;
        return false;
    }


    /**
     * This function is used to help deduplicate a set of Board
     * positions. This ID is calculated with respect to symmetry.
     * Not only will 2 identical positions have the same ID, but
     * also 2 positions that can be converted into each other via
     * a symmetry operation allowed for this Board.
     *
     * This ID is normalized with respect to symmetry by calculating
     * the properUniqueIDs for this board position as well as all board
     * positions that can be converted into this position via an allowed
     * symmetry operation and then taking the smallest value.
     *
     * @return 64-bit integer unique for a set of symmetry
     * equivalent board positions.
     */
    long calculateSymmetryNormalizedID()
    {
        Board b = new Board(sizeX, sizeY, occupiedPositions);
        List<Long> l = new ArrayList<Long>();
        l.add(b.properUniqueID());

        if(boardConfig.isHorizontalFlip())
        {
            l.add(b.horizontalFlip().properUniqueID());
        }

        if(boardConfig.isVerticalFlip())
        {
            l.add(b.verticalFlip().properUniqueID());
        }

        if(boardConfig.isLeftDiagonalFlip())
        {
            l.add(b.leftDiagonalFlip().properUniqueID());
        }

        if(boardConfig.isRightDiagonalFlip())
        {
            l.add(b.rightDiagonalFlip().properUniqueID());
        }

        if(boardConfig.isRotate90())
        {
            l.add(b.rotate90().properUniqueID());
        }

        if(boardConfig.isRotate180())
        {
            l.add(b.rotate180().properUniqueID());
        }

        if(boardConfig.isRotate270())
        {
            l.add(b.rotate270().properUniqueID());
        }

        return Collections.min(l);
    }


    List<BoardState> children()
    {
        List<BoardState> lb = new ArrayList<BoardState>();

        int n = numOccupiedPositions();

        for(int i = 0; i < boardSize; i++) {
            if(boardConfig.positionAllowed(i)) {
                if(occupiedPositions[i]) {
                    int x = i % sizeX;
                    int y = i / sizeX;

                    if(isValidMove(x, y+1, x, y+2)) {
                        BoardState child = beget(x, y, x, y+1, x, y+2);
                        if(child.numOccupiedPositions() != n - 1)
                        {
                            System.out.println(String.format("i: %d x: %d y: %d %n", i, x, y));
                            display("Parent");
                            child.display("Child");
                            throw new RuntimeException("y+");
                        }
                        lb.add(child);
                    }

                    if(isValidMove(x, y-1, x, y-2)) {
                        BoardState child = beget(x, y, x, y-1, x, y-2);
                        if(child.numOccupiedPositions() != n - 1)
                        {
                            System.out.println(String.format("i: %d x: %d y: %d %n", i, x, y));
                            display("Parent");
                            child.display("Child");
                            throw new RuntimeException("y-");
                        }
                        lb.add(child);
                    }

                    if(isValidMove(x+1, y, x+2, y)) {
                        BoardState child = beget(x, y, x+1, y, x+2, y);
                        if(child.numOccupiedPositions() != n - 1)
                        {
                            System.out.println(String.format("i: %d x: %d y: %d %n", i, x, y));
                            display("Parent");
                            child.display("Child");
                            throw new RuntimeException("x+");
                        }
                        lb.add(child);
                    }

                    if(isValidMove(x-1, y, x-2, y)) {
                        BoardState child = beget(x, y, x-1, y, x-2, y);
                        if(child.numOccupiedPositions() != n - 1)
                        {
                            System.out.println(String.format("i: %d x: %d y: %d %n", i, x, y));
                            display("Parent");
                            child.display("Child");
                            throw new RuntimeException("x-");
                        }
                        lb.add(child);
                    }
                }
            }
        }

        return lb;
    }


    boolean isValidMove(int x1, int y1, int x2, int y2)
    {
        if(x1 < 0) return false;
        if(x2 < 0) return false;
        if(y1 < 0) return false;
        if(y2 < 0) return false;
        if(x1 >= sizeX) return false;
        if(x2 >= sizeX) return false;
        if(y1 >= sizeY) return false;
        if(y2 >= sizeY) return false;

        int pos1 = y1*sizeX + x1;
        int pos2 = y2*sizeX + x2;

        if(boardConfig.positionAllowed(pos1) &&
            occupiedPositions[pos1] &&
            boardConfig.positionAllowed(pos2) &&
            !occupiedPositions[pos2]) {
            return true;
        }

        return false;
    }


    List<String> historyCopy() {
        return new ArrayList<String>(history);
    }


    void historyAdd(int x1, int y1, int x2, int y2) {
        history.add(String.format("[%d, %d, %d, %d],%n", x1, y1, x2, y2));
    }

    String getHistory()
    {
        StringBuilder sb = new StringBuilder();
        for(String s : history)
        {
            sb.append(s);
        }

        return sb.toString();
    }

    void display(String header)
    {
        System.out.println("============== " + header + " ==================");
        for(int y = 0; y < sizeY; y++)
        {
            for(int x = 0; x < sizeX; x++)
            {
                int i = y*sizeX + x;
                if(boardConfig.positionAllowed(i))
                {
                    if(occupiedPositions[i] == true)
                    {
                        System.out.print(" 1");
                    }
                    else
                    {
                        System.out.print(" 0");
                    }
                }
                else
                {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
        System.out.println("====================================================");
    }
}

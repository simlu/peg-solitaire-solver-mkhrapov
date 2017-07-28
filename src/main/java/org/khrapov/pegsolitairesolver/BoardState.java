package org.khrapov.pegsolitairesolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        if(x < 5 || y < 5 || x*y > 64)
        {
            throw new RuntimeException("Board dimentions may not be smaller than 5 " +
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
        BoardState bs = new BoardState(boardConfig, sizeX, sizeX);
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
        int counter = 0;

        for(int i = 0; i < occupiedPositions.length; i++)
        {
            if(occupiedPositions[i])
            {
                counter++;
            }
        }

        return counter == 1;
    }


    long id()
    {
        if(boardID == 0)
        {
            boardID = calculateID();
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


    long calculateID()
    {
        Board b = new Board(sizeX, sizeY, occupiedPositions);
        List<Long> l = new ArrayList<Long>();
        l.add(b.id());

        if(boardConfig.isHorizontalFlip())
        {
            l.add(b.horizontalFlip().id());
        }

        if(boardConfig.isVerticalFlip())
        {
            l.add(b.verticalFlip().id());
        }

        if(boardConfig.isLeftDiagonalFlip())
        {
            l.add(b.leftDiagonalFlip().id());
        }

        if(boardConfig.isRightDiagonalFlip())
        {
            l.add(b.rightDiagonalFlip().id());
        }

        if(boardConfig.isRotate90())
        {
            l.add(b.rotate90().id());
        }

        if(boardConfig.isRotate180())
        {
            l.add(b.rotate180().id());
        }

        if(boardConfig.isRotate270())
        {
            l.add(b.rotate270().id());
        }

        return Collections.min(l);
    }


    public List<BoardState> children()
    {
        List<BoardState> lb = new ArrayList<BoardState>();

        for(int i = 0; i < boardSize; i++) {
            if(boardConfig.positionAllowed(i)) {
                if(occupiedPositions[i]) {
                    int x = i % sizeX;
                    int y = i / sizeY;

                    if(isValidMove(x, y+1, x, y+2)) {
                        lb.add(beget(x, y, x, y+1, x, y+2));
                    }

                    if(isValidMove(x, y-1, x, y-2)) {
                        lb.add(beget(x, y, x, y-1, x, y-2));
                    }

                    if(isValidMove(x+1, y, x+2, y)) {
                        lb.add(beget(x, y, x+1, y, x+2, y));
                    }

                    if(isValidMove(x-1, y, x-2, y)) {
                        lb.add(beget(x, y, x-1, y, x-2, y));
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
        List<String> copy = new ArrayList<String>();
        for (String s : history)
        {
            copy.add(s);
        }
        return copy;
    }


    void historyAdd(int x1, int y1, int x2, int y2) {
        history.add(String.format("[%d, %d, %d, %d],%n", x1, y1, x2, y2));
    }

    public String getHistory()
    {
        StringBuilder sb = new StringBuilder();
        for(String s : history)
        {
            sb.append(s);
        }

        return sb.toString();
    }
}

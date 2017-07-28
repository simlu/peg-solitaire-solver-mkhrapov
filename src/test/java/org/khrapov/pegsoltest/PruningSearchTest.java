package org.khrapov.pegsoltest;

import static org.junit.Assert.*;

import org.khrapov.pegsolitairesolver.*;
import org.junit.*;

public class PruningSearchTest {

    @Test
    public void solveEnglishBoard()
    {
        BoardConfig boardConfig = new BoardConfig(7, 7);
        boardConfig.setAllowedPositions(
            new int[] {
                0, 0, 1, 1, 1, 0, 0,
                0, 0, 1, 1, 1, 0, 0,
                1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1,
                0, 0, 1, 1, 1, 0, 0,
                0, 0, 1, 1, 1, 0, 0
            }
        );

        BoardState boardState = boardConfig.initialState(3, 3);
        PruningSearch pruningSearch = new PruningSearch(boardState);
        pruningSearch.prune(10000);
        int solutions = pruningSearch.search();
        if(solutions > 0)
        {
            for(int i = 0; i < solutions; i++)
            {
                System.out.println("Solution " + (i+1));
                System.out.println(pruningSearch.getSolution(i));
            }
        }
        else
        {
            fail("Solution to English board has not been found");
        }
    }
}

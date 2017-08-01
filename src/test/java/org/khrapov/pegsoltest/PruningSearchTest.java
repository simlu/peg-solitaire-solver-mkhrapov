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

        // minimum pruning number with which this search can
        // find a solution
        pruningSearch.prune(10);
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


    @Test
    public void solveFrenchBoard1()
    {
        BoardConfig boardConfig = new BoardConfig(7, 7);
        boardConfig.setAllowedPositions(
            new int[] {
                0, 0, 1, 1, 1, 0, 0,
                0, 1, 1, 1, 1, 1, 0,
                1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1,
                0, 1, 1, 1, 1, 1, 0,
                0, 0, 1, 1, 1, 0, 0
            }
        );

        BoardState boardState = boardConfig.initialState(0, 2);
        PruningSearch pruningSearch = new PruningSearch(boardState);

        // minimum pruning number with which this search can
        // find a solution
        pruningSearch.prune(9);
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
            fail("Solution to French1 board has not been found");
        }
    }


    @Test
    public void solveFrenchBoard2()
    {
        BoardConfig boardConfig = new BoardConfig(7, 7);
        boardConfig.setAllowedPositions(
            new int[] {
                0, 0, 1, 1, 1, 0, 0,
                0, 1, 1, 1, 1, 1, 0,
                1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1,
                0, 1, 1, 1, 1, 1, 0,
                0, 0, 1, 1, 1, 0, 0
            }
        );

        BoardState boardState = boardConfig.initialState(1, 3);
        PruningSearch pruningSearch = new PruningSearch(boardState);

        // minimum pruning number with which this search can
        // find a solution
        pruningSearch.prune(26);
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
            fail("Solution to French2 board has not been found");
        }
    }


    @Test
    public void solve6x6Board()
    {
        BoardConfig boardConfig = new BoardConfig(6, 6);
        boardConfig.setAllowedPositions(
            new int[] {
                1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1
            }
        );

        BoardState boardState = boardConfig.initialState(1, 1);
        PruningSearch pruningSearch = new PruningSearch(boardState);

        // minimum pruning number with which this search can
        // find a solution
        pruningSearch.prune(17);
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
            fail("Solution to 6x6 board has not been found");
        }
    }

/*
    @Test
    public void pruningNumberZeroIsUnlimited()
    {
        BoardConfig boardConfig = new BoardConfig(4, 6);
        boardConfig.setAllowedPositions(
            new int[] {
                1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1
            }
        );

        BoardState boardState = boardConfig.initialState(1, 1);
        PruningSearch pruningSearch = new PruningSearch(boardState);

        // Zero means no pruning
        pruningSearch.prune(0);
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
            fail("Solution to 4x6 board has not been found");
        }
    }
    */
}

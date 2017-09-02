package org.khrapov.pegsoltest;

import static org.junit.Assert.*;

import org.khrapov.pegsolitairesolver.*;
import org.junit.*;


public class PruningSearchTest
{

  @Test
  public void solveEnglishBoard()
  {
    BoardConfig boardConfig = new BoardConfig(7, 7);
    boardConfig.setAllowedPositions(
        new int[]{
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
    pruningSearch.setDebug(true);

    // minimum pruning number with which this search can
    // find a solution is 10
    pruningSearch.prune(10);
    int solutions = pruningSearch.search();
    if (solutions > 0)
    {
      for (int i = 0; i < solutions; i++)
      {
        System.out.println("Solution " + (i + 1));
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
        new int[]{
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
    if (solutions > 0)
    {
      for (int i = 0; i < solutions; i++)
      {
        System.out.println("Solution " + (i + 1));
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
        new int[]{
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
    if (solutions > 0)
    {
      for (int i = 0; i < solutions; i++)
      {
        System.out.println("Solution " + (i + 1));
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
        new int[]{
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
    if (solutions > 0)
    {
      for (int i = 0; i < solutions; i++)
      {
        System.out.println("Solution " + (i + 1));
        System.out.println(pruningSearch.getSolution(i));
      }
    }
    else
    {
      fail("Solution to 6x6 board has not been found");
    }
  }


  @Test
  public void solve6x4Board()
  {
    BoardConfig boardConfig = new BoardConfig(4, 6);
    boardConfig.setAllowedPositions(
        new int[]{
            1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1
        }
    );

    BoardState boardState = boardConfig.initialState(1, 1);
    PruningSearch pruningSearch = new PruningSearch(boardState);

    pruningSearch.prune(5);
    int solutions = pruningSearch.search();
    if (solutions > 0)
    {
      for (int i = 0; i < solutions; i++)
      {
        System.out.println("Solution " + (i + 1));
        System.out.println(pruningSearch.getSolution(i));
      }
    }
    else
    {
      fail("Solution to 4x6 board has not been found");
    }
  }

  @Test
  public void minimalBoardSize()
  {
    BoardConfig bc = new BoardConfig(1, 1);
  }


  @Test(expected = RuntimeException.class)
  public void boardTooSmall()
  {
    BoardConfig bc = new BoardConfig(0, 0);
  }

  @Test
  public void solve9x9Board()
  {
    BoardConfig boardConfig = new BoardConfig(9, 9);
    boardConfig.setAllowedPositions(
        new int[]{
            1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1
        }
    );

    BoardState boardState = boardConfig.initialState(4, 4);
    PruningSearch pruningSearch = new PruningSearch(boardState);

    pruningSearch.prune(50);
    int solutions = pruningSearch.search();
    if (solutions > 0)
    {
      for (int i = 0; i < solutions; i++)
      {
        System.out.println("Solution " + (i + 1));
        System.out.println(pruningSearch.getSolution(i));
      }
    }
    else
    {
      fail("Solution to 9x9 board has not been found");
    }
  }
}

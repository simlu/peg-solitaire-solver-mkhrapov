package org.khrapov.pegsolitaire.test;

import static org.junit.Assert.*;

import org.khrapov.pegsolitaire.solver.Board;
import org.khrapov.pegsolitaire.solver.Move;
import org.khrapov.pegsolitaire.solver.Position;
import org.khrapov.pegsolitaire.solver.PruningSearch;
import org.junit.*;

import java.util.List;


public class PruningSearchTest
{
  private static int[] englishBoard = new int[]{
      0, 0, 1, 1, 1, 0, 0,
      0, 0, 1, 1, 1, 0, 0,
      1, 1, 1, 1, 1, 1, 1,
      1, 1, 1, 1, 1, 1, 1,
      1, 1, 1, 1, 1, 1, 1,
      0, 0, 1, 1, 1, 0, 0,
      0, 0, 1, 1, 1, 0, 0
  };


  private static int[] frenchBoard = new int[]{
      0, 0, 1, 1, 1, 0, 0,
      0, 1, 1, 1, 1, 1, 0,
      1, 1, 1, 1, 1, 1, 1,
      1, 1, 1, 1, 1, 1, 1,
      1, 1, 1, 1, 1, 1, 1,
      0, 1, 1, 1, 1, 1, 0,
      0, 0, 1, 1, 1, 0, 0
  };


  private static int[] board6x6 = new int[]{
      1, 1, 1, 1, 1, 1,
      1, 1, 1, 1, 1, 1,
      1, 1, 1, 1, 1, 1,
      1, 1, 1, 1, 1, 1,
      1, 1, 1, 1, 1, 1,
      1, 1, 1, 1, 1, 1
  };


  private static int[] board4x6 = new int[]{
      1, 1, 1, 1,
      1, 1, 1, 1,
      1, 1, 1, 1,
      1, 1, 1, 1,
      1, 1, 1, 1,
      1, 1, 1, 1
  };


  private static int[] board9x9 = new int[]{
      1, 1, 1, 1, 1, 1, 1, 1, 1,
      1, 1, 1, 1, 1, 1, 1, 1, 1,
      1, 1, 1, 1, 1, 1, 1, 1, 1,
      1, 1, 1, 1, 1, 1, 1, 1, 1,
      1, 1, 1, 1, 1, 1, 1, 1, 1,
      1, 1, 1, 1, 1, 1, 1, 1, 1,
      1, 1, 1, 1, 1, 1, 1, 1, 1,
      1, 1, 1, 1, 1, 1, 1, 1, 1,
      1, 1, 1, 1, 1, 1, 1, 1, 1
  };


  private static int[] wiegleb = new int[] {
      0, 0, 0, 1, 1, 1, 0, 0, 0,
      0, 0, 0, 1, 1, 1, 0, 0, 0,
      0, 0, 0, 1, 1, 1, 0, 0, 0,
      1, 1, 1, 1, 1, 1, 1, 1, 1,
      1, 1, 1, 1, 1, 1, 1, 1, 1,
      1, 1, 1, 1, 1, 1, 1, 1, 1,
      0, 0, 0, 1, 1, 1, 0, 0, 0,
      0, 0, 0, 1, 1, 1, 0, 0, 0,
      0, 0, 0, 1, 1, 1, 0, 0, 0
  };


  private static int[] diamond41 = new int[] {
      0, 0, 0, 0, 1, 0, 0, 0, 0,
      0, 0, 0, 1, 1, 1, 0, 0, 0,
      0, 0, 1, 1, 1, 1, 1, 0, 0,
      0, 1, 1, 1, 1, 1, 1, 1, 0,
      1, 1, 1, 1, 1, 1, 1, 1, 1,
      0, 1, 1, 1, 1, 1, 1, 1, 0,
      0, 0, 1, 1, 1, 1, 1, 0, 0,
      0, 0, 0, 1, 1, 1, 0, 0, 0,
      0, 0, 0, 0, 1, 0, 0, 0, 0
  };

  @Test
  public void solveXXX()
  {
    int[] board = new int[]{
            0, 0, 1, 1, 1, 0, 0,
            0, 0, 1, 1, 1, 0, 0,
            1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1,
            0, 0, 1, 1, 1, 0, 0,
            0, 0, 1, 1, 1, 0, 0
    };
    Board b = new Board(7, 7, board);
    Position p = b.initialPosition(3, 3);
    Position f = b.initialPosition(3, 3);
    PruningSearch pruningSearch = new PruningSearch(p, f);

    pruningSearch.prune(121);
    int solutions = pruningSearch.search();
    if (solutions < 1)
    {
      fail("Solution has not been found");
    }
    List<Move> solution = pruningSearch.getSolution(0);
    System.out.println(p);
    for (int i = 0; i < solution.size(); i += 1) {
      Move move = solution.get(i);
      p.set(move.x1, move.y1, false);
      p.set((move.x1 + move.x2) / 2, (move.y1 + move.y2) / 2, false);
      p.set(move.x2, move.y2, true);
      System.out.println(p);
    }
  }



  @Test
  public void solveEnglishBoard()
  {
    Board b = new Board(7, 7, englishBoard);
    Position p = b.initialPosition(3, 3);
    PruningSearch pruningSearch = new PruningSearch(p);

    pruningSearch.prune(121);
    int solutions = pruningSearch.search();
    if (solutions < 1)
    {
      fail("Solution to English board has not been found");
    }
  }


  @Test
  public void solveFrenchBoard1()
  {
    Board b = new Board(7, 7, frenchBoard);
    Position p = b.initialPosition(0, 2);
    PruningSearch pruningSearch = new PruningSearch(p);

    pruningSearch.prune(9);
    int solutions = pruningSearch.search();
    if (solutions < 1)
    {
      fail("Solution to French1 board has not been found");
    }
  }


  @Test
  public void solveFrenchBoard2()
  {
    Board b = new Board(7, 7, frenchBoard);
    Position p = b.initialPosition(1, 3);
    PruningSearch pruningSearch = new PruningSearch(p);

    pruningSearch.prune(24);
    int solutions = pruningSearch.search();
    if (solutions < 1)
    {
      fail("Solution to French2 board has not been found");
    }
  }


  @Test
  public void solve6x6Board()
  {
    Board b = new Board(6, 6, board6x6);
    Position p = b.initialPosition(1, 1);
    PruningSearch pruningSearch = new PruningSearch(p);

    pruningSearch.prune(54);
    int solutions = pruningSearch.search();
    if (solutions < 1)
    {
      fail("Solution to 6x6 board has not been found");
    }
  }


  @Test
  public void solve4x6Board()
  {
    Board b = new Board(6, 4, board4x6);
    Position p = b.initialPosition(1, 1);
    PruningSearch pruningSearch = new PruningSearch(p);

    pruningSearch.prune(4);
    int solutions = pruningSearch.search();
    if (solutions < 1)
    {
      fail("Solution to 4x6 board has not been found");
    }
  }


  @Test
  public void minimalBoardSize()
  {
    Board b = new Board(1, 1, new int[] { 1 });
  }


  @Test(expected = RuntimeException.class)
  public void boardTooSmall()
  {
    Board b = new Board(0, 0, new int[] { 1 });
  }


  @Test
  public void solve9x9Board()
  {
    Board b = new Board(9, 9, board9x9);
    Position p = b.initialPosition(4, 4);
    PruningSearch pruningSearch = new PruningSearch(p);
    pruningSearch.prune(15);

    int solutions = pruningSearch.search();
    if (solutions < 1)
    {
      fail("Solution to 9x9 board has not been found");
    }
  }


  @Test
  public void solve9x9BoardWithSymmetry()
  {
    Board b = new Board(9, 9, board9x9);
    Position p = b.initialPosition(4, 4);
    PruningSearch pruningSearch = new PruningSearch(p);
    pruningSearch.setUseSymmetry(true);
    pruningSearch.prune(17);

    int solutions = pruningSearch.search();
    if (solutions < 1)
    {
      fail("Solution to 9x9 board has not been found");
    }
  }


  @Test
  public void solveWieglebBoard()
  {
    Board b = new Board(9, 9, wiegleb);
    Position p = b.initialPosition(4, 4);
    PruningSearch pruningSearch = new PruningSearch(p);
    pruningSearch.prune(186);

    // somebody requested a benchmark
    long start = System.nanoTime();
    int solutions = pruningSearch.search();
    long end = System.nanoTime();

    System.out.printf("Solving Wiegleb board took %f seconds.%n", (end-start)/1_000_000_000.0);

    if (solutions < 1)
    {
      fail("Solution to Wiegleb board has not been found");
    }
  }


  @Test
  public void solveDiamond41Board1()
  {
    Board b = new Board(9, 9, diamond41);
    Position p = b.initialPosition(3, 1);
    PruningSearch pruningSearch = new PruningSearch(p);
    pruningSearch.setUseSymmetry(true);
    pruningSearch.prune(83);

    // somebody requested a benchmark
    long start = System.nanoTime();
    int solutions = pruningSearch.search();
    long end = System.nanoTime();

    System.out.printf("Solving Diamond41 board took %f seconds.%n", (end-start)/1_000_000_000.0);

    if (solutions < 1)
    {
      fail("Solution to Diamond41 board has not been found");
    }
  }


  @Test
  public void solveDiamond41Board2()
  {
    Board b = new Board(9, 9, diamond41);
    Position p = b.initialPosition(4, 2);
    PruningSearch pruningSearch = new PruningSearch(p);
    pruningSearch.setUseSymmetry(true);
    pruningSearch.prune(82);

    // somebody requested a benchmark
    long start = System.nanoTime();
    int solutions = pruningSearch.search();
    long end = System.nanoTime();

    System.out.printf("Solving Diamond41 board took %f seconds.%n", (end-start)/1_000_000_000.0);

    if (solutions < 1)
    {
      fail("Solution to Diamond41 board has not been found");
    }
  }
}

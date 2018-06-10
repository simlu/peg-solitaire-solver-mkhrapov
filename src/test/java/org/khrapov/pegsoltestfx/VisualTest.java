package org.khrapov.pegsoltestfx;

import static org.junit.Assert.*;

import org.khrapov.pegsolitairesolver.*;
import org.junit.*;

public class VisualTest
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

    pruningSearch.prune(10);
    int solutions = pruningSearch.search();
    if (solutions > 0)
    {
    }
    else
    {
      fail("Solution to English board has not been found");
    }
  }
}

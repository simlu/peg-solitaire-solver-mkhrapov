package org.khrapov.pegsolitaire.javafxviz;

public class WieglebBoard
{
  public static void main(String[] args)
  {
    SearchRunner sr = new SearchRunner();

    sr.boardInitArray = new int[] {
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

    sr.boardName = "Wiegleb Board";
    sr.pruningFactor = 186;
    sr.boardSizeX = 9;
    sr.boardSizeY = 9;
    sr.initPosX = 4;
    sr.initPosY = 4;

    sr.visualize();
  }
}

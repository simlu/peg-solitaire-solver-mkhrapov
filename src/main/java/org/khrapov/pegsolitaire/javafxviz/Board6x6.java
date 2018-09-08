package org.khrapov.pegsolitaire.javafxviz;

public class Board6x6
{
  public static void main(String[] args)
  {
    SearchRunner sr = new SearchRunner();

    sr.boardInitArray = new int[] {
        1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1
    };

    sr.boardName = "6x6 Board";
    sr.pruningFactor = 100;
    sr.boardSizeX = 6;
    sr.boardSizeY = 6;
    sr.initPosX = 1;
    sr.initPosY = 1;

    sr.visualize();
  }
}

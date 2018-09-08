package org.khrapov.pegsolitaire.javafxviz;

public class FrenchBoard2
{
  public static void main(String[] args)
  {
    SearchRunner sr = new SearchRunner();

    sr.boardInitArray = new int[] {
        0, 0, 1, 1, 1, 0, 0,
        0, 1, 1, 1, 1, 1, 0,
        1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1,
        0, 1, 1, 1, 1, 1, 0,
        0, 0, 1, 1, 1, 0, 0
    };

    sr.boardName = "French Board 2";
    sr.pruningFactor = 100;
    sr.boardSizeX = 7;
    sr.boardSizeY = 7;
    sr.initPosX = 3;
    sr.initPosY = 1;

    sr.visualize();
  }
}

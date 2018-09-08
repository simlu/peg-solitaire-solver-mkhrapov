package org.khrapov.pegsolitaire.javafxviz;

public class FrenchBoard1
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

    sr.boardName = "French Board 1";
    sr.pruningFactor = 100;
    sr.boardSizeX = 7;
    sr.boardSizeY = 7;
    sr.initPosX = 2;
    sr.initPosY = 0;

    sr.visualize();
  }
}

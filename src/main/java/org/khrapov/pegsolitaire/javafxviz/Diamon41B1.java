package org.khrapov.pegsolitaire.javafxviz;

public class Diamon41B1
{
  public static void main(String[] args)
  {
    SearchRunner sr = new SearchRunner();

    sr.boardInitArray = new int[] {
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

    sr.boardName = "Diamond 41 Board 1";
    sr.pruningFactor = 100;
    sr.boardSizeX = 9;
    sr.boardSizeY = 9;
    sr.initPosX = 3;
    sr.initPosY = 1;

    sr.visualize();
  }
}

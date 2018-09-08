package org.khrapov.pegsolitaire.javafxviz;

public class Diamond41B2
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

    sr.boardName = "Diamond 41 Board 2";
    sr.pruningFactor = 200;
    sr.boardSizeX = 9;
    sr.boardSizeY = 9;
    sr.initPosX = 4;
    sr.initPosY = 2;

    sr.visualize();
  }
}

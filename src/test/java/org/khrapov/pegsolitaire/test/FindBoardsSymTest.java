package org.khrapov.pegsolitaire.test;

import org.junit.Test;
import org.khrapov.pegsolitaire.solver.Board;
import org.khrapov.pegsolitaire.solver.Position;
import org.khrapov.pegsolitaire.solver.PruningSearch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static org.khrapov.pegsolitaire.test.Util.*;

public class FindBoardsSymTest {

    private void addRandomHole(int[][] board) {
        ArrayList<int[]> possible = new ArrayList<>();
        int w = board[0].length;
        int h = board.length;
        for (int x = 0; x < w; x += 1) {
            for (int y = 0; y < h; y += 1) {
                if (
                    board[y][x] == 0 && (
                        (x - 1 >= 0 && board[y][x - 1] == 1)
                        || (x + 1 < w && board[y][x + 1] == 1)
                        || (y - 1 >= 0 && board[y - 1][x] == 1)
                        || (y + 1 < h && board[y + 1][x] == 1)
                    )
                ) {
                    possible.add(new int[] { x, y });
                }
            }
        }
        int selected = (int) Math.floor(Math.random() * possible.size());
        int[] idx = possible.get(selected);
        board[idx[1]][idx[0]] = 1;
    }

    private void makeSymmetric(int[][] board) {
        int w = board[0].length;
        int h = board.length;
        for (int x = 0; x < w; x += 1) {
            for (int y = 0; y < h; y += 1) {
                if (board[y][x] == 1) {
                    board[h - y - 1][x] = 1;
                    board[y][w - x - 1] = 1;
                    board[h - y - 1][w - x - 1] = 1;

                    board[x][y] = 1;
                    board[x][h - y - 1] = 1;
                    board[w - x - 1][y] = 1;
                    board[w - x - 1][h - y - 1] = 1;
                }
            }
        }
    }

    private int[][] makeBoard() {
        // todo: make 9x9 and find only symmetrical boards (flip diagonally, flip vertically, flip horizontally, flip both, etc)
        int[][] board = new int[][]{
                new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[] {0, 0, 0, 0, 1, 0, 0, 0, 0},
                new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        int holes = 5 + (int)Math.floor(Math.random() * 18);
        for (int i = 0; i < holes - 1; i += 1) {
            addRandomHole(board);
        }

        makeSymmetric(board);

        return board;
    }

    private int[] makeSimple(int[][] board) {
        int h = board.length;
        int w = board[0].length;
        int[] result = new int[w * h];
        for (int y = 0; y < h; y += 1) {
            for (int x = 0; x < w; x += 1) {
                int idx = y * w + x;
                result[idx] = board[y][x];
            }
        }
        return result;
    }

    @Test
    public void findSolvableBoards() throws NoSuchAlgorithmException, FileNotFoundException, UnsupportedEncodingException {
        int solutions = 0;
        while (true) {
            int[][] board = makeBoard();
            int w = board[0].length;
            int h = board.length;
            Board b = new Board(w, h, makeSimple(board));
            int count = b.holeCount;
            if (count > 45) {
                continue;
            }

            Position p = b.initialPosition(w/2, h/2);
            Position f = b.initialPosition(w/2, h/2);
            PruningSearch pruningSearch = new PruningSearch(p, f);
            int pruneNumber = 30000;
            pruningSearch.prune(pruneNumber);

            File checkFile = new File("check/" +pruneNumber + "_" + hash(p.toString()) + ".txt");
            if (checkFile.isFile()) {
                continue;
            }
            PrintWriter checkWriter = new PrintWriter(checkFile, "UTF-8");
            checkWriter.print(p);
            checkWriter.close();

            solutions = pruningSearch.search();
            if (solutions != 0) {
                pruningSearch.clearSolutions();
                int difficultyAbsolute = 0;
                do {
                    difficultyAbsolute += 1;
                    pruningSearch.prune(difficultyAbsolute);
                    solutions = pruningSearch.search();
                } while (solutions == 0);

                String result = solutionToString(p, pruningSearch.getSolution(0));
                String stringHash = hash(result);

                int difficulty = (int)Math.round(difficultyAbsolute * 100.0 / count);
                String fileName = "data/" + difficulty + '_' + difficultyAbsolute + '_' + count + "_" + stringHash + ".txt";

                if (!new File(fileName).isFile()) {
                    System.out.println(p);
                    System.out.println("----------------");
                    PrintWriter writer = new PrintWriter(fileName, "UTF-8");
                    writer.print(result);
                    writer.close();
                }
            }
        }
    }
}

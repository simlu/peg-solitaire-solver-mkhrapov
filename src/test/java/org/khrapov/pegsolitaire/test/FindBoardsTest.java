package org.khrapov.pegsolitaire.test;

import org.junit.Test;
import org.khrapov.pegsolitaire.solver.Board;
import org.khrapov.pegsolitaire.solver.Move;
import org.khrapov.pegsolitaire.solver.Position;
import org.khrapov.pegsolitaire.solver.PruningSearch;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class FindBoardsTest {

    private void addRandomHole(int[] board, int w, int h) {
        ArrayList<Integer> possible = new ArrayList<>();
        for (int x = 0; x < w; x += 1) {
            for (int y = 0; y < h; y += 1) {
                int idx = y * w + x;
                if (
                    board[idx] == 0 && (
                        (idx - 1 >= 0 && board[idx - 1] == 1)
                        || (idx + 1 < board.length && board[idx + 1] == 1)
                        || (idx - w >= 0 && board[idx - w] == 1)
                        || (idx + w < board.length && board[idx + w] == 1)
                    )
                ) {
                    possible.add(idx);
                }
            }
        }
        int selected = (int) Math.floor(Math.random() * possible.size());
        board[possible.get(selected)] = 1;
    }

    private int[] makeBoard(int w, int h) {
        int[] board = new int[]{
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
        };
        // random start peg
        board[(int)Math.floor(Math.random() * board.length)] = 1;
        int holes = 35 + (int)Math.floor(Math.random() * 7);
        for (int i = 0; i < holes - 1; i += 1) {
            addRandomHole(board, w, h);
        }
        return board;
    }

    private int[] getStart(int w, int h, int[] board) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int idx = 0; idx < board.length; idx += 1) {
            if (board[idx] == 1) {
                list.add(idx);
            }
        }
        int selected = list.get((int)Math.floor(Math.random() * list.size()));
        return new int[] { selected % w, selected / h };
    }

    public static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    @Test
    public void findSolvableBoards() throws NoSuchAlgorithmException, FileNotFoundException, UnsupportedEncodingException {
        int solutions = 0;
        while (true) {
            int w = 8;
            int h = 8;
            int[] board = makeBoard(w, h);
            int[] start = getStart(w, h, board);
            Board b = new Board(w, h, board);
            Position p = b.initialPosition(start[0], start[1]);
            Position f = b.initialPosition(start[0], start[1]);
            PruningSearch pruningSearch = new PruningSearch(p, f);

            pruningSearch.prune(500);
            solutions = pruningSearch.search();
            if (solutions != 0) {
                StringBuilder s = new StringBuilder();
                s.append(p);
                List<Move> solution = pruningSearch.getSolution(0);
                for (int i = 0; i < solution.size(); i += 1) {
                    s.append("----------------\n");
                    Move move = solution.get(i);
                    p.set(move.x1, move.y1, false);
                    p.set((move.x1 + move.x2) / 2, (move.y1 + move.y2) / 2, false);
                    p.set(move.x2, move.y2, true);
                    s.append(p);
                }

                System.out.println(p);
                System.out.println("----------------");

                String result = s.toString();
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                messageDigest.update(result.getBytes());
                String stringHash = toHexString(messageDigest.digest()).substring(0, 48);

                PrintWriter writer = new PrintWriter("data/" + stringHash + ".txt", "UTF-8");
                writer.print(result);
                writer.close();
            }
        }
    }
}

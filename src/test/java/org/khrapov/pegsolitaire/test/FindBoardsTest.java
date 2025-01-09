package org.khrapov.pegsolitaire.test;

import org.junit.Test;
import org.khrapov.pegsolitaire.solver.Board;
import org.khrapov.pegsolitaire.solver.Move;
import org.khrapov.pegsolitaire.solver.Position;
import org.khrapov.pegsolitaire.solver.PruningSearch;

import java.util.List;

import static org.junit.Assert.fail;

public class FindBoardsTest {
    @Test
    public void solveBoard()
    {
        int[] board = new int[]{
                0, 0, 1, 1, 1, 0, 0,
                0, 0, 1, 1, 1, 0, 0,
                1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1,
                0, 0, 1, 1, 1, 0, 0,
                0, 0, 1, 1, 1, 0, 0,
                0, 0, 1, 1, 1, 0, 0,
                0, 0, 1, 1, 1, 0, 0,
                0, 0, 1, 1, 1, 0, 0
        };
        Board b = new Board(7, 10, board);
        Position p = b.initialPosition(3, 3);
        Position f = b.initialPosition(3, 3);
        PruningSearch pruningSearch = new PruningSearch(p, f);

        pruningSearch.prune(500);
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
}

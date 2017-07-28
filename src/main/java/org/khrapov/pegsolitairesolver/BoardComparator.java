package org.khrapov.pegsolitairesolver;

import java.io.Serializable;
import java.util.Comparator;

public class BoardComparator implements Comparator<BoardState>, Serializable {

    private static final long serialVersionUID = 13033L;

    @Override
    public int compare(BoardState b1, BoardState b2) {
        int score1 = b1.score();
        int score2 = b2.score();
        if (score1 < score2) return -1;
        if (score1 == score2) return 0;
        return 1;
    }
}

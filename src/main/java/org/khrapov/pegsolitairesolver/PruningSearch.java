package org.khrapov.pegsolitairesolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PruningSearch {
    private BoardState initialBoardState;
    private int pruningThreshold = 10000;
    private int numSolutions = 0;
    private List<BoardState> solutions;


    public PruningSearch(BoardState boardState)
    {
        initialBoardState = boardState;
        solutions = new ArrayList<BoardState>();
    }


    public void prune(int prune)
    {
        pruningThreshold = prune;
    }


    public int search()
    {
        List<BoardState> gen0 = new ArrayList<BoardState>();
        gen0.add(initialBoardState);

        searchByGeneration(gen0);
        return getNumSolutions();
    }


    public int getNumSolutions()
    {
        return numSolutions;
    }


    public String getSolution(int id)
    {
        return solutions.get(id).getHistory();
    }


    void searchByGeneration(List<BoardState> currentGen)
    {
        if(currentGen.size() == 0)
        {
            return;
        }

        Set<Long> dedup = new TreeSet<Long>();
        List<BoardState> children = new ArrayList<BoardState>();

        for(BoardState b : currentGen) {
            for(BoardState child : b.children()) {
                if(!dedup.contains(child.id())) {
                    dedup.add(child.id());
                    children.add(child);
                }
            }
        }

        for(BoardState b : children) {
            if(b.isFinal()) {
                numSolutions++;
                solutions.add(b);
            }
        }

        if(numSolutions > 0)
        {
            return;
        }

        if(children.size() > pruningThreshold) {
            Collections.sort(children, new BoardComparator());
            List<BoardState> children2 = new ArrayList<BoardState>();
            for(int i = 0; i < pruningThreshold; i++) {
                children2.add(children.get(i));
            }
            children = children2;
        }

        searchByGeneration(children);
    }
}

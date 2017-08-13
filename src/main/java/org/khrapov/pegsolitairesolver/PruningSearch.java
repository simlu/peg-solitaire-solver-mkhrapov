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
    private int generation;
    private boolean DEBUG = false;


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
        List<BoardState> gen1 = new ArrayList<BoardState>();
        gen1.add(initialBoardState);

        generation = 1;
        searchByGeneration(gen1);
        return getNumSolutions();
    }


    public int getNumSolutions()
    {
        return numSolutions;
    }

    public void setDebug(boolean dbg)
    {
        DEBUG = dbg;
    }


    public String getSolution(int id)
    {
        return solutions.get(id).getHistory();
    }


    void searchByGeneration(List<BoardState> currentGen)
    {
        if(DEBUG)
        {
            System.out.print(String.format("Generation: %d Size: %d %n", generation,
                currentGen.size()));
        }

        generation++;

        if(currentGen.size() == 0)
        {
            return;
        }

        // Is TreeSet or a HashSet faster?
        // I ran a performance test where I tried to solve
        // the English board with the pruning number
        // set to 10_000.
        // TreeSet took 25 sec.
        // HashSet took 25 sec.
        // I think it is highly unlikely that the two libraries
        // have literally identical performance.
        // I think most likely this result means that
        // this part of the code is not a bottle neck.

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

        if(pruningThreshold > 0 && children.size() > pruningThreshold) {
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

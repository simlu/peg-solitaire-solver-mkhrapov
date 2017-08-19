package org.khrapov.pegsolitairesolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class PruningSearch encapsulates the pruning search algorithm of this Peg Solitaire solver.
 */
public class PruningSearch {
    private BoardState initialBoardState;
    private int pruningNumber = 10000;
    private List<BoardState> solutions;
    private int generation;
    private boolean DEBUG = false;


    /**
     * Constructore takes the initial BoardState from which to conduct the search.
     * Each BoardState instance contains a reference to the BoardConfig object that
     * describes the Board we are searching, so no additional information is necessary.
     *
     * @param boardState initial Board State from which we will conduct the search.
     */
    public PruningSearch(BoardState boardState)
    {
        initialBoardState = boardState;
        solutions = new ArrayList<BoardState>();
    }


    /**
     * <p>This method sets the <code>pruningNumber</code>. It is not necessary
     * to call this as the default is preset to 10000. This default, however,
     * is very lax and in most cases a much smaller value will allow to solve a
     * board. This value also affects greatly how long the search will take. It is
     * thus recommended to set an initial low <code>pruningNumber</code> (e.g. &lt; 50)
     * and if this turns out to be too aggressive and no solution is found, then increase it
     * gradually until the board is solved.
     * </p>
     *
     * <p>
     * Setting this to 0 (zero) will disable the pruning. In such a case the full search
     * tree is examined.
     * </p>
     *
     * <p>
     * Approximate runtimes with different pruning numbers:<br>
     * 10 - milliseconds<br>
     * 10000 - seconds<br>
     * 0 - hours
     * </p>
     *
     * @param prune Sets the desired pruning number.
     */
    public void prune(int prune)
    {
        pruningNumber = prune;
    }


    /**
     * Initiates search.
     *
     * @return number of solutions found. If 0 (zero) no solutions have been found.
     */
    public int search()
    {
        List<BoardState> gen1 = new ArrayList<BoardState>();
        gen1.add(initialBoardState);

        generation = 1;
        searchByGeneration(gen1);
        return getNumSolutions();
    }


    /**
     * Returns the number of found solutions. Prior to calling <code>search</code>
     * method this number would always be zero.
     *
     * @return returns the number of found solutions.
     */
    public int getNumSolutions()
    {
        if(solutions != null)
        {
            return solutions.size();
        }
        else
        {
            return 0;
        }
    }


    /**
     * If set to true, extra debugging info will be output to STDOUT.
     *
     * @param dbg boolean enable or disable outputting extra debugging info
     */
    public void setDebug(boolean dbg)
    {
        DEBUG = dbg;
    }


    /**
     * Return a particular solution from the list of solutions. May return NULL if
     * properUniqueID is illegal.
     *
     * @param id return consecutively numbered solution
     * @return String solution represented as a String.
     */
    public String getSolution(int id)
    {
        if(solutions == null)
        {
            return null;
        }

        if(id >= solutions.size() || id < 0)
        {
            return null;
        }
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
                solutions.add(b);
            }
        }

        if(solutions != null && solutions.size() > 0)
        {
            return;
        }

        if(pruningNumber > 0 && children.size() > pruningNumber) {
            Collections.sort(children, new BoardComparator());
            List<BoardState> children2 = new ArrayList<BoardState>();
            for(int i = 0; i < pruningNumber; i++) {
                children2.add(children.get(i));
            }
            children = children2;
        }

        searchByGeneration(children);
    }
}

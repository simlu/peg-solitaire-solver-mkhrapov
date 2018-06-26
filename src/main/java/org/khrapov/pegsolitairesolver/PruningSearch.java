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
    private final Position initialPosition;
    private int pruningNumber = 200;
    private final List<Position> solutions;
    private boolean useSymmetry = false;


    /**
     * Constructor takes the initial Position from which to conduct the search.
     * Each Position instance contains a reference to the Board object that
     * describes the Board we are searching, so no additional information is necessary.
     *
     * @param initialPosition initial Position from which we will conduct the search.
     */
    public PruningSearch(Position initialPosition)
    {
        this.initialPosition = initialPosition;
        solutions = new ArrayList<Position>();
    }


    /**
     * <p>This method sets the <code>pruningNumber</code>. It is not necessary
     * to call this as the default is preset to 200. If this turns out to be too aggressive
     * and no solution is found, then increase it gradually until the board is solved.
     * Larger <code>pruningNumber</code> will result in longer search times.
     * </p>
     *
     * <p>
     * Setting this to 0 (zero) will disable the pruning. In such a case the full search
     * tree is examined. This will take hours.
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


    public void setUseSymmetry(boolean val)
    {
        useSymmetry = val;
    }


    /**
     * Initiates search.
     *
     * @return number of solutions found. If 0 (zero) no solutions have been found.
     */
    public int search()
    {
        List<Position> gen0 = new ArrayList<Position>();
        gen0.add(initialPosition);

        searchByGeneration(gen0);
        return solutions.size();
    }


    void searchByGeneration(List<Position> currentGen)
    {
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

        Set<String> dedup = new TreeSet<String>();
        List<Position> children = new ArrayList<Position>();

        for(Position b : currentGen) {
            for(Position child : b.children()) {
                if(useSymmetry)
                {
                    if(!dedup.contains(child.symmID()))
                    {
                        dedup.add(child.symmID());
                        children.add(child);
                    }
                }
                else
                {
                    if (!dedup.contains(child.id()))
                    {
                        dedup.add(child.id());
                        children.add(child);
                    }
                }
            }
        }

        for(Position b : children) {
            if(b.isFinal()) {
                solutions.add(b);
            }
        }

        if(solutions.size() > 0)
        {
            return;
        }

        if(pruningNumber > 0 && children.size() > pruningNumber) {
            Collections.sort(children, new PositionComparator());
            List<Position> children2 = new ArrayList<Position>();
            for(int i = 0; i < pruningNumber; i++) {
                children2.add(children.get(i));
            }
            children = children2;
        }

        searchByGeneration(children);
    }
}

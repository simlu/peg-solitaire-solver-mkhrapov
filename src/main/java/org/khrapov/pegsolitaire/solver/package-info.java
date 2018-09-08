/**

<p>
This package contains an extremely fast solver for Peg Solitaire boards.
</p>

<p>
For example, the English board can be solved in mere milliseconds on a 10 year old desktop computer.
This is a result of a clever heuristic that allows for a very aggressive pruning of the search tree.
</p>

<p>
The search starts with a list of positions that contains only the initial position.
On each iteration of the search, each member of the list produces children - positions that can
be reached from that member by a single jump of any peg. All these children combined produce the next generation.
</p>

<p>
Before this new generation is handed off to the next iteration of the search, it is pruned to reduce
the number of positions that need to be searched.
</p>

<p>
The first stage in pruning is to eliminate the duplicate positions. Symmetry can be considered if desired.
Then any position that can be converted into another position via any symmetry operation is considered a duplicate.
</p>

<p>
At the second stage, for each remaining member of the new generation, we calculate a heuristic score
that determines how likely this position is to produce a solution to the board. The members are sorted
on this score and only <code>pruningNumber</code> of highest scoring members are allowed into the
next iteration of the search. This value can be set by the <code>prune</code> method. The smaller the number,
the fewer members are selected for the next iteration, the faster is the search. In many cases
<code>pruningNumber</code> less than 200 works well. If the <code>pruningNumber</code> is too
aggressive, the algorithm may not find a solution. In this case, increase the
<code>pruningNumber</code> and rerun the search.
</p>

<p>
A code example:
</p>

<pre>
{@code
// Solve English Peg Solitaire Board
int[] englishBoard = new int[]{
    0, 0, 1, 1, 1, 0, 0,
    0, 0, 1, 1, 1, 0, 0,
    1, 1, 1, 1, 1, 1, 1,
    1, 1, 1, 1, 1, 1, 1,
    1, 1, 1, 1, 1, 1, 1,
    0, 0, 1, 1, 1, 0, 0,
    0, 0, 1, 1, 1, 0, 0
};

Board b = new Board(7, 7, englishBoard);
Position p = b.initialPosition(3, 3);
PruningSearch pruningSearch = new PruningSearch(p);
pruningSearch.prune(121);
int solutions = pruningSearch.search();

}
</pre>



*/

package org.khrapov.pegsolitaire.solver;

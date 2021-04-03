package problems.taquin;

import iialib.stateSpace.algs.IHeuristicSearchAlgorithm;
import iialib.stateSpace.algs.Solution;
import iialib.stateSpace.algs.implementation.AStarSearchStats;
import iialib.stateSpace.algs.implementation.IDAStar;
import iialib.stateSpace.model.Problem;

public class Test {
	
	
	public static void main(String[] args) {
		int[][] initialm = { 
				  {2, 8, 3}, 
				  {1, 6, 4}, 
				  {7, 0, 5} 
				};
		int[][] finalm = { 
				  {1, 2, 3}, 
				  {8, 0, 4}, 
				  {7, 6, 5} 
				};
		TaquinState initialState = new TaquinState(initialm) ;
		TaquinState finalState = new TaquinState(finalm) ;

		Problem<TaquinState> p = Problem.defineProblem(initialState, finalState);
		IHeuristicSearchAlgorithm<TaquinState,TaquinOperator> aStar = new IDAStar<>();
		IHeuristicSearchAlgorithm<TaquinState,TaquinOperator> idaStar = new AStarSearchStats<>();


		Solution<TaquinState,TaquinOperator> sol = aStar.solve(p, Heuristics.nbOfUnmatchedTiles(finalState));
		
		//Solution<TaquinState,TaquinOperator> sol2 = idaStar.solve(p, Heuristics.nbOfUnmatchedTiles(finalState));



	}
		

}
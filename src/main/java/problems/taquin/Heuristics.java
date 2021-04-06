package problems.taquin;

import iialib.stateSpace.model.IHeuristic;


// Constants ----------------------------------------------------------


public class Heuristics {
	
	
	
	/**
	 * returns a heuristic corresponding to t
	 * @param goal	the goal state
	 * @return the heuristic with respect to the goal state
	 */
	public static IHeuristic<TaquinState> nbOfUnmatchedTiles(TaquinState goal){
		return new IHeuristic<TaquinState>() {
			@Override
			public double apply(TaquinState state) {
				int n = 0;
				for (int i= 0; i<state.matrice.length; i++)
					for(int j=0; j<state.matrice[0].length; j++)
						if (state.matrice[i][j] != goal.matrice[i][j])
							n++;
						
				return n;
			}
			@Override
			public String toString() {
				return  "number of misplaced tiles wrt goal"; 		
			}
			};
	}
	
	
	
	public static IHeuristic<TaquinState> manhattanDistance(TaquinState goal){
		
		return new IHeuristic<TaquinState>() {
			@Override
			public double apply(TaquinState state) {
				int rep = 0;
				for (int i= 0; i<state.matrice.length; i++)
					for(int j=0; j<state.matrice[0].length; j++) {
						rep+=dist(state.matrice[i][j],i,j);
					}
						
				return rep;
			}
			public int dist(int tuile,int i_tuile,int j_tuile) {
				//On cherche la tuile dans la matricge goal
				for (int i= 0; i<goal.matrice.length; i++)
					for(int j=0; j<goal.matrice[0].length; j++) {
						if(goal.matrice[i][j]==tuile) {
							return Math.abs(i_tuile-i)+Math.abs(j_tuile-j);
						}
					}
				return 0;
			}
			@Override
			public String toString() {
				return  "Manhattan distance wrt goal"; 		
			}
		};

	}
	
	
	
	
	
}

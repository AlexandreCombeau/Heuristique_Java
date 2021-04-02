package problems.agc1;

import iialib.stateSpace.model.IHeuristic;

/**
 * Formalization of a specific abstract graph.
 * 
 * @author  Philippe Chatalic
 */


// Constants ----------------------------------------------------------
public class Heuristics {

	public static IHeuristic<LNode> h1 = new IHeuristic<>(){
		@Override
		public double apply(LNode state) {
			switch (state) {
			case A : return 19;
			case B : return 16;
			case C : return 17;
			case D : return 15;
			case E : return 19;
			case F : return 17;
			case G : return 11;
			case H : return 6;
			case I : return 5;
			case J : return 17;
			case K : return 10;
			case L : return 12;
			case M : return 16;
			case N : return 8;
			case O : return 3;
			case P : return 0;
			case Q : return 4;
			case R : return 0;
			case S : return 12;
			case T : return 0;
			case U : return 0;
			default: new RuntimeException("invalide enum value"); return 0;			
			}
		};
	};
	
}

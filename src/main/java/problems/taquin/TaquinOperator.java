package problems.taquin;

import java.util.Arrays;
import java.util.List;


import iialib.stateSpace.model.IOperatorWithCost;

public enum TaquinOperator implements IOperatorWithCost<TaquinState>{
	/**
	 * UP : deplace la tuile sous la case vide vers le haut (et la case vide vers le bas)
	 * DOWN : deplace la tuile sur la case vide vers le bas (et la case vide vers le haut)
	 * LEFT : deplace la tuile a droite de la case vide vers la gauche (et la case vide vers la droite)
	 * RIGHT : deplace la tuile a gauche de la case vide vers la droite (et la case vide vers la gauche)
	 */
	
	UP,DOWN,LEFT,RIGHT;

	public static List<TaquinOperator> ALL_OPS = Arrays.asList(TaquinOperator.values());

	@Override
	public String getName() {
		switch (this) {
		case UP: 
			return "UP";
		case DOWN:
			return "DOWN";
		case RIGHT: 
			return "RIGHT";
		default:	//left
			return "LEFT";
		}
	}

	@Override
	public boolean isApplicable(TaquinState s) {
		int i = s.getiVide();
		int j = s.getjVide();
		//On calcule les nouvelles coordonnees de la case vide
		switch (this) {
		case UP: 
			i = i+1;
			break;
		case DOWN:
			i = i-1;
			break;
		case RIGHT: 
			j = j-1;
			break;
		default:	//left
			j = j+1;
			break;
		}
		//Si les nouvelles coordonnees ne sortent pas du cadre 
		return (i>= 0 && i<TaquinState.ORDER && j >=0 && j<TaquinState.ORDER) ;
	}

	@Override
	public TaquinState successor(TaquinState s) {
		if (this.isApplicable(s)) {
			int iVide = s.getiVide();
			int jVide = s.getjVide();
			
			//On copie les anciennes coordonnees
			int iAncienVide = iVide;
			int jAncienVide = jVide;
			
			//On calcule les nouvelles coordonnees de la case vide
			switch (this) {
			case UP: 
				iVide = iVide+1;
				break;
			case DOWN:
				iVide = iVide-1;
				break;
			case RIGHT: 
				jVide = jVide-1;
				break;
			default:	//left
				jVide = jVide+1;
				break;
			}
			
			//On copie les anciennes tuiles
			int [][] matrice = new int[TaquinState.ORDER][TaquinState.ORDER];
			for (int i= 0; i<matrice.length; i++)
				for(int j=0; j<matrice[0].length; j++)
					matrice[i][j] = s.matrice[i][j];
			
			//On met a jour avec le deplacement 
			matrice[iAncienVide][jAncienVide] = matrice[iVide][jVide];
			matrice[iVide][jVide] = 0;
			
			return new TaquinState(matrice);
		}
		return null;
	}


	@Override
	public double getCost() {
		return 1;
	}
	


}

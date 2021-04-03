package problems.taquin;

import java.util.Iterator;
import iialib.stateSpace.model.ApplicableOpsIterator;
import iialib.stateSpace.model.IState;

public class TaquinState implements IState<TaquinOperator>{
	
	/**On represente un etat par une matrice d'entiers
	 * Les attributs iVide et jVide representent les indices de la case vide
	 * On considere qu'on deplace une tuile et non la case vide
	 */
	
	// ---------------------- Attributes STATIC ----------------------
	public static int ORDER = 3;

	
	// ---------------------- Attributes ----------------------
	public int[][] matrice;
	private  int iVide;
	private  int jVide;


	// ---------------------- Constructors ----------------------
	public TaquinState(int[][] initialState) {
		matrice = new int[ORDER][ORDER];
		for (int i=0; i< ORDER; i++) {
			for (int j=0; j<ORDER; j++) {
				matrice[i][j] = initialState[i][j];
				if (initialState[i][j] == 0) {
					iVide = i;
					jVide = j;
				}	
			}
		}
	}


	// ---------------------- Mothods from IState ----------------------

	@Override
	public Iterator<TaquinOperator> applicableOperators() {
		return new ApplicableOpsIterator<TaquinState, TaquinOperator>(TaquinOperator.ALL_OPS, this);
	}
	
	public void initializeOperators() {
		TaquinOperator.ALL_OPS.clear();
		//On calcule les nouvelles coordonnees de la case vide pour chaque deplacement et on regarde si c'est faisable
		int i = iVide+1;
		int j = jVide;
		if (i>=0 && i<ORDER)  TaquinOperator.ALL_OPS.add(TaquinOperator.UP) ;
		i = iVide-1;
		if (i>=0 && i<ORDER)  TaquinOperator.ALL_OPS.add(TaquinOperator.DOWN) ;
		i = iVide;
		j = jVide+1;
		if (j>=0 && j<ORDER)  TaquinOperator.ALL_OPS.add(TaquinOperator.LEFT) ;
		j = jVide-1;
		if (j>=0 && j<ORDER)  TaquinOperator.ALL_OPS.add(TaquinOperator.RIGHT) ;	
		
	}
	@Override
	public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final TaquinState otherState = (TaquinState) obj;
		for (int i=0; i< ORDER; i++) 
			for (int j=0; j<ORDER; j++)
				if(! (matrice[i][j] == otherState.matrice[i][j]))
					return false;
				
		return true;
	}
	@Override
	public String toString() {
		String str = "\n";
		for (int i=0; i< ORDER; i++) {
			for (int j=0; j<ORDER; j++) {
				str+=" "+matrice[i][j]+" ";
			}
			str+="\n";
		}
		return str;
		
	}
	//Getters/Setters
	public int getiVide() {
		return iVide;
	}


	public void setiVide(int iVide) {
		this.iVide = iVide;
	}


	public int getjVide() {
		return jVide;
	}


	public void setjVide(int jVide) {
		this.jVide = jVide;
	}



}

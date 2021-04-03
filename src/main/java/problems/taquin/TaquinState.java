package problems.taquin;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import iialib.stateSpace.model.ApplicableOpsIterator;
import iialib.stateSpace.model.IState;
import problems.toBucharest.City;
import problems.toBucharest.OpRoad;

public class TaquinState implements IState<TaquinOperator>{

	// ---------------------- Attributes ----------------------
	public static int ORDER = 3;
	public  int iVide;
	public  int jVide;

	// Attributes


	// ---------------------- Attributes ----------------------
	public int[][] matrice;


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
	public String toString() {
		String str = "";
		for (int i=0; i< ORDER; i++) {
			for (int j=0; j<ORDER; j++) {
				str+=" "+matrice[i][j]+" ";
			}
			str+="\n";
		}
		return str;
		
	}


}

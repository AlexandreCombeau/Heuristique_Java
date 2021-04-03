package iialib.stateSpace.algs.implementation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import iialib.stateSpace.algs.AHeuristicSearchAlgorithmStats;
import iialib.stateSpace.algs.Solution;
import iialib.stateSpace.algs.SolutionWithCost;
import iialib.stateSpace.model.IHeuristic;
import iialib.stateSpace.model.IOperatorWithCost;
import iialib.stateSpace.model.IState;
import iialib.stateSpace.model.Problem;

public class IDAStar <S extends IState<O>, O extends IOperatorWithCost<S>>
				extends AHeuristicSearchAlgorithmStats<S, O>{
	// ----------------- Static attributes -----------------
	private static final String DESCRIPTION = "IDA*";
	
	private Problem<S> problem;

	// -----------------  Constructors -----------------
	public IDAStar() {
		super();
		
	}

	// ----------------- Methods from IHeuristicSearchAlgorithm -----------------

	
	@Override
	public Solution<S, O> solve(Problem<S> p, IHeuristic<S> h) {
		this.problem = p;
		SSNode<S,O> node = new SSNode<S,O>(problem.getInitialState(), null, null, 0, h.apply(problem.getInitialState()));
		Set<SSNode<S,O>> developedNodes  = new HashSet<SSNode<S,O>>();
		
		
		
		System.out.println("-----------------------------------------------------");
		System.out.println("Solving problem: " + p);
		System.out.println("with algorithm: " + DESCRIPTION + " and heurisitic " + h);
		System.out.println("-----------------------------------------------------");
		resetStatistics();

		SolutionWithCost<S, O> sol =  search(node,developedNodes, h);  // TODO
		System.out.println("-----------------------------------------------------");
		System.out.println((sol != null) ?
				"Solution : " + sol + "\nCost : " + sol.cost()
				: "FAILURE !");
		System.out.println("-----------------------------------------------------");
		return sol;
	}
	
	
	

    	
	private SolutionWithCost<S,O> search(SSNode<S, O> node, Set<SSNode<S,O>> developedNodes, IHeuristic<S> h){	
		S state = node.getState();
		double borne = h.apply(state);
		boolean succes = false; 
		boolean stop = false;
		
		
		SolutionReturned result = null;
		while (!stop && !succes) {
			developedNodes.add(node);
			result = rProfHeuristiqueBornee(node, developedNodes, borne, h);
			if (result.isEchec()) {
				if (result.getValue()> borne) 
					borne = result.getValue();
				else 
					stop = true;
			}else 
				succes = true;	
				
		}
        return result.getSolutionWithCost();
    }
    	

 
	/**
	 * Fonction auxiliaire
	 */
	private SolutionReturned rProfHeuristiqueBornee(SSNode<S,O> node, Set<SSNode<S,O>> developedNodes,double borne, IHeuristic<S> h){
		double f = node.getF();
		if (f > borne) {
			return new SolutionReturned (f);
		}
		
		S state = node.getState();
		if (problem.isTerminal(state))
			return new SolutionReturned(new SolutionWithCost<S,O>(state));
		else {
			double nouvelleBorne = Integer.MAX_VALUE;
			
			Iterator<O> it = state.applicableOperators();
			while (it.hasNext()) {
				O operator = it.next();
				S successor = operator.successor(state);
			   	
				if (!containsNodeWithSameState(developedNodes, successor)) { 
					double g_s = node.getG() + operator.getCost();
					double f_s =  g_s + h.apply(successor);
					SSNode<S,O> nodeSuccessor = new SSNode<S,O>(successor,operator, node, g_s, f_s);
					
					Set<SSNode<S,O>> developedNodesNew = new HashSet<SSNode<S,O>>(); 
					developedNodesNew.addAll(developedNodes);
					developedNodesNew.add(node);
					
					
					
					SolutionReturned res = rProfHeuristiqueBornee(nodeSuccessor, developedNodesNew, borne, h);
					
					if (res.isEchec())
						nouvelleBorne = (nouvelleBorne < res.getValue()) ? nouvelleBorne : res.getValue();
					else
						return new SolutionReturned(new SolutionWithCost<S,O>(state, operator, res.getSolutionWithCost()));
				}
			}
			return new SolutionReturned (nouvelleBorne);
		}
	}
	
	/**
	 * Renvoie Vrai si un set contient un noeud du meme etat
	 */
    private static <S extends IState<O>, O extends IOperatorWithCost<S>> boolean  containsNodeWithSameState(Collection<SSNode<S,O>> collection, S state) {
    	for(SSNode<S,O>  node : collection)
			if (node.getState().equals(state))
					return true;
		return false;	
    }
    
	/**
	 * Class qui permet de retourner une solution ou une valeur d'echec
	 */
	private class SolutionReturned{
		boolean echec;
		SolutionWithCost<S,O> solutionWithCost;
		double value;
		
		public SolutionReturned ( SolutionWithCost<S,O> s) {
			solutionWithCost =s ;
			echec = false;
		}
		public SolutionReturned( double val) {
			value = val;
			echec = true;
		}
		public boolean isEchec() {
			return this.echec;
		}
		
		//Getters/Setters
		public SolutionWithCost<S, O> getSolutionWithCost() {
			return solutionWithCost;
		}
		public void setSolutionWithCost(SolutionWithCost<S, O> solutionWithCost) {
			this.solutionWithCost = solutionWithCost;
		}
		public double getValue() {
			return value;
		}
		public void setValue(double value) {
			this.value = value;
		}
		
	}
}

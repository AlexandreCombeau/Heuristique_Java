package iialib.stateSpace.algs.implementation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import iialib.stateSpace.algs.AHeuristicSearchAlgorithmStats;
import iialib.stateSpace.algs.Solution;
import iialib.stateSpace.algs.SolutionWithCost;
import iialib.stateSpace.model.IHeuristic;
import iialib.stateSpace.model.IOperatorWithCost;
import iialib.stateSpace.model.IState;
import iialib.stateSpace.model.Problem;

public class IDAStarSearch <S extends IState<O>, O extends IOperatorWithCost<S>>
				extends AHeuristicSearchAlgorithmStats<S, O>{
	// ----------------- Static attributes -----------------
	private static final String DESCRIPTION = "IDA*";
	
	private Problem<S> problem;
	private double valEchec;

	// -----------------  Constructors -----------------
	public IDAStarSearch() {
		super();
		
	}

	// ----------------- Methods from IHeuristicSearchAlgorithm -----------------

	
	@Override
	public Solution<S, O> solve(Problem<S> p, IHeuristic<S> h) {
		this.problem = p;
		System.out.println("-----------------------------------------------------");
		System.out.println("Solving problem: " + p);
		System.out.println("with algorithm: " + this.DESCRIPTION + " and heurisitic " + h);
		System.out.println("-----------------------------------------------------");
		resetStatistics();

		SolutionWithCost<S, O> sol =  search(problem.getInitialState(), h);  // TODO
		System.out.println("-----------------------------------------------------");
		System.out.println((sol != null) ?
				"Solution : " + sol + "\nCost : " + sol.cost()
				: "FAILURE !");
		System.out.println("-----------------------------------------------------");
		return sol;
	}
	
	
	
	private SolutionWithCost<S,O> search(S s, IHeuristic<S> h){
		SSNode<S,O> node = new SSNode<S,O>(s, null, null, 0, h.apply(s));
		Set<SSNode<S,O>> developedNodes  = new HashSet<SSNode<S,O>>(); 
		return search2(node, developedNodes, h);
	}
    	
	private SolutionWithCost<S,O> search2(SSNode<S, O> node, Set<SSNode<S,O>> developedNodes, IHeuristic<S> h){	
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
    	
    private static <S extends IState<O>, O extends IOperatorWithCost<S>> boolean  containsNodeWithSameState(Collection<SSNode<S,O>> collection, S state) {
    	for(SSNode<S,O>  node : collection)
			if (node.getState().equals(state))
					return true;
		return false;	
    }
    
 
	
	private SolutionReturned rProfHeuristiqueBornee(SSNode<S,O> node, Set<SSNode<S,O>> developedNodes,double borne, IHeuristic<S> h){
		
		double f = node.getF();
		if (f > borne) {
			return new SolutionReturned (f);
		}
		
		S state = node.getState();
		System.out.println(node.getState()+"  "+f);
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
					
					Set<SSNode<S,O>> developedNodes2 = new HashSet<SSNode<S,O>>(); 
					developedNodes2.addAll(developedNodes);
					developedNodes2.add(node);
					
					
					
					SolutionReturned res = rProfHeuristiqueBornee(nodeSuccessor, developedNodes2, borne, h);
					
					if (res.isEchec())
						nouvelleBorne = (nouvelleBorne < res.getValue()) ? nouvelleBorne : res.getValue();
					else
						return new SolutionReturned(new SolutionWithCost<S,O>(state, operator, res.getSolutionWithCost()));
				}
			}
			return new SolutionReturned (nouvelleBorne);
		}
	}
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

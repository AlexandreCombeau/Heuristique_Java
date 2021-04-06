package iialib.stateSpace.algs.implementation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import iialib.stateSpace.algs.*;
import iialib.stateSpace.model.Problem;
import iialib.stateSpace.model.IHeuristic;
import iialib.stateSpace.model.IOperatorWithCost;
import iialib.stateSpace.model.IState;

public class AStarSearchStats<S extends IState<O>, O extends IOperatorWithCost<S>>
		extends AHeuristicSearchAlgorithmStats<S, O> {

	// ----------------- Static attributes -----------------
	private static final String DESCRIPTION = "A*";
	
	private Problem<S> problem;

	// -----------------  Constructors -----------------
	public AStarSearchStats() {
		super();
	}

	// ----------------- Methods from IHeuristicSearchAlgorithm -----------------
	@Override
	public Solution<S, O> solve(Problem<S> p, IHeuristic<S> h) {
		this.problem = p;
		System.out.println("-----------------------------------------------------");
		System.out.println("Solving problem: " + p);
		System.out.println("with algorithm: " + DESCRIPTION + " and heurisitic " + h);
		System.out.println("-----------------------------------------------------");
		resetStatistics();

		SolutionWithCost<S, O> sol =  search(problem.getInitialState(), h); 
		System.out.println("-----------------------------------------------------");
		System.out.println((sol != null) ?
				"Solution : " + sol + "\nCost : " + sol.cost()
				: "FAILURE !");
		System.out.println("-----------------------------------------------------");
		return sol;
	}
	
	private SolutionWithCost<S,O> search(S s, IHeuristic<S> h){
		SolutionWithCost<S,O> result = null;
		// Data structures initialization
		Set<SSNode<S,O>> developedNodes = new HashSet<SSNode<S,O>>();
    	LinkedList<SSNode<S,O>> frontier = new LinkedList<SSNode<S,O>>();
    	
    	frontier.addLast(new SSNode<S,O>(s, null, null, 0, h.apply(s)));


    	while(!frontier.isEmpty()) {
    		SSNode<S,O> node  = frontier.getFirst();
    		
    		//choix node minimal
    		for(SSNode<S,O> ssn : frontier) {
    			if(ssn.getF() < node.getF()) 
    				node = ssn;
    		}
    		S state = node.getState();
    		
			// Test for terminations
			if (problem.isTerminal(state)) {
				 result = buildSolution(node);
				 break;
			} // Node expansion
    		else {
    	    	 frontier.remove(node);
    	    	 developedNodes.add(node);
    	    	 
    			 Iterator<O> it = state.applicableOperators();
    			 while (it.hasNext()) {
    				 O operator = it.next();
    				 S successor = operator.successor(state);
    			   	
    				 if (! containsNodeWithSameState(developedNodes,successor) && ! containsNodeWithSameState(frontier,successor)) { //ajouter condition 
    				   
    					 double g = node.getG() + operator.getCost();
    					 double f = g + h.apply(successor);
    					 frontier.addLast(new SSNode<S,O>(successor,operator,node,g,f));
    				 }else { 
    				   
    					 boolean found = false;
    					 SSNode<S,O> nodeToUpdate = null;
    					 for(SSNode<S,O> n : developedNodes) {
    						 if(n.getState().equals(successor) && (n.getG() > node.getG() + operator.getCost())) {
    							 nodeToUpdate = n;
    							 found = true;
    							 break;
    						 }
    					 }
    					 if (! found) {
    						 for(SSNode<S,O> n : frontier) {
    							 if(n.getState().equals(successor) && (n.getG() > node.getG() + operator.getCost())) {
    								 nodeToUpdate = n;
    								 found = true;
    								 break;
    							 }
    						 }
    					 }
    					 if (found) {
    						 double g = node.getG() + operator.getCost();
    						 double f = g + h.apply(successor);
    						 nodeToUpdate.setG(g);
    						 nodeToUpdate.setF(f);
    						 nodeToUpdate.setAncestor(node);
    						 nodeToUpdate.setOperator(operator);
    					 }
    				 }
    			}
      		}
     	}
        return result;
    }
    	
    private static <S extends IState<O>, O extends IOperatorWithCost<S>> boolean  containsNodeWithSameState(Collection<SSNode<S,O>> collection, S state) {
    	for(SSNode<S,O>  node : collection)
			if (node.getState().equals(state))
					return true;
		return false;	
    }
    
 
    
	private static <S extends IState<O>, O extends IOperatorWithCost<S>> SolutionWithCost<S,O> buildSolution(SSNode<S,O> node) { 
		S s = node.getState();
		O op = node.getOperator();
		SSNode<S,O> ancestor = node.getAncestor();
		SolutionWithCost<S,O> sol = new SolutionWithCost<S,O>(s);
		while (ancestor != null) {
			sol = new SolutionWithCost<S,O>(ancestor.getState(),op,sol);
			op = ancestor.getOperator();
			ancestor = ancestor.getAncestor();
		} 
		return sol;
	}


}
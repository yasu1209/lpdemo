package com.example.lpdemo;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import lpsolve.LpSolve;
import lpsolve.LpSolveException;
import scpsolver.constraints.LinearBiggerThanEqualsConstraint;
import scpsolver.lpsolver.LinearProgramSolver;
import scpsolver.lpsolver.SolverFactory;
import scpsolver.problems.LinearProgram;

@Component
public class LpSolver {

	/**
	 * Solve the linear problem for calculating best [x1,x2,...,xn]
	 * Rely on GLPKSolverPack.jar which is GPL.
	 * 
	 * @param cost    Cost for each variable [c1,c2,...,cn]
	 * @param weights Weight for each variable [w1,w2,...wn]
	 * @param total   Total weight required
	 * @return The best result [x1,x2,...,xn]
	 */
	public double[] solve(double[] costs, double[] weights, double total) {

		/**
		 * problem min(sum(ci*xi))
		 */
		LinearProgram lp = new LinearProgram(costs);

		/**
		 * condition sum(wi*xi) >= total
		 */
		lp.addConstraint(new LinearBiggerThanEqualsConstraint(weights, total, "c1"));
		lp.setMinProblem(true);

		/**
		 * xi should bigger than zero
		 */
		double[] lowerBound = new double[costs.length];
		lp.setLowerbound(lowerBound);

		/**
		 * xi should be a integer
		 */
		boolean[] isInteger = new boolean[costs.length];
		Arrays.fill(isInteger, Boolean.TRUE);
		lp.setIsinteger(isInteger);

		LinearProgramSolver solver = SolverFactory.newDefault();
		return solver.solve(lp);
	}

	
	/**
	 * Solve the linear problem for calculating best [x1,x2,...,xn]
	 * Only use LPSOLVESolverPack.jar which is LGPL.
	 * 
	 * @param cost    Cost for each variable [c1,c2,...,cn]
	 * @param weights Weight for each variable [w1,w2,...wn]
	 * @param total   Total weight required
	 * @return The best result [x1,x2,...,xn]
	 */
	public double[] solve2(double[] costs, double[] weights, double total) {
		double[] result = new double[] {};
		
		try {
			// Create a problem with variables
			LpSolve solver = LpSolve.makeLp(0, costs.length);
			
			// set objective function
			solver.strSetObjFn(arrayToString(costs));
			solver.setMinim();

			// add constraints
			solver.strAddConstraint(arrayToString(weights), LpSolve.GE, total);

			// declare all variables to be integer
			for(int i=1;i<=costs.length;i++) {
				solver.setInt(i, true);
			}

			// solve the problem
			solver.solve();

			// print minimal function value
			System.out.println("Value of objective function: " + solver.getObjective());
			
			result = solver.getPtrVariables();
			
			// delete the problem and free memory
			solver.deleteLp();
			
		} catch (LpSolveException e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	private String arrayToString (double[] array) {
		StringBuilder sb = new StringBuilder();
		for(double d : array) {
			sb.append(d).append(" ");
		}
		
		return sb.toString();
	}

}

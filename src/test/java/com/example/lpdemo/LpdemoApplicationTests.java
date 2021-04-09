package com.example.lpdemo;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LpdemoApplicationTests {
	
	@Autowired
	LpSolver lpSolver;

	@Test
	void solve() {
		// use GLPKSolverPack.jar(GPL)
		
		double[] costs = new double[] {272.0, 2915.0, 5015.0, 6200.0, 6930.0, 8935.0, 12310.0};
		double[] weights = new double[] {1.0, 8.0, 12.0, 14.0, 18.0, 24.0, 48.0};
		double total = 50;
		double[] sol = lpSolver.solve(costs, weights, total);
		
		System.out.println(Arrays.toString(sol));
		
		// use LPSOLVESolverPack.jar(LGPL)
		
		//double[] sol2 = lpSolver.solve2(costs, weights, total);
		
		//System.out.println(Arrays.toString(sol2));
	}

}

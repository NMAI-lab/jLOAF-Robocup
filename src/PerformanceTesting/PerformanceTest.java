package PerformanceTesting;

import org.jLOAF.casebase.CaseBase;
import org.jLOAF.performance.PerformanceEvaluator;


import AgentModules.RoboCupAgent;

/***
 * This will create an agent with one caseBase and use a testBase to measure its performance. It will output all the performance measures such as
 * accuracy, recall, precision, f-measure.
 * @author Sacha Gunaratne 
 * @since 2017 May 
 ***/
public class PerformanceTest extends PerformanceEvaluator {
	
	public static void main(String a[]){
		String matchType = "gmm";
		String [] cbname = {"Data/cb_react_all_flags_rs_new.cb","Data/cb_react_all_flags_ls_new.cb"};
		
		PerformanceTest pt = new PerformanceTest();
		pt.PerformanceEvaluatorMethod(matchType,cbname);
		
	}

	@Override
	public RoboCupAgent trainAgent(String matchType, CaseBase cb) {
		RoboCupAgent agent = new RoboCupAgent(cb);
		agent.setSim(matchType);
		return agent;
	}
}

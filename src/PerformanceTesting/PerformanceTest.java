package PerformanceTesting;

import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.performance.Statistics;
import org.jLOAF.preprocessing.filter.casebasefilter.Sampling;

import AgentModules.RoboCupAgent;

/***
 * This will create an agent with one caseBase and use a testBase to measure its performance. It will output all the performance measures such as
 * accuracy, recall, precision, f-measure. 
 ***/
public class PerformanceTest {
	
	public static void main(String a[]){
		String cbname;
		String matchType;
		String tbname;
		
		if(a.length>0){
			cbname = a[1];
			tbname = a[2];
			matchType = a[3];
		}else{
			cbname = "Data/cb_react_no_flags_rs.cb";
			tbname = "Data/cb_react_no_flags_ls.cb";
			matchType = "mean";
		}
		
		CaseBase cb = CaseBase.load(cbname);
		CaseBase tb = CaseBase.load(tbname);
		
		Sampling s = new Sampling();
		CaseBase processed_cb = s.filter(cb);
		RoboCupAgent agent = new RoboCupAgent(processed_cb);
		agent.setSim(matchType);
		Statistics stats_module = new Statistics(agent);
		
		for(Case test: tb.getCases()){
			System.out.println("Starting testing...");
			stats_module.predictedCorrectActionName(test);
		}
		System.out.println("Testing complete");
		
	}
}

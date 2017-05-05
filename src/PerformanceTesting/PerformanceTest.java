package PerformanceTesting;

import java.util.ArrayList;

import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.performance.PerformanceMeasureCalculator;
import org.jLOAF.performance.Statistics;
import org.jLOAF.performance.StatisticsBundle;
import org.jLOAF.preprocessing.filter.casebasefilter.Sampling;

import AgentModules.RoboCupAgent;

/***
 * This will create an agent with one caseBase and use a testBase to measure its performance. It will output all the performance measures such as
 * accuracy, recall, precision, f-measure.
 * @author Sacha Gunaratne 
 * @since 2017 May 
 ***/
public class PerformanceTest {
	
	public static void main(String a[]){
		String [] cbname;
		String matchType;
		ArrayList<CaseBase> listOfCaseBases=new ArrayList<CaseBase>();
		ArrayList<CaseBase> tempList = new ArrayList<CaseBase>();
		int ignore =0;
		CaseBase tb = null;
		CaseBase cb = new CaseBase();;
		
		if(a.length>0){
			cbname = new String[]{a[1]};
			matchType = a[3];
		}else{
			cbname = new String[]{"Data/cb_react_all_flags_rs.cb","Data/cb_react_all_flags_ls.cb" };
			matchType = "default";
		}
		
		//adds all casebases to masterlist
		for(String s: cbname){
			listOfCaseBases.add(CaseBase.load(s));
		}
		
		ArrayList<StatisticsBundle>AllStats = new ArrayList<StatisticsBundle>();
		PerformanceMeasureCalculator pmc = new PerformanceMeasureCalculator();
		
		//loop over all casebases
		for(int ii=0;ii<listOfCaseBases.size();ii++){
			//temp list
			tempList.addAll(listOfCaseBases);
			
			//add ignore index casebase to testbase tb and remove from templist
			//add temp list to caseBase cb
			for(int i=0;i<listOfCaseBases.size();i++){
				if(ignore==i) {tb = listOfCaseBases.get(i);tempList.remove(ignore);}
				else {cb.addListOfCaseBases(tempList);}
			}

			//create agent with cb
			RoboCupAgent agent = new RoboCupAgent(cb);
			agent.setSim(matchType);
			Statistics stats_module = new Statistics(agent);
			
			//start testing 
			System.out.println("Cycle: "+ ignore + " - Starting testing...");
			for(Case test: tb.getCases()){
				stats_module.predictedCorrectActionName(test);
			}
			
			System.out.println("Testing complete");
			AllStats.add(stats_module.getStatisticsBundle());

			ignore++;
		}
		
		pmc.CalculateAllStats(AllStats);
		
	}
}

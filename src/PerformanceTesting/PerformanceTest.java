package PerformanceTesting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jLOAF.casebase.CaseBase;
import org.jLOAF.performance.PerformanceEvaluator;


import AgentModules.RoboCupAgent;
import CasebaseCreation.LogFile2CaseBase;

/***
 * This will create an agent with one caseBase and use a testBase to measure its performance. It will output all the performance measures such as
 * accuracy, recall, precision, f-measure.
 * @author Sacha Gunaratne 
 * @since 2017 May 
 ***/
public class PerformanceTest extends PerformanceEvaluator {
	
	public static void main(String a[]) throws IOException{
		String matchType = "default";
		String [] filenames = {"Data/Carleton_1.lsf","Data/University_1.lsf"};
		
		PerformanceTest pt = new PerformanceTest();
		pt.PerformanceEvaluatorMethod(matchType,filenames);
	}

	@Override
	public RoboCupAgent trainAgent(String matchType, CaseBase cb) {
		RoboCupAgent agent = new RoboCupAgent(cb);
		agent.setSim(matchType);
		return agent;
	}

	@Override
	public String[] createArrayOfCasebaseNames(String[] filenames) throws IOException {
		LogFile2CaseBase lg2cb = new LogFile2CaseBase();
		int count = 0;
		String [] cbnames = new String [filenames.length];
		
		for(String s: filenames){
			String str = "Data/cb"+count+".cb";
			cbnames[count]=str;
			lg2cb.logParser(s,str);
			count++;
		}
		return cbnames;
	}
}

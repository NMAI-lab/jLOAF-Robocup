package PerformanceTesting;

import java.io.IOException;

import org.jLOAF.performance.TestingConfig;
import org.jLOAF.preprocessing.filter.CaseBaseFilter;

public class TestingConfigRobocup{

	public static void main(String[] args){
		TestingConfig tc = new TestingConfig();
		
		String [] filenames = tc.getFileNames(args);
		String output_filename = tc.getOutputFileName(args);
		
		
		CaseBaseFilter ft =tc.createCaseBaseFilter(args);
		
		PerformanceTest pt = new PerformanceTest(); 
		String r = tc.getReasoner(args);
		String stSim =tc.getStSim(args);
		String cpSim = tc.getCpSim(args);
		
		
		try {
			pt.PerformanceEvaluatorMethod(filenames, ft, output_filename,r,stSim,cpSim);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	
	
	
	}

	


}
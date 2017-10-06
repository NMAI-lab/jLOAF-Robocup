package PerformanceTesting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jLOAF.casebase.CaseBase;
import org.jLOAF.performance.PerformanceEvaluator;
import org.jLOAF.preprocessing.filter.CaseBaseFilter;
import org.jLOAF.preprocessing.filter.casebasefilter.UnderSampling;
import org.jLOAF.preprocessing.filter.casebasefilter.Sampling;
import org.jLOAF.preprocessing.filter.featureSelection.WeightsSeperatorFilter;
import org.jLOAF.preprocessing.standardization.Standardization;

import AgentModules.RoboCupAgent;
import CasebaseCreation.LogFile2CaseBase;

/***
 * This will create an agent with one caseBase and use a testBase to measure its performance. It will output all the performance measures such as
 * accuracy, recall, precision, f-measure.
 * @author Sacha Gunaratne 
 * @since 2017 May 
 ***/
public class PerformanceTest extends PerformanceEvaluator {
	
	/**
	 * This main function requires multiple parameters to be setup:
	 * 
	 * @param filenames Array of logfiles 
	 * @param output_filename Path and name of the output statistics file
	 * 
	 * 
	 * @param CaseBaseFilter: Single or multiple CaseBaseFilters that are chained together
	 * @param String Reasoner: Reasoner type
	 * @param String StateBasedSimilarityMetric: Similarity for stateBasedInputs
	 * @param String ComplexSimilarityMetric: Similarity Strategy for ComplexInputs
	 * 
	 * @FinalObject PerformanceTest: A performance Testing object, that takes the above parameters and performs cross-validation and return statistics.
	 * (see the PerformanceTest in jLOAF for further instructions)
	 *  
	 * ***/
	public static void main(String a[]) throws IOException{
		String [] filenames = {"Data/Carleton_1.lsf","Data/Carleton_2.lsf","Data/Carleton_3.lsf","Data/Carleton_4.lsf","Data/Carleton_5.lsf","Data/University_1.lsf","Data/University_2.lsf","Data/University_3.lsf","Data/University_4.lsf","Data/University_5.lsf"};
		String output_filename = "Results/KrisletCtsAndDscRUS,bayesian,wsf,standardize,none,none,.csv";
		
		
		CaseBaseFilter WSF = new WeightsSeperatorFilter(null);
		CaseBaseFilter standardize = new Standardization(WSF);
		CaseBaseFilter smote = new UnderSampling(standardize);
		//CaseBaseFilter sample = new Sampling(standardize);
		PerformanceTest pt = new PerformanceTest();
		pt.PerformanceEvaluatorMethod(filenames, smote, output_filename,"bayesian",null, null);
	}
	
	/**
	 * Creates an agent
	 * @param Nothing
	 * @return an Agent
	 * ***/
	@Override
	public RoboCupAgent createAgent() {
		RoboCupAgent agent = new RoboCupAgent();
		return agent;
	}
	/**
	 * Parses and creates CaseBases from logfiles
	 * @param filenames An array of logfile names which contain run information
	 * 
	 * @return cbnames An array of CaseBase names
	 * 
	 * @author sachagunaratne
	 * ***/
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

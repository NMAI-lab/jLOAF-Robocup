package PerformanceTesting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jLOAF.casebase.CaseBase;
import org.jLOAF.performance.PerformanceEvaluator;
import org.jLOAF.preprocessing.filter.CaseBaseFilter;
import org.jLOAF.preprocessing.filter.casebasefilter.Sampling;
import org.jLOAF.preprocessing.filter.casebasefilter.UnderSampling;
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
		//String [] filenames = {"Data_wstate/Carleton_1.lsf","Data_wstate/Carleton_2.lsf","Data_wstate/Carleton_3.lsf","Data_wstate/Carleton_4.lsf","Data_wstate/Carleton_5.lsf","Data_wstate/University_1.lsf","Data_wstate/University_2.lsf","Data_wstate/University_3.lsf","Data_wstate/University_4.lsf","Data_wstate/University_5.lsf"};
		//String [] filenames = {"Data/Carleton_1.lsf","Data/Carleton_2.lsf","Data/Carleton_3.lsf","Data/Carleton_4.lsf","Data/Carleton_5.lsf","Data/University_1.lsf","Data/University_2.lsf","Data/University_3.lsf","Data/University_4.lsf","Data/University_5.lsf"};
		//String [] filenames = {"Data/Carleton_1.lsf","Data/Carleton_2.lsf"};
		
		// Classic Krislet log files
/*		String [] filenames = {"DataBaseline/ClassicKrislet/Game 1/Carleton_1.lsf", "DataBaseline/ClassicKrislet/Game 1/Carleton_2.lsf", "DataBaseline/ClassicKrislet/Game 1/Carleton_3.lsf",
				"DataBaseline/ClassicKrislet/Game 1/Carleton_4.lsf", "DataBaseline/ClassicKrislet/Game 1/Carleton_5.lsf", "DataBaseline/ClassicKrislet/Game 1/University_1.lsf",
				"DataBaseline/ClassicKrislet/Game 1/University_2.lsf","DataBaseline/ClassicKrislet/Game 1/University_3.lsf", "DataBaseline/ClassicKrislet/Game 1/University_4.lsf",
				"DataBaseline/ClassicKrislet/Game 1/University_5.lsf", "DataBaseline/ClassicKrislet/Game 2/Carleton_1.lsf", "DataBaseline/ClassicKrislet/Game 2/Carleton_2.lsf",
				"DataBaseline/ClassicKrislet/Game 2/Carleton_3.lsf", "DataBaseline/ClassicKrislet/Game 2/Carleton_4.lsf", "DataBaseline/ClassicKrislet/Game 2/Carleton_5.lsf",
				"DataBaseline/ClassicKrislet/Game 2/University_1.lsf", "DataBaseline/ClassicKrislet/Game 2/University_2.lsf", "DataBaseline/ClassicKrislet/Game 2/University_3.lsf",
				"DataBaseline/ClassicKrislet/Game 2/University_4.lsf", "DataBaseline/ClassicKrislet/Game 2/University_5.lsf", "DataBaseline/ClassicKrislet/Game 3/Carleton_1.lsf",
				"DataBaseline/ClassicKrislet/Game 3/Carleton_2.lsf", "DataBaseline/ClassicKrislet/Game 3/Carleton_3.lsf", "DataBaseline/ClassicKrislet/Game 3/Carleton_4.lsf",
				"DataBaseline/ClassicKrislet/Game 3/Carleton_5.lsf", "DataBaseline/ClassicKrislet/Game 3/University_1.lsf", "DataBaseline/ClassicKrislet/Game 3/University_2.lsf",
				"DataBaseline/ClassicKrislet/Game 3/University_3.lsf", "DataBaseline/ClassicKrislet/Game 3/University_4.lsf", "DataBaseline/ClassicKrislet/Game 3/University_5.lsf",
				"DataBaseline/ClassicKrislet/Game 4/Carleton_1.lsf", "DataBaseline/ClassicKrislet/Game 4/Carleton_2.lsf", "DataBaseline/ClassicKrislet/Game 4/Carleton_3.lsf",
				"DataBaseline/ClassicKrislet/Game 4/Carleton_4.lsf", "DataBaseline/ClassicKrislet/Game 4/Carleton_5.lsf", "DataBaseline/ClassicKrislet/Game 4/University_1.lsf",
				"DataBaseline/ClassicKrislet/Game 4/University_2.lsf", "DataBaseline/ClassicKrislet/Game 4/University_3.lsf", "DataBaseline/ClassicKrislet/Game 4/University_4.lsf",
				"DataBaseline/ClassicKrislet/Game 4/University_5.lsf", "DataBaseline/ClassicKrislet/Game 5/Carleton_1.lsf", "DataBaseline/ClassicKrislet/Game 5/Carleton_2.lsf",
				"DataBaseline/ClassicKrislet/Game 5/Carleton_3.lsf", "DataBaseline/ClassicKrislet/Game 5/Carleton_4.lsf", "DataBaseline/ClassicKrislet/Game 5/Carleton_5.lsf",
				"DataBaseline/ClassicKrislet/Game 5/University_1.lsf", "DataBaseline/ClassicKrislet/Game 5/University_2.lsf", "DataBaseline/ClassicKrislet/Game 5/University_3.lsf",
				"DataBaseline/ClassicKrislet/Game 5/University_4.lsf", "DataBaseline/ClassicKrislet/Game 5/University_5.lsf", "DataBaseline/ClassicKrislet/Game 6/Carleton_1.lsf",
				"DataBaseline/ClassicKrislet/Game 6/Carleton_2.lsf", "DataBaseline/ClassicKrislet/Game 6/Carleton_3.lsf", "DataBaseline/ClassicKrislet/Game 6/Carleton_4.lsf",
				"DataBaseline/ClassicKrislet/Game 6/Carleton_5.lsf", "DataBaseline/ClassicKrislet/Game 6/University_1.lsf", "DataBaseline/ClassicKrislet/Game 6/University_2.lsf",
				"DataBaseline/ClassicKrislet/Game 6/University_3.lsf", "DataBaseline/ClassicKrislet/Game 6/University_4.lsf", "DataBaseline/ClassicKrislet/Game 6/University_5.lsf",
				"DataBaseline/ClassicKrislet/Game 7/Carleton_1.lsf", "DataBaseline/ClassicKrislet/Game 7/Carleton_2.lsf", "DataBaseline/ClassicKrislet/Game 7/Carleton_3.lsf",
				"DataBaseline/ClassicKrislet/Game 7/Carleton_4.lsf", "DataBaseline/ClassicKrislet/Game 7/Carleton_5.lsf", "DataBaseline/ClassicKrislet/Game 7/University_1.lsf",
				"DataBaseline/ClassicKrislet/Game 7/University_2.lsf", "DataBaseline/ClassicKrislet/Game 7/University_3.lsf", "DataBaseline/ClassicKrislet/Game 7/University_4.lsf",
				"DataBaseline/ClassicKrislet/Game 7/University_5.lsf", "DataBaseline/ClassicKrislet/Game 8/Carleton_1.lsf", "DataBaseline/ClassicKrislet/Game 8/Carleton_2.lsf",
				"DataBaseline/ClassicKrislet/Game 8/Carleton_3.lsf", "DataBaseline/ClassicKrislet/Game 8/Carleton_4.lsf", "DataBaseline/ClassicKrislet/Game 8/Carleton_5.lsf",
				"DataBaseline/ClassicKrislet/Game 8/University_1.lsf", "DataBaseline/ClassicKrislet/Game 8/University_2.lsf", "DataBaseline/ClassicKrislet/Game 8/University_3.lsf",
				"DataBaseline/ClassicKrislet/Game 8/University_4.lsf", "DataBaseline/ClassicKrislet/Game 8/University_5.lsf", "DataBaseline/ClassicKrislet/Game 9/Carleton_1.lsf",
				"DataBaseline/ClassicKrislet/Game 9/Carleton_2.lsf", "DataBaseline/ClassicKrislet/Game 9/Carleton_3.lsf", "DataBaseline/ClassicKrislet/Game 9/Carleton_4.lsf",
				"DataBaseline/ClassicKrislet/Game 9/Carleton_5.lsf", "DataBaseline/ClassicKrislet/Game 9/University_1.lsf", "DataBaseline/ClassicKrislet/Game 9/University_2.lsf",
				"DataBaseline/ClassicKrislet/Game 9/University_3.lsf", "DataBaseline/ClassicKrislet/Game 9/University_4.lsf", "DataBaseline/ClassicKrislet/Game 9/University_5.lsf",
				"DataBaseline/ClassicKrislet/Game 10/Carleton_1.lsf", "DataBaseline/ClassicKrislet/Game 10/Carleton_2.lsf", "DataBaseline/ClassicKrislet/Game 10/Carleton_3.lsf",
				"DataBaseline/ClassicKrislet/Game 10/Carleton_4.lsf", "DataBaseline/ClassicKrislet/Game 10/Carleton_5.lsf", "DataBaseline/ClassicKrislet/Game 10/University_1.lsf",
				"DataBaseline/ClassicKrislet/Game 10/University_2.lsf", "DataBaseline/ClassicKrislet/Game 10/University_3.lsf", "DataBaseline/ClassicKrislet/Game 10/University_4.lsf",
				"DataBaseline/ClassicKrislet/Game 10/University_5.lsf"};
*/		
		// Finite Turn Krislet log files
		String [] filenames = {"DataBaseline/FiniteTurnKrislet/Game 1/Carleton_1.lsf", "DataBaseline/FiniteTurnKrislet/Game 1/Carleton_2.lsf", "DataBaseline/FiniteTurnKrislet/Game 1/Carleton_3.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 1/Carleton_4.lsf", "DataBaseline/FiniteTurnKrislet/Game 1/Carleton_5.lsf", "DataBaseline/FiniteTurnKrislet/Game 1/University_1.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 1/University_2.lsf","DataBaseline/FiniteTurnKrislet/Game 1/University_3.lsf", "DataBaseline/FiniteTurnKrislet/Game 1/University_4.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 1/University_5.lsf"};/*, "DataBaseline/FiniteTurnKrislet/Game 2/Carleton_1.lsf", "DataBaseline/FiniteTurnKrislet/Game 2/Carleton_2.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 2/Carleton_3.lsf", "DataBaseline/FiniteTurnKrislet/Game 2/Carleton_4.lsf", "DataBaseline/FiniteTurnKrislet/Game 2/Carleton_5.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 2/University_1.lsf", "DataBaseline/FiniteTurnKrislet/Game 2/University_2.lsf", "DataBaseline/FiniteTurnKrislet/Game 2/University_3.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 2/University_4.lsf", "DataBaseline/FiniteTurnKrislet/Game 2/University_5.lsf", "DataBaseline/FiniteTurnKrislet/Game 3/Carleton_1.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 3/Carleton_2.lsf", "DataBaseline/FiniteTurnKrislet/Game 3/Carleton_3.lsf", "DataBaseline/FiniteTurnKrislet/Game 3/Carleton_4.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 3/Carleton_5.lsf", "DataBaseline/FiniteTurnKrislet/Game 3/University_1.lsf", "DataBaseline/FiniteTurnKrislet/Game 3/University_2.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 3/University_3.lsf", "DataBaseline/FiniteTurnKrislet/Game 3/University_4.lsf", "DataBaseline/FiniteTurnKrislet/Game 3/University_5.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 4/Carleton_1.lsf", "DataBaseline/FiniteTurnKrislet/Game 4/Carleton_2.lsf", "DataBaseline/FiniteTurnKrislet/Game 4/Carleton_3.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 4/Carleton_4.lsf", "DataBaseline/FiniteTurnKrislet/Game 4/Carleton_5.lsf", "DataBaseline/FiniteTurnKrislet/Game 4/University_1.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 4/University_2.lsf", "DataBaseline/FiniteTurnKrislet/Game 4/University_3.lsf", "DataBaseline/FiniteTurnKrislet/Game 4/University_4.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 4/University_5.lsf", "DataBaseline/FiniteTurnKrislet/Game 5/Carleton_1.lsf", "DataBaseline/FiniteTurnKrislet/Game 5/Carleton_2.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 5/Carleton_3.lsf", "DataBaseline/FiniteTurnKrislet/Game 5/Carleton_4.lsf", "DataBaseline/FiniteTurnKrislet/Game 5/Carleton_5.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 5/University_1.lsf", "DataBaseline/FiniteTurnKrislet/Game 5/University_2.lsf", "DataBaseline/FiniteTurnKrislet/Game 5/University_3.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 5/University_4.lsf", "DataBaseline/FiniteTurnKrislet/Game 5/University_5.lsf", "DataBaseline/FiniteTurnKrislet/Game 6/Carleton_1.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 6/Carleton_2.lsf", "DataBaseline/FiniteTurnKrislet/Game 6/Carleton_3.lsf", "DataBaseline/FiniteTurnKrislet/Game 6/Carleton_4.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 6/Carleton_5.lsf", "DataBaseline/FiniteTurnKrislet/Game 6/University_1.lsf", "DataBaseline/FiniteTurnKrislet/Game 6/University_2.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 6/University_3.lsf", "DataBaseline/FiniteTurnKrislet/Game 6/University_4.lsf", "DataBaseline/FiniteTurnKrislet/Game 6/University_5.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 7/Carleton_1.lsf", "DataBaseline/FiniteTurnKrislet/Game 7/Carleton_2.lsf", "DataBaseline/FiniteTurnKrislet/Game 7/Carleton_3.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 7/Carleton_4.lsf", "DataBaseline/FiniteTurnKrislet/Game 7/Carleton_5.lsf", "DataBaseline/FiniteTurnKrislet/Game 7/University_1.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 7/University_2.lsf", "DataBaseline/FiniteTurnKrislet/Game 7/University_3.lsf", "DataBaseline/FiniteTurnKrislet/Game 7/University_4.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 7/University_5.lsf", "DataBaseline/FiniteTurnKrislet/Game 8/Carleton_1.lsf", "DataBaseline/FiniteTurnKrislet/Game 8/Carleton_2.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 8/Carleton_3.lsf", "DataBaseline/FiniteTurnKrislet/Game 8/Carleton_4.lsf", "DataBaseline/FiniteTurnKrislet/Game 8/Carleton_5.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 8/University_1.lsf", "DataBaseline/FiniteTurnKrislet/Game 8/University_2.lsf", "DataBaseline/FiniteTurnKrislet/Game 8/University_3.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 8/University_4.lsf", "DataBaseline/FiniteTurnKrislet/Game 8/University_5.lsf", "DataBaseline/FiniteTurnKrislet/Game 9/Carleton_1.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 9/Carleton_2.lsf", "DataBaseline/FiniteTurnKrislet/Game 9/Carleton_3.lsf", "DataBaseline/FiniteTurnKrislet/Game 9/Carleton_4.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 9/Carleton_5.lsf", "DataBaseline/FiniteTurnKrislet/Game 9/University_1.lsf", "DataBaseline/FiniteTurnKrislet/Game 9/University_2.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 9/University_3.lsf", "DataBaseline/FiniteTurnKrislet/Game 9/University_4.lsf", "DataBaseline/FiniteTurnKrislet/Game 9/University_5.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 10/Carleton_1.lsf", "DataBaseline/FiniteTurnKrislet/Game 10/Carleton_2.lsf", "DataBaseline/FiniteTurnKrislet/Game 10/Carleton_3.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 10/Carleton_4.lsf", "DataBaseline/FiniteTurnKrislet/Game 10/Carleton_5.lsf", "DataBaseline/FiniteTurnKrislet/Game 10/University_1.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 10/University_2.lsf", "DataBaseline/FiniteTurnKrislet/Game 10/University_3.lsf", "DataBaseline/FiniteTurnKrislet/Game 10/University_4.lsf",
				"DataBaseline/FiniteTurnKrislet/Game 10/University_5.lsf"};
		
		// State Based Krislet log files
		String [] filenames = {"DataBaseline/StateBasedKrislet/Game 1/Carleton_1.lsf", "DataBaseline/StateBasedKrislet/Game 1/Carleton_2.lsf", "DataBaseline/StateBasedKrislet/Game 1/Carleton_3.lsf",
				"DataBaseline/StateBasedKrislet/Game 1/Carleton_4.lsf", "DataBaseline/StateBasedKrislet/Game 1/Carleton_5.lsf", "DataBaseline/StateBasedKrislet/Game 1/University_1.lsf",
				"DataBaseline/StateBasedKrislet/Game 1/University_2.lsf","DataBaseline/StateBasedKrislet/Game 1/University_3.lsf", "DataBaseline/StateBasedKrislet/Game 1/University_4.lsf",
				"DataBaseline/StateBasedKrislet/Game 1/University_5.lsf", "DataBaseline/StateBasedKrislet/Game 2/Carleton_1.lsf", "DataBaseline/StateBasedKrislet/Game 2/Carleton_2.lsf",
				"DataBaseline/StateBasedKrislet/Game 2/Carleton_3.lsf", "DataBaseline/StateBasedKrislet/Game 2/Carleton_4.lsf", "DataBaseline/StateBasedKrislet/Game 2/Carleton_5.lsf",
				"DataBaseline/StateBasedKrislet/Game 2/University_1.lsf", "DataBaseline/StateBasedKrislet/Game 2/University_2.lsf", "DataBaseline/StateBasedKrislet/Game 2/University_3.lsf",
				"DataBaseline/StateBasedKrislet/Game 2/University_4.lsf", "DataBaseline/StateBasedKrislet/Game 2/University_5.lsf", "DataBaseline/StateBasedKrislet/Game 3/Carleton_1.lsf",
				"DataBaseline/StateBasedKrislet/Game 3/Carleton_2.lsf", "DataBaseline/StateBasedKrislet/Game 3/Carleton_3.lsf", "DataBaseline/StateBasedKrislet/Game 3/Carleton_4.lsf",
				"DataBaseline/StateBasedKrislet/Game 3/Carleton_5.lsf", "DataBaseline/StateBasedKrislet/Game 3/University_1.lsf", "DataBaseline/StateBasedKrislet/Game 3/University_2.lsf",
				"DataBaseline/StateBasedKrislet/Game 3/University_3.lsf", "DataBaseline/StateBasedKrislet/Game 3/University_4.lsf", "DataBaseline/StateBasedKrislet/Game 3/University_5.lsf",
				"DataBaseline/StateBasedKrislet/Game 4/Carleton_1.lsf", "DataBaseline/StateBasedKrislet/Game 4/Carleton_2.lsf", "DataBaseline/StateBasedKrislet/Game 4/Carleton_3.lsf",
				"DataBaseline/StateBasedKrislet/Game 4/Carleton_4.lsf", "DataBaseline/StateBasedKrislet/Game 4/Carleton_5.lsf", "DataBaseline/StateBasedKrislet/Game 4/University_1.lsf",
				"DataBaseline/StateBasedKrislet/Game 4/University_2.lsf", "DataBaseline/StateBasedKrislet/Game 4/University_3.lsf", "DataBaseline/StateBasedKrislet/Game 4/University_4.lsf",
				"DataBaseline/StateBasedKrislet/Game 4/University_5.lsf", "DataBaseline/StateBasedKrislet/Game 5/Carleton_1.lsf", "DataBaseline/StateBasedKrislet/Game 5/Carleton_2.lsf",
				"DataBaseline/StateBasedKrislet/Game 5/Carleton_3.lsf", "DataBaseline/StateBasedKrislet/Game 5/Carleton_4.lsf", "DataBaseline/StateBasedKrislet/Game 5/Carleton_5.lsf",
				"DataBaseline/StateBasedKrislet/Game 5/University_1.lsf", "DataBaseline/StateBasedKrislet/Game 5/University_2.lsf", "DataBaseline/StateBasedKrislet/Game 5/University_3.lsf",
				"DataBaseline/StateBasedKrislet/Game 5/University_4.lsf", "DataBaseline/StateBasedKrislet/Game 5/University_5.lsf", "DataBaseline/StateBasedKrislet/Game 6/Carleton_1.lsf",
				"DataBaseline/StateBasedKrislet/Game 6/Carleton_2.lsf", "DataBaseline/StateBasedKrislet/Game 6/Carleton_3.lsf", "DataBaseline/StateBasedKrislet/Game 6/Carleton_4.lsf",
				"DataBaseline/StateBasedKrislet/Game 6/Carleton_5.lsf", "DataBaseline/StateBasedKrislet/Game 6/University_1.lsf", "DataBaseline/StateBasedKrislet/Game 6/University_2.lsf",
				"DataBaseline/StateBasedKrislet/Game 6/University_3.lsf", "DataBaseline/StateBasedKrislet/Game 6/University_4.lsf", "DataBaseline/StateBasedKrislet/Game 6/University_5.lsf",
				"DataBaseline/StateBasedKrislet/Game 7/Carleton_1.lsf", "DataBaseline/StateBasedKrislet/Game 7/Carleton_2.lsf", "DataBaseline/StateBasedKrislet/Game 7/Carleton_3.lsf",
				"DataBaseline/StateBasedKrislet/Game 7/Carleton_4.lsf", "DataBaseline/StateBasedKrislet/Game 7/Carleton_5.lsf", "DataBaseline/StateBasedKrislet/Game 7/University_1.lsf",
				"DataBaseline/StateBasedKrislet/Game 7/University_2.lsf", "DataBaseline/StateBasedKrislet/Game 7/University_3.lsf", "DataBaseline/StateBasedKrislet/Game 7/University_4.lsf",
				"DataBaseline/StateBasedKrislet/Game 7/University_5.lsf", "DataBaseline/StateBasedKrislet/Game 8/Carleton_1.lsf", "DataBaseline/StateBasedKrislet/Game 8/Carleton_2.lsf",
				"DataBaseline/StateBasedKrislet/Game 8/Carleton_3.lsf", "DataBaseline/StateBasedKrislet/Game 8/Carleton_4.lsf", "DataBaseline/StateBasedKrislet/Game 8/Carleton_5.lsf",
				"DataBaseline/StateBasedKrislet/Game 8/University_1.lsf", "DataBaseline/StateBasedKrislet/Game 8/University_2.lsf", "DataBaseline/StateBasedKrislet/Game 8/University_3.lsf",
				"DataBaseline/StateBasedKrislet/Game 8/University_4.lsf", "DataBaseline/StateBasedKrislet/Game 8/University_5.lsf", "DataBaseline/StateBasedKrislet/Game 9/Carleton_1.lsf",
				"DataBaseline/StateBasedKrislet/Game 9/Carleton_2.lsf", "DataBaseline/StateBasedKrislet/Game 9/Carleton_3.lsf", "DataBaseline/StateBasedKrislet/Game 9/Carleton_4.lsf",
				"DataBaseline/StateBasedKrislet/Game 9/Carleton_5.lsf", "DataBaseline/StateBasedKrislet/Game 9/University_1.lsf", "DataBaseline/StateBasedKrislet/Game 9/University_2.lsf",
				"DataBaseline/StateBasedKrislet/Game 9/University_3.lsf", "DataBaseline/StateBasedKrislet/Game 9/University_4.lsf", "DataBaseline/StateBasedKrislet/Game 9/University_5.lsf",
				"DataBaseline/StateBasedKrislet/Game 10/Carleton_1.lsf", "DataBaseline/StateBasedKrislet/Game 10/Carleton_2.lsf", "DataBaseline/StateBasedKrislet/Game 10/Carleton_3.lsf",
				"DataBaseline/StateBasedKrislet/Game 10/Carleton_4.lsf", "DataBaseline/StateBasedKrislet/Game 10/Carleton_5.lsf", "DataBaseline/StateBasedKrislet/Game 10/University_1.lsf",
				"DataBaseline/StateBasedKrislet/Game 10/University_2.lsf", "DataBaseline/StateBasedKrislet/Game 10/University_3.lsf", "DataBaseline/StateBasedKrislet/Game 10/University_4.lsf",
				"DataBaseline/StateBasedKrislet/Game 10/University_5.lsf"};
*/		

		CaseBaseFilter WSF = new WeightsSeperatorFilter(null);
		CaseBaseFilter standardize = new Standardization(WSF);
		//CaseBaseFilter smote = new UnderSampling(standardize);
		//CaseBaseFilter sample = new Sampling(standardize);
		PerformanceTest pt = new PerformanceTest();

		String output_filename;
		
		// TB test
//		output_filename = "BaselineResults/CBR,TB,standardize,none,none,none,.csv";
//		pt.PerformanceEvaluatorMethod(filenames, standardize, output_filename,"TB",null, null);
		
		// CBR kordered (state based) test
//		output_filename = "BaselineResults/CBR,weightedKnn,standardize,none,kordered,none,.csv";
//		pt.PerformanceEvaluatorMethod(filenames, standardize, output_filename,"weightedKNN","kordered", null);
		
		// CBR kunordered (state based) test
		output_filename = "BaselineResults/CBR,weightedKnn,standardize,none,kunordered,none,.csv";
		pt.PerformanceEvaluatorMethod(filenames, standardize, output_filename,"weightedKNN","kunordered", null);
		
		// CBR kordered_r (reactive) test
//		output_filename = "BaselineResults/CBR,weightedKnn,standardize,none,kordered_r,none,.csv";
//		pt.PerformanceEvaluatorMethod(filenames, standardize, output_filename,"weightedKNN","kordered_r", null);
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

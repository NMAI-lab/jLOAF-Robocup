package AgentModules;
import org.jLOAF.Agent;

import org.jLOAF.casebase.CaseBase;

import org.jLOAF.inputs.Input;
import org.jLOAF.reasoning.BayesianReasoner;
import org.jLOAF.reasoning.DynamicBayesianReasoner;
import org.jLOAF.reasoning.NeuralNetworkOrderKReasoner;
import org.jLOAF.reasoning.NeuralNetworkReasoner;
import org.jLOAF.reasoning.TBReasoning;
import org.jLOAF.reasoning.WeightedKNN;

/***
 * @constructor: 
 * creates a Agent using super
 * 
 * @methods run Takes an input and calls the selectAction function that exists in reasoner which returns an Action
 * @methods train Takes a CaseBase, checks if the reasoner is null, and if it is then sets the reasoner to a specified reasoner. 
 * 
 * ***/
public class RoboCupAgent extends Agent {

	private String filename = "C:/Users/sachagunaratne/Documents/GitHub/jLOAF-Robocup/Data/DBN_reactive.txt";
	
	public RoboCupAgent() {
		super(null, null, null, null);
		
		this.mc = new RoboCupMotorControl();
		this.p = new RoboCupPerception();
	}
	
	@Override
	public RoboCupAction run(Input input) {
		return (RoboCupAction) this.r.selectAction(input);
	}

	@Override
	public void train(CaseBase casebase) {
		this.cb = casebase;
		if(r==null){
			this.r = new DynamicBayesianReasoner(casebase, filename);
		}
		if(this.r instanceof DynamicBayesianReasoner){
			((DynamicBayesianReasoner) r).setTrain();
		}
		if(this.r instanceof BayesianReasoner){
			((BayesianReasoner) r).setTrain();
		}
		//this.r = new WeightedKNN(5, casebase);
		//this.r = new TBReasoning(casebase);
		//this.r = new NeuralNetworkReasoner(casebase, filename);
	}
	
	

}

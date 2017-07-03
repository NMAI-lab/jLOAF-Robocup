package AgentModules;
import org.jLOAF.Agent;

import org.jLOAF.casebase.CaseBase;

import org.jLOAF.inputs.Input;
import org.jLOAF.reasoning.BayesianReasoner;
import org.jLOAF.reasoning.WeightedKNN;


public class RoboCupAgent extends Agent {
	private String filename = "C:/Users/sachagunaratne/Documents/GitHub/jLOAF-Robocup/Data/Bayesian_csv.txt";
	
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
		this.r = new BayesianReasoner(casebase, filename);
	}
	
	

}

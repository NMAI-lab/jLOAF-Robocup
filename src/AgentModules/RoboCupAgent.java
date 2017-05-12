package AgentModules;
import org.jLOAF.Agent;
import org.jLOAF.MotorControl;
import org.jLOAF.Perception;
import org.jLOAF.Reasoning;
import org.jLOAF.action.Action;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.inputs.AtomicInput;
import org.jLOAF.inputs.ComplexInput;
import org.jLOAF.inputs.Input;
import org.jLOAF.reasoning.SimpleKNN;
import org.jLOAF.reasoning.WeightedKNN;
import org.jLOAF.sim.atomic.Equality;
import org.jLOAF.sim.atomic.EuclideanDistance;
import org.jLOAF.sim.atomic.PercentDifference;
import org.jLOAF.sim.complex.GreedyMunkrezMatching;
import org.jLOAF.sim.complex.Hungarian_alg;
import org.jLOAF.sim.complex.Mean;
import org.jLOAF.sim.complex.WeightedMean;
import org.jLOAF.weights.SimilarityWeights;

public class RoboCupAgent extends Agent {

	public RoboCupAgent(CaseBase casebase) {
		super(null, null, null, casebase);
		
		this.mc = new RoboCupMotorControl();
		this.p = new RoboCupPerception();
		
		this.r = new WeightedKNN(5,cb);
		
		SimilarityWeights sim_weights = new SimilarityWeights(1.0);
		sim_weights.setFeatureWeight("ball", 10);
		sim_weights.setFeatureWeight("goal r", 10);
		sim_weights.setFeatureWeight("goal l", 10); 
		
		RoboCupInput.setClassSimilarityMetric(new WeightedMean(sim_weights));
		AtomicInput.setClassStrategy(new EuclideanDistance());
		
		this.cb = casebase;
	}

	public void setSim(String matchType) {
		if(matchType.equals("default")){
			ComplexInput.setClassStrategy(new WeightedMean(new SimilarityWeights(1.0)));
		}else if(matchType.equals("gmm")){
			ComplexInput.setClassStrategy(new GreedyMunkrezMatching());
		}
	}
	
	@Override
	public RoboCupAction run(Input input) {
		return (RoboCupAction) this.r.selectAction(input);
	}

	@Override
	public void train(CaseBase casebase) {
		// TODO Auto-generated method stub
		
	}
	
	

}

import org.jLOAF.Agent;
import org.jLOAF.MotorControl;
import org.jLOAF.Perception;
import org.jLOAF.Reasoning;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.inputs.AtomicInput;
import org.jLOAF.inputs.ComplexInput;
import org.jLOAF.reasoning.SimpleKNN;
import org.jLOAF.reasoning.WeightedKNN;
import org.jLOAF.sim.atomic.Equality;
import org.jLOAF.sim.atomic.EuclideanDistance;
import org.jLOAF.sim.atomic.PercentDifference;
import org.jLOAF.sim.complex.GreedyMunkrezMatching;
import org.jLOAF.sim.complex.Hungarian_alg;
import org.jLOAF.sim.complex.Mean;

public class RoboCupAgent extends Agent {

	public RoboCupAgent(CaseBase casebase) {
		super(null, null, null, casebase);
		
		this.mc = new RoboCupMotorControl();
		this.p = new RoboCupPerception();
		
		this.r = new WeightedKNN(5,cb);
		
		ComplexInput.setClassStrategy(new Mean());
		AtomicInput.setClassStrategy(new EuclideanDistance());
		
		this.cb = casebase;
	}

	public void setSim(String matchType) {
		if(matchType.equals("default")){
			RoboCupInput.setClassSimilarityMetric(new Mean());
		}else if(matchType.equals("gmm")){
			RoboCupInput.setClassSimilarityMetric(new GreedyMunkrezMatching());
		}
	}
	
	

}

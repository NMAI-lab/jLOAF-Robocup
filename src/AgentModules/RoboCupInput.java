package AgentModules;

import org.jLOAF.inputs.ComplexInput;
import org.jLOAF.inputs.Input;
import org.jLOAF.sim.ComplexSimilarityMetricStrategy;
import org.jLOAF.sim.SimilarityMetricStrategy;

/**
 * @constructor Requires a name and a similarityMetricStrategy, these are passed to the superclass.
 * It is serializable
 */
public class RoboCupInput extends ComplexInput {


	private static final long serialVersionUID = 1L;
	
	public RoboCupInput(String s, ComplexSimilarityMetricStrategy sim) {
		super(s, sim);
	}

}

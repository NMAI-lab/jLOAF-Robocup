package AgentModules;

import org.jLOAF.inputs.ComplexInput;
import org.jLOAF.inputs.Input;
import org.jLOAF.sim.SimilarityMetricStrategy;

public class RoboCupInput extends ComplexInput {

	/**
	 * @constructor Requires a name and a similarityMetricStrategy, these are passed to the superclass.
	 * It is serializable
	 */
	private static final long serialVersionUID = 1L;
	
	public RoboCupInput(String s, SimilarityMetricStrategy sim) {
		super(s, sim);
	}

}

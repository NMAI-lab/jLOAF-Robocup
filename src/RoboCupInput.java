
import org.jLOAF.inputs.ComplexInput;
import org.jLOAF.inputs.Input;
import org.jLOAF.sim.SimilarityMetricStrategy;

public class RoboCupInput extends ComplexInput {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static SimilarityMetricStrategy simMet;
	
	public RoboCupInput(String s) {
		super(s);
	}
	
	@Override
	public double similarity(Input i) {
		//See if the user has defined similarity for each specific input, for all inputs
		//  of a specific type, of deferred to superclass
		if(this.simStrategy != null){
			return simStrategy.similarity(this, i);
		}else if(RoboCupInput.isClassStrategySet()){
			return RoboCupInput.similarity(this, i);
		}else{
			return super.similarity(i);
		}
	}

	private static double similarity(Input complexInput, Input i) {
		return RoboCupInput.simMet.similarity(complexInput, i);
	}

	public static boolean isClassStrategySet(){
		if(RoboCupInput.simMet == null){
			return false;
		}else{
			return true;
		}
	}

	public static void setClassSimilarityMetric(SimilarityMetricStrategy s){
		RoboCupInput.simMet = s;
	}

}

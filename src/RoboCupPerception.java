import org.jLOAF.Perception;
import org.jLOAF.inputs.AtomicInput;
import org.jLOAF.inputs.Input;

public class RoboCupPerception implements Perception {

	public Input sense(Memory m){
		//convert VisualInfor info into a complex input and add it into RoboCupInput
		return Convert2Complex(m);
	}

	private RoboCupInput Convert2Complex(Memory m) {
		VisualInfo info = m.getVisualInfo();
		return null;
	}
}

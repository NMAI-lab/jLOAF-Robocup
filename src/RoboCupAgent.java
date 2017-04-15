import org.jLOAF.Agent;
import org.jLOAF.MotorControl;
import org.jLOAF.Perception;
import org.jLOAF.Reasoning;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.reasoning.SimpleKNN;

public class RoboCupAgent extends Agent {

	public RoboCupAgent(CaseBase casebase) {
		super(null, null, null, casebase);
		
		this.mc = new RoboCupMotorControl();
		this.p = new RoboCupPerception();
		
		this.r = new SimpleKNN(1,cb);
		this.cb = casebase;
	}
	
	

}

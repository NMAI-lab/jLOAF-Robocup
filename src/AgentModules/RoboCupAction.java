package AgentModules;

import org.jLOAF.action.ComplexAction;
/***
 * @constructor Takes an action Name and passes it to the SuperClass to make a ComplexAction
 * ***/
public class RoboCupAction extends ComplexAction {
	
	private static final long serialVersionUID = 1L;

	public RoboCupAction(String name) {
		super(name);
	}

}

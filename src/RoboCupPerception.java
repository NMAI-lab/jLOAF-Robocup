
import org.jLOAF.Perception;
import org.jLOAF.inputs.Input;

public class RoboCupPerception implements Perception {

	public Input sense(){
		//convert VisualInfo info into a RoboCupInput
		return null;
	}

//	private RoboCupInput Convert2Complex(Memory m) {
//		//get visualinfo
//		VisualInfo info = m.getVisualInfo();
//		//get objectInfo vector
//		if(info!=null){
//			Vector<ObjectInfo> m_objects = info.m_objects;
//			
//			RoboCupInput input = new RoboCupInput();
//			
//			for(ObjectInfo obj: m_objects){
//				//add ball info
//				if(obj.m_type.equals("ball")){
//					Feature dist = new Feature(obj.m_distance);
//					Feature dir = new Feature(obj.m_direction);
//					AtomicInput b_dist = new AtomicInput("ball_dist",dist);
//					AtomicInput b_dir = new AtomicInput("ball_dir",dir);
//					ComplexInput ball = new ComplexInput("ball");
//					ball.add(b_dist);
//					ball.add(b_dir);
//					input.add(ball);
//				}
//				//add goal r info
//				if(obj.m_type.equals("goal r")){
//					Feature dist = new Feature(obj.m_distance);
//					Feature dir = new Feature(obj.m_direction);
//					AtomicInput g_dist = new AtomicInput("goal_dist",dist);
//					AtomicInput g_dir = new AtomicInput("goal_dir",dir);
//					ComplexInput goal_r = new ComplexInput("goal r");
//					goal_r.add(g_dist);
//					goal_r.add(g_dir);
//					input.add(goal_r);
//				}
//				//add goal l info
//				if(obj.m_type.equals("goal l")){
//					Feature dist = new Feature(obj.m_distance);
//					Feature dir = new Feature(obj.m_direction);
//					AtomicInput g_dist = new AtomicInput("goal_dist",dist);
//					AtomicInput g_dir = new AtomicInput("goal_dir",dir);
//					ComplexInput goal_l = new ComplexInput("goal l");
//					goal_l.add(g_dist);
//					goal_l.add(g_dir);
//					input.add(goal_l);
//				}	
//			}
//			return input;
//		}else{
//			System.out.println("Error :There is no visual information");
//			return null;
//		}
//		
//	}
	
	public static void main(String[] args){
	}
}

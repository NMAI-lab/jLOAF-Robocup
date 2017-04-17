import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.regex.*;

import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;
import org.jLOAF.inputs.AtomicInput;
import org.jLOAF.inputs.ComplexInput;
import org.jLOAF.inputs.Feature;

public class LogFile2CaseBase {
	//converts a log file into a casebase and writes to a file
	
	public static void main(String [] args) throws IOException{
		String logfile = "Data/Carleton_1.lsf";
		String outfile = "Data/casebase1.cb";
		
		//patterns
		String visualPattern = "\\(see (?<time>[\\d]{0,4}) .*\\)";
		String goalPattern = "\\(\\(g (?<goalSide>[r,l]\\)) (?<goalDistance>[\\d,\\.]+) (?<goalAngle>[\\-,\\d]+)";
		String ballPattern = "\\(\\(b\\) (?<ballDistance>[\\d,\\.]+) (?<ballAngle>[\\-,\\d]+)";
		String actionPattern = "^\\(\\b(?<action>kick|turn|dash)\\b";
		String turnPattern = "\\(turn (?<turnAngle>[\\-,\\d,\\.]+)";
		String DashPattern = "\\(dash (?<DashPower>[\\-,\\d,\\.]+)";
		String KickPattern = "\\(kick (?<KickPower>[\\-,\\d,\\.]+) (?<KickAngle>[\\-,\\d,\\.]+)";
		
		//pattern objects
		Pattern vp = Pattern.compile(visualPattern);
		Pattern gp = Pattern.compile(goalPattern);
		Pattern bp = Pattern.compile(ballPattern);
		Pattern ap = Pattern.compile(actionPattern);
		Pattern tp = Pattern.compile(turnPattern);
		Pattern dp = Pattern.compile(DashPattern);
		Pattern kp = Pattern.compile(KickPattern);
		Matcher m;
		
		//inputs
		RoboCupAction action = null;
		ComplexInput ginput;
		ComplexInput binput;
		RoboCupInput input = null;
		
		boolean hasAction = false;
		boolean hasInput = false;
		
		CaseBase cb = new CaseBase();
	
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(logfile),'r');
			String Line;
			System.out.println("Creating casebase...");
			while ((Line = br.readLine()) != null){
				//check action
				m = ap.matcher(Line);
				if(m.find()){
					//System.out.println(m.group(1));
					action = new RoboCupAction(m.group(1));
					hasAction = true;
					
					if(m.group(1).equals("turn")){
						//check turnAngle
						m = tp.matcher(Line);
						if(m.find()){
							//System.out.println(m.group(1));
							Feature turnAngle = new Feature(Double.parseDouble(m.group(1)));
							action.addFeature(turnAngle);
						}	
					}else if (m.group(1).equals("dash")){
						//check dashPower
						m = dp.matcher(Line);
						if(m.find()){
							//System.out.println(m.group(1));
							Feature dashPower = new Feature(Double.parseDouble(m.group(1)));
							action.addFeature(dashPower);
						}
					}else if(m.group(1).equals("kick")){
						//check kickPower and kickAngle
						m = kp.matcher(Line);
						if(m.find()){
							//System.out.println(m.group(1));
							//System.out.println(m.group(2));
							Feature kickPower = new Feature(Double.parseDouble(m.group(1)));
							Feature kickAngle = new Feature(Double.parseDouble(m.group(2)));
							action.addFeature(kickPower);
							action.addFeature(kickAngle);
						}
					}
				}
				//check perception
				m = vp.matcher(Line);
				if(m.find()){
					input = new RoboCupInput("SenseEnvironment");
					hasInput = true;
					//check goalDistance and goalAngle
					m = gp.matcher(Line);
					if(m.find()){
						ginput = new ComplexInput("goal "+m.group(1).replace(")", ""));
						//System.out.println(m.group(1).replace(')', ' '));
						//System.out.println(m.group(2));
						//System.out.println(m.group(3));
						Feature goalDist = new Feature(Double.parseDouble(m.group(2))); 
						Feature goalAngle = new Feature(Double.parseDouble(m.group(3)));
						ginput.add(new AtomicInput("goal_dist", goalDist));
						ginput.add(new AtomicInput("goal_dir", goalAngle));
						
						//add to input
						input.add(ginput);					
					}
					//check BallDistance and ballAngle
					m = bp.matcher(Line);
					if(m.find()){
						binput = new ComplexInput("ball");
						//System.out.println(m.group(1));
						//System.out.println(m.group(2));
						Feature ballDist = new Feature(Double.parseDouble(m.group(1))); 
						Feature ballAngle = new Feature(Double.parseDouble(m.group(2)));
						binput.add(new AtomicInput("ball_dist", ballDist));
						binput.add(new AtomicInput("ball_dir", ballAngle));
						
						//add to input
						input.add(binput);	
					}
					
				}
				//only add to casebase if an state action pair exists
				if(hasInput && hasAction){
					Case c = new Case(input, action);
					cb.add(c);
				}	
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
		System.out.println("CaseBase created.");
		//write to a file
		System.out.println("Writing to file: " + outfile);
		CaseBase.save(cb, outfile);
		System.out.println("Done!");
	}
}

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
		String outfile = "Data/casebase_reactive.cb";
		
		//patterns
		String visualPattern = "\\(see (?<time>[\\d]{0,4}) .*\\)";
		String goalPattern = "\\(\\(g (?<goalSide>[r,l]\\)) (?<goalDistance>[\\d,\\.]+) (?<goalAngle>[\\-,\\d]+)";
		String ballPattern = "\\(\\(b\\) (?<ballDistance>[\\d,\\.]+) (?<ballAngle>[\\-,\\d]+)";
		String ballPattern2 = "\\(\\(B\\) (?<ballDistance2>[\\d,\\.]+) (?<ballAngle2>[\\-,\\d]+)";
		String actionPattern = "^\\(\\b(?<action>kick|turn|dash)\\b";
		String turnPattern = "\\(turn (?<turnAngle>[\\-,\\d,\\.]+)";
		String DashPattern = "\\(dash (?<DashPower>[\\-,\\d,\\.]+)";
		String KickPattern = "\\(kick (?<KickPower>[\\-,\\d,\\.]+) (?<KickAngle>[\\-,\\d,\\.]+)";
		String fcbPattern ="\\(\\(f c b\\) (?<fcbDistance>[\\d,\\.]+) (?<fcbAngle>[\\-,\\d]+)";
		String flbPattern ="\\(\\(f l b\\) (?<flbDistance>[\\d,\\.]+) (?<flbAngle>[\\-,\\d]+)";
		String frbPattern ="\\(\\(f r b\\) (?<frbDistance>[\\d,\\.]+) (?<frbAngle>[\\-,\\d]+)";
		String fctPattern ="\\(\\(f c t\\) (?<fctDistance>[\\d,\\.]+) (?<fctAngle>[\\-,\\d]+)";
		String fltPattern ="\\(\\(f l t\\) (?<fltDistance>[\\d,\\.]+) (?<fltAngle>[\\-,\\d]+)";
		String frtPattern ="\\(\\(f r t\\) (?<frtDistance>[\\d,\\.]+) (?<frtAngle>[\\-,\\d]+)";
		String fcPattern ="\\(\\(f c\\) (?<fcDistance>[\\d,\\.]+) (?<fcAngle>[\\-,\\d]+)";
		
		//pattern objects
		Pattern vp = Pattern.compile(visualPattern);
		Pattern gp = Pattern.compile(goalPattern);
		Pattern bp = Pattern.compile(ballPattern);
		Pattern bp2 = Pattern.compile(ballPattern2);
		Pattern ap = Pattern.compile(actionPattern);
		Pattern tp = Pattern.compile(turnPattern);
		Pattern dp = Pattern.compile(DashPattern);
		Pattern kp = Pattern.compile(KickPattern);
		
		//flag patterns
		Pattern fcb = Pattern.compile(fcbPattern);
		Pattern flb = Pattern.compile(flbPattern);
		Pattern frb = Pattern.compile(frbPattern);
		Pattern fct = Pattern.compile(fctPattern);
		Pattern flt = Pattern.compile(fltPattern);
		Pattern frt = Pattern.compile(frtPattern);
		Pattern fc = Pattern.compile(fcPattern);
		
		//matcher
		Matcher m;
		
		//inputs
		RoboCupAction action = null;
		ComplexInput ginput;
		ComplexInput binput;
		ComplexInput fcbinput;
		ComplexInput flbinput;
		ComplexInput frbinput;
		ComplexInput fctinput;
		ComplexInput fltinput;
		ComplexInput frtinput;
		ComplexInput fcinput;
		
		
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
					
					//check BallDistance and ballAngle
					m = bp2.matcher(Line);
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
					
					m = fcb.matcher(Line);
					if(m.find()){
						fcbinput = new ComplexInput("fcb");
						//System.out.println(m.group(1));
						//System.out.println(m.group(2));
						Feature fcbDist = new Feature(Double.parseDouble(m.group(1))); 
						Feature fcbAngle = new Feature(Double.parseDouble(m.group(2)));
						fcbinput.add(new AtomicInput("fcb_dist", fcbDist));
						fcbinput.add(new AtomicInput("fcb_dir", fcbAngle));
						
						//add to input
						input.add(fcbinput);	
					}
					
					m = flb.matcher(Line);
					if(m.find()){
						flbinput = new ComplexInput("flb");
						//System.out.println(m.group(1));
						//System.out.println(m.group(2));
						Feature Dist = new Feature(Double.parseDouble(m.group(1))); 
						Feature Angle = new Feature(Double.parseDouble(m.group(2)));
						flbinput.add(new AtomicInput("flb_dist", Dist));
						flbinput.add(new AtomicInput("flb_dir", Angle));
						
						//add to input
						input.add(flbinput);	
					}
					
					m = frb.matcher(Line);
					if(m.find()){
						frbinput = new ComplexInput("frb");
						//System.out.println(m.group(1));
						//System.out.println(m.group(2));
						Feature Dist = new Feature(Double.parseDouble(m.group(1))); 
						Feature Angle = new Feature(Double.parseDouble(m.group(2)));
						frbinput.add(new AtomicInput("frb_dist", Dist));
						frbinput.add(new AtomicInput("frb_dir", Angle));
						
						//add to input
						input.add(frbinput);	
					}
					
					m = fct.matcher(Line);
					if(m.find()){
						fctinput = new ComplexInput("fct");
						//System.out.println(m.group(1));
						//System.out.println(m.group(2));
						Feature Dist = new Feature(Double.parseDouble(m.group(1))); 
						Feature Angle = new Feature(Double.parseDouble(m.group(2)));
						fctinput.add(new AtomicInput("fct_dist", Dist));
						fctinput.add(new AtomicInput("fct_dir", Angle));
						
						//add to input
						input.add(fctinput);	
					}
					
					m = flt.matcher(Line);
					if(m.find()){
						fltinput = new ComplexInput("flt");
						//System.out.println(m.group(1));
						//System.out.println(m.group(2));
						Feature Dist = new Feature(Double.parseDouble(m.group(1))); 
						Feature Angle = new Feature(Double.parseDouble(m.group(2)));
						fltinput.add(new AtomicInput("flt_dist", Dist));
						fltinput.add(new AtomicInput("flt_dir", Angle));
						
						//add to input
						input.add(fltinput);	
					}
					
					m = frt.matcher(Line);
					if(m.find()){
						frtinput = new ComplexInput("frt");
						//System.out.println(m.group(1));
						//System.out.println(m.group(2));
						Feature Dist = new Feature(Double.parseDouble(m.group(1))); 
						Feature Angle = new Feature(Double.parseDouble(m.group(2)));
						frtinput.add(new AtomicInput("frt_dist", Dist));
						frtinput.add(new AtomicInput("frt_dir", Angle));
						
						//add to input
						input.add(frtinput);	
					}
					
					m = fc.matcher(Line);
					if(m.find()){
						fcinput = new ComplexInput("fc");
						//System.out.println(m.group(1));
						//System.out.println(m.group(2));
						Feature Dist = new Feature(Double.parseDouble(m.group(1))); 
						Feature Angle = new Feature(Double.parseDouble(m.group(2)));
						fcinput.add(new AtomicInput("fc_dist", Dist));
						fcinput.add(new AtomicInput("fc_dir", Angle));
						
						//add to input
						input.add(fcinput);	
					}
					
				}
				//only add to casebase if an state action pair exists
				if(hasInput && hasAction){
					Case c = new Case(input, action);
					cb.add(c);
					hasInput = false;
					hasAction = false;
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

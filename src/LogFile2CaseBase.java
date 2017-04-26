import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
		String outfile = "Data/casebase_reactive_new.cb";
		
		String [] flagPatterns = new String[45];
		String [] flagPattern_Names = {"fcb", "flb","frb", "fct","flt", "frt", "fc","fplt", "fplc", "fplb", "fprt", "fprc", "fprb","ftl50","ftr50","fbl50","fbr50","ftl40","ftr40","fbl40","fbr40","ftl30","ftr30","fbl30","fbr30","ftl20","ftr20","fbl20","fbr20","ftl10","ftr10","fbl10","fbr10","frt30","frb30","flt30","flb30","frt20","frb20","flt20","flb20","frt10","frb10","flt10","flb10"};
		
		//patterns
		String visualPattern = "\\(see (?<time>[\\d]{0,4}) .*\\)";
		String goalPattern = "\\(\\(g (?<goalSide>[r,l]\\)) (?<goalDistance>[\\d,\\.]+) (?<goalAngle>[\\-,\\d]+)";
		String ballPattern = "\\(\\(b\\) (?<ballDistance>[\\d,\\.]+) (?<ballAngle>[\\-,\\d]+)";
		String ballPattern2 = "\\(\\(B\\) (?<ballDistance2>[\\d,\\.]+) (?<ballAngle2>[\\-,\\d]+)";
		String actionPattern = "^\\(\\b(?<action>kick|turn|dash)\\b";
		String turnPattern = "\\(turn (?<turnAngle>[\\-,\\d,\\.]+)";
		String DashPattern = "\\(dash (?<DashPower>[\\-,\\d,\\.]+)";
		String KickPattern = "\\(kick (?<KickPower>[\\-,\\d,\\.]+) (?<KickAngle>[\\-,\\d,\\.]+)";
		//flags
		flagPatterns[0] ="\\(\\(f c b\\) (?<fcbDistance>[\\d,\\.]+) (?<fcbAngle>[\\-,\\d]+)";
		flagPatterns[1] ="\\(\\(f l b\\) (?<flbDistance>[\\d,\\.]+) (?<flbAngle>[\\-,\\d]+)";
		flagPatterns[2] ="\\(\\(f r b\\) (?<frbDistance>[\\d,\\.]+) (?<frbAngle>[\\-,\\d]+)";
		flagPatterns[3] ="\\(\\(f c t\\) (?<fctDistance>[\\d,\\.]+) (?<fctAngle>[\\-,\\d]+)";
		flagPatterns[4] ="\\(\\(f l t\\) (?<fltDistance>[\\d,\\.]+) (?<fltAngle>[\\-,\\d]+)";
		flagPatterns[5] ="\\(\\(f r t\\) (?<frtDistance>[\\d,\\.]+) (?<frtAngle>[\\-,\\d]+)";
		flagPatterns[6] ="\\(\\(f c\\) (?<fcDistance>[\\d,\\.]+) (?<fcAngle>[\\-,\\d]+)";
		flagPatterns[7] ="\\(\\(f p l t\\) (?<fpltDistance>[\\d,\\.]+) (?<fpltAngle>[\\-,\\d]+)";
		flagPatterns[8] ="\\(\\(f p l c\\) (?<fplcDistance>[\\d,\\.]+) (?<fplcAngle>[\\-,\\d]+)";
		flagPatterns[9] ="\\(\\(f p l b\\) (?<fplbDistance>[\\d,\\.]+) (?<fplbAngle>[\\-,\\d]+)";
		flagPatterns[10] ="\\(\\(f p r t\\) (?<fprtDistance>[\\d,\\.]+) (?<fprtAngle>[\\-,\\d]+)";
		flagPatterns[11] ="\\(\\(f p r c\\) (?<fprcDistance>[\\d,\\.]+) (?<fprcAngle>[\\-,\\d]+)";
		flagPatterns[12] ="\\(\\(f p r b\\) (?<fprbDistance>[\\d,\\.]+) (?<fprbAngle>[\\-,\\d]+)";
		flagPatterns[13] ="\\(\\(f t l 50\\) (?<ftl50Distance>[\\d,\\.]+) (?<ftl50Angle>[\\-,\\d]+)";
		flagPatterns[14] ="\\(\\(f t r 50\\) (?<ftr50Distance>[\\d,\\.]+) (?<ftr50Angle>[\\-,\\d]+)";
		flagPatterns[15] ="\\(\\(f b l 50\\) (?<fbl50Distance>[\\d,\\.]+) (?<fbl50Angle>[\\-,\\d]+)";
		flagPatterns[16] ="\\(\\(f b r 50\\) (?<fbr50Distance>[\\d,\\.]+) (?<fbr50Angle>[\\-,\\d]+)";
		flagPatterns[17] ="\\(\\(f t l 40\\) (?<ftl40Distance>[\\d,\\.]+) (?<ftl40Angle>[\\-,\\d]+)";
		flagPatterns[18] ="\\(\\(f t r 40\\) (?<ftr40Distance>[\\d,\\.]+) (?<ftr40Angle>[\\-,\\d]+)";
		flagPatterns[19] ="\\(\\(f b l 40\\) (?<fbl40Distance>[\\d,\\.]+) (?<fbl40Angle>[\\-,\\d]+)";
		flagPatterns[20] ="\\(\\(f b r 40\\) (?<fbr40Distance>[\\d,\\.]+) (?<fbr40Angle>[\\-,\\d]+)";
		flagPatterns[21] ="\\(\\(f t l 30\\) (?<ftl30Distance>[\\d,\\.]+) (?<ftl30Angle>[\\-,\\d]+)";
		flagPatterns[22] ="\\(\\(f t r 30\\) (?<ftr30Distance>[\\d,\\.]+) (?<ftr30Angle>[\\-,\\d]+)";
		flagPatterns[23] ="\\(\\(f b l 30\\) (?<fbl30Distance>[\\d,\\.]+) (?<fbl30Angle>[\\-,\\d]+)";
		flagPatterns[24] ="\\(\\(f b r 30\\) (?<fbr30Distance>[\\d,\\.]+) (?<fbr30Angle>[\\-,\\d]+)";
		flagPatterns[25] ="\\(\\(f t l 20\\) (?<ftl20Distance>[\\d,\\.]+) (?<ftl20Angle>[\\-,\\d]+)";
		flagPatterns[26] ="\\(\\(f t r 20\\) (?<ftr20Distance>[\\d,\\.]+) (?<ftr20Angle>[\\-,\\d]+)";
		flagPatterns[27] ="\\(\\(f b l 20\\) (?<fbl20Distance>[\\d,\\.]+) (?<fbl20Angle>[\\-,\\d]+)";
		flagPatterns[28] ="\\(\\(f b r 20\\) (?<fbr20Distance>[\\d,\\.]+) (?<fbr20Angle>[\\-,\\d]+)";
		flagPatterns[29] ="\\(\\(f t l 10\\) (?<ftl10Distance>[\\d,\\.]+) (?<ftl10Angle>[\\-,\\d]+)";
		flagPatterns[30] ="\\(\\(f t r 10\\) (?<ftr10Distance>[\\d,\\.]+) (?<ftr10Angle>[\\-,\\d]+)";
		flagPatterns[31] ="\\(\\(f b l 10\\) (?<fbl10Distance>[\\d,\\.]+) (?<fbl10Angle>[\\-,\\d]+)";
		flagPatterns[32] ="\\(\\(f b r 10\\) (?<fbr10Distance>[\\d,\\.]+) (?<fbr10Angle>[\\-,\\d]+)";
		flagPatterns[33] ="\\(\\(f r t 30\\) (?<frt30Distance>[\\d,\\.]+) (?<frt30Angle>[\\-,\\d]+)";
		flagPatterns[34] ="\\(\\(f r b 30\\) (?<frb30Distance>[\\d,\\.]+) (?<frb30Angle>[\\-,\\d]+)";
		flagPatterns[35] ="\\(\\(f l t 30\\) (?<flt30Distance>[\\d,\\.]+) (?<flt30bAngle>[\\-,\\d]+)";
		flagPatterns[36] ="\\(\\(f l b 30\\) (?<flb30Distance>[\\d,\\.]+) (?<flb30Angle>[\\-,\\d]+)";
		flagPatterns[37] ="\\(\\(f r t 20\\) (?<frt20Distance>[\\d,\\.]+) (?<frt20Angle>[\\-,\\d]+)";
		flagPatterns[38] ="\\(\\(f r b 20\\) (?<frb20Distance>[\\d,\\.]+) (?<frb20Angle>[\\-,\\d]+)";
		flagPatterns[39] ="\\(\\(f l t 20\\) (?<flt20Distance>[\\d,\\.]+) (?<flt20Angle>[\\-,\\d]+)";
		flagPatterns[40] ="\\(\\(f l b 20\\) (?<flb20Distance>[\\d,\\.]+) (?<flb20Angle>[\\-,\\d]+)";
		flagPatterns[41] ="\\(\\(f r t 10\\) (?<frt10Distance>[\\d,\\.]+) (?<frt10Angle>[\\-,\\d]+)";
		flagPatterns[42] ="\\(\\(f r b 10\\) (?<frb10Distance>[\\d,\\.]+) (?<frb10Angle>[\\-,\\d]+)";
		flagPatterns[43] ="\\(\\(f l t 10\\) (?<flt10Distance>[\\d,\\.]+) (?<flt10Angle>[\\-,\\d]+)";
		flagPatterns[44] ="\\(\\(f l b 10\\) (?<flb10Distance>[\\d,\\.]+) (?<flb10Angle>[\\-,\\d]+)";
		
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
		Pattern flag;
		
		//matcher
		Matcher m;
		
		//inputs
		RoboCupAction action = null;
		ComplexInput ginput;
		ComplexInput binput;
		ComplexInput flaginput;
		
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
						ginput.add(new AtomicInput("dist", goalDist));
						ginput.add(new AtomicInput("dir", goalAngle));
						
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
						binput.add(new AtomicInput("dist", ballDist));
						binput.add(new AtomicInput("dir", ballAngle));
						
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
						binput.add(new AtomicInput("dist", ballDist));
						binput.add(new AtomicInput("dir", ballAngle));
						
						//add to input
						input.add(binput);	
					}
					
					for(int i =0;i<flagPatterns.length;i++){
						flag = Pattern.compile(flagPatterns[i]);
						m = flag.matcher(Line);
						if(m.find()){
							flaginput = new ComplexInput(flagPattern_Names[i]);
							Feature Dist = new Feature(Double.parseDouble(m.group(1))); 
							Feature Angle = new Feature(Double.parseDouble(m.group(2)));
							flaginput.add(new AtomicInput("dist", Dist));
							flaginput.add(new AtomicInput("dir", Angle));
							
							//add to input
							input.add(flaginput);
						}
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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.regex.*;

public class LogFile2CaseBase {
	//converts a log file into a casebase
	
	
	
	public static void main(String [] args) throws IOException{
		String logfile = "Data/Carleton_1.lsf";
		String outfile = "casebase1.cb";
		
		//String visualLogPattern = "\A(see (?<time>[\d]{0,4}) .*\)";
		String visualPattern = "\\(see (?<time>[\\d]{0,4}) .*\\)";
		String actionPattern = "^\\(\\b(?<action>kick|turn|dash)\\b";
		String turnPattern = "\\(turn (?<turnAction>[\\-,\\d,\\.]+)";
		String DashPattern = "\\(turn (?<DashAction>[\\-,\\d,\\.]+)";
		String KickPattern = "\\(turn (?<KickAction>[\\-,\\d,\\.]+)";
		
		Pattern pv = Pattern.compile(visualPattern);
		Pattern pa = Pattern.compile(actionPattern);
		Pattern ptv = Pattern.compile(turnPattern);
		Pattern ptv = Pattern.compile(turnPattern);
		Pattern ptv = Pattern.compile(turnPattern);
		Matcher m;
	
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(logfile),'r');
			String Line;
			
			while ((Line = br.readLine()) != null){
				m = pa.matcher(Line);
				if(m.find()){
					System.out.println(m.group(1));
					m = ptv.matcher(Line);
					if(m.find()){
						System.out.println(m.group(1));
					}
				}
//				
//				m = pa.matcher(Line);
//				if(m.find()){
//					System.out.println(m.group(1));
//				}
				
				
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		} 
	}
}

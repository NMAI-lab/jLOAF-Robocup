/*
package CasebaseCreation;

import java.util.ArrayList;
import java.io.File;

import org.jLOAF.casebase.CaseBase;

public class caseBaseMergeTool {
	public static void main() {
		File folder = new File("../../Data");
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
			}
		}
		
		
		ArrayList<CaseBase> cbList = new ArrayList<CaseBase>();
		CaseBase cb1 = CaseBase.load("cb1.cb");
		cbList.add(cb1);
		CaseBase cb2 = CaseBase.load("cb2.cb");
		cbList.add(cb2);
		CaseBase fullCB = new CaseBase();
		CaseBase.save(fullCB, "fullCB.cb");
	}
}
*/
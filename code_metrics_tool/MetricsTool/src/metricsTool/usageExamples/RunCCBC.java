package metricsTool.usageExamples;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.runtime.CoreException;

import metricsTool.beans.ClassBean;
import metricsTool.beans.PackageBean;
import metricsTool.metrics.*;

public class RunCCBC {

	public static void main(String args[]) {
		
		// Path to the directory containing all the projects under analysis 
		String pathToDirectory = "/Users/najienka/eclipse-workspace/code_smell_tool/exp/projects";
		//String pathToDirectory = "/Users/najienka/eclipse-workspace/case_studies/chess-system-java-master/";
		
		File experimentDirectory = new File(pathToDirectory);
		
		
		// CCBC
		String headerCCBC = "className1;className2;CCBC\n";
		System.out.print(headerCCBC); // out.println will move to new line leaving blank line
		
		String outputCCBC = "";
		for(File project: experimentDirectory.listFiles()) {
			try {
				// Method to convert a directory into a set of java packages.
				Vector<PackageBean> packages = FolderToJavaProjectConverter.convert(project.getAbsolutePath());
				Vector<ClassBean> system = FolderToJavaProjectConverter.extractClasses(project.getAbsolutePath());
				
				
				//for(PackageBean packageBean: packages) {
					// converting class bean vector to list for nested looping for CCBC of class pairs
					List<ClassBean> list = Collections.list(system.elements()); 
					for (int i = 0; i < list.size(); ++i) {
				        for (int j = i + 1; j < list.size(); ++j) {
				        	//packageBean.getName() +"."+classBean.getName()
				        	System.out.println(list.get(i).getBelongingPackage() + "." + list.get(i).getName() 
				        			+ ";" 
				        			+ list.get(j).getBelongingPackage() + "." + list.get(j).getName() 
				        			+ ";"
				        			+ ConceptualMetrics.getCCBC(list.get(i),list.get(j))
				        			);
				        	//	System.out.println(list.get(i) + " : " + list.get(j));
				        	//	System.out.println(ConceptualMetrics.getCCBC(list.get(i),list.get(j)));
				        }
				    }
					
					
				//}	
				
				//FileUtility.writeFile(output, "folder/" + project.getName() + ".csv");
				
			} catch (CoreException e) {
				e.printStackTrace();
			}	
		}
		
		
	}
}

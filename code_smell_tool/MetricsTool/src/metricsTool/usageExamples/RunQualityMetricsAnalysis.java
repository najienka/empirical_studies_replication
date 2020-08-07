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

public class RunQualityMetricsAnalysis {

	public static void main(String args[]) {
		
		// Path to the directory containing all the projects under analysis 
		//String pathToDirectory = "/Users/najienka/eclipse-workspace/case_studies/chess-system-java-master/";
		String pathToDirectory = "/Users/najienka/eclipse-workspace/case_studies/chess-system-java-master";
		File experimentDirectory = new File(pathToDirectory);
		
		String header = "className;LOC;LCOM;CBO;WMC;RFC;DIT;NOC;C3\n";
		System.out.print(header); // out.println will move to new line leaving blank line
		
		String output = "";
		for(File project: experimentDirectory.listFiles()) {
			try {
				// Method to convert a directory into a set of java packages.
				Vector<PackageBean> packages = FolderToJavaProjectConverter.convert(project.getAbsolutePath());
				Vector<ClassBean> system = FolderToJavaProjectConverter.extractClasses(project.getAbsolutePath());

				
				
				for(PackageBean packageBean: packages) {
					
					for(ClassBean classBean: packageBean.getClasses()) {
									
						output+=packageBean.getName() +"."+classBean.getName() + ";" + CKMetrics.getLOC(classBean) + ";" 
								+ CKMetrics.getLCOM2(classBean) + ";" + CKMetrics.getCBO(classBean) + ";" 
								+ CKMetrics.getMcCabeMetric(classBean) + ";" + CKMetrics.getRFC(classBean) 
								
						// Other metrics are available in the CKMetrics class.
								+ ";" + CKMetrics.getDIT(classBean, system, 0) 
								+ ";" +  CKMetrics.getNOC(classBean, system)
								+ ";" + ConceptualMetrics.getC3(classBean)
								+ "\n";
					}
				}	
				
				//FileUtility.writeFile(output, "folder/" + project.getName() + ".csv"); // REMOVED
				System.out.print(output);
			} catch (CoreException e) {
				e.printStackTrace();
			}	
		}
		
		
		
	}
}

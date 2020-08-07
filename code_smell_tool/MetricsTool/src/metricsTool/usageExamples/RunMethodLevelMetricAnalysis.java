package metricsTool.usageExamples;

import java.io.File;
import java.util.Vector;

import org.eclipse.core.runtime.CoreException;

import metricsTool.beans.ClassBean;
import metricsTool.beans.MethodBean;
import metricsTool.beans.PackageBean;
import metricsTool.metrics.CKMetrics;

public class RunMethodLevelMetricAnalysis {

	public static void main(String args[]) {
		// Path to the directory containing all the projects under analysis 
		String pathToDirectory = "/Users/najienka/eclipse-workspace/code_smell_tool/exp/projects";
		File experimentDirectory = new File(pathToDirectory);

		for(File project: experimentDirectory.listFiles()) {
			if(!project.isHidden()) {
				System.out.println("Running detection on " + project.getName());

				String projectOutput = "method-name;LOC;MPC;IFC;McCabe;HalsteadVolume;HalsteadVocabulary;HalsteadLenght\n";
				Vector<PackageBean> packages;
				try {
					packages = FolderToJavaProjectConverter.convert(project.getAbsolutePath());
					
					for(PackageBean packageBean: packages) {
						for(ClassBean classBean: packageBean.getClasses()) {
							for(MethodBean methodBean: classBean.getMethods()) {
								projectOutput+=classBean.getBelongingPackage()+"."+classBean.getName() + "." + methodBean.getName() +";";
								
								projectOutput+=CKMetrics.getLOC(methodBean) + ";" 
										+ CKMetrics.getMPC(methodBean) + ";" + CKMetrics.getIFC(methodBean) + ";" 
										+ CKMetrics.getMcCabeMetric(methodBean) + ";" + CKMetrics.getHalsteadVolume(methodBean) + ";" + 
										+ CKMetrics.getHalsteadVocabulary(methodBean) + ";" + CKMetrics.getHalsteadLenght(methodBean) + "\n";
							}
						}
					}
					
				} catch (CoreException e) {
					e.printStackTrace();
				}

				//FileUtility.writeFile(projectOutput, "folder/"+project.getName()+".csv");
				System.out.print(projectOutput);
				
			}
		}
	}
}

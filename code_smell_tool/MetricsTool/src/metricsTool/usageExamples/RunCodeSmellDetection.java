package metricsTool.usageExamples;

import java.io.File;
import java.util.Vector;

import org.eclipse.core.runtime.CoreException;

import metricsTool.beans.ClassBean;
import metricsTool.beans.MethodBean;
import metricsTool.beans.PackageBean;
import metricsTool.codeSmells.*;
import metricsTool.metrics.CKMetrics;

public class RunCodeSmellDetection {
	public static void main(String args[]) {

		// Path to the directory containing all the projects under analysis 
		String pathToDirectory = "/Users/najienka/eclipse-workspace/case_studies";
		File experimentDirectory = new File(pathToDirectory);

		// Declaring Class-level code smell objects.
		ClassDataShouldBePrivateRule cdsbp = new ClassDataShouldBePrivateRule();
		ComplexClassRule complexClass = new ComplexClassRule();
		FunctionalDecompositionRule functionalDecomposition = new FunctionalDecompositionRule();
		GodClassRule godClass = new GodClassRule();
		SpaghettiCodeRule spaghettiCode = new SpaghettiCodeRule(); 
		LongMethodRule longMethod = new LongMethodRule();

		// The following rules are quite low for detecting smelly code components.
		//MisplacedClassRule misplacedClass = new MisplacedClassRule();
		//FeatureEnvyRule featureEnvy = new FeatureEnvyRule();
	
		for(File project: experimentDirectory.listFiles()) {
			if(!project.isHidden()) {
				System.out.println("Running detection on " + project.getName());
				
				for(File release: project.listFiles()) {
					if(!release.isHidden()) {
						try {
							String projectOutput = "class-name;CDSBP;CC;FD;Blob;SC;LM;LOC;LCOM5;TCC;Connectivity;LCC;ICH;CBO;MPC;DAC;McCabe;RFC;WMC;Halstead-Volume;Halstead-Vocabulary;Halstead-Length\n";
							
							System.out.println("	Running detection on " + release.getName());
							// Method to convert a directory into a set of java packages.
							Vector<PackageBean> packages = FolderToJavaProjectConverter.convert(release.getAbsolutePath());
			
							for(PackageBean packageBean: packages) {
								for(ClassBean classBean: packageBean.getClasses()) {
			
									// How to call methods for computing code smell detection.
									// The currently implemented detector is DECOR (http://www.irisa.fr/triskell/publis/2009/Moha09d.pdf)
								
									boolean isClassDataShouldBePrivate = cdsbp.isClassDataShouldBePrivate(classBean);
									boolean isComplexClass = complexClass.isComplexClass(classBean);
									boolean isFunctionalDecomposition = functionalDecomposition.isFunctionalDecomposition(classBean);
									boolean isGodClass = godClass.isGodClass(classBean);
									boolean isSpaghettiCode = spaghettiCode.isSpaghettiCode(classBean);
		
									projectOutput+=classBean.getBelongingPackage()+"."+classBean.getName() + ";" + 
											isClassDataShouldBePrivate + ";" + isComplexClass + ";" + isFunctionalDecomposition 
											+ ";" + isGodClass + ";" + isSpaghettiCode + ";";
									
									/*System.out.println("Class: " + classBean.getBelongingPackage() 
										+ "." + classBean.getName() + "\n"
										+ "		ClassDataShouldBePrivate: " + isClassDataShouldBePrivate + "\n"
										+ "		ComplexClass: " + isComplexClass + "\n"
										+ "		FunctionalDecomposition: " + isFunctionalDecomposition + "\n"
										+ "		GodClass: " + isGodClass + "\n"
										+ "		SpaghettiCode: " + isSpaghettiCode);
									*/
									
									boolean isClassSmelly=false;
									for(MethodBean methodBean: classBean.getMethods()) {
										boolean isLongMethod = longMethod.isLongMethod(methodBean);
										if(isLongMethod=true)
											isClassSmelly=true;
									}
									
									projectOutput+=isClassSmelly+";";
									
									projectOutput+=CKMetrics.getLOC(classBean) + ";" 
									+ CKMetrics.getLCOM5(classBean) + ";" + CKMetrics.getTCC(classBean) + ";" + CKMetrics.getConnectivity(classBean) + ";" + CKMetrics.getLCC(classBean) + ";" +
									+ CKMetrics.getICH(classBean) + ";" + CKMetrics.getCBO(classBean) + ";" + CKMetrics.getMPC(classBean) + ";" + CKMetrics.getDAC2(classBean) + ";" 
									+ CKMetrics.getMcCabeMetric(classBean) + ";" + CKMetrics.getRFC(classBean) + ";" + CKMetrics.getWMC(classBean) + ";" + CKMetrics.getHalsteadVolume(classBean) + ";" + 
									+ CKMetrics.getHalsteadVocabulary(classBean) + ";" + CKMetrics.getHalsteadLenght(classBean) + "\n";
								}
							}
							
							/*File projectResultDir= new File("folder/"+project.getName());
							if(!projectResultDir.exists()) {
								projectResultDir.mkdirs();
							}
							
							FileUtility.writeFile(projectOutput, projectResultDir.getAbsolutePath()+"/"+release.getName()+".csv");*/
							System.out.print(projectOutput);
						} catch (CoreException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}

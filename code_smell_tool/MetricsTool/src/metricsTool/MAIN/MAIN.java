package metricsTool.MAIN;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.runtime.CoreException;

import metricsTool.beans.ClassBean;
import metricsTool.beans.MethodBean;
import metricsTool.beans.PackageBean;
import metricsTool.codeSmells.ClassDataShouldBePrivateRule;
import metricsTool.codeSmells.ComplexClassRule;
import metricsTool.codeSmells.FunctionalDecompositionRule;
import metricsTool.codeSmells.GodClassRule;
import metricsTool.codeSmells.LongMethodRule;
import metricsTool.codeSmells.SpaghettiCodeRule;
import metricsTool.metrics.*;
import metricsTool.usageExamples.FileUtility;
import metricsTool.usageExamples.FolderToJavaProjectConverter;

public class MAIN {
	
	//Path to the directory containing all the projects under analysis 
	static String pathToDirectory = "/Users/najienka/eclipse-workspace/case_studies/";
	//static String pathToDirectory = "/Users/najienka/Documents/academic/research/datasets/reaper_dataset/case_studies";
	
	public static void main(String args[]) throws IOException {
		
		//compute metrics
		//boolStructuralDependency(); //returns boolean true/false
        CKC3Metrics();
        //otherSmellMetrics(); //contains some boolean true/false metrics
        //CCBC(); //conceptual coupling scores for class pairs
				
		//writing to file
		/*
		String path = "/Users/najienka/eclipse-workspace/case_studies/output.txt";
		//write to file with file writer or file utility class which overwrites
		//use filewriter
		//FileWriter projClasses = new FileWriter(path);
        String newPath = "computing all metrics";
        //projClasses.write(newPath + "\n");
        //projClasses.close();
        //OR
		//FileUtility.writeFile(newPath, path);//overwrites 
		*/
	}
	
	public static void boolStructuralDependency() {
		
		File experimentDirectory = new File(pathToDirectory);
		
		String headerDep = "className1;className2;strDep\n";
		// System.out.print(headerDep); 
		
		for(File project: experimentDirectory.listFiles()) {
			String outputDep = "";
			outputDep += headerDep;
			System.out.println("Processing "+ project.getName() + "\n");
			try {
				// Method to convert a directory into a set of java packages.
				Vector<PackageBean> packages = FolderToJavaProjectConverter.convert(project.getAbsolutePath());
				Vector<ClassBean> system = FolderToJavaProjectConverter.extractClasses(project.getAbsolutePath());				
					// converting class bean vector to list for nested looping for CCBC of class pairs
					List<ClassBean> list = Collections.list(system.elements()); 
					//using nested for loop to analyse unique class pairs
					for (int i = 0; i < list.size(); ++i) { 
				        for (int j = i + 1; j < list.size(); ++j) {
				        	//packageBean.getName() +"."+classBean.getName()
				        	outputDep += list.get(i).getBelongingPackage() + "." + list.get(i).getName() 
				        			+ ";" 
				        			+ list.get(j).getBelongingPackage() + "." + list.get(j).getName() 
				        			+ ";"
				        			+ CKMetrics.existsDependence(list.get(i),list.get(j))
				        			+ "\n";
				        	//	System.out.println(list.get(i) + " : " + list.get(j)); // will print the class beans including class name, instance variables and methods
				        	//	System.out.println(ConceptualMetrics.getCCBC(list.get(i),list.get(j)));
				        }
				    }
				System.out.print(outputDep);
				//overwrites
				//FileUtility.writeFile(outputDep, pathToDirectory + "/" + project.getName() + "/" + project.getName() + "StrDep.csv");
				FileUtility.writeFile(outputDep, project.getAbsolutePath() + "/" + project.getName() + "StrDep.csv");
			} catch (CoreException e) {
				e.printStackTrace();
			}	
		}
				
	}
	
	public static void CKC3Metrics() {
		File experimentDirectory = new File(pathToDirectory);
		
		String header = "className;LOC;LCOM;CBO;WMC;RFC;DIT;NOC;NOO;NOPA;NOPrivateA;McCabeMetric;C3\n";
		//System.out.print(header); // out.println will move to new line leaving blank line
		
		
		for(File project: experimentDirectory.listFiles()) {
			String output = "";
			output += header;
			System.out.println("Processing "+ project.getName() + "\n");
			
			try {
				// Method to convert a directory into a set of java packages.
				Vector<PackageBean> packages = FolderToJavaProjectConverter.convert(project.getAbsolutePath());
				Vector<ClassBean> system = FolderToJavaProjectConverter.extractClasses(project.getAbsolutePath());

				for(PackageBean packageBean: packages) {
					
					for(ClassBean classBean: packageBean.getClasses()) {
						//QUALITY METRICS
						output+=packageBean.getName() +"."+classBean.getName() + ";" + CKMetrics.getLOC(classBean) + ";" 
								+ CKMetrics.getLCOM2(classBean) + ";" + CKMetrics.getCBO(classBean) + ";" 
								+ CKMetrics.getMcCabeMetric(classBean) + ";" + CKMetrics.getRFC(classBean) 
								
						// Other metrics are available in the CKMetrics class.
								+ ";" + CKMetrics.getDIT(classBean, system, 0) 
								+ ";" +  CKMetrics.getNOC(classBean, system)
								+ ";" + CKMetrics.getNOO(classBean, system)
								+ ";" + CKMetrics.getNOPA(classBean)
								+ ";" + CKMetrics.getNOPrivateA(classBean)
								+ ";" + CKMetrics.getMcCabeMetric(classBean)
								//NOPA – Number of Public Attributes
								//NOO, TCC (tight class cohesion), - https://www.researchgate.net/publication/220299213_Empirical_Analysis_of_Object-Oriented_Design_Metrics_Towards_a_New_Metric_Using_Control_Flow_Paths_and_Probabilities
								//NOO (Number of Operations): NOO gives the number of methods in a class [Henderson-Sellers 96]. 
								//If a class has many operations, it is difficult to reuse and often looses cohesion. 
								+ ";" + ConceptualMetrics.getC3(classBean)
								+ "\n";
						
					}
				}	
				
				FileUtility.writeFile(output, project.getAbsolutePath() + "/" + project.getName() + "CKC3Metrics.csv");
				System.out.print(output);
			} catch (CoreException e) {
				e.printStackTrace();
			}	
		}
		
	}
	
	public static void otherSmellMetrics() {

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
			
			System.out.println("Processing "+ project.getName() + "\n");
			String projectOutput = "class-name;CDSBP;CC;FD;Blob;SC;LM;LOC;LCOM5;TCC;Connectivity;LCC;ICH;CBO;MPC;DAC;McCabe;RFC;WMC;Halstead-Volume;Halstead-Vocabulary;Halstead-Length;DAC2;Coh;IFC;LCOM1;LCOM2;LCOM3;LCOM4\n";
			if(!project.isHidden() && project.isDirectory() && !project.getName().contains(".DS_Store")) {
				System.out.println("Running detection on " + project.getName());
				
				for(File release: project.listFiles()) {
					if(!release.isHidden()) {
						try {
							//String projectOutput = "class-name;CDSBP;CC;FD;Blob;SC;LM;LOC;LCOM5;TCC;Connectivity;LCC;ICH;CBO;MPC;DAC;McCabe;RFC;WMC;Halstead-Volume;Halstead-Vocabulary;Halstead-Length;DAC2;Coh;IFC;LCOM1;LCOM2;LCOM3;LCOM4\n";
							//lcom 1-5 - http://www.jot.fm/issues/issue_2004_04/article8.pdf
							
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
									+ CKMetrics.getHalsteadVocabulary(classBean) + ";" + CKMetrics.getHalsteadLenght(classBean) + ";" + CKMetrics.getDAC2(classBean) + ";" +
									+ CKMetrics.getCoh(classBean) + ";" + CKMetrics.getIFC(classBean) + ";" 
									+ CKMetrics.getLCOM1(classBean) + ";" 
									+ CKMetrics.getLCOM2(classBean) + ";" 
									+ CKMetrics.getLCOM3(classBean) + ";" 
									+ CKMetrics.getLCOM4(classBean) + "\n";
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
				
				FileUtility.writeFile(projectOutput, project.getAbsolutePath() + "/" + project.getName() + "OtherSmellMetrics.csv");
			}
		}
	}
	
	public static void CCBC() {
		
		File experimentDirectory = new File(pathToDirectory);
		
		String headerCCBC = "className1;className2;CCBC\n";
		//System.out.print(headerCCBC); 
		
		
		for(File project: experimentDirectory.listFiles()) {
			String outputCCBC = "";
			outputCCBC += headerCCBC;
			System.out.println("Processing "+ project.getName() + "\n");
			try {
				// Method to convert a directory into a set of java packages.
				Vector<PackageBean> packages = FolderToJavaProjectConverter.convert(project.getAbsolutePath());
				Vector<ClassBean> system = FolderToJavaProjectConverter.extractClasses(project.getAbsolutePath());				
					// converting class bean vector to list for nested looping for CCBC of class pairs
					List<ClassBean> list = Collections.list(system.elements()); 
					//using nested for loop to analyse unique class pairs
					for (int i = 0; i < list.size(); ++i) { 
				        for (int j = i + 1; j < list.size(); ++j) {
				        	//packageBean.getName() +"."+classBean.getName()
				        	outputCCBC += list.get(i).getBelongingPackage() + "." + list.get(i).getName() 
				        			+ ";" 
				        			+ list.get(j).getBelongingPackage() + "." + list.get(j).getName() 
				        			+ ";"
				        			+ ConceptualMetrics.getCCBC(list.get(i),list.get(j))
				        			+ "\n";
				        	//	System.out.println(list.get(i) + " : " + list.get(j));
				        	//	System.out.println(ConceptualMetrics.getCCBC(list.get(i),list.get(j)));
				        }
				    }
				System.out.print(outputCCBC);
				FileUtility.writeFile(outputCCBC, project.getAbsolutePath() + "/" + project.getName() + "CCBC.csv");	
			} catch (CoreException e) {
				e.printStackTrace();
			}	
		}
		
	}
}

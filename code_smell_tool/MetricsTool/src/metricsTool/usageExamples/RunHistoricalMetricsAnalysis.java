package metricsTool.usageExamples;

import java.io.File;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.runtime.CoreException;

import metricsTool.beans.ClassBean;
import metricsTool.beans.Commit;
import metricsTool.beans.MethodBean;
import metricsTool.beans.PackageBean;
import metricsTool.beans.Release;
import metricsTool.metrics.CKMetrics;
import metricsTool.metrics.HistoricalMetrics;
import metricsTool.parser.LogParser;

public class RunHistoricalMetricsAnalysis {

	public static void main(String args[]) {
		// Path to the directory containing all the projects under analysis 
		String pathToDirectory = "path";
		File experimentDirectory = new File(pathToDirectory);
		
		String pathToReleases = "path";		
		String pathToLogFiles = "path";
		for(File project: experimentDirectory.listFiles()) {
			String projectName = project.getName();
			
			File projectReleases = new File(pathToReleases+"/"+projectName);
			File projectLogFile = new File(pathToLogFiles+"/"+projectName);
			
			LogParser logParser = new LogParser();
			
			List<Release> releases = logParser.readReleaseData(projectReleases.getAbsolutePath(), projectLogFile.getAbsolutePath());
			List<Commit> commits = logParser.readCommits(projectLogFile.getAbsolutePath());
			
			if(!project.isHidden()) {
				System.out.println("Running analysis on " + project.getName());

				String projectOutput = "class-name;BOC;FCH;FRCH;LCH;WCD;WFR;TACH;ATAF;CHD;LCA;LCD;CSB;CSBS;ACDF;CHO\n";
				Vector<PackageBean> packages;
				try {
					packages = FolderToJavaProjectConverter.convert(project.getAbsolutePath());

					for(PackageBean packageBean: packages) {
						for(ClassBean classBean: packageBean.getClasses()) {
							projectOutput+=classBean.getBelongingPackage()+"."+classBean.getName() +";";

							projectOutput+=HistoricalMetrics.getBOC(releases, classBean) + ";" + 
									HistoricalMetrics.getFCHc(commits, classBean) + ";" + 
									HistoricalMetrics.getFC(releases, classBean) + ";" + 
									HistoricalMetrics.getLCH(releases, classBean) + ";" +
									HistoricalMetrics.getWCH(releases, commits, classBean) + ";" +
									HistoricalMetrics.getWFR(releases, commits, classBean) + ";" +
									HistoricalMetrics.getTACH(commits, classBean) + ";" +
									HistoricalMetrics.getATAF(releases, commits, classBean) + ";" +
									HistoricalMetrics.getCHD(commits, classBean) + ";" +
									HistoricalMetrics.getLCA(releases, commits, classBean) + ";" +
									HistoricalMetrics.getLCD(releases, commits, classBean) + ";" +
									HistoricalMetrics.getCIC(releases, commits, classBean) + ";" +
									HistoricalMetrics.getACDF(releases, commits, classBean) + ";" +  
									HistoricalMetrics.getCHO(releases, classBean) + "\n";
						}
						
					}

				} catch (CoreException e) {
					e.printStackTrace();
				}

				FileUtility.writeFile(projectOutput, "folder/"+project.getName()+".csv");

			}
		}
	}

}

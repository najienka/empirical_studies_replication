package metricsTool.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import metricsTool.beans.Change;
import metricsTool.beans.Commit;
import metricsTool.beans.FileBean;
import metricsTool.beans.Release;
import metricsTool.usageExamples.FileUtility;

public class LogParser {
	
	public List<Release> readReleaseData(String pReleasePath, String pLogPath) {
		List<Release> releases = new ArrayList<Release>();
		
		try {
			
			String releaseFileLines[] = FileUtility.readFile(pReleasePath).split("\n");
			
			for(String release: releaseFileLines) {
				String fields[] = release.split(";");
				
				Release releaseData = new Release();
				
				releaseData.setReleaseNumber(Integer.parseInt(fields[0]));
				releaseData.setReleaseName(fields[1]);
				
				String commitList[] = fields[2].split(",");
				ArrayList<Commit> releaseCommits = new ArrayList<Commit>();
				
				for(String commit: commitList) {
					releaseCommits.add(this.readCommit(pLogPath, commit));
				}
				
				releaseData.setCommits(releaseCommits);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return releases;
	}
	
	public Commit readCommit(String pLogPath, String pCommitID) {
		Commit commit = new Commit();
		try {
			String logFileLines[] = FileUtility.readFile(pLogPath).split("\n");
			
			for(String logData: logFileLines) {
				String fields[] = logData.split(";");
				
				if(fields[0].equals(pCommitID)) {
					
					commit.setCommitHash(fields[0]);
					commit.setAbbreviateCommitHash(fields[1]); 
					commit.setSubject(fields[2]);
					commit.setBody(fields[3]);
					
					String changeSet[] = fields[4].split(",");
					ArrayList<Change> changes = new ArrayList<Change>();
					
					for(String change: changeSet) {
						Change commitChange = new Change();
						
						FileBean fileBean = new FileBean();
						fileBean.setPath(change.substring(0, change.indexOf("|")));
						commitChange.setFile(fileBean);
						
						String metaData[] = change.split("|");
						commitChange.setAddedlines(Integer.parseInt(metaData[0]));
						commitChange.setModifiedlines(Integer.parseInt(metaData[1]));
						commitChange.setRemovedlines(Integer.parseInt(metaData[2]));
					
						changes.add(commitChange);
					}
					
					commit.setChanges(changes);
				}
			}		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return commit;
	}
	
	public List<Commit> readCommits(String pLogPath) {
		List<Commit> commits = new ArrayList<Commit>();
		
		Commit commit = new Commit();
		try {
			String logFileLines[] = FileUtility.readFile(pLogPath).split("\n");
			
			for(String logData: logFileLines) {
				String fields[] = logData.split(";");
				
				commit.setCommitHash(fields[0]);
				commit.setAbbreviateCommitHash(fields[1]); 
				commit.setSubject(fields[2]);
				commit.setBody(fields[3]);
				
				String changeSet[] = fields[4].split(",");
				ArrayList<Change> changes = new ArrayList<Change>();
				
				for(String change: changeSet) {
					Change commitChange = new Change();
					
					FileBean fileBean = new FileBean();
					fileBean.setPath(change.substring(0, change.indexOf("|")));
					commitChange.setFile(fileBean);
					
					String metaData[] = change.split("|");
					commitChange.setAddedlines(Integer.parseInt(metaData[0]));
					commitChange.setModifiedlines(Integer.parseInt(metaData[1]));
					commitChange.setRemovedlines(Integer.parseInt(metaData[2]));
				
					changes.add(commitChange);
				}
				
				commit.setChanges(changes);
				commits.add(commit);
			}		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return commits;
	}

}

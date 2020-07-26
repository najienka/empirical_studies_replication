package metricsTool.metrics;

import java.util.List;

import metricsTool.beans.Change;
import metricsTool.beans.ClassBean;
import metricsTool.beans.Commit;
import metricsTool.beans.Release;

public class HistoricalMetrics {

	
	public static double getBOC(List<Release> pReleases, ClassBean pClass) {
		int releaseNumber=-1;
		
		for(Release release: pReleases) {
			for(Commit commit: release.getCommits()) {
				for(Change change: commit.getChanges()) {
					
					if(change.getFile().getPath().equals(pClass.getPathToClass())) {
						return release.getReleaseNumber();
					}
					
				}
				
			}
		}
		
		return releaseNumber;
	}
	
	public static double getTACH(List<Commit> pCommits, ClassBean pClass) {
		double numberOfChanges=0.0;
		
		for(Commit commit: pCommits) {
			for(Change change: commit.getChanges()) {
				
				if(change.getFile().getPath().equals(pClass.getPathToClass())) {
					numberOfChanges++;
				}
			}
		}
				
		return numberOfChanges;
	}
	
	public static double getTACHr(Release pRelease, ClassBean pClass) {
		double numberOfChanges=0.0;
		
		for(Commit commit: pRelease.getCommits()) {
			for(Change change: commit.getChanges()) {
				
				if(change.getFile().getPath().equals(pClass.getPathToClass())) {
					numberOfChanges++;
				}
			}
		}
				
		return numberOfChanges;
	}
	
	public static int getFCHr(List<Release> pReleases, ClassBean pClass) {
		int firstChanged=-1;
		
		for(Release release: pReleases) {
			for(Commit commit: release.getCommits()) {
				for(Change change: commit.getChanges()) {
					
					if(change.getFile().getPath().equals(pClass.getPathToClass())) {
						firstChanged = release.getReleaseNumber();
						return firstChanged;
					}
				}
			}
		}
		
		return firstChanged;
	}
	
	
	public static int getLCH(List<Release> pReleases, ClassBean pClass) {
		int lastChanged=0;
		
		for(Release release: pReleases) {
			for(Commit commit: release.getCommits()) {
				for(Change change: commit.getChanges()) {
					
					if(change.getFile().getPath().equals(pClass.getPathToClass())) {
						lastChanged = release.getReleaseNumber();
					}
				}
			}
		}
		
		return lastChanged;
	}
	
	public static int getFC(List<Release> pReleases, ClassBean pClass) {
		int numberOfReleasesInWhichChanged=0;
		
		for(Release release: pReleases) {
			boolean changedInRelease = false;
			for(Commit commit: release.getCommits()) {
				for(Change change: commit.getChanges()) {
					
					if(change.getFile().getPath().equals(pClass.getPathToClass())) {
						changedInRelease=true;
					}
				}
			}
			
			if(changedInRelease) 
				numberOfReleasesInWhichChanged++;
		}
		
		return numberOfReleasesInWhichChanged;
	}
	
	public static int getFCHc(List<Commit> pCommits, ClassBean pClass) {
		int firstChanged=0;
		
		for(Commit commit: pCommits) {
			firstChanged++;
				for(Change change: commit.getChanges()) {
					
					if(change.getFile().getPath().equals(pClass.getPathToClass())) {
						return firstChanged;
					}
				}
			}
		
		return firstChanged;
	}
	

	public static boolean getCHO(List<Release> pReleases, ClassBean pClass) {
		
		for(int i=0; i<pReleases.size(); i++) {
			for(int j=1; j<pReleases.size(); j++) {
				
				boolean changeInFirst=false;
				boolean changeInSecond=false;
				
				for(Commit commit: pReleases.get(i).getCommits()) {
					for(Change change: commit.getChanges()) {
						
						if(change.getFile().getPath().equals(pClass.getPathToClass())) {
							changeInFirst=true;
						}
					}
				}
				
				for(Commit commit: pReleases.get(j).getCommits()) {
					for(Change change: commit.getChanges()) {
						
						if(change.getFile().getPath().equals(pClass.getPathToClass())) {
							changeInSecond=true;
						}
					}
				}
				
				if(changeInFirst && changeInSecond) 
					return true;
				
			}
		}
	
		return false;
	}
	
	public static double getCHD(List<Commit> pCommits, ClassBean pClass) {
		double LOC = pClass.getLOC();
		return HistoricalMetrics.getTACH(pCommits, pClass) / LOC;
	}
	
	public static double getWCH(List<Release> pReleases, List<Commit> pCommits, ClassBean pClass) {
		double TACH = HistoricalMetrics.getTACH(pCommits, pClass);
		return TACH * (Math.pow(HistoricalMetrics.getLCH(pReleases, pClass), HistoricalMetrics.getBOC(pReleases, pClass)));
	}
	
	public static double getWFR(List<Release> pReleases, List<Commit> pCommits, ClassBean pClass) {
		double TACH = HistoricalMetrics.getTACH(pCommits, pClass);
		double FC = HistoricalMetrics.getFC(pReleases, pClass);
		
		return FC*TACH;
	}
	
	public static double getATAF(List<Release> pReleases, List<Commit> pCommits, ClassBean pClass) {
		double TACH = HistoricalMetrics.getTACH(pCommits, pClass);
		double TACHrSum=0.0;
		
		
		if(TACH == 0) 
			return 0.0;
		else {
			
			for(Release release: pReleases) {
				TACHrSum+=HistoricalMetrics.getTACHr(release, pClass);
			}
		
			return TACHrSum/TACH;
		}
	}
	
	public static double getLCA(List<Release> pReleases, List<Commit> pCommits, ClassBean pClass) {
		double LCH = HistoricalMetrics.getLCH(pReleases, pClass);

		if(LCH==pReleases.size()-1) {
			return HistoricalMetrics.getTACH(pCommits, pClass);
		} else return 0.0;
	}
	
	public static double getLCD(List<Release> pReleases, List<Commit> pCommits, ClassBean pClass) {
		double LOC = pClass.getLOC();
		return HistoricalMetrics.getLCA(pReleases, pCommits, pClass)/LOC;
	}
	
	public static double getCIC(List<Release> pReleases, List<Commit> pCommits, ClassBean pClass) {
		double releaseNumber = HistoricalMetrics.getBOC(pReleases, pClass);
		double sum=0.0;
		
		for(int i=(int)releaseNumber; i<pReleases.size(); i++) {
			for(Commit commit: pReleases.get(i).getCommits()) {
				for(Change change:commit.getChanges()) {
					
					if(change.getFile().getPath().equals(pClass.getPathToClass())) {
						sum+=change.getAddedlines();
						sum+=change.getModifiedlines();
						sum-=change.getRemovedlines();
					}
				}
			}
		}
		
		return sum;
	}
	
	public static double getACDF(List<Release> pReleases, List<Commit> pCommits, ClassBean pClass) {		
		double ACDF = HistoricalMetrics.getACDF(pReleases, pCommits, pClass);
		double CHD = HistoricalMetrics.getCHD(pCommits, pClass);

		return ACDF/CHD;
	}
}



package metricsTool.beans;


public class Change implements java.io.Serializable{
	private FileBean file;
	private int addedlines;
	private int removedlines;
	private int modifiedlines;
	
	
	public FileBean getFile() {
		return file;
	}
	public void setFile(FileBean file) {
		this.file = file;
	}
	public int getAddedlines() {
		return addedlines;
	}
	public void setAddedlines(int addedlines) {
		this.addedlines = addedlines;
	}
	public int getRemovedlines() {
		return removedlines;
	}
	public void setRemovedlines(int removedlines) {
		this.removedlines = removedlines;
	}
	public int getModifiedlines() {
		return modifiedlines;
	}
	public void setModifiedlines(int modifiedlines) {
		this.modifiedlines = modifiedlines;
	}

}

package metricsTool.beans;

import java.util.HashMap;
import java.util.List;

public class Commit implements java.io.Serializable{

	private String commitHash;
	private String abbreviateCommitHash;
	private String author;
	private long authorTime;
	private String committer;
	private long committerTime;
	private String subject;
	private String body;
	private List<Change> changes;
	private HashMap<String, String> tags = new HashMap<String, String>();
	
	public String getCommitHash() {
		return commitHash;
	}
	public void setCommitHash(String commitHash) {
		this.commitHash = commitHash;
	}
	public String getAbbreviateCommitHash() {
		return abbreviateCommitHash;
	}
	public void setAbbreviateCommitHash(String abbreviateCommitHash) {
		this.abbreviateCommitHash = abbreviateCommitHash;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public long getAuthorTime() {
		return authorTime;
	}
	public void setAuthorTime(long authorTime) {
		this.authorTime = authorTime;
	}
	public String getCommitter() {
		return committer;
	}
	public void setCommitter(String committer) {
		this.committer = committer;
	}
	public long getCommitterTime() {
		return committerTime;
	}
	public void setCommitterTime(long committerTime) {
		this.committerTime = committerTime;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public List<Change> getChanges() {
		return changes;
	}
	public void setChanges(List<Change> changes) {
		this.changes = changes;
	}
	public HashMap<String, String> getTags() {
		return tags;
	}
	public void setTags(HashMap<String, String> tags) {
		this.tags = tags;
	}
}

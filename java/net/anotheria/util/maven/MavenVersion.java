package net.anotheria.util.maven;

import java.io.Serializable;

public class MavenVersion implements Serializable{
	private String fileTimestamp;
	private String version;
	private String group;
	private String artifact;
	
	@Override public String toString(){
		return getGroup()+"/"+getArtifact()+" "+getVersion()+" file: "+getFileTimestamp();
	}

	public String getFileTimestamp() {
		return fileTimestamp;
	}

	void setFileTimestamp(String date) {
		this.fileTimestamp = date;
	}

	public String getVersion() {
		return version;
	}

	void setVersion(String version) {
		this.version = version;
	}

	public String getGroup() {
		return group;
	}

	void setGroup(String group) {
		this.group = group;
	}

	public String getArtifact() {
		return artifact;
	}

	void setArtifact(String artifact) {
		this.artifact = artifact;
	}
}

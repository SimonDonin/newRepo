package journalmanager;

import java.io.Serializable;

public class Entry implements Serializable {
	private String entryName;
	private String content;
	
	public Entry(String entryName, String content) {
		this.entryName = entryName;
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public String getEntryName() {
		return entryName;
	}
}

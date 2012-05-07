package calculator;

import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable {

	private static final long serialVersionUID = -5899680116199753971L;
	private String username;
	private String password;

	// Holds users transcripts - possibly from multiple institutions.
	// List element names are the names of the school.
	private HashMap<String, Transcript> transcripts;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		transcripts = new HashMap<String, Transcript>();
	}

	protected String getUsername() {
		return username;
	}

	protected void addTranscript(String schoolName, Transcript transcript) {
		transcripts.put(schoolName, transcript);
	}

	protected void removeTranscript(String schoolName) {
		// should probably check valid.
		transcripts.remove(schoolName);
	}

	protected HashMap<String, Transcript> getTranscripts() {
		return transcripts;
	}

	// May want to get a specific transcript at some point....

	// Validates pass (SystemController)
	public boolean checkPassword(String pass) {
		if (pass.equals(password)) {
			return true;
		}
		return false;
	}
}

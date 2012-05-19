package calculator;

import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable {

	private static final long serialVersionUID = -5899680116199753971L;
	private String username;
	private String password;
	
	// Keep this if user has to enter username, will auto-login afterwards.
	// Alternative way of doing this is to bypass the WelcomePanel altogether
	// (and thus login) and keep user logged in until he logs out....
	private boolean rememberLoginInfo;

	// Holds users transcripts - possibly from multiple institutions.
	// List element names are the names of the school.
	private HashMap<String, Transcript> transcripts;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.rememberLoginInfo = false;
		transcripts = new HashMap<String, Transcript>();
	}

	protected String getUsername() {
		return username;
	}

	protected boolean getRememberLoginInfo() {
		return rememberLoginInfo;
	}

	protected void setRememberLoginInfo(boolean rememberLoginInfo) {
		this.rememberLoginInfo = rememberLoginInfo;
	}

	protected boolean addTranscript(String schoolName, Transcript transcript) {
		if (transcripts.keySet().contains(schoolName))
			return false;
		transcripts.put(schoolName, transcript);
		return true;
	}

	protected void removeTranscript(String schoolName) {
		// should probably check valid.
		transcripts.remove(schoolName);
	}

	protected Transcript getTranscript(String schoolName) {
		return transcripts.get(schoolName);
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

package calculator;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

	private String username;
	private String password;

	// Holds users transcripts - possibly from multiple institutions.
	private ArrayList<Transcript> transcripts;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		transcripts = new ArrayList<Transcript>();
	}

	protected String getUsername() {
		return username;
	}

	protected void addTranscript(Transcript transcript) {
		transcripts.add(transcript);
	}

	protected ArrayList<Transcript> getTranscripts() {
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

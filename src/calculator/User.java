package calculator;

import java.io.Serializable;

public class User implements Serializable { // might be able to get rid of this
											// b/c just serializing arraylist
	private String username;
	private String password;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	protected String getUsername() {
		return username;
	}

	// Validates pass (SystemController)
	public boolean checkPassword(String pass) {
		if (pass.equals(password)) {
			return true;
		}
		return false;
	}
}

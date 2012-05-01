package calculator;

import java.io.*;

import java.util.ArrayList;

public class SystemController {

	protected final String ROOTDIR = System.getenv("APPDATA") + "\\GPACalcJava";
	protected User activeUser;
	protected ArrayList<User> users;

	// need a data structure to store user data - perhaps a map with users as
	// the key, classes as the value. (Can replace classes with some collection
	// of data is necessary, i.e. - multiple schools).
	

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	public SystemController() {

		this.activeUser = null;
		this.users = new ArrayList<User>();

		// System.out.println(new File(ROOTDIR).exists());

		if (!new File(ROOTDIR).exists()) {
			new File(ROOTDIR).mkdir();
		} else {
			loadUserList();
		}

	}

	// Returns true if username is available, false otherwise.
	protected boolean checkUserNameAvail(String username) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equals(username)) {
				return false;
			}
		}
		return true;
	}

	// Returns true is successfully registered user, false otherwise.
	protected boolean register(String username, String password) {
		// returns false if username not available
		if (checkUserNameAvail(username)) {
			User user = new User(username, password);
			users.add(user); // add new user to persistent user list.
			activeUser = user;
			saveUserList();
			return true;
		}
		return false;
	}

	// Returns true if valid user/pass for login, false otherwise.
	protected boolean login(String username, String password) {
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			// Checks if input username & password are valid/match.
			if (user.getUsername().equals(username)
					&& user.checkPassword(password)) {
				// Set active user.
				activeUser = user;
				return true;
			}
		}
		// Invalid user/pass.
		return false;
	}

	// Can be used anywhere but ALWAYS called on exit.
	protected void saveUserList() {
		try {
			for (int i = 0; i < users.size(); i++) {
				System.out.println(users.get(i).getUsername()
						+ " saveUserList()");
			}
			FileOutputStream fos = new FileOutputStream(ROOTDIR + "\\userlist");
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(users);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void loadUserList() {
		try {
			FileInputStream fis = new FileInputStream(ROOTDIR + "\\userlist");
			ObjectInputStream in = new ObjectInputStream(fis);
			users = (ArrayList<User>) in.readObject();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
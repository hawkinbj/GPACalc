package calculator;

import java.io.*;

import java.util.ArrayList;

public class SystemController {

	protected final String ROOTDIR = System.getenv("APPDATA") + "\\GPACalcJava";
	protected User activeUser;
	protected ArrayList<User> users;
	protected ArrayList<School> schools; // List of known schools.

	public SystemController() {

		activeUser = null;
		users = new ArrayList<User>();
		schools = new ArrayList<School>();

		if (!new File(ROOTDIR).exists()) {
			new File(ROOTDIR).mkdir();
			populateSchools();
		} else {
			loadSchoolList();
			loadUserList();
		}
	}

	// Populates list of known schools. Only run on the first execution of
	// program. Probably should be part of the installer if one is ever made...
	private void populateSchools() {
		schools.add(new School("GMU"));
		schools.add(new School("UTSA"));
		schools.add(new School("TNCC"));
		schools.add(new School("ODU"));
		saveSchoolList();
	}

	protected boolean addSchool(String name) {
		for (int i = 0; i < schools.size(); i++) {
			if (schools.get(i).getName().equals(name)) {
				return false;
			}
		}
		schools.add(new School(name));
		saveSchoolList();
		return true;
	}

	protected void removeSchool(String name) {
		// TODO
	}

	protected void addTranscript(Transcript transcript) {
		activeUser.addTranscript(transcript);
		saveUserList();
	}

	// NEED TO CHECK THIS.
	private void saveSchoolList() {
		try {
			FileOutputStream fos = new FileOutputStream(ROOTDIR + "\\schools");
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(schools);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadSchoolList() {
		try {
			FileInputStream fis = new FileInputStream(ROOTDIR + "\\schools");
			ObjectInputStream in = new ObjectInputStream(fis);
			schools = (ArrayList<School>) in.readObject();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
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
	private void saveUserList() {
		try {
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
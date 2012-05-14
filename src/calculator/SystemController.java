/*TODO
 * 
 * -add activeSemester and get rid of references to "previousFrame" in all panel classes.
 * 
 */

package calculator;

import java.io.*;

import java.util.HashMap;
import java.util.HashSet;

public class SystemController {

	protected final String ROOTDIR = System.getenv("APPDATA") + "\\GPACalcJava";
	protected User activeUser;
	protected School activeSchool;
	protected Course activeCourse;
	protected Semester activeSemester;
	protected HashMap<String, User> users;
	// List of known schools.
	protected HashMap<String, School> schools;
	protected final RootFrame rootFrame;
	protected final HashSet<String> gradeTypes = new HashSet<String>();

	public SystemController() {

		logOut(); // sets to null.
		users = new HashMap<String, User>();
		schools = new HashMap<String, School>();
		populateGradeTypes();

		if (!new File(ROOTDIR).exists()) {
			new File(ROOTDIR).mkdir();
			populateSchools();
		} else {
			loadSchoolList();
			loadUserList();
		}

		rootFrame = new RootFrame(this);
	}

	// Needs to be called every time program runs as this list isn't saved.
	private void populateGradeTypes() {
		gradeTypes.add("Quiz");
		gradeTypes.add("Test");
		gradeTypes.add("Final Exam");
		gradeTypes.add("Midterm");
		gradeTypes.add("Homework");
		gradeTypes.add("Project");
		gradeTypes.add("Lab");
		gradeTypes.add("BlackBoard Post");
		gradeTypes.add("Class Participation");
		gradeTypes.add("Essay");
	}

	// Populates list of known schools. Only run on the first execution of
	// program. Probably should be part of the installer if one is ever made...
	private void populateSchools() {
		schools.put("GMU", new School("GMU", new GradingScale(true)));
		schools.put("UTSA", new School("UTSA", new GradingScale(true)));
		schools.put("TNCC", new School("TNCC", new GradingScale(false)));
		schools.put("ODU", new School("ODU", new GradingScale(false)));
		saveSchoolList();
	}

	protected boolean addSchool(String name, GradingScale gradingScale) {
		if (schools.containsKey(name) || name.equals(""))
			return false;
		schools.put(name, new School(name, gradingScale));
		saveSchoolList();
		return true;
	}

	protected void removeSchool(String name) {
		// Should probably check it's valid.
		schools.remove(name);
	}

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

	@SuppressWarnings("unchecked")
	private void loadSchoolList() {
		try {
			FileInputStream fis = new FileInputStream(ROOTDIR + "\\schools");
			ObjectInputStream in = new ObjectInputStream(fis);
			schools = (HashMap<String, School>) in.readObject();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Returns true is successfully registered user, false otherwise.
	protected boolean register(String username, String password) {
		// returns false if username not available
		if (users.containsKey(username))
			return false;
		User user = new User(username, password);
		users.put(username, user); // add new user to persistent user list.
		activeUser = user;
		saveUserList();
		return true;
	}

	// Returns true if valid user/pass for login, false otherwise.
	protected boolean login(String username, String password) {
		if (users.containsKey(username)) {
			User user = users.get(username);
			if (user.checkPassword(password)) {
				activeUser = user;
				return true;
			}
		}
		// Invalid user/pass.
		return false;
	}

	protected void logOut() {
		activeUser = null;
		activeSchool = null;
		activeCourse = null;
		activeSemester = null;
	}

	// Can be used anywhere but ALWAYS called on exit.
	protected void saveUserList() {
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
			users = (HashMap<String, User>) in.readObject();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected double calcSemseterGPA() {
		double qualityPoints = 0;
		for (Course course : activeSemester.getCourses().values()) {
			qualityPoints += activeSchool.getGradingScale()
					.getGradingScaleMap().get(course.getFinalGrade())
					* course.getCreditHours();
		}
		activeSemester.setGPA(qualityPoints
				/ activeSemester.getTotalHoursAttempted());
		saveUserList();
		return activeSemester.getGPA();
	}

	protected double calcTranscriptGPA() {
		double total = 0;
		for (Semester semester : activeUser
				.getTranscript(activeSchool.getName()).getSemesters().values()) {
			total += semester.getGPA();
		}
		activeUser.getTranscript(activeSchool.getName()).setGPA(
				total
						/ activeUser.getTranscript(activeSchool.getName())
								.getSemesters().size());
		saveUserList();
		return activeUser.getTranscript(activeSchool.getName()).getGPA();
	}
}
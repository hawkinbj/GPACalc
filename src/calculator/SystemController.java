/*TODO
 * 
 * - Implement a rightClick JMenu for removing elements as opposed to have a popup dialog 
 * 		 (in other words, get rid of all JDialogs)!!!
 * 
 * MAYBE:
 * - Add a "simulate semester" feature/button to CoursePanel (enter num of hours and gpa)
 *
 * 
 */

package calculator;

import java.io.*;

import java.util.HashMap;
import java.util.HashSet;

public class SystemController {

	private final String ROOTDIR = System.getenv("APPDATA") + "\\GPACalcJava";
	protected User activeUser;
	protected School activeSchool; // get rid of this.
	protected Course activeCourse;
	protected Semester activeSemester;
	protected Transcript activeTranscript;
	protected HashMap<String, User> users;
	protected HashMap<String, School> schools;
	protected final RootFrame rootFrame = new RootFrame(this);
	protected final HashSet<String> gradeTypes = new HashSet<String>();

	public SystemController() {

		users = new HashMap<String, User>();
		schools = new HashMap<String, School>();
		populateGradeTypes();

		// Program has never been run. Create directory.
		if (!new File(ROOTDIR).exists()) {
			new File(ROOTDIR).mkdir();
			populateSchools();
			saveUserList();
		} else {
			loadSchoolList();
			loadUserList();
		}

		loadActiveUser();

		// Bypass login.
		if (activeUser != null && activeUser.getRememberLoginInfo()) {
			saveActiveUser();
			rootFrame.addPanel(new MainMenuPanel(this), new GUIPanel(this));
		} else {
			rootFrame.addPanel(new WelcomePanel(this), new GUIPanel(this));
		}
	}

	protected void saveActiveUser() {
		try {
			FileOutputStream fos = new FileOutputStream(ROOTDIR
					+ "\\activeUser");
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(activeUser);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		saveUserList();
	}

	private void loadActiveUser() {
		// CAN PROB JUST USE ACTIVEUSER
		User user = null;
		try {
			if (new File(ROOTDIR + "\\activeUser").exists()) {
				FileInputStream fis = new FileInputStream(ROOTDIR
						+ "\\activeUser");
				ObjectInputStream in = new ObjectInputStream(fis);
				user = (User) in.readObject();
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Return the User object from the list to get saved data.
		if (user != null)
			activeUser = users.get(user.getUsername());
		else
			activeUser = null;
	}

	protected void logOut() {
		activeUser.setRememberLoginInfo(false);
		saveActiveUser();
		activeUser = null;
		activeSchool = null;
		activeCourse = null;
		activeSemester = null;
		activeTranscript = null;
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
	protected void loadUserList() {
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
			// Return -1 if any of the courses' final grades aren't set.
			if (course.getFinalGrade().equals("N/A")) {
				activeSemester.setGPA(-1);
				return -1;
			}
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
		double gpaTotal = 0;
		int numOfSemesters = 0;
		for (Semester semester : activeTranscript.getSemesters().values()) {
			// Don't include incomplete semesters in GPA calculation.
			if (semester.getGPA() == -1)
				continue;
			else {
				numOfSemesters += 1;
				gpaTotal += semester.getGPA();
			}
		}
		activeTranscript.setGPA(gpaTotal / numOfSemesters);
		saveUserList();
		return activeTranscript.getGPA();
	}
}
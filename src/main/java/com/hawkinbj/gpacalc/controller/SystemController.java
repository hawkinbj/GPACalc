package com.hawkinbj.gpacalc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.hawkinbj.gpacalc.model.Course;
import com.hawkinbj.gpacalc.model.GUIPanel;
import com.hawkinbj.gpacalc.model.GradingScale;
import com.hawkinbj.gpacalc.model.School;
import com.hawkinbj.gpacalc.model.Semester;
import com.hawkinbj.gpacalc.model.Transcript;
import com.hawkinbj.gpacalc.model.User;
import com.hawkinbj.gpacalc.view.MainMenuPanel;
import com.hawkinbj.gpacalc.view.RootFrame;
import com.hawkinbj.gpacalc.view.WelcomePanel;

public class SystemController {
	private final String ROOTDIR = System.getenv("APPDATA") + "\\GPACalcJava";
	private User activeUser;
	private School activeSchool;
	private Course activeCourse;
	private Semester activeSemester;
	private Transcript activeTranscript;
	private Map<String, User> users;
	private Map<String, School> schools;
	private Map<String, String> majors;
	private final RootFrame rootFrame = new RootFrame(this);
	private final Set<String> gradeTypes = new HashSet<String>();

	public SystemController() {
		this.users = new HashMap<String, User>();
		this.schools = new HashMap<String, School>();
		this.majors = new HashMap<String, String>();

		this.populateGradeTypes();
		this.populateMajors();

		if (!new File(this.ROOTDIR).exists()) {
			new File(this.ROOTDIR).mkdir();
			populateSchools();
			saveUserList();
		} else {
			loadSchoolList();
			loadUserList();
		}

		loadActiveUser();

		if ((this.activeUser != null)
				&& (this.activeUser.getRememberLoginInfo())) {
			saveActiveUser();
			this.rootFrame
					.addPanel(new MainMenuPanel(this), new GUIPanel(this));
		} else {
			this.rootFrame.addPanel(new WelcomePanel(this), new GUIPanel(this));
		}
	}

	public void saveActiveUser() {
		try {
			FileOutputStream fos = new FileOutputStream(this.ROOTDIR
					+ "\\activeUser");
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(this.activeUser);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		saveUserList();
	}

	private void loadActiveUser() {
		User user = null;
		try {
			if (new File(this.ROOTDIR + "\\activeUser").exists()) {
				FileInputStream fis = new FileInputStream(this.ROOTDIR
						+ "\\activeUser");
				ObjectInputStream in = new ObjectInputStream(fis);
				user = (User) in.readObject();
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (user != null)
			this.activeUser = ((User) this.users.get(user.getUsername()));
		else
			this.activeUser = null;
	}

	public void logOut() {
		this.activeUser.setRememberLoginInfo(false);
		saveActiveUser();
		this.activeUser = null;
		this.activeSchool = null;
		this.activeCourse = null;
		this.activeSemester = null;
		this.activeTranscript = null;
	}

	private void populateGradeTypes() {
		this.gradeTypes.add("Quiz");
		this.gradeTypes.add("Test");
		this.gradeTypes.add("Final Exam");
		this.gradeTypes.add("Midterm");
		this.gradeTypes.add("Homework");
		this.gradeTypes.add("Project");
		this.gradeTypes.add("Lab");
		this.gradeTypes.add("BlackBoard Post");
		this.gradeTypes.add("Class Participation");
		this.gradeTypes.add("Essay");
	}

	private void populateMajors() {
		this.majors = new HashMap<String, String>();
		this.majors.put("Computer Science", "CS");
		this.majors.put("Biology", "BIO");
		this.majors.put("Math", "MATH");
	}

	private void populateSchools() {
		this.schools.put("GMU", new School("GMU", new GradingScale(true)));
		this.schools.put("UTSA", new School("UTSA", new GradingScale(true)));
		this.schools.put("TNCC", new School("TNCC", new GradingScale(false)));
		this.schools.put("ODU", new School("ODU", new GradingScale(false)));
		saveSchoolList();
	}

	public boolean addSchool(String name, GradingScale gradingScale) {
		if ((this.schools.containsKey(name)) || (name.equals("")))
			return false;
		this.schools.put(name, new School(name, gradingScale));
		saveSchoolList();
		return true;
	}

	protected void removeSchool(String name) {
		this.schools.remove(name);
	}

	private void saveSchoolList() {
		try {
			FileOutputStream fos = new FileOutputStream(this.ROOTDIR
					+ "\\schools");
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(this.schools);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void loadSchoolList() {
		try {
			FileInputStream fis = new FileInputStream(this.ROOTDIR
					+ "\\schools");
			ObjectInputStream in = new ObjectInputStream(fis);
			this.schools = ((HashMap<String, School>) in.readObject());
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean register(String username, String password) {
		if (this.users.containsKey(username))
			return false;
		User user = new User(username, password);
		this.users.put(username, user);
		this.activeUser = user;
		saveUserList();
		return true;
	}

	public boolean login(String username, String password) {
		if (this.users.containsKey(username)) {
			User user = (User) this.users.get(username);
			if (user.checkPassword(password)) {
				this.activeUser = user;
				return true;
			}
		}

		return false;
	}

	public void saveUserList() {
		System.out.println("Saving user list");
		try {
			FileOutputStream fos = new FileOutputStream(this.ROOTDIR
					+ "\\userlist");
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(this.users);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	protected void loadUserList() {
		try {
			FileInputStream fis = new FileInputStream(this.ROOTDIR
					+ "\\userlist");
			ObjectInputStream in = new ObjectInputStream(fis);
			this.users = ((HashMap<String, User>) in.readObject());
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public double calcSemseterGPA() {
		double qualityPoints = 0;

		for (Course course : activeSemester.getCourses().values()) {
			if (course.getFinalGrade().equals("N/A")) {
				activeSemester.setGPA(-1);
				return -1;
			}

			qualityPoints = qualityPoints
					+ ((Double) activeSchool.getGradingScale()
							.getGradingScaleMap().get(course.getFinalGrade()))
							.doubleValue() * course.getCreditHours();
		}

		double gpa = (qualityPoints / activeSemester.getTotalHoursAttempted());

		activeSemester.setGPA(gpa);
		saveUserList();

		return gpa;
	}

	public double calcTranscriptGPA() {
		double gpaTotal = 0;
		int numOfSemesters = 0;

		for (Semester semester : activeTranscript.getSemesters().values()) {
			if (semester.getGPA() != -1) {
				numOfSemesters++;
				gpaTotal += semester.getGPA();
			}
		}

		double gpa = (gpaTotal / numOfSemesters);

		activeTranscript.setGPA(gpa);
		saveUserList();

		return gpa;
	}

	public double calcMajorGPA() {
		double qualityPoints = 0;
		double totalCreditHours = 0;

		System.out.println(activeUser.getMajor());

		for (Semester semester : activeTranscript.getSemesters().values()) {
			for (Course course : semester.getCourses().values()) {

				String activeUserMajor = majors.get(activeUser.getMajor());

				if (activeUserMajor != null
						&& !course.getFinalGrade().equals("N/A")
						&& course.getCourseName().startsWith(activeUserMajor)) {

					qualityPoints = qualityPoints
							+ ((Double) activeSchool.getGradingScale()
									.getGradingScaleMap()
									.get(course.getFinalGrade())).doubleValue()
							* course.getCreditHours();

					totalCreditHours += course.getCreditHours();
				}
			}
		}

		double gpa = (qualityPoints / totalCreditHours);

		System.out.println("qualityPoints: " + qualityPoints);
		System.out.println("totalCreditHours: " + totalCreditHours);
		System.out.println("gpa: " + gpa);

		activeTranscript.setMajorGPA(gpa);
		saveUserList();

		return gpa;

	}

	public Semester getActiveSemester() {
		return activeSemester;
	}

	public void setActiveSemester(Semester activeSemester) {
		this.activeSemester = activeSemester;
	}

	public Course getActiveCourse() {
		return activeCourse;
	}

	public void setActiveCourse(Course activeCourse) {
		this.activeCourse = activeCourse;
	}

	public School getActiveSchool() {
		return activeSchool;
	}

	public void setActiveSchool(School activeSchool) {
		this.activeSchool = activeSchool;
	}

	public Set<String> getGradeTypes() {
		return gradeTypes;
	}

	public RootFrame getRootFrame() {
		return rootFrame;
	}

	public User getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(User activeUser) {
		this.activeUser = activeUser;
	}

	public Transcript getActiveTranscript() {
		return activeTranscript;
	}

	public void setActiveTranscript(Transcript activeTranscript) {
		this.activeTranscript = activeTranscript;
	}

	public Map<String, String> getMajors() {
		return majors;
	}

	public Map<String, School> getSchools() {
		return schools;
	}

	public void setSchools(Map<String, School> schools) {
		this.schools = schools;
	}

	public Map<String, User> getUsers() {
		return users;
	}

	public void setUsers(Map<String, User> users) {
		this.users = users;
	}
}
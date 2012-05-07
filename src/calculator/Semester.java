package calculator;

import java.io.Serializable;
import java.util.HashMap;

// Not sure about extending...
public class Semester implements Serializable {

	private static final long serialVersionUID = -7113939304071168721L;
	/* Stored in Transcript - it's a list of Course objects */
	private HashMap<String, Course> courses;
	private String semesterName; // e.g. - Spring 2012
	private String schoolName;

	public Semester(String semesterName) {
		courses = new HashMap<String, Course>();
		this.semesterName = semesterName;
	}

	protected String getSemesterName() {
		return semesterName;
	}

	protected void addCourse(String courseName, Course course) {
		courses.put(courseName, course);
	}

	protected void removeCourse(String courseName) {
		courses.remove(courseName);
	}

	protected HashMap<String, Course> getCourses() {
		return courses;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

}

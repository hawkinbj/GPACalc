package calculator;

import java.io.Serializable;
import java.util.HashMap;

public class Semester implements Serializable {

	private static final long serialVersionUID = -7113939304071168721L;
	/* Stored in Transcript - it's a list of Course objects */
	private HashMap<String, Course> courses;
	private String term;
	private String year; // yyyy
	private String schoolName;
	private double GPA;

	public Semester(String term, String year) {
		courses = new HashMap<String, Course>();
		this.term = term;
		this.year = year;
		GPA = -1;
	}

	protected String getYear() {
		return year;
	}

	protected void setYear(String year) {
		this.year = year;
	}

	protected String getTerm() {
		return term;
	}

	protected void setTerm(String term) {
		this.term = term;
	}

	protected void addCourse(String courseName, Course course) {
		courses.put(courseName, course);
	}

	protected void setGPA(double GPA) {
		this.GPA = GPA;
	}

	protected double getGPA() {
		return GPA;
	}

	protected int getTotalHoursAttempted() {
		int total = 0;
		for (Course course : courses.values()) {
			total += course.getCreditHours();
		}
		return total;
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

	protected void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String toString() {
		// 2012 Spring
		return year + " " + term;
	}
}

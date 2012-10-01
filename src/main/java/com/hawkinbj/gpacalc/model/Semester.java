package com.hawkinbj.gpacalc.model;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Semester implements Serializable {
	private static final long serialVersionUID = -7113939304071168721L;

	private Map<String, Course> courses;

	private String term;

	private String year;

	private String schoolName;

	private double GPA;

	public Semester(String term, String year) {
		this.courses = new TreeMap<String, Course>();
		this.term = term;
		this.year = year;
		this.GPA = -1.0D;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public void addCourse(String courseName, Course course) {
		courses.put(courseName, course);
	}

	public void setGPA(double GPA) {
		this.GPA = GPA;
	}

	public double getGPA() {
		return GPA;
	}

	public int getTotalHoursAttempted() {
		int total = 0;

		for (Course course : courses.values()) {
			total += course.getCreditHours();
		}

		return total;
	}

	public void removeCourse(String courseName) {
		courses.remove(courseName);
	}

	public Map<String, Course> getCourses() {
		return courses;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String toString() {
		return year + " " + term;
	}
}
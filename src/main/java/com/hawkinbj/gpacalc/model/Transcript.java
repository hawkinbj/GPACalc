package com.hawkinbj.gpacalc.model;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Transcript implements Serializable {
	private static final long serialVersionUID = 6258413269754654426L;
	private Map<String, Semester> semesters;
	private School school;
	private double GPA;

	public Transcript(School school) {
		this.school = school;
		this.semesters = new TreeMap<String, Semester>();
		this.GPA = 0;
	}

	public School getSchool() {
		return this.school;
	}

	public void addSemester(String semesterName, Semester semester) {
		this.semesters.put(semesterName, semester);
		((Semester) this.semesters.get(semesterName)).setSchoolName(this.school
				.getName());
	}

	public void removeSemester(String semesterName) {
		this.semesters.remove(semesterName);
	}

	public Map<String, Semester> getSemesters() {
		return this.semesters;
	}

	public double getCreditHoursCompleted() {
		double totalHours = 0;

		for (Semester s : semesters.values()) {
			totalHours += s.getTotalHoursAttempted();
		}

		return totalHours;
	}

	public double getGPA() {
		return this.GPA;
	}

	public void setGPA(double GPA) {
		this.GPA = GPA;
	}
}
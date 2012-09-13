package com.hawkinbj.gpacalc.model;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Course implements Comparable<Course>, Serializable {
	private static final long serialVersionUID = -4379223235756969208L;
	private String courseName;
	private Map<String, Grade> grades;
	private int creditHours;
	private String finalGrade;
	private boolean weighted;

	public Course(String courseName, int creditHours) {
		setCourseName(courseName);
		this.creditHours = creditHours;
		this.grades = new TreeMap<String, Grade>();
		this.finalGrade = "N/A";
	}

	public double getTotalPointsEarned() {
		double total = 0.0D;
		for (Grade grade : this.grades.values()) {
			total += grade.getTotalEarned();
		}
		return total;
	}

	public int getTotalPointsPossible() {
		int total = 0;
		for (Grade grade : this.grades.values()) {
			total += grade.getTotalPossible();
		}
		return total;
	}

	public double getWeightedTotalPointsEarned() {
		double total = 0.0D;
		for (Grade grade : this.grades.values()) {
			total += grade.getTotalEarned() * grade.getPercentWeight();
		}
		return total / 100.0D;
	}

	public void addGrade(String type, Grade grade) {
		this.grades.put(type, grade);
	}

	public void removeGrade(String type) {
		this.grades.remove(type);
	}

	public Map<String, Grade> getGrades() {
		return this.grades;
	}

	public String getCourseName() {
		return this.courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getCreditHours() {
		return this.creditHours;
	}

	public void setCreditHours(int creditHours) {
		this.creditHours = creditHours;
	}

	public String getFinalGrade() {
		if (this.finalGrade == null) {
			return "N/A";
		}
		return this.finalGrade;
	}

	public void setFinalGrade(String finalGrade) {
		this.finalGrade = finalGrade;
	}

	public boolean getWeighted() {
		return this.weighted;
	}

	public void setWeighted(boolean weighted) {
		this.weighted = weighted;
	}

	public String toString() {
		return this.courseName;
	}

	public int compareTo(Course c) {
		return getCourseName().compareTo(c.getCourseName());
	}
}
/*******************************************************************************
 * Copyright (c) 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
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
		this.setCourseName(courseName);
		this.creditHours = creditHours;

		grades = new TreeMap<String, Grade>();

		finalGrade = "N/A";
	}

	public double getTotalPointsEarned() {
		double total = 0;

		for (Grade grade : grades.values()) {
			total += grade.getTotalEarned();
		}

		return total;
	}

	public int getTotalPointsPossible() {
		int total = 0;

		for (Grade grade : grades.values()) {
			total += grade.getTotalPossible();
		}

		return total;
	}

	public double getWeightedTotalPointsEarned() {
		double total = 0;

		for (Grade grade : grades.values()) {
			total += grade.getTotalEarned() * grade.getPercentWeight();
		}

		return total / 100;
	}

	public void addGrade(String type, Grade grade) {
		grades.put(type, grade);
	}

	public void removeGrade(String type) {
		grades.remove(type);
	}

	public Map<String, Grade> getGrades() {
		return grades;
	}

	public String getCourseName() {
		return courseName;
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
		if (finalGrade == null) {
			return "N/A";
		}
		return finalGrade;
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
		return courseName;
	}

	public int compareTo(Course c) {
		return getCourseName().compareTo(c.getCourseName());
	}
}
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

public class Transcript implements Serializable {
	private static final long serialVersionUID = 6258413269754654426L;

	private Map<String, Semester> semesters;

	private School school;

	private double GPA;

	private double majorGPA;

	public Transcript(School school) {
		this.school = school;
		this.semesters = new TreeMap<String, Semester>();
		this.GPA = 0;
	}

	public School getSchool() {
		return school;
	}

	public void addSemester(String semesterName, Semester semester) {
		semesters.put(semesterName, semester);

		((Semester) semesters.get(semesterName))
				.setSchoolName(school.getName());
	}

	public void removeSemester(String semesterName) {
		semesters.remove(semesterName);
	}

	public Map<String, Semester> getSemesters() {
		return semesters;
	}

	public double getCreditHoursCompleted() {
		double totalHours = 0;

		for (Semester s : semesters.values()) {
			totalHours += s.getTotalHoursAttempted();
		}

		return totalHours;
	}

	public double getGPA() {
		return GPA;
	}

	public void setGPA(double GPA) {
		this.GPA = GPA;
	}

	public double getMajorGPA() {
		return majorGPA;
	}

	public void setMajorGPA(double majorGPA) {
		this.majorGPA = majorGPA;
	}
}
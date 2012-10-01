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
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class User implements Serializable {
	private static final long serialVersionUID = -5899680116199753971L;
	private String username;
	private String password;
	private String major;
	private boolean rememberLoginInfo;
	private Map<String, Transcript> transcripts;
	protected Map<String, Course> coursesToTake;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.rememberLoginInfo = false;
		this.transcripts = new HashMap<String, Transcript>();
		this.coursesToTake = new TreeMap<String, Course>();
	}

	public Map<String, Course> getCoursesToTake() {
		if (this.coursesToTake == null) {
			this.coursesToTake = new TreeMap<String, Course>();
		}
		return this.coursesToTake;
	}

	public void setCoursesToTake(HashMap<String, Course> coursesToTake) {
		this.coursesToTake = coursesToTake;
	}

	public void addCourseToTake(Course c) {
		if (this.coursesToTake == null) {
			this.coursesToTake = new TreeMap<String, Course>();
		}

		this.coursesToTake.put(c.getCourseName(), c);
	}

	public void removeCourseToTake(String c) {
		if (this.coursesToTake == null) {
			this.coursesToTake = new TreeMap<String, Course>();
		}
		this.coursesToTake.remove(c);
	}

	public String getUsername() {
		return this.username;
	}

	public boolean getRememberLoginInfo() {
		return this.rememberLoginInfo;
	}

	public void setRememberLoginInfo(boolean rememberLoginInfo) {
		this.rememberLoginInfo = rememberLoginInfo;
	}

	public boolean addTranscript(String schoolName, Transcript t) {
		if (this.transcripts.keySet().contains(schoolName))
			return false;
		this.transcripts.put(schoolName, t);
		return true;
	}

	public void removeTranscript(String schoolName) {
		this.transcripts.remove(schoolName);
	}

	public Transcript getTranscript(String schoolName) {
		return (Transcript) this.transcripts.get(schoolName);
	}

	public Map<String, Transcript> getTranscripts() {
		return this.transcripts;
	}

	public boolean checkPassword(String pass) {
		if (pass.equals(this.password)) {
			return true;
		}
		return false;
	}

	public String toString() {
		return this.username;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}
}
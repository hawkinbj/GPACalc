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

public class Grade implements Serializable {
	private static final long serialVersionUID = 9191538844602888225L;

	private String type;

	private int pointsPossiblePer;

	double totalEarned;

	private int percentWeight;

	private boolean dropLowest;

	private int numOfGrades;

	private boolean isExtraCreditOffered;

	public Grade(String type, int pointsPossiblePer, double totalEarned,
			int numOfGrades, int percentWeight, boolean dropLowest) {

		this.setType(type);
		this.setPointsPossiblePer(pointsPossiblePer);
		this.setTotalEarned(totalEarned);
		this.setDropLowest(dropLowest);
	}

	public int getTotalPossible() {
		int total = this.numOfGrades * pointsPossiblePer;

		if (this.dropLowest)
			total -= pointsPossiblePer;
		return total;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPercentWeight() {
		return percentWeight;
	}

	public void setPercentWeight(int percentWeight) {
		this.percentWeight = percentWeight;
	}

	public int getPointsPossiblePer() {
		return pointsPossiblePer;
	}

	public void setPointsPossiblePer(int pointsPossiblePer) {
		this.pointsPossiblePer = pointsPossiblePer;
	}

	public double getTotalEarned() {
		return this.totalEarned;
	}

	public void setTotalEarned(double totalEarned) {
		this.totalEarned = totalEarned;
	}

	public boolean getDropLowest() {
		return this.dropLowest;
	}

	public void setDropLowest(boolean dropLowest) {
		this.dropLowest = dropLowest;
	}

	public int getNumOfGrades() {
		return this.numOfGrades;
	}

	public void setNumOfGrades(int numOfGrades) {
		this.numOfGrades = numOfGrades;
	}

	public boolean isExtraCreditOffered() {
		return isExtraCreditOffered;
	}

	public void setExtraCreditOffered(boolean isExtraCreditOffered) {
		this.isExtraCreditOffered = isExtraCreditOffered;
	}
}
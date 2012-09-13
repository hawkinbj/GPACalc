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

	public Grade(String type, int pointsPossiblePer, double totalEarned,
			int numOfGrades, int percentWeight, boolean dropLowest) {
		setType(type);
		setPointsPossiblePer(pointsPossiblePer);
		setTotalEarned(totalEarned);
		setDropLowest(dropLowest);
	}

	public int getTotalPossible() {
		int total = this.numOfGrades * this.pointsPossiblePer;
		if (this.dropLowest)
			total -= this.pointsPossiblePer;
		return total;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPercentWeight() {
		return this.percentWeight;
	}

	public void setPercentWeight(int percentWeight) {
		this.percentWeight = percentWeight;
	}

	public int getPointsPossiblePer() {
		return this.pointsPossiblePer;
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
}
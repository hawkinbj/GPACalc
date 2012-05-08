package calculator;

import java.io.Serializable;

public class Grade implements Serializable {

	private static final long serialVersionUID = 9191538844602888225L;
	private String type;
	private int pointsPossiblePer;
	private int totalEarned;
	private int percentWeight;
	private boolean dropLowest;
	private int numOfGrades;

	public Grade(String type, int pointsPossiblePer, int totalEarned,
			int numOfGrades, int percentWeight, boolean dropLowest) {
		this.setType(type);
		this.setPointsPossiblePer(pointsPossiblePer);
		this.setTotalEarned(totalEarned);
		this.setDropLowest(dropLowest);
	}

	protected int getTotalPossible() {
		int total = numOfGrades * pointsPossiblePer;
		if (dropLowest)
			total = total - pointsPossiblePer;
		return total;
	}

	protected String getType() {
		return type;
	}

	protected void setType(String type) {
		this.type = type;
	}

	protected int getPercentWeight() {
		return percentWeight;
	}

	protected void setPercentWeight(int percentWeight) {
		this.percentWeight = percentWeight;
	}

	protected int getPointsPossiblePer() {
		return pointsPossiblePer;
	}

	protected void setPointsPossiblePer(int pointsPossiblePer) {
		this.pointsPossiblePer = pointsPossiblePer;
	}

	protected int getTotalEarned() {
		return totalEarned;
	}

	protected void setTotalEarned(int totalEarned) {
		this.totalEarned = totalEarned;
	}

	protected boolean getDropLowest() {
		return dropLowest;
	}

	protected void setDropLowest(boolean dropLowest) {
		this.dropLowest = dropLowest;
	}

	protected int getNumOfGrades() {
		return numOfGrades;
	}

	protected void setNumOfGrades(int numOfGrades) {
		this.numOfGrades = numOfGrades;
	}

}

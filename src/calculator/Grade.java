package calculator;

import java.io.Serializable;

public class Grade implements Serializable{
	
	private static final long serialVersionUID = 9191538844602888225L;
	private String type;
	private int maxPoints;
	private int earnedPoints;

	public Grade(String name, int maxPoints, int earnedPoints) {
		this.setType(name);
		this.setMaxPoints(maxPoints);
		this.setEarnedPoints(earnedPoints);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getEarnedPoints() {
		return earnedPoints;
	}

	public void setEarnedPoints(int earnedPoints) {
		this.earnedPoints = earnedPoints;
	}

	public int getMaxPoints() {
		return maxPoints;
	}

	public void setMaxPoints(int maxPoints) {
		this.maxPoints = maxPoints;
	}

}

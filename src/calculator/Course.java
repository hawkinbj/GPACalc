package calculator;

import java.io.Serializable;
import java.util.HashMap;

public class Course implements Serializable {

	private static final long serialVersionUID = -4379223235756969208L;
	private String courseName;
	private HashMap<String, Grade> grades;
	private int creditHours;
	private String finalGrade;
	// Are the grades weighted? (i.e. - 30%)
	private boolean weighted;

	public Course(String courseName, int creditHours) {
		this.setCourseName(courseName);
		this.creditHours = creditHours;
		grades = new HashMap<String, Grade>();
		finalGrade = "N/A";
	}

	protected double getTotalPointsEarned() {
		double total = 0;
		for (Grade grade : grades.values()) {
			total += grade.getTotalEarned();
		}
		return total;
	}

	protected int getTotalPointsPossible() {
		int total = 0;
		for (Grade grade : grades.values()) {
			total += grade.getTotalPossible();
		}
		return total;
	}

	protected double getWeightedTotalPointsEarned() {
		double total = 0;
		for (Grade grade : grades.values()) {
			total += (grade.getTotalEarned() * grade.getPercentWeight());
		}
		return total / 100;
	}

	protected void addGrade(String type, Grade grade) {
		grades.put(type, grade);
	}

	protected void removeGrade(String type) {
		grades.remove(type);
	}

	protected HashMap<String, Grade> getGrades() {
		return grades;
	}

	protected String getCourseName() {
		return courseName;
	}

	protected void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	protected int getCreditHours() {
		return creditHours;
	}

	protected void setCreditHours(int creditHours) {
		this.creditHours = creditHours;
	}

	protected String getFinalGrade() {
		if (finalGrade == null) {
			return "N/A";
		}
		return finalGrade;
	}

	protected void setFinalGrade(String finalGrade) {
		this.finalGrade = finalGrade;
	}

	protected boolean getWeighted() {
		return weighted;
	}

	protected void setWeighted(boolean weighted) {
		this.weighted = weighted;
	}
	
	public String toString(){
		return courseName;
	}
}

package calculator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Course implements Serializable {

	private static final long serialVersionUID = -4379223235756969208L;
	private String courseName;
	private HashMap<String, Grade> grades;
	private int creditHours;
	private String finalGrade;
	// Each course has a default list of gradetypes. Can be edited.
	private ArrayList<String> gradeTypes;

	public Course(String courseName, int creditHours) {
		this.setCourseName(courseName);
		this.creditHours = creditHours;
		grades = new HashMap<String, Grade>();
		gradeTypes = new ArrayList<String>();
		populateGradeTypes();
	}

	private void populateGradeTypes() {
		gradeTypes.add("Quiz");
		gradeTypes.add("Test");
		gradeTypes.add("Final Exam");
		gradeTypes.add("Midterm");
		gradeTypes.add("Homework");
		gradeTypes.add("Project");
		gradeTypes.add("Lab");
		gradeTypes.add("BlackBoard Post");
		gradeTypes.add("Class Participation");
		gradeTypes.add("Essay");
	}

	protected void addGradeType(String name) {
		gradeTypes.add(name);
	}

	public ArrayList<String> getGradeTypes() {
		return gradeTypes;
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
		return total;
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
}

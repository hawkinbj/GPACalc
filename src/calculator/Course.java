package calculator;

import java.io.Serializable;
import java.util.HashMap;

public class Course implements Serializable {

	private static final long serialVersionUID = -4379223235756969208L;
	private String courseName;
	private HashMap<String, Grade> grades;
	private int creditHours;
	private String finalGrade;

	public Course(String courseName, int creditHours) {
		this.setCourseName(courseName);
		this.creditHours = creditHours;
		grades = new HashMap<String, Grade>();

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
		return finalGrade;
	}

	public void setFinalGrade(String finalGrade) {
		this.finalGrade = finalGrade;
	}

}

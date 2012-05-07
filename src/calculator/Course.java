package calculator;

import java.io.Serializable;
import java.util.HashMap;

public class Course implements Serializable {

	private static final long serialVersionUID = -4379223235756969208L;
	private String courseName;
	private HashMap<String, Grade> grades;

	public Course(String courseName) {
		this.setCourseName(courseName);
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

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

}

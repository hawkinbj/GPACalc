package calculator;

import java.io.Serializable;
import java.util.ArrayList;

// Not sure about extending...
public class Semester implements Serializable {
	/* Stored in Transcript - it's a list of Course objects */
	private ArrayList<Course> courses;
	private String semester; // Spring 12

	public Semester(String semester) {
		courses = new ArrayList<Course>();
		this.semester = semester;
	}

	protected String getSemesterName() {
		return semester;
	}

	protected ArrayList<Course> getCourses() {
		return courses;
	}

}

/* 
 * Class is scalable in that it's intended to accommodate multiple schools. 
 * Defaulting to one for now. 
 */

package calculator;

import java.io.Serializable;
import java.util.HashMap;

public class Transcript implements Serializable {

	private static final long serialVersionUID = 6258413269754654426L;
	private HashMap<String, Semester> semesters;
	private School school;
	private double GPA;

	public Transcript(School school) {
		this.school = school;
		semesters = new HashMap<String, Semester>();
		GPA = 0;
	}

	protected School getSchool() {
		return school;
	}

	protected void addSemester(String semesterName, Semester semester) {
		semesters.put(semesterName, semester);
		semesters.get(semesterName).setSchoolName(school.getName());
	}

	protected void removeSemester(String semesterName) {
		semesters.remove(semesterName);
	}

	protected HashMap<String, Semester> getSemesters() {
		return semesters;
	}

	public double getGPA() {
		return GPA;
	}

	public void setGPA(double GPA) {
		this.GPA = GPA;
	}
}

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
	private String schoolName;

	public Transcript(String schoolName) {
		this.schoolName = schoolName;
		semesters = new HashMap<String, Semester>();
	}

	protected String getSchoolName() {
		return schoolName;
	}

	protected void addSemester(String semesterName, Semester semester) {
		semesters.put(semesterName, semester);
		semesters.get(semesterName).setSchoolName(schoolName);
	}

	protected void removeSemester(String semesterName) {
		semesters.remove(semesterName);
	}

	protected HashMap<String, Semester> getSemesters() {
		return semesters;
	}
}

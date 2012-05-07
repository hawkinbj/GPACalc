/* 
 * Class is scalable in that it's intended to accommodate multiple schools. 
 * Defaulting to one for now. 
 */

package calculator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Transcript implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6258413269754654426L;
	private HashMap<String, Semester> transcript;
	private String school;

	// private HashMap<String, int[]> gradingScale;

	public Transcript() {
		transcript = new HashMap<String, Semester>();
		// gradingScale = new HashMap<String, int[]>();
	}

	protected String getSchoolName() {
		return school;
	}

	protected HashMap<String, Semester> getSemesters() {
		return transcript;
	}
}

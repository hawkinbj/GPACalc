/* 
 * Class is scalable in that it's intended to accommodate multiple schools. 
 * Defaulting to one for now. 
 */

package calculator;

import java.io.Serializable;
import java.util.ArrayList;

public class Transcript implements Serializable {

	private ArrayList<Semester> transcript;
	private String school;

	// private HashMap<String, int[]> gradingScale;

	public Transcript(String school) {
		this.school = school;
		transcript = new ArrayList<Semester>();
		// gradingScale = new HashMap<String, int[]>();
	}
}

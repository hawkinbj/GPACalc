package calculator;

import java.io.Serializable;

public class School implements Serializable {

	private static final long serialVersionUID = 8843646586868126972L;
	private String name;
	private GradingScale gradingScale;

	public School(String name, GradingScale gradingScale) {
		this.name = name;
		this.gradingScale = gradingScale;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GradingScale getGradingScale() {
		return gradingScale;
	}

	public void setGradingScale(GradingScale gradingScale) {
		this.gradingScale = gradingScale;
	}

}
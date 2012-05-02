package calculator;

import java.io.Serializable;

public class School implements Serializable {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public School(String name) {
		this.name = name;
	}
}

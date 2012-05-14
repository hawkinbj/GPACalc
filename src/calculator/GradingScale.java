package calculator;

import java.io.Serializable;
import java.util.HashMap;

public class GradingScale implements Serializable {

	private static final long serialVersionUID = 8640739218123155037L;
	private boolean plusMinus; // Probably don't need this field.
	private HashMap<String, Double> gradingScaleMap; // <Letter, grade points>

	public GradingScale(boolean plusMinus) {
		gradingScaleMap = new HashMap<String, Double>();
		// Determine kind of scale: +/- or regular.
		this.setPlusMinus(plusMinus);
	}

	public boolean isPlusMinus() {
		return plusMinus;
	}

	protected HashMap<String, Double> getGradingScaleMap() {
		return gradingScaleMap;
	}

	public void setPlusMinus(boolean plusMinus) {
		this.plusMinus = plusMinus;
		gradingScaleMap.put("A", 4.00);
		gradingScaleMap.put("B", 3.00);
		gradingScaleMap.put("C", 2.00);
		gradingScaleMap.put("D", 1.00);
		gradingScaleMap.put("F", 0.00);
		if (plusMinus) {
			gradingScaleMap.put("A-", 3.70);
			gradingScaleMap.put("B+", 3.30);
			gradingScaleMap.put("B-", 2.70);
			gradingScaleMap.put("C+", 2.30);
			gradingScaleMap.put("C-", 1.70);
			gradingScaleMap.put("D+", 1.30);
			gradingScaleMap.put("D-", 0.70);
		}
	}
}

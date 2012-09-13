package com.hawkinbj.gpacalc.model;

import java.io.Serializable;
import java.util.HashMap;

public class GradingScale implements Serializable {
	private static final long serialVersionUID = 8640739218123155037L;
	private boolean plusMinus;
	private HashMap<String, Double> gradingScaleMap;

	public GradingScale(boolean plusMinus) {
		this.gradingScaleMap = new HashMap<String, Double>();

		setPlusMinus(plusMinus);
	}

	public boolean isPlusMinus() {
		return this.plusMinus;
	}

	public HashMap<String, Double> getGradingScaleMap() {
		return this.gradingScaleMap;
	}

	public void setPlusMinus(boolean plusMinus) {
		this.plusMinus = plusMinus;
		this.gradingScaleMap.put("A", Double.valueOf(4.0D));
		this.gradingScaleMap.put("B", Double.valueOf(3.0D));
		this.gradingScaleMap.put("C", Double.valueOf(2.0D));
		this.gradingScaleMap.put("D", Double.valueOf(1.0D));
		this.gradingScaleMap.put("F", Double.valueOf(0.0D));
		if (plusMinus) {
			this.gradingScaleMap.put("A-", Double.valueOf(3.7D));
			this.gradingScaleMap.put("B+", Double.valueOf(3.3D));
			this.gradingScaleMap.put("B-", Double.valueOf(2.7D));
			this.gradingScaleMap.put("C+", Double.valueOf(2.3D));
			this.gradingScaleMap.put("C-", Double.valueOf(1.7D));
			this.gradingScaleMap.put("D+", Double.valueOf(1.3D));
			this.gradingScaleMap.put("D-", Double.valueOf(0.7D));
		}
	}
}
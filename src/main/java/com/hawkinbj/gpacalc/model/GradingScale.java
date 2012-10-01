/*******************************************************************************
 * Copyright (c) 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.hawkinbj.gpacalc.model;

import java.io.Serializable;
import java.util.HashMap;

public class GradingScale implements Serializable {
	private static final long serialVersionUID = 8640739218123155037L;

	private boolean plusMinus;

	private HashMap<String, Double> gradingScaleMap;

	public GradingScale(boolean plusMinus) {
		this.gradingScaleMap = new HashMap<String, Double>();

		this.setPlusMinus(plusMinus);
	}

	public boolean isPlusMinus() {
		return this.plusMinus;
	}

	public HashMap<String, Double> getGradingScaleMap() {
		return this.gradingScaleMap;
	}

	public void setPlusMinus(boolean plusMinus) {
		this.plusMinus = plusMinus;

		this.gradingScaleMap.put("A", Double.valueOf(4));
		this.gradingScaleMap.put("B", Double.valueOf(3));
		this.gradingScaleMap.put("C", Double.valueOf(2));
		this.gradingScaleMap.put("D", Double.valueOf(1));
		this.gradingScaleMap.put("F", Double.valueOf(0));

		if (plusMinus) {
			this.gradingScaleMap.put("A-", 3.7);
			this.gradingScaleMap.put("B+", 3.3);
			this.gradingScaleMap.put("B-", 2.7);
			this.gradingScaleMap.put("C+", 2.3);
			this.gradingScaleMap.put("C-", 1.7);
			this.gradingScaleMap.put("D+", 1.3);
			this.gradingScaleMap.put("D-", 0.7);
		}
	}
}
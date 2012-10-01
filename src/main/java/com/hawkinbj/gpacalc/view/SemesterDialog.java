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
package com.hawkinbj.gpacalc.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.GUIPanel;
import com.hawkinbj.gpacalc.model.Semester;

public class SemesterDialog extends GUIPanel {
	private static final long serialVersionUID = 4982367508399428981L;

	private JRadioButton fallRadioBtn;

	private JRadioButton springRadioBtn;

	private JRadioButton summerRadioBtn;

	private JSpinner spinner;

	private SpinnerNumberModel yearModel;

	private JPanel mainPanel;

	private JPanel navigationPanel;

	public SemesterDialog(SystemController controller) {
		super(controller);
		this.addComponentsToPane();
	}

	private void addComponentsToPane() {
		fallRadioBtn = new JRadioButton("Fall", true);
		springRadioBtn = new JRadioButton("Spring", false);
		this.summerRadioBtn = new JRadioButton("Summer", false);

		ButtonGroup radioBtns = new ButtonGroup();
		radioBtns.add(fallRadioBtn);
		radioBtns.add(springRadioBtn);
		radioBtns.add(this.summerRadioBtn);

		this.mainPanel = new JPanel(new GridLayout(4, 1));

		this.createTitledBorder(this.mainPanel, "Select Term");

		int currentYear = Calendar.getInstance().get(1);
		this.yearModel = new SpinnerNumberModel(currentYear, currentYear - 10,
				currentYear + 10, 1);
		spinner = new JSpinner(this.yearModel);
		spinner.setEditor(new JSpinner.NumberEditor(spinner, "#"));
		this.mainPanel.add(spinner);
		this.mainPanel.add(fallRadioBtn);
		this.mainPanel.add(springRadioBtn);
		this.mainPanel.add(this.summerRadioBtn);

		this.navigationPanel = new JPanel(new GridLayout(2, 1));
		this.navigationPanel.add(createButton("done", "Done"));
		this.navigationPanel.add(createButton("cancel", "Cancel"));

		this.createTitledBorder(this.navigationPanel, "Navigation");

		this.setLayout(new BoxLayout(this, 3));
		this.add(this.mainPanel);
		this.add(this.navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		if (action.equals("cancel")) {
			controller.getRootFrame().showPanel("SemesterPanel", this);
		} else if (action.equals("done")) {
			Semester newSemester = new Semester(null, spinner.getValue()
					.toString());

			if (fallRadioBtn.isSelected())
				newSemester.setTerm("Fall");
			else if (springRadioBtn.isSelected())
				newSemester.setTerm("Spring");
			else if (this.summerRadioBtn.isSelected()) {
				newSemester.setTerm("Summer");
			}

			if (controller.getActiveTranscript().getSemesters()
					.containsKey(newSemester.toString())) {
				JOptionPane
						.showMessageDialog(
								this,
								"That semester already exists or no term was selected.",
								"Error", 0);
			} else {
				controller.getActiveTranscript().addSemester(
						newSemester.toString(), newSemester);

				controller.saveUserList();

				controller.getRootFrame().addPanel(
						new SemesterPanel(controller), this);
			}
		}
	}
}
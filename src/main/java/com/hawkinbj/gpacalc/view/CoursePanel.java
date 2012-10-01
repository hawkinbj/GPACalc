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

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.Course;
import com.hawkinbj.gpacalc.model.GUIPanel;

public class CoursePanel extends GUIPanel implements ActionListener {

	private static final long serialVersionUID = -6768153191699813450L;

	protected JPanel infoPanel;

	protected JPanel coursePanel;

	protected JPanel navigationPanel;

	private JLabel gpaLbl;

	private JLabel creditHoursLbl;

	public CoursePanel(SystemController controller) {
		super(controller);
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, 3));

		creditHoursLbl = new JLabel("Credit hours: "
				+ Integer.toString(controller.getActiveSemester()
						.getTotalHoursAttempted()));

		gpaLbl = this.formatGPADisplay("Semester GPA: ",
				controller.calcSemseterGPA());

		infoPanel.add(creditHoursLbl);
		infoPanel.add(gpaLbl);

		this.createTitledBorder(infoPanel, controller.getActiveSemester()
				.getSchoolName() + " " + controller.getActiveSemester());

		coursePanel = new JPanel();
		coursePanel.setLayout(new BoxLayout(coursePanel, 3));

		this.createTitledBorder(coursePanel, "Select Course");

		if (controller.getActiveSemester().getCourses().size() == 0) {
			JLabel instructionLbl = new JLabel(
					"Add a new course to get started.");
			instructionLbl.setForeground(Color.blue);
			coursePanel.add(instructionLbl);
		} else {
			for (String key : controller.getActiveSemester().getCourses()
					.keySet()) {
				coursePanel.add(createButton(key));
			}

		}

		navigationPanel = new JPanel();
		navigationPanel.setLayout(new BoxLayout(navigationPanel, 3));
		navigationPanel.add(createButton("addCourse", "Add new..."));
		navigationPanel.add(createButton("back", "Back"));

		this.createTitledBorder(navigationPanel, "Navigation");

		this.setLayout(new BoxLayout(this, 3));
		this.add(infoPanel);
		this.add(coursePanel);
		this.add(navigationPanel);
	}

	private JLabel formatGPADisplay(String baseText, double GPA) {
		JLabel gpaLabel = new JLabel();

		if (GPA == -1 || Double.isNaN(GPA)) {
			gpaLabel.setText(baseText + "N/A");
		} else {
			gpaLabel.setText(baseText
					+ String.format("%.2f",
							new Object[] { Double.valueOf(GPA) }));
		}

		return gpaLabel;
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		if (action.equals("back")) {
			controller.setActiveSemester(null);
			controller.getRootFrame().addPanel(new SemesterPanel(controller),
					this);
		} else if (action.equals("addCourse")) {
			controller.getRootFrame().addPanel(new CourseDialog(controller),
					this);
		} else {
			controller.setActiveCourse(((Course) controller.getActiveSemester()
					.getCourses().get(action)));
			controller.getRootFrame().addPanel(new CourseInfoPanel(controller),
					this);
		}
	}

	public void mouseClicked(MouseEvent e) {
		Component component = e.getComponent();

		if ((SwingUtilities.isRightMouseButton(e))
				&& (component.getName() != null)) {

			int response = JOptionPane.showConfirmDialog(this,
					"Are you sure you wish to remove this course?", "Confirm",
					0, 3);

			if (response == 0) {
				controller.getActiveSemester().getCourses()
						.remove(component.getName());
				controller.saveUserList();
				controller.getRootFrame().addPanel(new CoursePanel(controller),
						this);
			}
		}
	}
}
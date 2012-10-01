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

import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.GUIPanel;
import com.hawkinbj.gpacalc.model.School;
import com.hawkinbj.gpacalc.model.Transcript;

public class SchoolPanel extends GUIPanel {
	private static final long serialVersionUID = 1029559901433692231L;

	private JPanel schoolsPanel;

	private JPanel navigationPanel;

	public SchoolPanel(SystemController controller) {
		super(controller);
		this.addComponentsToPane();
	}

	private void addComponentsToPane() {
		schoolsPanel = new JPanel();
		schoolsPanel.setLayout(new BoxLayout(schoolsPanel, 3));

		this.createTitledBorder(schoolsPanel, "Select School");

		for (School school : controller.getSchools().values()) {
			schoolsPanel.add(createButton(school.getName()));
		}

		navigationPanel = new JPanel();
		navigationPanel.setLayout(new BoxLayout(navigationPanel, 3));
		navigationPanel.add(createButton("newSchoolPanel", "Add new..."));
		navigationPanel.add(createButton("cancel", "Cancel"));

		this.createTitledBorder(navigationPanel, "Navigation");

		this.setLayout(new BoxLayout(this, 3));
		this.add(schoolsPanel);
		this.add(navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		if (action.equals("cancel")) {
			controller.getRootFrame().showPanel("MainMenuPanel", this);
		} else if (action.equals("newSchoolPanel")) {
			controller.getRootFrame().addPanel(new SchoolDialog(controller),
					this);
		} else if (controller.getActiveUser().addTranscript(action,
				new Transcript((School) controller.getSchools().get(action)))) {
			controller.saveUserList();
			controller.getRootFrame().addPanel(new MainMenuPanel(controller),
					this);
		} else {
			JOptionPane.showMessageDialog(this, "That school already exists.",
					"Error", 0);
		}
	}
}

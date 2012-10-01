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
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.GUIPanel;
import com.hawkinbj.gpacalc.model.GradingScale;

public class SchoolDialog extends GUIPanel {
	private static final long serialVersionUID = -873283817151884443L;

	private JPanel navigationPanel;

	private JPanel newSchoolPanel;

	private JPanel radioPanel;

	private JTextField newSchoolField;

	private JRadioButton plusMinusRadio;

	private JRadioButton normalRadio;

	public SchoolDialog(SystemController controller) {
		super(controller);
		this.addComponentsToPane();
	}

	private void addComponentsToPane() {
		this.newSchoolPanel = new JPanel(new GridLayout(1, 2));
		this.newSchoolField = new JTextField(5);
		this.newSchoolPanel.add(this.newSchoolField);

		this.createTitledBorder(this.newSchoolPanel, "Name of School");

		this.radioPanel = new JPanel(new GridLayout(1, 2));

		this.createTitledBorder(this.radioPanel, "Grading Scale");

		ButtonGroup addSchoolBtns = new ButtonGroup();
		this.plusMinusRadio = new JRadioButton("Plus/Minus", true);
		this.normalRadio = new JRadioButton("Normal");

		addSchoolBtns.add(this.plusMinusRadio);
		addSchoolBtns.add(this.normalRadio);
		this.radioPanel.add(this.plusMinusRadio);
		this.radioPanel.add(this.normalRadio);

		this.navigationPanel = new JPanel(new GridLayout(2, 1));
		this.navigationPanel.add(createButton("add", "Add"));
		this.navigationPanel.add(createButton("cancel", "Cancel"));

		this.createTitledBorder(this.navigationPanel, "Navigation");

		this.setLayout(new BoxLayout(this, 3));
		this.add(this.newSchoolPanel);
		this.add(this.radioPanel);
		this.add(this.navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		if (action.equals("cancel")) {
			controller.getRootFrame().showPanel("SchoolPanel", this);
		} else if (action.equals("add")) {
			String newSchoolName = this.newSchoolField.getText();

			if ((newSchoolName == null)
					|| (!controller.addSchool(newSchoolName, new GradingScale(
							plusMinusRadio.isSelected()))))
				JOptionPane.showMessageDialog(this,
						"That school already exists or no name was entered.",
						"Error", 0);
			else
				controller.getRootFrame().addPanel(new SchoolPanel(controller),
						this);
		}
	}
}
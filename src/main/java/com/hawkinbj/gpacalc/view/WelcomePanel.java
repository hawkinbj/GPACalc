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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.GUIPanel;

public class WelcomePanel extends GUIPanel {
	private static final long serialVersionUID = -6797117896418157857L;

	private JPanel instructionPanel;

	private JPanel navPanel;

	public WelcomePanel(SystemController controller) {
		super(controller);
		this.addComponentsToPane();
	}

	private void addComponentsToPane() {
		instructionPanel = new JPanel(new GridLayout(2, 1));

		this.createTitledBorder(instructionPanel, "Welcome to GPACalc");

		JLabel instructionLbl = new JLabel("Select an option below.");
		instructionLbl.setForeground(Color.blue);
		instructionPanel.add(instructionLbl);

		navPanel = new JPanel(new GridLayout(2, 1));
		navPanel.add(createButton("login", "Login"));
		navPanel.add(createButton("register", "Register"));

		this.createTitledBorder(navPanel, "Navigation");

		this.setLayout(new BoxLayout(this, 3));
		this.add(instructionPanel);
		this.add(navPanel);

		controller.getRootFrame().pack();
	}

	private void menuHandler(String menuAction) {
		if (menuAction.equals("login")) {
			if (controller.getUsers().size() == 0)
				JOptionPane.showMessageDialog(this,
						"You need to register first!", "Error", 0);
			else
				controller.getRootFrame().addPanel(new LoginPanel(controller),
						this);
		}

		if (menuAction.equals("register"))
			controller.getRootFrame().addPanel(new RegisterPanel(controller),
					this);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		this.menuHandler(action);
	}
}
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.GUIPanel;

public class RegisterPanel extends GUIPanel {
	private static final long serialVersionUID = -5184321484494774189L;

	private JLabel usernameLbl;

	private JLabel passwordLbl;

	private JTextField usernameField;

	private JTextField passwordField;

	private JPanel entryPanel;

	private JPanel navigationPanel;

	public RegisterPanel(SystemController controller) {
		super(controller);
		this.addComponentsToPane();
	}

	private void addComponentsToPane() {
		usernameLbl = new JLabel("Username:");
		usernameField = new JTextField(15);
		passwordLbl = new JLabel("Password:");
		passwordField = new JPasswordField(15);

		entryPanel = new JPanel(new GridLayout(4, 2));
		entryPanel.add(usernameLbl);
		entryPanel.add(usernameField);
		entryPanel.add(passwordLbl);
		entryPanel.add(passwordField);

		this.createTitledBorder(entryPanel, "Register");

		navigationPanel = new JPanel(new GridLayout(2, 1));
		navigationPanel.add(createButton("submit", "Submit"));
		navigationPanel.add(createButton("back", "Back"));

		this.createTitledBorder(navigationPanel, "Navigation");

		this.setLayout(new BoxLayout(this, 3));
		this.add(entryPanel);
		this.add(navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		String username = this.usernameField.getText();
		String password = this.passwordField.getText();

		if (action.equals("submit")) {
			if (this.controller.register(username, password)) {
				this.controller.getRootFrame().addPanel(
						new MainMenuPanel(this.controller), this);
			} else {
				JOptionPane
						.showMessageDialog(
								this,
								"That username is already taken. Please enter a different username",
								"Error", 0);
			}

		}

		if (action.equals("back"))
			this.controller.getRootFrame().showPanel("WelcomePanel", this);
	}
}

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
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		this.instructionPanel = new JPanel(new GridLayout(2, 1));
		createTitledBorder(this.instructionPanel, "Welcome to GPACalc");
		JLabel instructionLbl = new JLabel("Select an option below.");
		instructionLbl.setForeground(Color.blue);
		this.instructionPanel.add(instructionLbl);

		this.navPanel = new JPanel(new GridLayout(2, 1));

		createTitledBorder(this.navPanel, "Navigation");
		this.navPanel.add(createButton("login", "Login"));
		this.navPanel.add(createButton("register", "Register"));

		setLayout(new BoxLayout(this, 3));
		add(this.instructionPanel);
		add(this.navPanel);
		this.controller.getRootFrame().pack();
	}

	private void menuHandler(String menuAction) {
		if (menuAction.equals("login")) {
			if (this.controller.getUsers().size() == 0)
				JOptionPane.showMessageDialog(this,
						"You need to register first!", "Error", 0);
			else
				this.controller.getRootFrame().addPanel(
						new LoginPanel(this.controller), this);
		}
		if (menuAction.equals("register"))
			this.controller.getRootFrame().addPanel(
					new RegisterPanel(this.controller), this);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		menuHandler(action);
	}
}
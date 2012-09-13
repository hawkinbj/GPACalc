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
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JTextField usernameField;
	private JTextField passwordField;
	private JPanel entryPanel;
	private JPanel navigationPanel;

	public RegisterPanel(SystemController controller) {
		super(controller);
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		this.usernameLabel = new JLabel("Username:");
		this.usernameField = new JTextField(15);
		this.passwordLabel = new JLabel("Password:");
		this.passwordField = new JPasswordField(15);

		this.entryPanel = new JPanel(new GridLayout(4, 2));

		this.entryPanel.add(this.usernameLabel);
		this.entryPanel.add(this.usernameField);
		this.entryPanel.add(this.passwordLabel);
		this.entryPanel.add(this.passwordField);
		createTitledBorder(this.entryPanel, "Register");

		this.navigationPanel = new JPanel(new GridLayout(2, 1));
		createTitledBorder(this.navigationPanel, "Navigation");
		this.navigationPanel.add(createButton("submit", "Submit"));
		this.navigationPanel.add(createButton("back", "Back"));
		setLayout(new BoxLayout(this, 3));
		add(this.entryPanel);
		add(this.navigationPanel);
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

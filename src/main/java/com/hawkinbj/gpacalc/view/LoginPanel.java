package com.hawkinbj.gpacalc.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.GUIPanel;

public class LoginPanel extends GUIPanel {
	private static final long serialVersionUID = 2318278158794448042L;

	private JLabel usernameLabel;

	private JLabel passwordLabel;

	private JTextField usernameField;

	private JTextField passwordField;

	private JPanel entryPanel;

	private JPanel rememberInfoPanel;

	private JPanel navigationPanel;

	private JCheckBox rememberInfoBox;

	public LoginPanel(SystemController controller) {
		super(controller);
		this.addComponentsToPane();
	}

	private void addComponentsToPane() {
		usernameLabel = new JLabel("Username:");
		usernameField = new JTextField(15);
		passwordLabel = new JLabel("Password:");
		passwordField = new JPasswordField(15);

		entryPanel = new JPanel(new GridLayout(4, 2));
		entryPanel.add(usernameLabel);
		entryPanel.add(usernameField);
		entryPanel.add(passwordLabel);
		entryPanel.add(passwordField);

		this.createTitledBorder(entryPanel, "Login");

		rememberInfoPanel = new JPanel();
		rememberInfoPanel.setLayout(new BoxLayout(rememberInfoPanel, 3));
		rememberInfoBox = new JCheckBox("Stay logged in?");
		rememberInfoPanel.add(rememberInfoBox);

		navigationPanel = new JPanel(new GridLayout(2, 1));
		navigationPanel.add(createButton("submit", "Submit"));
		navigationPanel.add(createButton("back", "Back"));

		this.createTitledBorder(navigationPanel, "Navigation");

		this.setLayout(new BoxLayout(this, 3));
		this.add(entryPanel);
		this.add(rememberInfoPanel);
		this.add(navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String username = usernameField.getText();
		String password = passwordField.getText();
		String action = e.getActionCommand();

		if (action.equals("submit")) {
			if (controller.login(username, password)) {
				if (rememberInfoBox.isSelected()) {
					controller.getActiveUser().setRememberLoginInfo(true);
					controller.saveActiveUser();
				}

				controller.getRootFrame().addPanel(
						new MainMenuPanel(controller), this);
			} else {
				JOptionPane.showMessageDialog(this,
						"Incorrect login or password", "Error", 0);
			}
		} else if (action.equals("back")) {
			controller.getRootFrame().showPanel("WelcomePanel", this);
		}
	}
}

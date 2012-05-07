package calculator;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class RegisterPanel extends GUIPanel {

	private static final long serialVersionUID = -5184321484494774189L;
	private JLabel usernameLabel, passwordLabel;
	private JTextField usernameField, passwordField;
	private JPanel panel;

	public RegisterPanel(SystemController controller) {
		super(controller);
		addComponentsToPane();
	}

	private void addComponentsToPane() {

		// Labels and TextFields.
		usernameLabel = new JLabel("Username:");
		usernameField = new JTextField(15);
		passwordLabel = new JLabel("Password:");
		passwordField = new JPasswordField(15);

		// layout
		panel = new JPanel(new GridLayout(3, 1));
		panel.add(usernameLabel);
		panel.add(usernameField);
		panel.add(passwordLabel);
		panel.add(passwordField);
		panel.add(createButton("back", "Back"));
		panel.add(createButton("submit", "Submit"));
		add(panel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		String username = usernameField.getText();
		String password = passwordField.getText();
		// attempt to register if submit button pressed
		if (action.equals("submit")) {
			// open MainHub window if valid registration
			if (controller.register(username, password)) {
				System.out
						.println("Successfully registered. Printed from RegisterPanel");
				controller.addPanel(new MainMenuPanel(controller), "mainMenu");
				controller.showPanel("mainMenu", this);
			} else { // if this executes it means username is already in use
				JOptionPane
						.showMessageDialog(
								this,
								"That username is already taken. Please enter a different username",
								"Error", JOptionPane.ERROR_MESSAGE);
				// new RegisterPanel(controller,
				// previousFrame).setVisible(true);
			}

		}
		// open previous menu if back button pressed
		if (action.equals("back")) {
			controller.showPanel("welcome", this);

		}
	}

}
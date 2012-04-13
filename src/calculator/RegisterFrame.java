package calculator;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class RegisterFrame extends JFrame implements ActionListener {
	private JFrame previousFrame;
	private JLabel usernameLabel, passwordLabel;
	private JButton backButton, submitButton;
	private JTextField usernameField, passwordField;
	private SystemController controller;
	private JPanel panel;

	public RegisterFrame(SystemController controller, JFrame previousFrame) {

		setSize(300, 300);
		setTitle("Register");
		this.controller = controller;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.previousFrame = previousFrame;
		this.controller = controller;
		addComponentsToPane();
	}

	private void addComponentsToPane() {

		// Labels and TextFields.
		usernameLabel = new JLabel("Username:");
		usernameField = new JTextField(15);
		passwordLabel = new JLabel("Password:");
		passwordField = new JPasswordField(15);

		// back button
		backButton = new JButton("Back");
		backButton.setActionCommand("back");
		backButton.addActionListener(this);

		// Submit button
		submitButton = new JButton("Submit");
		submitButton.setActionCommand("submit");
		submitButton.addActionListener(this);

		// layout
		panel = new JPanel(new GridLayout(3, 1));
		panel.add(usernameLabel);
		panel.add(usernameField);
		panel.add(passwordLabel);
		panel.add(passwordField);
		panel.add(backButton);
		panel.add(submitButton);
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
				new GradingScaleFrame(controller, this).setVisible(true);
				setVisible(false);
			} else { // if this excutes it means username is already in use
				JOptionPane
						.showMessageDialog(
								this,
								"That username is already taken. Please enter a different username",
								"Error", JOptionPane.ERROR_MESSAGE);
				setVisible(false);
				new RegisterFrame(controller, previousFrame).setVisible(true);
			}
			// open previous menu if back button pressed
		}
		if (action.equals("back")) {
			setVisible(false);
			previousFrame.setVisible(true);
		}
	}

}
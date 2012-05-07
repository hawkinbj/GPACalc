package calculator;

import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegisterPanel extends GUIPanel {

	private static final long serialVersionUID = -5184321484494774189L;
	private JLabel usernameLabel, passwordLabel;
	private JTextField usernameField, passwordField;
	private JPanel entryPanel, navigationPanel;

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
		entryPanel = new JPanel(new GridLayout(4, 2));
		// panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		entryPanel.add(usernameLabel);
		entryPanel.add(usernameField);
		entryPanel.add(passwordLabel);
		entryPanel.add(passwordField);

		navigationPanel = new JPanel(new GridLayout(2, 1));
		navigationPanel.add(createButton("submit", "Submit"));
		navigationPanel.add(createButton("back", "Back"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(entryPanel);
		add(navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		String username = usernameField.getText();
		String password = passwordField.getText();
		// attempt to register if submit button pressed
		if (action.equals("submit")) {
			// open MainHub window if valid registration
			if (controller.register(username, password)) {
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
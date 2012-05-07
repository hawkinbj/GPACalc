package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends GUIPanel {

	private static final long serialVersionUID = 2318278158794448042L;
	private JFrame previousFrame;
	private JLabel usernameLabel, passwordLabel;
	private JTextField usernameField, passwordField;
	private JPanel panel;

	public LoginPanel(SystemController controller) {
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
		if (action.equals("submit")) {
			if (controller.login(username, password)) {
				controller.addPanel(new MainMenuPanel(controller), "mainMenu");
				controller.showPanel("mainMenu", this);
			} else {
				JOptionPane.showMessageDialog(this,
						"Incorrect login or password", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		if (action.equals("back")) {
			setVisible(false);
			previousFrame.setVisible(true);
		}
	}
}
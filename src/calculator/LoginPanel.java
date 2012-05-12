package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends GUIPanel {

	private static final long serialVersionUID = 2318278158794448042L;
	private JLabel usernameLabel, passwordLabel;
	private JTextField usernameField, passwordField;
	private JPanel entryPanel, navigationPanel;

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
		if (action.equals("submit")) {
			if (controller.login(username, password)) {
				controller.rootFrame.addPanel(new MainMenuPanel(controller),
						"mainMenu");
				controller.rootFrame.showPanel("mainMenu", this);
			} else {
				JOptionPane.showMessageDialog(this,
						"Incorrect login or password", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		if (action.equals("back")) {
			controller.rootFrame.showPanel("welcome", this);
		}
	}
}
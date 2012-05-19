package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends GUIPanel {

	private static final long serialVersionUID = 2318278158794448042L;
	private JLabel usernameLabel, passwordLabel;
	private JTextField usernameField, passwordField;
	private JPanel entryPanel, rememberInfoPanel, navigationPanel;
	private JCheckBox rememberInfoBox;

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

		// Entry panel.
		entryPanel = new JPanel(new GridLayout(4, 2));
		// panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		entryPanel.add(usernameLabel);
		entryPanel.add(usernameField);
		entryPanel.add(passwordLabel);
		entryPanel.add(passwordField);
		createTitledBorder(entryPanel, "Login");

		// Remember login/pass panel.
		rememberInfoPanel = new JPanel();
		rememberInfoPanel.setLayout(new BoxLayout(rememberInfoPanel,
				BoxLayout.PAGE_AXIS));
		// createTitledBorder(rememberInfoPanel, "Remember User/Pass");
		rememberInfoBox = new JCheckBox("Stay logged in?");
		rememberInfoPanel.add(rememberInfoBox);

		navigationPanel = new JPanel(new GridLayout(2, 1));
		createTitledBorder(navigationPanel, "Navigation");
		navigationPanel.add(createButton("submit", "Submit"));
		navigationPanel.add(createButton("back", "Back"));

		// Layout.
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(entryPanel);
		add(rememberInfoPanel);
		add(navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		String username = usernameField.getText();
		String password = passwordField.getText();
		if (action.equals("submit")) {
			if (controller.login(username, password)) {
				// Check this doesn't skip next part
				if (rememberInfoBox.isSelected()) {
					controller.activeUser.setRememberLoginInfo(true);
					controller.saveActiveUser();
				}
				controller.rootFrame.addPanel(new MainMenuPanel(controller),
						this);
			} else {
				JOptionPane.showMessageDialog(this,
						"Incorrect login or password", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		if (action.equals("back")) {
			controller.rootFrame.showPanel("WelcomePanel", this);
		}
	}
}
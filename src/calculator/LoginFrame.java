package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame implements ActionListener {

	private JFrame previousFrame;
	private JLabel usernameLabel, passwordLabel;
	private JButton backBtn, submitBtn;
	private JTextField usernameField, passwordField;
	private SystemController controller;
	private JPanel panel;

	public LoginFrame(SystemController controller, JFrame previousFrame) {

		setSize(300, 300);
		setTitle("Login");
		this.controller = controller;
		this.previousFrame = previousFrame;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponentsToPane();
	}

	private void addComponentsToPane() {

		// Labels and TextFields.
		usernameLabel = new JLabel("Username:");
		usernameField = new JTextField(15);
		passwordLabel = new JLabel("Password:");
		passwordField = new JPasswordField(15);

		// back button
		backBtn = new JButton("Back");
		backBtn.setActionCommand("back");
		backBtn.addActionListener(this);

		// register button
		submitBtn = new JButton("Submit");
		submitBtn.setActionCommand("submit");
		submitBtn.addActionListener(this);

		// layout
		panel = new JPanel(new GridLayout(3, 1));
		panel.add(usernameLabel);
		panel.add(usernameField);
		panel.add(passwordLabel);
		panel.add(passwordField);
		panel.add(backBtn);
		panel.add(submitBtn);
		add(panel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		String username = usernameField.getText();
		String password = passwordField.getText();
		if (action.equals("submit")) {
			if (controller.login(username, password)) {
				new MainHub(controller).setVisible(true);
				this.setVisible(false);
			} else {
				JOptionPane.showMessageDialog(this,
						"Incorrect login or password", "Error",
						JOptionPane.ERROR_MESSAGE);
				setVisible(false);
				new LoginFrame(controller, previousFrame).setVisible(true);
			}
		}
		if (action.equals("back")) {
			setVisible(false);
			previousFrame.setVisible(true);
		}
	}

}
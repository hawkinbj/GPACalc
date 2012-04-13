package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class WelcomeFrame extends JFrame implements ActionListener {
	private JButton loginButton, registerButton;
	private JPanel panel;
	private JTextArea instructionArea;
	private final SystemController controller;

	WelcomeFrame(SystemController controller) {

		setSize(300, 300);
		setTitle("GPACalc");
		this.controller = controller;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		// instruction label
		instructionArea = new JTextArea();
		instructionArea.setText("Welcome to GPACalc.\n\nPlease "
				+ "make a selection below:");
		instructionArea.setEditable(false);
		instructionArea.setCursor(null);
		instructionArea.setOpaque(false);
		instructionArea.setFocusable(false);

		// login button
		loginButton = new JButton("Login");
		loginButton.setActionCommand("login");
		loginButton.addActionListener(this);
		add(loginButton);

		// register button
		registerButton = new JButton("Register");
		registerButton.setActionCommand("register");
		registerButton.addActionListener(this);
		add(registerButton);

		// layout
		panel = new JPanel(new GridLayout(3, 0));
		panel.add(instructionArea);
		panel.add(registerButton);
		panel.add(loginButton);
		add(panel);
	}

	private void menuHandler(String menuAction) {
		if (menuAction.equals("login")) {
			setVisible(false);
			new LoginFrame(controller, this).setVisible(true);
		}
		if (menuAction.equals("register")) {
			new RegisterFrame(controller, this).setVisible(true);
			setVisible(false);
		}
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		menuHandler(action);
	}

}
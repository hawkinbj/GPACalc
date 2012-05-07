package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class WelcomePanel extends GUIPanel {

	private static final long serialVersionUID = -6797117896418157857L;
	private JButton loginButton, registerButton;
	private JPanel panel;
	private JTextArea instructionArea;

	public WelcomePanel(SystemController controller) {
		super(controller);
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
		panel = new JPanel(new GridLayout(3, 1));
		panel.add(instructionArea);
		panel.add(registerButton);
		panel.add(loginButton);
		add(panel);
	}

	private void menuHandler(String menuAction) {
		if (menuAction.equals("login")) {
			controller.addPanel(new LoginPanel(controller), "login");
			controller.showPanel("login", this);
		}
		if (menuAction.equals("register")) {
			controller.addPanel(new RegisterPanel(controller), "register");
			controller.showPanel("register", this);
		}
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		menuHandler(action);
	}

}
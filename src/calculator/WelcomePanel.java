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

		// layout
		panel = new JPanel(new GridLayout(3, 1));
		panel.add(instructionArea);
		// login button.
		panel.add(createButton("login", "Login"));
		// register button.
		panel.add(createButton("register", "Register"));
		add(panel);
	}

	private void menuHandler(String menuAction) {
		if (menuAction.equals("login")) {
			controller.rootFrame.addPanel(new LoginPanel(controller), this);
			// controller.rootFrame.showPanel("login", this);
		}
		if (menuAction.equals("register")) {
			controller.rootFrame.addPanel(new RegisterPanel(controller), this);
			// controller.rootFrame.showPanel("register", this);
		}
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		menuHandler(action);
	}

}
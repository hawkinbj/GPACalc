package calculator;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class WelcomePanel extends GUIPanel {

	private static final long serialVersionUID = -6797117896418157857L;
	private JPanel instructionPanel, navPanel;

	public WelcomePanel(SystemController controller) {
		super(controller);
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		// Instruction panel.
		instructionPanel = new JPanel(new GridLayout(2, 1));
		createTitledBorder(instructionPanel, "Welcome to GPACalc");
		JLabel instructionLbl = new JLabel("Select an option below.");
		instructionLbl.setForeground(Color.blue);
		instructionPanel.add(instructionLbl);

		// Navigation panel.
		navPanel = new JPanel(new GridLayout(2, 1));
		// navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.PAGE_AXIS));
		createTitledBorder(navPanel, "Navigation");
		navPanel.add(createButton("login", "Login"));
		navPanel.add(createButton("register", "Register"));

		// Layout.
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(instructionPanel);
		add(navPanel);
		controller.rootFrame.pack();

	}

	private void menuHandler(String menuAction) {
		if (menuAction.equals("login")) {
			// Don't show LoginFrame unless at least 1 user is registered.
			if (controller.users.size() == 0)
				JOptionPane.showMessageDialog(this,
						"You need to register first!", "Error",
						JOptionPane.ERROR_MESSAGE);
			else
				controller.rootFrame.addPanel(new LoginPanel(controller), this);
		}
		if (menuAction.equals("register")) {
			controller.rootFrame.addPanel(new RegisterPanel(controller), this);
		}
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		menuHandler(action);
	}

}
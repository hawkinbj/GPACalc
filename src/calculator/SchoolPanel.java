package calculator;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class SchoolPanel extends GUIPanel {

	private static final long serialVersionUID = 1029559901433692231L;
	private JPanel schoolsPanel, navigationPanel;

	public SchoolPanel(SystemController controller) {

		super(controller);
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		// Schools panel.
		schoolsPanel = new JPanel();
		schoolsPanel
				.setLayout(new BoxLayout(schoolsPanel, BoxLayout.PAGE_AXIS));
		// Instructions label.
		JLabel schoolsInstructionLbl = new JLabel("Choose a school:");
		schoolsPanel.add(schoolsInstructionLbl);
		// Buttons to represent schools.
		for (School school : controller.schools.values()) {
			schoolsPanel.add(createButton(school.getName()));
		}

		// Navigation label and separator panel.
		navigationPanel = new JPanel();
		navigationPanel.setLayout(new BoxLayout(navigationPanel,
				BoxLayout.PAGE_AXIS));
		navigationPanel.add(new JLabel("Navigation"));

		// New school screen button.
		navigationPanel.add(createButton("newSchoolPanel", "Add new..."));

		// Cancel button.
		navigationPanel.add(createButton("cancel", "Cancel"));

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(schoolsPanel);
		add(navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("cancel"))
			controller.showPanel("mainMenu", this);
		if (action.equals("newSchoolPanel")) {
			String newSchoolName = JOptionPane.showInputDialog(this,
					"Name of new school:");
			if (newSchoolName == null) {
				return;
			}
			if (!controller.addSchool(newSchoolName)) {
				JOptionPane.showMessageDialog(this,
						"That school already exists or no name was entered.",
						"Error", JOptionPane.ERROR_MESSAGE);
			} else {
				schoolsPanel.add(createButton(newSchoolName));
				this.revalidate();
			}
		} else {
			controller.activeUser.addTranscript(action, new Transcript());
			controller.saveUserList();
			MainMenuPanel previousFrame = (MainMenuPanel) controller.panels
					.get("mainMenu");
			previousFrame.mainMenuPanel.add(previousFrame.createButton(action));
			// previousFrame.revalidate();
			controller.showPanel("mainMenu", this);
		}
	}
}

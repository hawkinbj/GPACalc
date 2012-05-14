package calculator;

import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
		if (action.equals("cancel")) {
			controller.rootFrame.showPanel("MainMenuPanel", this);
		} else if (action.equals("newSchoolPanel")) {
			controller.rootFrame.addPanel(new SchoolDialog(controller), this);
		} else {
			if (controller.activeUser.addTranscript(action, new Transcript(
					controller.schools.get(action)))) {
				controller.saveUserList();
				controller.rootFrame.addPanel(new MainMenuPanel(controller),
						this);
			} else {
				JOptionPane.showMessageDialog(this,
						"That school already exists.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}

	}
}

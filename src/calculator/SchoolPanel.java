package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class SchoolPanel extends GUIPanel {

	private static final long serialVersionUID = 1029559901433692231L;
	private JPanel schoolsPanel, navigationPanel;

	public SchoolPanel(SystemController controller) {

		super(controller);
		addComponentsToPane();
		System.out.println(this.getClass());
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
			controller.showPanel("mainMenu", this);
			return;
		} else if (action.equals("newSchoolPanel")) {
			JPanel panel = new JPanel(new GridLayout(2, 2));
			panel.add(new JLabel("Name of new school: "));
			JTextField newSchoolField = new JTextField(5);
			panel.add(newSchoolField);
			//
			ButtonGroup addSchoolBtns = new ButtonGroup();
			JRadioButton plusMinusRadio = new JRadioButton("Plus/Minus", true);
			JRadioButton regularRadio = new JRadioButton("Normal");
			addSchoolBtns.add(plusMinusRadio);
			addSchoolBtns.add(regularRadio);
			panel.add(plusMinusRadio);
			panel.add(regularRadio);
			//
			int test = JOptionPane.showOptionDialog(this, panel, "Radio Test",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, null, null);
			//
			String newSchoolName = newSchoolField.getText();
			boolean plusMinus = false;
			//
			if (plusMinusRadio.isSelected()) {
				plusMinus = true;
			}
			if (test == 0) {
				if (newSchoolName == null
						|| !controller.addSchool(newSchoolName,
								new GradingScale(plusMinus))) {
					JOptionPane
							.showMessageDialog(
									this,
									"That school already exists or no name was entered.",
									"Error", JOptionPane.ERROR_MESSAGE);
				} else {
					schoolsPanel.add(createButton(newSchoolName));
					this.revalidate();
				}
			} else {
				return;
			}
		} else {
			if (controller.activeUser.addTranscript(action, new Transcript(
					action))) {
				controller.saveUserList();
				MainMenuPanel previousFrame = (MainMenuPanel) controller.panels
						.get("mainMenu");
				previousFrame.instructionPanel.add(previousFrame
						.createButton(action));
				controller.showPanel("mainMenu", this);
			} else {
				JOptionPane.showMessageDialog(this,
						"That school already exists.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}

package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class SchoolFrame extends JFrame implements ActionListener {

	private SystemController controller;
	private MainHub previousFrame;
	private JTextField newSchoolNameField;
	private JPanel schoolsPanel, newSchoolPanel;

	public SchoolFrame(SystemController controller, MainHub previousFrame) {

		setSize(300, 300);
		this.controller = controller;
		this.previousFrame = previousFrame;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Select School");
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		// Schools panel.
		schoolsPanel = new JPanel(new GridLayout(controller.schools.size() + 4,
				1));
		// Instructions label.
		JLabel schoolsInstructionLbl = new JLabel("Choose a school:");
		schoolsPanel.add(schoolsInstructionLbl);
		// Buttons to represent schools.
		for (School school : controller.schools) {
			String schoolName = school.getName();
			JButton schoolBtn = new JButton(schoolName); // Might need to
															// uniquely name....
			schoolBtn.setActionCommand(schoolName);
			schoolBtn.addActionListener(this);
			schoolsPanel.add(schoolBtn);
		}

		JButton addSchoolBtn = new JButton("Add new...");
		addSchoolBtn.setActionCommand("newSchoolPanel");
		addSchoolBtn.addActionListener(this);
		schoolsPanel.add(addSchoolBtn);

		// Navigation label/separator.
		JLabel schoolsNavLbl = new JLabel("Navigation");
		schoolsPanel.add(schoolsNavLbl);

		// Cancel button.
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.setActionCommand("cancel");
		cancelBtn.addActionListener(this);

		schoolsPanel.add(cancelBtn);
		add(schoolsPanel);
	}

	private void newSchoolMenu() {
		// Label and textfield to enter new school name.
		JLabel addSchoolInstructionLbl = new JLabel("School name: ");
		newSchoolNameField = new JTextField(10); // int = entry spaces.
		// Done button.
		JButton doneBtn = new JButton("Done");
		doneBtn.setActionCommand("done");
		doneBtn.addActionListener(this);
		// Layout.
		newSchoolPanel = new JPanel(new GridLayout(5, 1));
		newSchoolPanel.add(addSchoolInstructionLbl);
		newSchoolPanel.add(newSchoolNameField);
		newSchoolPanel.add(doneBtn);
		add(newSchoolPanel);
		schoolsPanel.setVisible(false);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("cancel")) {
			setVisible(false);
			previousFrame.setVisible(true);
		}
		if (action.equals("newSchoolPanel")) {
			newSchoolMenu();
		}
		if (action.equals("done")) { // Selected a school.
			String newSchoolName = newSchoolNameField.getText();
			if (!controller.addSchool(newSchoolName)) {
				JOptionPane.showMessageDialog(this,
						"That school already exists.", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				JButton newSchoolBtn = new JButton(newSchoolName);
				newSchoolBtn.setActionCommand(newSchoolName);
				newSchoolBtn.addActionListener(previousFrame);
				newSchoolBtn.addActionListener(this);
				schoolsPanel.add(newSchoolBtn);
				newSchoolPanel.setVisible(false);
				addComponentsToPane();
				schoolsPanel.setVisible(true);

			}
		} else {
			for (School school : controller.schools) {
				String schoolName = school.getName();
				if (schoolName.equals(action)) {
					controller.addTranscript(new Transcript(schoolName));
					JButton selectedSchoolBtn = new JButton(schoolName);
					selectedSchoolBtn.setActionCommand(schoolName);
					selectedSchoolBtn.addMouseListener(previousFrame);
					selectedSchoolBtn.addActionListener(previousFrame);
					previousFrame.mainMenuPanel.add(selectedSchoolBtn);
					previousFrame.setVisible(true);
					setVisible(false);
				}
			}

		}
	}
}

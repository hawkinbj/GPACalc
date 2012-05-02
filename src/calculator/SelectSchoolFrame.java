package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SelectSchoolFrame extends JFrame implements ActionListener {

	private SystemController controller;
	private MainHub previousFrame;
	private JTextField schoolNameField;
	private JPanel schoolsPanel;

	public SelectSchoolFrame(SystemController controller, MainHub previousFrame) {

		setSize(300, 300);
		this.controller = controller;
		this.previousFrame = previousFrame;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Select School");

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
		addSchoolBtn.setActionCommand("addSchool");
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

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("cancel")) {
			setVisible(false);
			previousFrame.setVisible(true);
		if (action.equals("addSchool")){
			// IMPLEMENT THIS
			// controller.addSchool();
		}
		} else {
			for (School school : controller.schools) {
				String schoolName = school.getName();
				if (schoolName.equals(action)) {
					System.out.println("asdfasl;djasdf");
					JButton selectedSchoolBtn = new JButton(schoolName);
					selectedSchoolBtn.setActionCommand(schoolName);
					selectedSchoolBtn.addActionListener(this);
					previousFrame.mainMenuPanel.add(selectedSchoolBtn);
					previousFrame.setVisible(true);
					setVisible(false);
				}
			}

		}
	}
}

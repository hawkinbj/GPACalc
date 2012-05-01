package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class CourseFrame extends JFrame implements ActionListener {

	// NEED TO STORE COURSES.
	private SystemController controller;
	private MainHub previousFrame;
	private JPanel coursePanel, addCoursePanel, quizPanel;
	private JTextField courseNameField;

	public CourseFrame(SystemController controller, MainHub previousFrame) {
		// Initialization.
		setSize(300, 300);
		setTitle("New Course");
		this.controller = controller;
		this.previousFrame = previousFrame;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ShowCourses();
	}

	private void ShowCourses() {
		// Add course button.
		JButton addCourse = new JButton("Add New...");
		addCourse.setActionCommand("addCourse");
		addCourse.addActionListener(this);

		// Main menu button.
		JButton mainMenu = new JButton("Main Menu");
		mainMenu.setActionCommand("mainMenu");
		mainMenu.addActionListener(this);

		// Layout.
		coursePanel = new JPanel(new GridLayout(5, 1));
		coursePanel.add(addCourse);
		coursePanel.add(mainMenu);
		add(coursePanel);
	}

	private void AddCourse() {

		// Course name entry label.
		JLabel courseNameLbl = new JLabel("Course name: ");
		courseNameField = new JTextField(10); // # of entry spaces.
		courseNameField.addActionListener(this);
		JLabel gradeTypesLbl = new JLabel("Select grade types:");

		// namePanel.
		JPanel namePanel = new JPanel(new GridLayout(2, 1));
		namePanel.add(courseNameLbl);
		namePanel.add(courseNameField);
		namePanel.add(gradeTypesLbl);

		// Grade types checkboxes - stored in a list.
		ArrayList<JCheckBox> gradeTypes = new ArrayList<JCheckBox>();
		// Grade type checkboxes.
		JCheckBox quizCheckBox = new JCheckBox("Quiz");
		gradeTypes.add(quizCheckBox);
		JCheckBox testCheckBox = new JCheckBox("Test");
		gradeTypes.add(testCheckBox);
		JCheckBox projectCheckBox = new JCheckBox("Project");
		gradeTypes.add(projectCheckBox);
		JCheckBox midtermCheckBox = new JCheckBox("Midterm");
		gradeTypes.add(midtermCheckBox);
		JCheckBox finalCheckBox = new JCheckBox("Final");
		gradeTypes.add(finalCheckBox);
		JCheckBox labCheckBox = new JCheckBox("Lab");
		gradeTypes.add(labCheckBox);

		// Grade types panel.
		JPanel gradeTypesPanel = new JPanel(new GridLayout(2,
				(gradeTypes.size() / 2)));
		for (int i = 0; i < gradeTypes.size(); i++) {
			// Add checkboxes to panel.
			gradeTypesPanel.add(gradeTypes.get(i));
		}

		// Cancel button.
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.setActionCommand("cancel");
		cancelBtn.addActionListener(this);

		// Next button.
		JButton doneBtn = new JButton("Next");
		doneBtn.setActionCommand("Next");
		doneBtn.addActionListener(this);

		// Nav panel.
		JPanel navPanel = new JPanel(new GridLayout(2, 1));
		navPanel.add(cancelBtn);
		navPanel.add(doneBtn);

		// Add all sub-panels to a super-panel.
		addCoursePanel = new JPanel(new GridLayout(3, 1));
		addCoursePanel.add(namePanel);
		addCoursePanel.add(gradeTypesPanel);
		addCoursePanel.add(navPanel);
		add(addCoursePanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		// Consider using Switch here **!!
		if (action.equals("mainMenu")) {
			setVisible(false);
			previousFrame.setVisible(true);
		}
		if (action.equals("cancel")) {
			addCoursePanel.setVisible(false);
			coursePanel.setVisible(true);
		}
		if (action.equals("addCourse")) {
			coursePanel.setVisible(false);
			AddCourse();
		}
		if (action.equals("next")) { // may need to rename.
			// Check that a valid course name was entered.
			String courseName = courseNameField.getText();
			if (courseName.equals("")) {
				JOptionPane.showMessageDialog(this,
						"Please enter a course name.", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {

				// Need this stuff after all info is entered.
				// JButton newCourseBtn = new JButton(courseName);
				// newCourseBtn.setActionCommand(courseName);
				// newCourseBtn.addActionListener(this);
				// coursePanel.add(newCourseBtn);
				// addCoursePanel.setVisible(false);
				// coursePanel.setVisible(true);
			}

		}

	}
}

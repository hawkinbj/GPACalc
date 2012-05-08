package calculator;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CourseDialog extends GUIPanel {

	private static final long serialVersionUID = -6080991655918955653L;
	protected JTextField courseNameField;
	private JPanel addCoursePanel, namePanel, gradeTypesPanel, navigationPanel;
	protected ArrayList<JCheckBox> gradeCheckBoxes;

	public CourseDialog(SystemController controller) {
		super(controller);
		controller.rootFrame.setSize(300, 300);
		gradeCheckBoxes = new ArrayList<JCheckBox>();
		System.out.println(this.getClass());

		// Course name entry label.
		JLabel courseNameLbl = new ;
		courseNameField = new JTextField(10); // # of entry spaces.

		JLabel gradeTypesLbl = new JLabel("Select grade types:");

		// namePanel.
		namePanel = new JPanel(new GridLayout(2, 1));
		namePanel.add(courseNameLbl);
		namePanel.add(courseNameField);
		namePanel.add(gradeTypesLbl);

		// Grade types panel.
		gradeTypesPanel = new JPanel(new GridLayout(3,
				(controller.gradeTypes.size() / 2)));
		for (String gradeType : controller.gradeTypes) {
			addGradeCheckBox(gradeType);
		}

		// Nav panel.
		navigationPanel = new JPanel();
		navigationPanel.setLayout(new BoxLayout(navigationPanel,
				BoxLayout.PAGE_AXIS));
		navigationPanel.add(new JLabel("Navigation"));
		navigationPanel.add(createButton("next", "Next"));
		navigationPanel.add(createButton("cancel", "Cancel"));

		// Add all sub-panels to a super-panel.
		addCoursePanel = new JPanel(new GridLayout(3, 1));
		addCoursePanel.add(namePanel);
		addCoursePanel.add(gradeTypesPanel);
		addCoursePanel.add(navigationPanel);
		add(addCoursePanel);
	}

	private void addGradeCheckBox(String name) {
		JCheckBox newCheckBox = new JCheckBox(name);
		newCheckBox.setName(name);
		gradeCheckBoxes.add(newCheckBox);
		gradeTypesPanel.add(newCheckBox);

	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("cancel")) {
			controller.showPanel("coursePanel", this);
		}
		addCourseLbl: if (action.equals("next")) {
			// Check that a valid course name was entered.
			String courseName = courseNameField.getText();
			CoursePanel previousFrame = (CoursePanel) controller.panels
					.get("coursePanel");
			// No course name entered.
			if (courseName.equals("")) {
				JOptionPane.showMessageDialog(this,
						"Please enter a course name.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			// Course already exists. Overwrite?
			else if (previousFrame.semester.getCourses()
					.containsKey(courseName)) {
				int response = JOptionPane
						.showConfirmDialog(null,
								"Course alread exists. Overwrite?", "Confirm",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					// Overwrite accomplished by comparing all elements of
					// previousFrame.coursePanel (there aren't many) with
					// courseName. A match means it's the component (button
					// name) to overwrite.
					for (Component component : previousFrame.coursePanel
							.getComponents()) {
						if (component.getName() != null) {
							if (component.getName().equals(courseName)) {

								previousFrame.semester.getCourses().remove(
										courseName);
								controller.saveUserList();
								previousFrame.coursePanel.remove(component);
								previousFrame.coursePanel.revalidate();
								previousFrame.coursePanel.repaint();
							}
						}
					}
				} else {
					// Do not overwrite.
					break addCourseLbl;
				}
			}
			// All clear to add new course and button.
			previousFrame.semester
					.addCourse(courseName, new Course(courseName));
			// Add new Grade objects for each checked gradtype box.
			for (JCheckBox checkBox : gradeCheckBoxes) {
				String gradeType = checkBox.getName();
				if (checkBox.isSelected()) {
					previousFrame.semester
							.getCourses()
							.get(courseName)
							.addGrade(gradeType,
									new Grade(gradeType, 0, 0, 0, 0, false));
				}
			}
			// Show next panel and save data.
			controller.addPanel(new CourseInfoPanel(controller,
					previousFrame.semester.getCourses().get(courseName)),
					"courseInfoPanel");
			previousFrame.coursePanel.add(previousFrame
					.createButton(courseName));
			controller.saveUserList();
			controller.showPanel("courseInfoPanel", this);
		}
		return;
	}
}
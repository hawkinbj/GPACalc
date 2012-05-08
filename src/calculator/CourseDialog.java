package calculator;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CourseDialog extends GUIPanel {

	private static final long serialVersionUID = -6080991655918955653L;
	protected JTextField courseNameField, newGradeTypeField;
	private JPanel namePanel, gradeTypesPanel, newGradeTypePanel,
			setGradePanel, navigationPanel;
	private JComboBox creditHrsComboBox, letterGradeComboBox;
	private Object[] letterGrades; // Will actually be of type String[]
	protected ArrayList<JCheckBox> gradeCheckBoxes;

	public CourseDialog(SystemController controller) {
		super(controller);
		// Convert GradingScale's gradingScaleMap to array of choices.
		letterGrades = controller.activeSchool.getGradingScale()
				.getGradingScaleMap().keySet().toArray();
		Arrays.sort(letterGrades); // not sure how it will sort...
		controller.rootFrame.setSize(300, 300);
		gradeCheckBoxes = new ArrayList<JCheckBox>();
		System.out.println(this.getClass());

		// Course name entry label.
		courseNameField = new JTextField(10); // # of entry spaces.
		// namePanel.
		namePanel = new JPanel(new GridLayout(3, 2));
		namePanel.add(new JLabel("Course name:"));
		namePanel.add(courseNameField);
		namePanel.add(new JLabel("Credit hours:"));
		creditHrsComboBox = new JComboBox(controller.CREDITHOURS);
		creditHrsComboBox.setSelectedItem("3");
		namePanel.add(creditHrsComboBox);
		namePanel.add(new JLabel("Select grade types:"));

		// Grade types panel.
		gradeTypesPanel = new JPanel(new GridLayout(
				(controller.gradeTypes.size() / 2) + 2, 2));
		for (String gradeType : controller.gradeTypes) {
			addGradeCheckBox(gradeType);
		}

		// New grade type panel.
		newGradeTypePanel = new JPanel(new GridLayout(1, 2));
		newGradeTypePanel.add(new JLabel("Add new type: "));
		newGradeTypeField = new JTextField(10);
		newGradeTypePanel.add(newGradeTypeField);

		// Final grade panel
		setGradePanel = new JPanel(new GridLayout(1, 2));
		setGradePanel.add(new JLabel("Set final letter grade"));
		letterGradeComboBox = new JComboBox(letterGrades);
		setGradePanel.add(letterGradeComboBox);

		// Nav panel.
		navigationPanel = new JPanel(new GridLayout(3, 1));
		navigationPanel.add(new JLabel("Navigation"));
		navigationPanel.add(createButton("next", "Next"));
		navigationPanel.add(createButton("cancel", "Cancel"));

		// layout.
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(namePanel);
		add(gradeTypesPanel);
		add(newGradeTypePanel);
		add(setGradePanel);
		add(navigationPanel);
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
			previousFrame.semester.addCourse(
					courseName,
					new Course(courseName, Integer
							.parseInt((String) creditHrsComboBox
									.getSelectedItem())));
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
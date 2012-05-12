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
	protected static final String[] CREDITHOURS = { "1", "2", "3", "4" };
	private Course course;

	public CourseDialog(SystemController controller) {
		super(controller);
		// Convert GradingScale's gradingScaleMap to array of choices.
		letterGrades = controller.activeSchool.getGradingScale()
				.getGradingScaleMap().keySet().toArray();
		Arrays.sort(letterGrades); // not sure how it will sort...
		controller.rootFrame.setSize(300, 300);
		gradeCheckBoxes = new ArrayList<JCheckBox>();
		System.out.println(this.getClass());
		addComponentsToPane();
	}

	private void addComponentsToPane() {

		// Initialize empty course.
		course = new Course(null, 0);

		// Course name entry label.
		courseNameField = new JTextField(10); // # of entry spaces.
		// namePanel.
		namePanel = new JPanel(new GridLayout(3, 2));
		namePanel.add(new JLabel("Course name:"));
		namePanel.add(courseNameField);
		namePanel.add(new JLabel("Credit hours:"));
		creditHrsComboBox = new JComboBox(CREDITHOURS);
		creditHrsComboBox.setSelectedItem("3");
		namePanel.add(creditHrsComboBox);
		namePanel.add(new JLabel("Select grade types:"));

		// Grade types panel.
		gradeTypesPanel = new JPanel();
		gradeTypesPanel.setLayout(new BoxLayout(gradeTypesPanel,
				BoxLayout.PAGE_AXIS));
		// gradeTypesPanel = new JPanel(new GridLayout(
		// (controller.gradeTypes.size() / 2) + 2, 2));
		for (String gradeType : course.getGradeTypes()) {
			addGradeCheckBox(gradeType);
		}

		newGradeTypePanel = new JPanel(new GridLayout(1, 2));
		newGradeTypeField = new JTextField(10);
		newGradeTypePanel.add(newGradeTypeField);
		newGradeTypePanel.add(createButton("add", "Add"));

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

	// Helper to add checkboxes to internal list and check status.
	private void addGradeCheckBox(String name) {
		JCheckBox newCheckBox = new JCheckBox(name);
		newCheckBox.setName(name);
		gradeCheckBoxes.add(newCheckBox);
		gradeTypesPanel.add(newCheckBox);

	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		CourseInfoPanel infoFrame = null;
		if (action.equals("cancel")) {
			System.out.println(controller.rootFrame.getPreviousPanel().getName());
			if (controller.rootFrame.getPreviousPanel().getClass().toString().contains("CourseInfo")) {
				infoFrame = (CourseInfoPanel) controller.rootFrame
						.getPanel("courseInfoPanel");
				controller.activeCourse = infoFrame.course;
				controller.rootFrame.showPanel("courseInfoPanel", this);
			} else {
				// Clear active course
				controller.activeCourse = null;
				controller.rootFrame.showPanel("coursePanel", this);
				controller.rootFrame.setSize(400, 400);
			}

		}
		if (action.equals("add")) {
			String newGradeType = newGradeTypeField.getText();
			// controller.addGradeType(newGradeTypeField.getText());
			addGradeCheckBox(newGradeType);
			validate();
			repaint();

		}
		addCourseLbl: if (action.equals("next")) {
			// Check that a valid course name was entered.
			String courseName = courseNameField.getText();
			CoursePanel previousFrame = (CoursePanel) controller.rootFrame
					.getPanel("coursePanel");
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
			course.setCourseName(courseName);
			course.setCreditHours(Integer.parseInt((String) creditHrsComboBox
					.getSelectedItem()));
			previousFrame.semester.addCourse(courseName, course);
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
			controller.rootFrame.addPanel(new CourseInfoPanel(controller,
					previousFrame.semester.getCourses().get(courseName)),
					"courseInfoPanel");
			previousFrame.coursePanel.add(previousFrame
					.createButton(courseName));
			controller.saveUserList();
			controller.rootFrame.showPanel("courseInfoPanel", this);
		}
		return;
	}
}
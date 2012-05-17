package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;

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
			navigationPanel, rootPanel;
	private JComboBox creditHrsComboBox;
	protected HashMap<String, JCheckBox> gradeCheckBoxes;
	protected static final String[] CREDITHOURS = { "0", "1", "2", "3", "4" };
	private Course course;
	private GridLayout layout;

	public CourseDialog(SystemController controller) {
		super(controller);
		gradeCheckBoxes = new HashMap<String, JCheckBox>();
		// Initialize course.
		course = controller.activeCourse;
		if (course == null) {
			System.out.println("Creating new course");
			course = new Course(null, -1);
			controller.activeCourse = course;
		}
		addComponentsToPane();
	}

	private void addComponentsToPane() {

		// Course name entry label.
		courseNameField = new JTextField(10); // # of entry spaces.
		courseNameField.setText(course.getCourseName());
		// namePanel.
		namePanel = new JPanel(new GridLayout(3, 2));
		namePanel.add(new JLabel("Course name:"));
		namePanel.add(courseNameField);
		namePanel.add(new JLabel("Credit hours:"));
		creditHrsComboBox = new JComboBox(CREDITHOURS);
		// Set credit hours if existing course.
		if (course.getCreditHours() == -1)
			creditHrsComboBox.setSelectedItem("3");
		else
			creditHrsComboBox.setSelectedItem(Integer.toString(course
					.getCreditHours()));

		namePanel.add(creditHrsComboBox);
		namePanel.add(new JLabel("Select grade types:"));

		// Grade types panel.
		gradeTypesPanel = new JPanel();
		
		//CLEAN THIS UP.
		layout = new GridLayout(
				((course.getGrades().keySet().size() + controller.gradeTypes
						.size()) / 2),
				2);
		gradeTypesPanel.setLayout(layout);
		for (String gradeType : controller.gradeTypes) {
			addGradeCheckBox(gradeType);
		}
		for (String gradeType : course.getGrades().keySet()) {
			// Add any additional custom gradetypes user may have added.
			if (!controller.gradeTypes.contains(gradeType))
				addGradeCheckBox(gradeType);
			// Set appropriate status of checkboxes.
			gradeCheckBoxes.get(gradeType).setSelected(true);
		}

		newGradeTypePanel = new JPanel(new GridLayout(1, 2));
		newGradeTypeField = new JTextField(10);
		newGradeTypePanel.add(newGradeTypeField);
		newGradeTypePanel.add(createButton("add", "Add"));

		// Nav panel.
		navigationPanel = new JPanel(new GridLayout(3, 1));
		navigationPanel.add(new JLabel("Navigation"));
		navigationPanel.add(createButton("next", "Next"));
		navigationPanel.add(createButton("cancel", "Cancel"));

		// layout.
		rootPanel = new JPanel();
		rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.PAGE_AXIS));
		rootPanel.add(namePanel);
		rootPanel.add(gradeTypesPanel);
		rootPanel.add(newGradeTypePanel);
		rootPanel.add(navigationPanel);
		add(rootPanel);
	}

	// Helper to add checkboxes to internal list and check status.
	private void addGradeCheckBox(String name) {
		JCheckBox newCheckBox = new JCheckBox(name);
		newCheckBox.setName(name);
		// Keep actual JCheckBoxes in list to iterate over values.
		gradeCheckBoxes.put(name, newCheckBox);
		gradeTypesPanel.add(newCheckBox);

	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("cancel")) {
			if (controller.rootFrame.getPreviousPanel().getName()
					.equals("courseInfoPanel")) {
				controller.rootFrame.showPanel("courseInfoPanel", this);
			} else {
				// Clear active course
				controller.activeCourse = null;
				controller.rootFrame
						.addPanel(new CoursePanel(controller), this);
			}
		} else if (action.equals("add")) {
			String newGradeType = newGradeTypeField.getText();
			// Check valid type name.
			if (newGradeType.equals("")
					|| course.getGrades().containsKey(newGradeType)) {
				JOptionPane
						.showMessageDialog(
								this,
								"That grade type already exists or name is blank. Try again.",
								"Error", JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				// For consistency, should probably
				// controller.rootFrame.addPanel() but it's relatively
				// expensive.
				addGradeCheckBox(newGradeType);
				newGradeTypeField.setText("");
				layout.setRows((course.getGrades().keySet().size() + controller.gradeTypes
						.size()) / 2);
				validate();
				repaint();
				controller.rootFrame.pack();
			}
		}
		addCourseLbl: if (action.equals("next")) {
			// Check that a valid course name was entered.
			String courseName = courseNameField.getText();
			// No course name entered.
			if (courseName.equals("")) {
				JOptionPane.showMessageDialog(this,
						"Please enter a course name.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			// Course already exists. Overwrite?
			else if (controller.activeSemester.getCourses().containsKey(
					courseName)) {
				int response = JOptionPane
						.showConfirmDialog(null,
								"Course already exists. Overwrite?", "Confirm",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					controller.activeSemester.getCourses().remove(courseName);
					controller.saveUserList();
				} else {
					// Do not overwrite.
					break addCourseLbl;
				}
			}
			// All clear to add new course and button.
			course.setCourseName(courseName);
			course.setCreditHours(Integer.parseInt((String) creditHrsComboBox
					.getSelectedItem()));
			// Add new Grade objects for each checked gradtype box.
			for (JCheckBox checkBox : gradeCheckBoxes.values()) {
				String gradeType = checkBox.getName();
				if (checkBox.isSelected()) {
					course.addGrade(gradeType, new Grade(gradeType, 0, 0, 0, 0,
							false));
				}
			}
			controller.activeSemester.addCourse(courseName, course);
			controller.activeCourse = course;
			controller.saveUserList();
			// Show next panel and save data.
			controller.rootFrame
					.addPanel(new CourseInfoPanel(controller), this);
		}
	}
}
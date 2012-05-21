package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class CourseDialog extends GUIPanel {

	private static final long serialVersionUID = -6080991655918955653L;
	protected JTextField courseNameField, newGradeTypeField;
	private JPanel namePanel, gradeTypesPanel, newGradeTypePanel,
			navigationPanel, weightedPanel;
	private JComboBox<String> creditHrsComboBox, letterGradeComboBox;
	protected HashMap<String, JCheckBox> gradeCheckBoxes;
	protected JRadioButton weightedRdio, notWeightedRdio;
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
		// createTitledBorder(namePanel, "")
		namePanel.add(new JLabel("Course name:"));
		namePanel.add(courseNameField);
		namePanel.add(new JLabel("Credit hours:"));
		creditHrsComboBox = new JComboBox<String>(CREDITHOURS);

		// Set credit hours if existing course.
		if (course.getCreditHours() == -1)
			creditHrsComboBox.setSelectedItem("3");
		else
			creditHrsComboBox.setSelectedItem(Integer.toString(course
					.getCreditHours()));
		namePanel.add(creditHrsComboBox);

		// Set final grade.
		namePanel.add(new JLabel("Set final grade: "));
		Object[] letterGrades = controller.activeSchool.getGradingScale()
				.getGradingScaleMap().keySet().toArray();
		Arrays.sort(letterGrades);
		letterGradeComboBox = new JComboBox(letterGrades);
		String finalGrade = course.getFinalGrade();

		if (finalGrade.equals("N/A"))
			letterGradeComboBox.setSelectedIndex(-1);
		else
			letterGradeComboBox.setSelectedItem(finalGrade);
		namePanel.add(letterGradeComboBox);

		// Grade types panel.
		gradeTypesPanel = new JPanel();
		createTitledBorder(gradeTypesPanel, "Select Grade Types");
		layout = new GridLayout(gradeCheckBoxes.size() / 2, 2);
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

		// Add new grade type panel.
		newGradeTypePanel = new JPanel(new GridLayout(1, 2));
		createTitledBorder(newGradeTypePanel, "New Grade Type");
		newGradeTypeField = new JTextField(10);
		newGradeTypePanel.add(newGradeTypeField);
		newGradeTypePanel.add(createButton("add", "Add"));

		// Use weighted grades or not.
		weightedPanel = new JPanel(new GridLayout(1, 3));
		// weightedPanel.add(new JLabel("Are grades weighted?: "));
		createTitledBorder(weightedPanel, "Are Grades Weighted?");
		ButtonGroup weightedGroup = new ButtonGroup();
		weightedRdio = new JRadioButton("Yes");
		notWeightedRdio = new JRadioButton("No", true);

		// If course is being edited, set weighted settings.
		if (course.getWeighted()) {
			weightedRdio.setSelected(true);
		}
		
		weightedGroup.add(weightedRdio);
		weightedGroup.add(notWeightedRdio);
		weightedPanel.add(weightedRdio);
		weightedPanel.add(notWeightedRdio);

		// Nav panel.
		navigationPanel = new JPanel(new GridLayout(2, 1));
		createTitledBorder(navigationPanel, "Navigation");
		navigationPanel.add(createButton("next", "Next"));
		navigationPanel.add(createButton("cancel", "Cancel"));

		// layout.
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(namePanel);
		add(gradeTypesPanel);
		add(newGradeTypePanel);
		add(weightedPanel);
		add(navigationPanel);
	}

	// Helper to add checkboxes to internal list and check status.
	private void addGradeCheckBox(String name) {
		JCheckBox newCheckBox = new JCheckBox(name);
		newCheckBox.setName(name);
		// Keep actual JCheckBoxes in list to iterate over values.
		gradeCheckBoxes.put(name, newCheckBox);
		layout.setRows(gradeCheckBoxes.size() / 2 + 1);
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
					|| gradeCheckBoxes.containsKey(newGradeType)) {
				JOptionPane
						.showMessageDialog(
								this,
								"That grade type already exists or name is blank. Try again.",
								"Error", JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				addGradeCheckBox(newGradeType);
				// Check the newly added box.
				gradeCheckBoxes.get(newGradeType).setSelected(
						true);
				newGradeTypeField.setText("");
				revalidate();
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
					// NEED TO ONLY OVERWRITE NEW INFO....
					controller.activeSemester.getCourses().remove(courseName);
					controller.saveUserList();
				} else {
					// Do not overwrite.
					break addCourseLbl;
				}
			}
			// All clear to add new course and button.
			course.setCourseName(courseName);
			course.setFinalGrade((String) letterGradeComboBox.getSelectedItem());
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
			// Set if course is weighted.
			if (weightedRdio.isSelected())
				course.setWeighted(true);
			else
				course.setWeighted(false);

			controller.activeSemester.addCourse(courseName, course);
			controller.saveUserList();

			// If final grade hasn't been set, take user to CourseInfoPanel.
			if (course.getFinalGrade().equals("N/A")) {
				controller.activeCourse = course;
				controller.rootFrame.addPanel(new CourseInfoPanel(controller),
						this);
			} else {
				controller.activeCourse = null;
				controller.rootFrame
						.addPanel(new CoursePanel(controller), this);
			}
		}
	}
}
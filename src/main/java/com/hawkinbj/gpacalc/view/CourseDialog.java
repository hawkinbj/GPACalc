package com.hawkinbj.gpacalc.view;

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

import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.Course;
import com.hawkinbj.gpacalc.model.GUIPanel;
import com.hawkinbj.gpacalc.model.Grade;

public class CourseDialog extends GUIPanel {
	private static final long serialVersionUID = -6080991655918955653L;
	protected JTextField courseNameField;
	protected JTextField newGradeTypeField;
	private JPanel namePanel;
	private JPanel gradeTypesPanel;
	private JPanel newGradeTypePanel;
	private JPanel navigationPanel;
	private JPanel weightedPanel;
	private JComboBox<String> creditHrsComboBox;
	private JComboBox<String> letterGradeComboBox;
	protected HashMap<String, JCheckBox> gradeCheckBoxes;
	protected JRadioButton weightedRdio;
	protected JRadioButton notWeightedRdio;
	protected static final String[] CREDITHOURS = { "0", "1", "2", "3", "4" };
	private Course course;
	private GridLayout layout;

	public CourseDialog(SystemController controller) {
		super(controller);
		this.gradeCheckBoxes = new HashMap<String, JCheckBox>();
		this.course = controller.getActiveCourse();

		if (this.course == null) {
			System.out.println("Creating new course");
			this.course = new Course(null, -1);
			controller.setActiveCourse(this.course);
		}

		addComponentsToPane();
	}

	private void addComponentsToPane() {
		this.courseNameField = new JTextField(10);
		this.courseNameField.setText(this.course.getCourseName());

		this.namePanel = new JPanel(new GridLayout(3, 2));

		this.namePanel.add(new JLabel("Course name:"));
		this.namePanel.add(this.courseNameField);
		this.namePanel.add(new JLabel("Credit hours:"));
		this.creditHrsComboBox = new JComboBox(CREDITHOURS);

		if (this.course.getCreditHours() == -1)
			this.creditHrsComboBox.setSelectedItem("3");
		else
			this.creditHrsComboBox.setSelectedItem(Integer.toString(this.course
					.getCreditHours()));
		this.namePanel.add(this.creditHrsComboBox);

		this.namePanel.add(new JLabel("Set final grade: "));
		Object[] letterGrades = this.controller.getActiveSchool()
				.getGradingScale().getGradingScaleMap().keySet().toArray();
		Arrays.sort(letterGrades);
		this.letterGradeComboBox = new JComboBox(letterGrades);
		String finalGrade = this.course.getFinalGrade();

		if (finalGrade.equals("N/A"))
			this.letterGradeComboBox.setSelectedIndex(-1);
		else
			this.letterGradeComboBox.setSelectedItem(finalGrade);
		
		this.namePanel.add(this.letterGradeComboBox);

		this.gradeTypesPanel = new JPanel();
		createTitledBorder(this.gradeTypesPanel, "Select Grade Types");
		this.layout = new GridLayout(this.gradeCheckBoxes.size() / 2, 2);
		this.gradeTypesPanel.setLayout(this.layout);

		for (String gradeType : this.controller.getGradeTypes()) {
			addGradeCheckBox(gradeType);
		}
		for (String gradeType : this.course.getGrades().keySet()) {
			if (!this.controller.getGradeTypes().contains(gradeType)) {
				addGradeCheckBox(gradeType);
			}
			((JCheckBox) this.gradeCheckBoxes.get(gradeType)).setSelected(true);
		}

		this.newGradeTypePanel = new JPanel(new GridLayout(1, 2));
		createTitledBorder(this.newGradeTypePanel, "New Grade Type");
		this.newGradeTypeField = new JTextField(10);
		this.newGradeTypePanel.add(this.newGradeTypeField);
		this.newGradeTypePanel.add(createButton("add", "Add"));

		this.weightedPanel = new JPanel(new GridLayout(1, 3));

		createTitledBorder(this.weightedPanel, "Are Grades Weighted?");
		ButtonGroup weightedGroup = new ButtonGroup();
		this.weightedRdio = new JRadioButton("Yes");
		this.notWeightedRdio = new JRadioButton("No", true);

		if (this.course.getWeighted()) {
			this.weightedRdio.setSelected(true);
		}

		weightedGroup.add(this.weightedRdio);
		weightedGroup.add(this.notWeightedRdio);
		this.weightedPanel.add(this.weightedRdio);
		this.weightedPanel.add(this.notWeightedRdio);

		this.navigationPanel = new JPanel(new GridLayout(2, 1));
		createTitledBorder(this.navigationPanel, "Navigation");
		this.navigationPanel.add(createButton("next", "Next"));
		this.navigationPanel.add(createButton("cancel", "Cancel"));

		setLayout(new BoxLayout(this, 3));
		add(this.namePanel);
		add(this.gradeTypesPanel);
		add(this.newGradeTypePanel);
		add(this.weightedPanel);
		add(this.navigationPanel);
	}

	private void addGradeCheckBox(String name) {
		JCheckBox newCheckBox = new JCheckBox(name);
		newCheckBox.setName(name);

		this.gradeCheckBoxes.put(name, newCheckBox);
		this.layout.setRows(this.gradeCheckBoxes.size() / 2 + 1);
		this.gradeTypesPanel.add(newCheckBox);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("cancel")) {
			if (this.controller.getRootFrame().getPreviousPanel().getName()
					.equals("courseInfoPanel")) {
				this.controller.getRootFrame().showPanel("courseInfoPanel",
						this);
			} else {
				this.controller.setActiveCourse(null);
				this.controller.getRootFrame().addPanel(
						new CoursePanel(this.controller), this);
			}
		} else if (action.equals("add")) {
			String newGradeType = this.newGradeTypeField.getText();

			if ((newGradeType.equals(""))
					|| (this.gradeCheckBoxes.containsKey(newGradeType))) {
				JOptionPane
						.showMessageDialog(
								this,
								"That grade type already exists or name is blank. Try again.",
								"Error", 0);
				return;
			}
			addGradeCheckBox(newGradeType);

			((JCheckBox) this.gradeCheckBoxes.get(newGradeType))
					.setSelected(true);
			this.newGradeTypeField.setText("");
			revalidate();
			repaint();
			this.controller.getRootFrame().pack();
		}

		if (action.equals("next")) {
			String courseName = this.courseNameField.getText();

			if (courseName.equals("")) {
				JOptionPane.showMessageDialog(this,
						"Please enter a course name.", "Error", 0);
				return;
			}

			if (this.controller.getActiveSemester().getCourses()
					.containsKey(courseName)) {
				int response = JOptionPane.showConfirmDialog(null,
						"Course already exists. Overwrite?", "Confirm", 0, 3);
				if (response == 0) {
					this.controller.getActiveSemester().getCourses()
							.remove(courseName);
					this.controller.saveUserList();
				}

			} else {
				this.course.setCourseName(courseName);
				this.course.setFinalGrade((String) this.letterGradeComboBox
						.getSelectedItem());
				this.course.setCreditHours(Integer
						.parseInt((String) this.creditHrsComboBox
								.getSelectedItem()));

				for (JCheckBox checkBox : this.gradeCheckBoxes.values()) {
					String gradeType = checkBox.getName();
					if (checkBox.isSelected()) {
						this.course.addGrade(gradeType, new Grade(gradeType, 0,
								0.0D, 0, 0, false));
					}
				}

				if (this.weightedRdio.isSelected())
					this.course.setWeighted(true);
				else {
					this.course.setWeighted(false);
				}
				this.controller.getActiveSemester().addCourse(courseName,
						this.course);
				this.controller.saveUserList();

				if (this.course.getFinalGrade().equals("N/A")) {
					this.controller.setActiveCourse(this.course);
					this.controller.getRootFrame().addPanel(
							new CourseInfoPanel(this.controller), this);
				} else {
					this.controller.setActiveCourse(null);
					this.controller.getRootFrame().addPanel(
							new CoursePanel(this.controller), this);
				}
			}
		}
	}
}
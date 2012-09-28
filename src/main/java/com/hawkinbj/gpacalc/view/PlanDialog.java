package com.hawkinbj.gpacalc.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.Course;
import com.hawkinbj.gpacalc.model.GUIPanel;

public class PlanDialog extends GUIPanel implements ActionListener {
	private static final long serialVersionUID = 8646422403970117818L;

	protected static final String[] CREDITHOURS = { "0", "1", "2", "3", "4" };

	protected JTextField courseNameField;

	protected JTextField newGradeTypeField;

	private JPanel namePanel;

	private JPanel navigationPanel;

	private JComboBox<String> creditHrsComboBox;

	public PlanDialog(SystemController controller) {
		super(controller);
		this.addComponentsToPane();
	}

	private void addComponentsToPane() {
		courseNameField = new JTextField(10);

		namePanel = new JPanel(new GridLayout(3, 2));
		namePanel.add(new JLabel("Course name:"));
		namePanel.add(courseNameField);
		namePanel.add(new JLabel("Credit hours:"));

		creditHrsComboBox = new JComboBox<String>(CREDITHOURS);
		creditHrsComboBox.setSelectedItem("3");

		namePanel.add(creditHrsComboBox);

		navigationPanel = new JPanel(new GridLayout(2, 1));
		navigationPanel.add(createButton("add", "Add"));
		navigationPanel.add(createButton("cancel", "Cancel"));

		createTitledBorder(navigationPanel, "Navigation");

		this.setLayout(new BoxLayout(this, 3));
		this.add(namePanel);
		this.add(navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		if (action.equals("cancel")) {
			controller.getRootFrame().showPanel("PlanPanel", this);
		} else if (action.equals("add")) {
			String courseName = this.courseNameField.getText();

			if ((courseName == "") || (courseName == null)) {
				JOptionPane.showMessageDialog(this,
						"Please enter a course name.", "Error", 0);
			}

			if (controller.getActiveUser().getCoursesToTake().keySet()
					.contains(courseName)) {
				JOptionPane.showMessageDialog(this, "Course already exists.",
						"Error", 0);
			} else {
				int creditHours = Integer
						.parseInt((String) this.creditHrsComboBox
								.getSelectedItem());

				controller.getActiveUser().addCourseToTake(
						new Course(courseName, creditHours));

				controller.saveActiveUser();

				controller.getRootFrame().addPanel(new PlanPanel(controller),
						this);
			}
		}
	}
}

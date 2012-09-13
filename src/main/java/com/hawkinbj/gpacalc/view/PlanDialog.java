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
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		this.courseNameField = new JTextField(10);

		this.namePanel = new JPanel(new GridLayout(3, 2));
		this.namePanel.add(new JLabel("Course name:"));
		this.namePanel.add(this.courseNameField);
		this.namePanel.add(new JLabel("Credit hours:"));

		this.creditHrsComboBox = new JComboBox(CREDITHOURS);
		this.creditHrsComboBox.setSelectedItem("3");

		this.namePanel.add(this.creditHrsComboBox);

		this.navigationPanel = new JPanel(new GridLayout(2, 1));
		createTitledBorder(this.navigationPanel, "Navigation");
		this.navigationPanel.add(createButton("add", "Add"));
		this.navigationPanel.add(createButton("cancel", "Cancel"));

		setLayout(new BoxLayout(this, 3));
		add(this.namePanel);
		add(this.navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("cancel")) {
			this.controller.getRootFrame().showPanel("planPanel", this);
		} else if (action.equals("add")) {
			String courseName = this.courseNameField.getText();

			if ((courseName == "") || (courseName == null)) {
				JOptionPane.showMessageDialog(this,
						"Please enter a course name.", "Error", 0);
			}

			if (this.controller.getActiveUser().getCoursesToTake().keySet()
					.contains(courseName)) {
				JOptionPane.showMessageDialog(this, "Course already exists.",
						"Error", 0);
			} else {
				int creditHours = Integer
						.parseInt((String) this.creditHrsComboBox
								.getSelectedItem());

				this.controller.getActiveUser().addCourseToTake(
						new Course(courseName, creditHours));

				this.controller.getRootFrame().addPanel(
						new PlanPanel(this.controller), this);
			}
		}
	}
}

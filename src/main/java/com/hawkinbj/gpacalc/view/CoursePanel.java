package com.hawkinbj.gpacalc.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.Course;
import com.hawkinbj.gpacalc.model.GUIPanel;

public class CoursePanel extends GUIPanel implements ActionListener {
	private static final long serialVersionUID = -6768153191699813450L;
	protected JPanel infoPanel;
	protected JPanel coursePanel;
	protected JPanel navigationPanel;

	public CoursePanel(SystemController controller) {
		super(controller);
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		this.infoPanel = new JPanel();
		this.infoPanel.setLayout(new BoxLayout(this.infoPanel, 3));
		createTitledBorder(this.infoPanel, this.controller.getActiveSemester()
				.getSchoolName() + " " + this.controller.getActiveSemester());

		JLabel creditHoursLbl = new JLabel("Credit hours: "
				+ Integer.toString(this.controller.getActiveSemester()
						.getTotalHoursAttempted()));
		this.infoPanel.add(creditHoursLbl);

		double gpa = this.controller.calcSemseterGPA();
		JLabel gpaLbl = new JLabel();

		if ((gpa == -1.0D) || (Double.isNaN(gpa)))
			gpaLbl.setText("Semester GPA: N/A");
		else {
			gpaLbl.setText("Semester GPA: "
					+ String.format("%.2f",
							new Object[] { Double.valueOf(gpa) }));
		}

		this.infoPanel.add(gpaLbl);

		this.coursePanel = new JPanel();
		this.coursePanel.setLayout(new BoxLayout(this.coursePanel, 3));
		createTitledBorder(this.coursePanel, "Select Course");

		if (this.controller.getActiveSemester().getCourses().size() == 0) {
			JLabel instructionLbl = new JLabel(
					"Add a new course to get started.");
			instructionLbl.setForeground(Color.blue);
			this.coursePanel.add(instructionLbl);
		} else {
			for (String key : this.controller.getActiveSemester().getCourses()
					.keySet()) {
				this.coursePanel.add(createButton(key));
			}

		}

		this.navigationPanel = new JPanel();
		this.navigationPanel.setLayout(new BoxLayout(this.navigationPanel, 3));
		createTitledBorder(this.navigationPanel, "Navigation");

		this.navigationPanel.add(createButton("addCourse", "Add new..."));

		this.navigationPanel.add(createButton("back", "Back"));

		setLayout(new BoxLayout(this, 3));
		add(this.infoPanel);
		add(this.coursePanel);
		add(this.navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("back")) {
			this.controller.setActiveSemester(null);
			this.controller.getRootFrame().addPanel(
					new SemesterPanel(this.controller), this);
		} else if (action.equals("addCourse")) {
			this.controller.getRootFrame().addPanel(
					new CourseDialog(this.controller), this);
		} else {
			this.controller.setActiveCourse(((Course) this.controller
					.getActiveSemester().getCourses().get(action)));
			this.controller.getRootFrame().addPanel(
					new CourseInfoPanel(this.controller), this);
		}
	}

	public void mouseClicked(MouseEvent e) {
		Component component = e.getComponent();

		if ((SwingUtilities.isRightMouseButton(e))
				&& (component.getName() != null)) {
			int response = JOptionPane.showConfirmDialog(this,
					"Are you sure you wish to remove this course?", "Confirm",
					0, 3);
			if (response == 0) {
				this.controller.getActiveSemester().getCourses()
						.remove(component.getName());
				this.controller.saveUserList();
				this.controller.getRootFrame().addPanel(
						new CoursePanel(this.controller), this);
			} else if (response != -1)
				;
		} else
			;
	}
}
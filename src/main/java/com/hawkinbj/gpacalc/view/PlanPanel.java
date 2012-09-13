package com.hawkinbj.gpacalc.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.Course;
import com.hawkinbj.gpacalc.model.GUIPanel;
import com.hawkinbj.gpacalc.model.Semester;

public class PlanPanel extends GUIPanel implements ActionListener {
	private static final long serialVersionUID = 8135873613951664946L;
	private String schoolName;
	private Map<String, Course> coursesToTake;
	protected JPanel infoPanel;
	protected JPanel coursesTakenPanel;
	protected JPanel coursesToTakePanel;
	protected JPanel navigationPanel;

	public PlanPanel(SystemController controller) {
		super(controller);
		this.schoolName = controller.getActiveSchool().getName();
		this.coursesToTake = controller.getActiveUser().getCoursesToTake();
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		this.infoPanel = new JPanel();
		this.infoPanel.setLayout(new BoxLayout(this.infoPanel, 3));

		createTitledBorder(this.infoPanel, this.schoolName);

		double gpa = this.controller.calcTranscriptGPA();
		JLabel gpaLbl = new JLabel();

		if (Double.isNaN(gpa))
			gpaLbl.setText("GPA: N/A");
		else {
			gpaLbl.setText("GPA: "
					+ String.format("%.2f",
							new Object[] { Double.valueOf(gpa) }));
		}
		this.infoPanel.add(gpaLbl);

		this.coursesTakenPanel = new JPanel();
		this.coursesTakenPanel.setLayout(new BoxLayout(this.coursesTakenPanel,
				3));

		this.createTitledBorder(this.coursesTakenPanel, "Courses Taken");

		TreeMap<String, Course> sortedCourseMap = new TreeMap<String, Course>();

		Iterator<Semester> localIterator = this.controller
				.getActiveTranscript().getSemesters().values().iterator();

		while (localIterator.hasNext()) {
			Semester s = (Semester) localIterator.next();
			sortedCourseMap.putAll(s.getCourses());
		}

		if (sortedCourseMap.size() == 0) {
			JLabel instructionLbl = new JLabel("Add a course to get started.");
			instructionLbl.setForeground(Color.blue);
			this.coursesTakenPanel.add(instructionLbl);
		} else {
			for (Course c : sortedCourseMap.values()) {
				this.coursesTakenPanel.add(createLabel(c.getCreditHours()
						+ " - " + c.getCourseName() + " [" + c.getFinalGrade()
						+ "]"));
			}
		}

		this.coursesToTakePanel = new JPanel();
		this.coursesToTakePanel.setLayout(new BoxLayout(
				this.coursesToTakePanel, 3));

		createTitledBorder(this.coursesToTakePanel, "Courses To Take");

		if (this.coursesToTake.size() == 0) {
			JLabel instructionLbl = new JLabel("Add a course to get started.");
			instructionLbl.setForeground(Color.blue);
			this.coursesToTakePanel.add(instructionLbl);
		} else {
			for (Course c : this.coursesToTake.values()) {
				this.coursesToTakePanel.add(createLabel(c.getCreditHours()
						+ " - " + c.getCourseName()));
			}
		}

		this.navigationPanel = new JPanel();
		this.navigationPanel.setLayout(new BoxLayout(this.navigationPanel, 3));
		this.navigationPanel.add(createButton("addCourse", "Add Course"));
		this.navigationPanel.add(createButton("back", "Back"));

		createTitledBorder(this.navigationPanel, "Navigation");

		setLayout(new BoxLayout(this, 3));
		add(this.infoPanel);
		add(this.coursesTakenPanel);
		add(this.coursesToTakePanel);
		add(this.navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("addCourse"))
			this.controller.getRootFrame().addPanel(
					new PlanDialog(this.controller), this);
		else if (action.equals("back"))
			this.controller.getRootFrame().showPanel("SemesterPanel", this);
	}
}
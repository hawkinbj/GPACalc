package calculator;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SemesterPanel extends GUIPanel {

	private static final long serialVersionUID = -3839021242565662981L;
	private String schoolName;
	protected JPanel infoPanel, semestersPanel, navigationPanel;

	public SemesterPanel(SystemController controller) {
		super(controller);
		schoolName = controller.activeSchool.getName();
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		// Info panel.
		infoPanel = new JPanel();
		// NEED TO FIGURE OUT HOW TO MAKE THIS CENTER AND MAX WIDTH
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
		createTitledBorder(infoPanel, schoolName);
		// GPA label.
		double gpa = controller.calcTranscriptGPA();
		JLabel gpaLbl = new JLabel();
		if (Double.isNaN(gpa))
			gpaLbl.setText("GPA: N/A");
		else
			gpaLbl.setText("GPA: "
					+ String.format("%.2f", gpa));
		infoPanel.add(gpaLbl);

		// Semester panel.
		semestersPanel = new JPanel();
		semestersPanel.setLayout(new BoxLayout(semestersPanel,
				BoxLayout.PAGE_AXIS));
		createTitledBorder(semestersPanel, "Select Semester");
		// Buttons to represent semesters. Sort them first.
		Object[] sortedSemesters = controller.activeTranscript.getSemesters()
				.keySet().toArray();
		// Show instructions if no semesters added yet.
		if (sortedSemesters.length == 0) {
			JLabel instructionLbl = new JLabel(
					"Add a new semester to get started.");
			instructionLbl.setForeground(Color.blue);
			semestersPanel.add(instructionLbl);
		} else {
			Arrays.sort(sortedSemesters, Collections.reverseOrder());
			for (Object semester : sortedSemesters) {
				semestersPanel.add(createButton((String) semester));
			}
		}

		// Navigation panel.
		navigationPanel = new JPanel();
		navigationPanel.setLayout(new BoxLayout(navigationPanel,
				BoxLayout.PAGE_AXIS));
		createTitledBorder(navigationPanel, "Navigation");
		// New school screen button.
		navigationPanel.add(createButton("newSemesterPanel", "Add new..."));
		// Back button.
		navigationPanel.add(createButton("back", "Back"));

		// Layout.
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(infoPanel);
		add(semestersPanel);
		add(navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("back")) {
			controller.activeTranscript = null;
			controller.rootFrame.showPanel("MainMenuPanel", this);
		} else if (action.equals("newSemesterPanel")) {
			controller.rootFrame.addPanel(new SemesterDialog(controller), this);
		} else {
			controller.activeSemester = controller.activeTranscript
					.getSemesters().get(action);
			controller.rootFrame.addPanel(new CoursePanel(controller), this);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Component component = e.getComponent();
		// If right click and a semester button.
		if (SwingUtilities.isRightMouseButton(e) && component.getName() != null) {
			int response = JOptionPane.showConfirmDialog(this,
					"Are you sure you wish to remove this semester?",
					"Confirm", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				controller.activeUser.getTranscripts().get(schoolName)
						.removeSemester(component.getName());
				controller.saveUserList();
				// Need to re-create as components have changed.
				controller.rootFrame.addPanel(new SemesterPanel(controller),
						this);
			}
		}
	}
}

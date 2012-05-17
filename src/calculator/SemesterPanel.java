package calculator;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class SemesterPanel extends GUIPanel {

	private static final long serialVersionUID = -3839021242565662981L;
	private String schoolName;
	protected Transcript transcript; // active transcript
	protected JPanel infoPanel, semestersPanel, navigationPanel;

	public SemesterPanel(SystemController controller) {
		super(controller);
		schoolName = controller.activeSchool.getName();
		transcript = controller.activeUser.getTranscripts().get(schoolName);
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		// Info panel.
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
		infoPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				schoolName, TitledBorder.CENTER, TitledBorder.TOP));
		// School label.
		// JLabel schoolLbl = new JLabel("School: " + schoolName + "\n");
		// schoolLbl.setForeground(Color.blue);
		// infoPanel.add(schoolLbl);
		// GPA label.
		double gpa = controller.calcTranscriptGPA();
		JLabel gpaLbl = new JLabel();
		if (gpa == -1 || Double.isNaN(gpa))
			gpaLbl.setText("GPA: N/A");
		else
			gpaLbl.setText("GPA: " + Double.toString(gpa));
		infoPanel.add(gpaLbl);

		// Semester panel.
		semestersPanel = new JPanel();
		semestersPanel.setLayout(new BoxLayout(semestersPanel,
				BoxLayout.PAGE_AXIS));
		// TitledBorder border = BorderFactory.createTitledBorder(
		// BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
		// "Choose a semester", TitledBorder.LEFT, TitledBorder.TOP);
		// semestersPanel.setBorder(border);
		semestersPanel.add(new JLabel("Choose semester:"));

		// Buttons to represent semesters.
		for (String key : transcript.getSemesters().keySet()) {
			semestersPanel.add(createButton(key));
		}

		// Navigation panel.
		navigationPanel = new JPanel();
		navigationPanel.setLayout(new BoxLayout(navigationPanel,
				BoxLayout.PAGE_AXIS));
		navigationPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				"Navigation", TitledBorder.LEFT, TitledBorder.TOP));

		// New school screen button.
		navigationPanel.add(createButton("newSemesterPanel", "Add new..."));
		// Back button.
		navigationPanel.add(createButton("back", "Back"));

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
			controller.activeSemester = transcript.getSemesters().get(action);
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
				semestersPanel.remove(component);
				semestersPanel.revalidate();
				semestersPanel.repaint();
			} else if (response == JOptionPane.CLOSED_OPTION) {
				return;
			}
		} else {
			return;
		}
	}
}

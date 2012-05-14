package calculator;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SemesterPanel extends GUIPanel {

	private static final long serialVersionUID = -3839021242565662981L;
	private String schoolName;
	protected Transcript transcript; // active transcript
	protected JPanel semestersPanel, navigationPanel;

	public SemesterPanel(SystemController controller) {
		super(controller);
		schoolName = controller.activeSchool.getName();
		transcript = controller.activeUser.getTranscripts().get(schoolName);
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		// Semesters panel.
		semestersPanel = new JPanel();
		semestersPanel.setLayout(new BoxLayout(semestersPanel,
				BoxLayout.PAGE_AXIS));
		// School label.
		JLabel schoolLbl = new JLabel("School: " + schoolName + "\n");
		schoolLbl.setForeground(Color.blue);
		semestersPanel.add(schoolLbl);
		// GPA label.
		semestersPanel
				.add(new JLabel("GPA: " + controller.calcTranscriptGPA()));
		// Instructions label.
		JLabel semestersInstructionLbl = new JLabel("Choose a semester:");
		semestersPanel.add(semestersInstructionLbl);
		// Buttons to represent semesters.
		for (String key : transcript.getSemesters().keySet()) {
			semestersPanel.add(createButton(key));
		}

		// Navigation label/separator.
		navigationPanel = new JPanel();
		navigationPanel.setLayout(new BoxLayout(navigationPanel,
				BoxLayout.PAGE_AXIS));
		navigationPanel.add(new JLabel("Navigation"));
		// New school screen button.
		navigationPanel.add(createButton("newSemesterPanel", "Add new..."));
		// Back button.
		navigationPanel.add(createButton("back", "Back"));

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(semestersPanel);
		add(navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("back")) {
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

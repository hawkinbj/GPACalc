package calculator;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainMenuPanel extends GUIPanel {

	private static final long serialVersionUID = 997821906993439522L;
	protected JPanel instructionPanel, schoolsPanel, navigationPanel;

	public MainMenuPanel(SystemController controller) {
		super(controller);

		controller.rootFrame.setSize(200, 300);
		// mainMenuPanel
		instructionPanel = new JPanel();
		instructionPanel.setLayout(new BoxLayout(instructionPanel,
				BoxLayout.PAGE_AXIS));
		instructionPanel.add(new JLabel("Instructions go here."));

		schoolsPanel = new JPanel();
		schoolsPanel
				.setLayout(new BoxLayout(schoolsPanel, BoxLayout.PAGE_AXIS));
		// Load active users information (if any) and display on screen.
		for (String schoolName : controller.activeUser.getTranscripts()
				.keySet()) {
			schoolsPanel.add(createButton(schoolName));
		}

		// Navigation label/separator.
		navigationPanel = new JPanel();
		navigationPanel.setLayout(new BoxLayout(navigationPanel,
				BoxLayout.PAGE_AXIS));
		navigationPanel.add(new JLabel("Navigation"));
		// New school screen button.
		navigationPanel.add(createButton("selectSchool", "Add new..."));

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(instructionPanel);
		add(schoolsPanel);
		add(navigationPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("selectSchool")) {
			controller.addPanel(new SchoolPanel(controller), "schoolPanel");
			controller.showPanel("schoolPanel", this);
		} else {
			// Set active school.
			controller.activeSchool = controller.activeUser.getTranscript(
					action).getSchool();
			controller.addPanel(new SemesterPanel(controller), "semesterPanel");
			controller.showPanel("semesterPanel", this);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Component component = e.getComponent();
		System.out.println(component.getName());
		// If right click and a school button.
		if (SwingUtilities.isRightMouseButton(e) && component.getName() != null) {
			int response = JOptionPane.showConfirmDialog(null,
					"Are you sure you wish to remove this school?", "Confirm",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				controller.activeUser.removeTranscript(component.getName());
				controller.saveUserList();
				instructionPanel.remove(component);
				instructionPanel.revalidate();
				instructionPanel.repaint();
			} else if (response == JOptionPane.CLOSED_OPTION) {
				return;
			}
		} else {
			return;
		}
	}
}

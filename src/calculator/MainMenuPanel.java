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

public class MainMenuPanel extends GUIPanel {

	private static final long serialVersionUID = 997821906993439522L;
	protected JPanel instructionPanel, schoolsPanel, navigationPanel;

	public MainMenuPanel(SystemController controller) {
		super(controller);
		addComponentsToPane();
	}

	private void addComponentsToPane() {

		// Schools panel.
		schoolsPanel = new JPanel();
		schoolsPanel
				.setLayout(new BoxLayout(schoolsPanel, BoxLayout.PAGE_AXIS));
		createTitledBorder(schoolsPanel, "Select School");
		// Load active users information (if any) and display on screen.
		if (controller.activeUser.getTranscripts().size() == 0) {
			JLabel instructionLbl = new JLabel(
					"Add a new school to get started.");
			instructionLbl.setForeground(Color.blue);
			schoolsPanel.add(instructionLbl);
		} else {
			for (String schoolName : controller.activeUser.getTranscripts()
					.keySet()) {
				schoolsPanel.add(createButton(schoolName));
			}
		}

		// Navigation label/separator.
		navigationPanel = new JPanel();
		navigationPanel.setLayout(new BoxLayout(navigationPanel,
				BoxLayout.PAGE_AXIS));
		createTitledBorder(navigationPanel, "Navigation");
		// New school screen button.
		navigationPanel.add(createButton("selectSchool", "Add new..."));

		// Layout.
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		// add(instructionPanel);
		add(schoolsPanel);
		add(navigationPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("selectSchool")) {
			controller.rootFrame.addPanel(new SchoolPanel(controller), this);
			// controller.rootFrame.showPanel("schoolPanel", this);
		} else {
			// Set active school.
			controller.activeTranscript = controller.activeUser
					.getTranscript(action);
			controller.activeSchool = controller.activeUser.getTranscript(
					action).getSchool();
			controller.rootFrame.addPanel(new SemesterPanel(controller), this);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Component component = e.getComponent();
		System.out.println(component.getName());
		// If right click and a school button.
		if (SwingUtilities.isRightMouseButton(e) && component.getName() != null) {
			int response = JOptionPane.showConfirmDialog(this,
					"Are you sure you wish to remove this school?", "Confirm",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				controller.activeUser.removeTranscript(component.getName());
				controller.saveUserList();
				controller.rootFrame.addPanel(new MainMenuPanel(controller),
						this);
			} else if (response == JOptionPane.CLOSED_OPTION) {
				return;
			}
		} else {
			return;
		}
	}
}

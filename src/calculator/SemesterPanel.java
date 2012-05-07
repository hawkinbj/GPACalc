package calculator;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class SemesterPanel extends GUIPanel {

	private static final long serialVersionUID = -3839021242565662981L;
	private String schoolName;
	protected Transcript transcript; // active transcript
	protected JPanel semestersPanel, navigationPanel;

	public SemesterPanel(SystemController controller, String schoolName) {
		super(controller);
		this.transcript = controller.activeUser.getTranscripts()
				.get(schoolName);
		this.schoolName = schoolName;
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
			controller.showPanel("mainMenu", this);
		}
		if (action.equals("newSemesterPanel")) {
			SemesterDialog newSemesterDialog = new SemesterDialog(controller);
		}
	}
}

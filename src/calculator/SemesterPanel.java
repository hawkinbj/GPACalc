package calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class SemesterPanel extends GUIPanel implements ActionListener {

	private static final long serialVersionUID = -3839021242565662981L;
	private MainMenuPanel previousFrame;
	private JTextField newSemesterField;
	private String schoolName;
	private Transcript transcript; // active transcript
	private JPanel semestersPanel;
	private JPopupMenu removeSchoolMenu;

	// private Transcript transcript;

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
		// Instructions label.
		JLabel semestersInstructionLbl = new JLabel("Choose a semester:");
		semestersPanel.add(semestersInstructionLbl);
		// Buttons to represent semesters.
		for (String key : transcript.getSemesters().keySet()) {
			semestersPanel.add(createButton(key));
		}

		semestersPanel.add(createButton("newSemesterPanel", "Add new..."));

		// Navigation label/separator.
		JLabel semesterNavLbl = new JLabel("Navigation");
		semestersPanel.add(semesterNavLbl);

		// Back button.
		semestersPanel.add(createButton("back", "Back"));
		add(semestersPanel);
	}

	// private void newSchoolMenu() {
	// // Label and textfield to enter new school name.
	// JLabel addSchoolInstructionLbl = new JLabel("School name: ");
	// newSchoolNameField = new JTextField(10); // int = entry spaces.
	// // Done button.
	// JButton doneBtn = new JButton("Done");
	// doneBtn.setActionCommand("done");
	// doneBtn.addActionListener(this);
	// // Layout.
	// newSchoolPanel = new JPanel(new GridLayout(5, 1));
	// newSchoolPanel.add(addSchoolInstructionLbl);
	// newSchoolPanel.add(newSchoolNameField);
	// newSchoolPanel.add(doneBtn);
	// add(newSchoolPanel);
	// semestersPanel.setVisible(false);
	// }

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("back")) {
			setVisible(false);
			previousFrame.setVisible(true);
		}

	}
}

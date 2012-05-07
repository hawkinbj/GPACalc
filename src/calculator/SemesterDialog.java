package calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SemesterDialog extends JDialog implements ActionListener {

	private JRadioButton fallRadioBtn, springRadioBtn;
	private JSpinner spinner;
	private SpinnerNumberModel yearModel;
	private SystemController controller;

	public SemesterDialog(SystemController controller) {
		this.controller = controller;

		// setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JPanel dialogPanel = new JPanel();
		dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.PAGE_AXIS));
		dialogPanel.add(new JLabel("Select a term:"));

		fallRadioBtn = new JRadioButton("Fall", false);
		fallRadioBtn.setActionCommand("radio");
		fallRadioBtn.addActionListener(this);
		springRadioBtn = new JRadioButton("Spring", false);
		springRadioBtn.addActionListener(this);

		ButtonGroup radioBtns = new ButtonGroup();
		radioBtns.add(fallRadioBtn);
		radioBtns.add(springRadioBtn);

		dialogPanel.add(fallRadioBtn);
		dialogPanel.add(springRadioBtn);

		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		yearModel = new SpinnerNumberModel(currentYear, currentYear - 10,
				currentYear + 10, 1);
		spinner = new JSpinner(yearModel);
		spinner.setEditor(new JSpinner.NumberEditor(spinner, "#"));
		dialogPanel.add(spinner);

		JButton doneBtn = new JButton("Done");
		doneBtn.setActionCommand("done");
		doneBtn.addActionListener(this);
		dialogPanel.add(doneBtn);

		add(dialogPanel);
		setVisible(true);
		setSize(300, 300);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("done")) {
			String semesterName = spinner.getValue() + " ";
			boolean validRadioSelection = false;
			if (fallRadioBtn.isSelected()) {
				semesterName += " Fall";
				validRadioSelection = true;
			}
			if (springRadioBtn.isSelected()) {
				semesterName += " Spring";
				validRadioSelection = true;
			}

			SemesterPanel previousFrame = (SemesterPanel) controller.panels
					.get("semesterPanel");
			if (previousFrame.transcript.getSemesters().containsKey(
					semesterName)
					|| !validRadioSelection) {
				JOptionPane
						.showMessageDialog(
								this,
								"That semester already exists or no term was selected.",
								"Error", JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				previousFrame.semestersPanel.add(previousFrame
						.createButton(semesterName));
				controller.showPanel("semesterPanel");
				previousFrame.transcript.addSemester(semesterName,
						new Semester(semesterName));
				controller.saveUserList();
				this.setVisible(false);
			}
		}
	}
}
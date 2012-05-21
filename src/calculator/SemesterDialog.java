package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class SemesterDialog extends GUIPanel {

	private static final long serialVersionUID = 4982367508399428981L;
	private JRadioButton fallRadioBtn, springRadioBtn, summerRadioBtn;
	private JSpinner spinner;
	private SpinnerNumberModel yearModel;
	private JPanel mainPanel, navigationPanel;

	public SemesterDialog(SystemController controller) {
		super(controller);
		addComponentsToPane();
	}

	private void addComponentsToPane() {

		//
		fallRadioBtn = new JRadioButton("Fall", true);
		springRadioBtn = new JRadioButton("Spring", false);
		summerRadioBtn = new JRadioButton("Summer", false);

		ButtonGroup radioBtns = new ButtonGroup();
		radioBtns.add(fallRadioBtn);
		radioBtns.add(springRadioBtn);
		radioBtns.add(summerRadioBtn);

		mainPanel = new JPanel(new GridLayout(4, 1));
		createTitledBorder(mainPanel, "Select Term");

		// Year spinner.
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		yearModel = new SpinnerNumberModel(currentYear, currentYear - 10,
				currentYear + 10, 1);
		spinner = new JSpinner(yearModel);
		spinner.setEditor(new JSpinner.NumberEditor(spinner, "#"));
		mainPanel.add(spinner);
		mainPanel.add(fallRadioBtn);
		mainPanel.add(springRadioBtn);
		mainPanel.add(summerRadioBtn);

		// add cancel button and navigation panel
		navigationPanel = new JPanel(new GridLayout(2, 1));
		createTitledBorder(navigationPanel, "Navigation");
		navigationPanel.add(createButton("done", "Done"));
		navigationPanel.add(createButton("cancel", "Cancel"));

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(mainPanel);
		add(navigationPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("cancel")) {
			controller.rootFrame.showPanel("SemesterPanel", this);
		}
		if (action.equals("done")) {
			Semester newSemester = new Semester(null, spinner.getValue()
					.toString());
			if (fallRadioBtn.isSelected())
				newSemester.setTerm("Fall");
			else if (springRadioBtn.isSelected())
				newSemester.setTerm("Spring");
			else if (summerRadioBtn.isSelected())
				newSemester.setTerm("Summer");
			if (controller.activeTranscript.getSemesters()
					.containsKey(newSemester.toString())) {
				JOptionPane
						.showMessageDialog(
								this,
								"That semester already exists or no term was selected.",
								"Error", JOptionPane.ERROR_MESSAGE);
			} else {

				controller.activeTranscript.addSemester(newSemester.toString(),
						newSemester);
				controller.saveUserList();
				// Re-create previous panel with update info.
				controller.rootFrame.addPanel(new SemesterPanel(controller),
						this);
			}
		}
	}
}
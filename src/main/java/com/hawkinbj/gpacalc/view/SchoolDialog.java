package com.hawkinbj.gpacalc.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.GUIPanel;
import com.hawkinbj.gpacalc.model.GradingScale;

public class SchoolDialog extends GUIPanel {
	private static final long serialVersionUID = -873283817151884443L;
	private JPanel navigationPanel;
	private JPanel newSchoolPanel;
	private JPanel radioPanel;
	private JTextField newSchoolField;
	private JRadioButton plusMinusRadio;
	private JRadioButton normalRadio;

	public SchoolDialog(SystemController controller) {
		super(controller);
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		this.newSchoolPanel = new JPanel(new GridLayout(1, 2));

		createTitledBorder(this.newSchoolPanel, "Name of School");
		this.newSchoolField = new JTextField(5);
		this.newSchoolPanel.add(this.newSchoolField);

		this.radioPanel = new JPanel(new GridLayout(1, 2));
		createTitledBorder(this.radioPanel, "Grading Scale");
		ButtonGroup addSchoolBtns = new ButtonGroup();
		this.plusMinusRadio = new JRadioButton("Plus/Minus", true);
		this.normalRadio = new JRadioButton("Normal");
		addSchoolBtns.add(this.plusMinusRadio);
		addSchoolBtns.add(this.normalRadio);
		this.radioPanel.add(this.plusMinusRadio);
		this.radioPanel.add(this.normalRadio);

		this.navigationPanel = new JPanel(new GridLayout(2, 1));
		createTitledBorder(this.navigationPanel, "Navigation");
		this.navigationPanel.add(createButton("add", "Add"));
		this.navigationPanel.add(createButton("cancel", "Cancel"));

		setLayout(new BoxLayout(this, 3));

		add(this.newSchoolPanel);
		add(this.radioPanel);
		add(this.navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("cancel")) {
			this.controller.getRootFrame().showPanel("SchoolPanel", this);
		} else if (action.equals("add")) {
			String newSchoolName = this.newSchoolField.getText();
			if ((newSchoolName == null)
					|| (!this.controller.addSchool(newSchoolName,
							new GradingScale(this.plusMinusRadio.isSelected()))))
				JOptionPane.showMessageDialog(this,
						"That school already exists or no name was entered.",
						"Error", 0);
			else
				this.controller.getRootFrame().addPanel(
						new SchoolPanel(this.controller), this);
		}
	}
}
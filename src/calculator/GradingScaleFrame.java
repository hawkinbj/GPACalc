package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GradingScaleFrame extends GUIPanel  {

	private static final long serialVersionUID = 2825014072171370846L;
	private JFrame previousFrame;
	private JTextArea instructionArea;
	private JComboBox scaleBox;
	private JPanel panel;
	private JButton backButton, submitButton;

	public GradingScaleFrame(SystemController controller) {
		super(controller);
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		// Instruction label
		instructionArea = new JTextArea();
		instructionArea
				.setText("Select the desired grading scale.\nClick \"Submit\" when done.\n");
		instructionArea.setEditable(false);
		instructionArea.setCursor(null);
		instructionArea.setOpaque(false);
		instructionArea.setFocusable(false);

		// back button
		backButton = new JButton("Back");
		backButton.setActionCommand("back");
		backButton.addActionListener(this);

		// register button
		submitButton = new JButton("Submit");
		submitButton.setActionCommand("submit");
		submitButton.addActionListener(this);

		// Combobox
		String[] scales = { "5 point scale (A = 95-99)",
				"7 point scale (A = 93-99)", "10 point scale (A = 90-99)" };
		scaleBox = new JComboBox(scales);
		scaleBox.setSelectedIndex(2);
		scaleBox.setActionCommand("scale");
		scaleBox.addActionListener(this);

		// Layout.
		panel = new JPanel(new GridLayout(6, 1));
		panel.add(instructionArea);
		panel.add(scaleBox);
		panel.add(backButton);
		panel.add(submitButton);
		// setDefaultScale();
		add(panel);

	}

	private void setDefaultScale() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("submit")) {

		}
		// open previous menu if back button pressed
		if (action.equals("back")) {
			setVisible(false);
			previousFrame.setVisible(true);
		}
	}
}
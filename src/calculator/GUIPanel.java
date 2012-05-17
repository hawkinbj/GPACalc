package calculator;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUIPanel extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1317154632755304524L;
	protected SystemController controller;

	public GUIPanel(SystemController controller) {
		this.controller = controller;
		this.setMinimumSize(new Dimension(200, 200));
		// Auto-name instances.
		this.setName(this.getClass().getSimpleName());
	}

	// Helper method to facilitate creating buttons. This version allows
	// specification of button display text and action command.
	protected JButton createButton(String btnName, String btnTxt) {
		JButton newBtn = new JButton(btnTxt);
		newBtn.setActionCommand(btnName);
		// newBtn.setName(btnName);
		newBtn.addMouseListener(this);
		newBtn.addActionListener(this);
		newBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
		newBtn.setMaximumSize(new Dimension(200, 50));
		return newBtn;
	}

	// Used to add courses, semesters, schools to screen.
	protected JButton createButton(String btnName) {
		JButton newBtn = new JButton(btnName);
		newBtn.setActionCommand(btnName);
		newBtn.setName(btnName);
		newBtn.addMouseListener(this);
		newBtn.addActionListener(this);
		newBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
		newBtn.setMaximumSize(new Dimension(200, 50));
		return newBtn;
	}

	protected void createTitledBorder(JPanel panel, String borderTxt) {
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				borderTxt, TitledBorder.LEFT, TitledBorder.TOP));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}

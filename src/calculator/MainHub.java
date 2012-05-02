package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MainHub extends JFrame implements ActionListener {

	private SystemController controller;
	protected JPanel mainMenuPanel;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem newAction, openAction;
	private JButton selectSchool;

	public MainHub(SystemController controller) {

		setSize(300, 300);
		this.controller = controller;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Main Menu");

		// Menu.
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Define and add two drop down menu to the menubar.
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		// Create and add menu item to drop down menus.
		JMenuItem newAction = new JMenuItem("New");
		JMenuItem openAction = new JMenuItem("Open");
		fileMenu.add(newAction);
		fileMenu.add(openAction);

		// Add school button.
		selectSchool = new JButton("Add School...");
		selectSchool.setActionCommand("selectSchool");
		selectSchool.addActionListener(this);

		// Layout.
		mainMenuPanel = new JPanel(new GridLayout(3, 1));
		mainMenuPanel.add(selectSchool);
		add(mainMenuPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("selectSchool")) {
			new SelectSchoolFrame(controller, this).setVisible(true);
			setVisible(false);
		}
	}
}

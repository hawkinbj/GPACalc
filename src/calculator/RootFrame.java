/* TODO
 * 
 * 
 * 
 * 
 */

package calculator;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class RootFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = -1406044665804707164L;
	protected Container contentPane;
	private SystemController controller;
	private HashMap<String, GUIPanel> panels;
	private String previous, current;

	public RootFrame(SystemController controller) {
		this.controller = controller;
		setDefaultLookAndFeelDecorated(true);
		setTitle("GPACalc");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(200, 300));
		this.setLocation(600, 200);
		contentPane = getContentPane();
		contentPane.setLayout(new CardLayout());
		panels = new HashMap<String, GUIPanel>();
		previous = current = null;
		addComponentsToPane();
		setVisible(true);
	}

	private void addComponentsToPane() {
		// Menu.
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Define and add two drop down menu to the menubar.
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		// Create and add menu item to drop down menus.
		JMenuItem newAction = new JMenuItem("New");
		JMenuItem logOut = new JMenuItem("Log out");
		logOut.setActionCommand("logOut");
		logOut.addActionListener(this);
		fileMenu.add(newAction);
		fileMenu.add(logOut);
	}

	protected HashMap<String, GUIPanel> getAllPanels() {
		return panels;
	}

	protected void clearAllPanels() {
		panels.clear();
	}

	protected GUIPanel getPanel(String panelName) {
		return panels.get(panelName);
	}

	// Adds new panel to rootFrame and stores in map.
	protected void addPanel(GUIPanel panelToAdd, GUIPanel panelToHide) {
		String panelToAddName = panelToAdd.getName();
		panels.put(panelToAddName, panelToAdd);
		showPanel(panelToAddName, panelToHide);
	}

	// I want to GET RID OF THIS but can't figure out how (CourseDialog
	// dependency).
	protected GUIPanel getPreviousPanel() {
		return panels.get(previous);
	}

	protected void showPanel(String newPanelName, GUIPanel panelToHide) {
		System.out.println(newPanelName); // For navigation debugging.
		previous = current;
		current = newPanelName;
		// This is necessary to properly display panels.
		contentPane.remove(panelToHide);
		contentPane.add(panels.get(newPanelName), newPanelName);
		panelToHide.setVisible(false);
		panels.get(newPanelName).repaint();
		panels.get(newPanelName).setVisible(true);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("logOut")) {
			controller.activeUser = null;
			panels.clear();
			contentPane.removeAll();
			addPanel(new WelcomePanel(controller), new GUIPanel(controller));
		}
	}
}

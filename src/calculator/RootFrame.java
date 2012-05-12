/* TODO
 * 
 * -create an auto-resize method.
 * 
 * 
 */

package calculator;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class RootFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = -1406044665804707164L;
	private Container contentPane;
	private SystemController controller;
	private HashMap<String, GUIPanel> panels;
	private String previous, current;
	//private LinkedList<GUIPanel> panelsList;

	public RootFrame(SystemController controller) {
		this.controller = controller;
		setTitle("GPACalc");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(200, 300);
		this.setLocation(600, 200);
		contentPane = getContentPane();
		contentPane.setLayout(new CardLayout());
		panels = new HashMap<String, GUIPanel>();
		previous = current = null;
		//panelsList = new LinkedList<GUIPanel>();
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
	protected void addPanel(GUIPanel panel, String name) {
		contentPane.add(panel, name);
		panels.put(name, panel);
		contentPane.validate();
	}

	protected GUIPanel getPreviousPanel() {
		return panels.get(previous);
	}

	protected void showPanel(String newPanelName, GUIPanel panelToHide) {
		setSize(200, 300);
		previous = current;
		current = newPanelName;
		panelToHide.setVisible(false);
		panels.get(newPanelName).setVisible(true);
		contentPane.validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("logOut")) {
			controller.activeUser = null;
			panels.clear();
			addPanel(new WelcomePanel(controller), "welcome");
			CardLayout cl = (CardLayout) contentPane.getLayout();
			cl.show(contentPane, "welcome");
		}
	}
}

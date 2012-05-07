package calculator;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.io.*;

import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class SystemController {

	protected final String ROOTDIR = System.getenv("APPDATA") + "\\GPACalcJava";
	protected User activeUser;
	protected HashMap<String, User> users;
	protected HashMap<String, School> schools; // List of known schools.
	protected JFrame rootFrame; // Top level container.
	protected Container contentPane; // Top level Container - add panels to
										// this.
	// Panel manager - consider making a new class.
	protected HashMap<String, GUIPanel> panels;

	public SystemController() {

		activeUser = null;
		users = new HashMap<String, User>();
		schools = new HashMap<String, School>();
		panels = new HashMap<String, GUIPanel>();

		if (!new File(ROOTDIR).exists()) {
			new File(ROOTDIR).mkdir();
			populateSchools();
		} else {
			loadSchoolList();
			loadUserList();
		}

		rootFrame = new JFrame("GPACalc");
		rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		rootFrame.setSize(300, 300);
		rootFrame.setVisible(true);
		contentPane = rootFrame.getContentPane();
		contentPane.setLayout(new CardLayout());
		// Menu.
		JMenuBar menuBar = new JMenuBar();
		rootFrame.setJMenuBar(menuBar);

		// Define and add two drop down menu to the menubar.
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		// Create and add menu item to drop down menus.
		JMenuItem newAction = new JMenuItem("New");
		JMenuItem openAction = new JMenuItem("Open");
		fileMenu.add(newAction);
		fileMenu.add(openAction);

		addPanel(new WelcomePanel(this), "welcome");
	}

	// Adds new panel to rootFrame and stores in map.
	protected void addPanel(GUIPanel panel, String name) {
		contentPane.add(panel, name);
		panels.put(name, panel);
		contentPane.validate();
	}

	protected void showPanel(String name, GUIPanel panelToHide) {
		panelToHide.setVisible(false);
		CardLayout cl = (CardLayout) contentPane.getLayout();
		cl.show(contentPane, name);
		contentPane.validate();
	}

	// Populates list of known schools. Only run on the first execution of
	// program. Probably should be part of the installer if one is ever made...
	private void populateSchools() {
		schools.put("GMU", new School("GMU"));
		schools.put("UTSA", new School("UTSA"));
		schools.put("TNCC", new School("TNCC"));
		schools.put("ODU", new School("ODU"));
		saveSchoolList();
	}

	protected boolean addSchool(String name) {
		if (schools.containsKey(name) || name.equals(""))
			return false;
		schools.put(name, new School(name));
		saveSchoolList();
		return true;
	}

	protected void removeSchool(String name) {
		// Should probably check it's valid.
		schools.remove(name);
	}

	private void saveSchoolList() {
		try {
			FileOutputStream fos = new FileOutputStream(ROOTDIR + "\\schools");
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(schools);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void loadSchoolList() {
		try {
			FileInputStream fis = new FileInputStream(ROOTDIR + "\\schools");
			ObjectInputStream in = new ObjectInputStream(fis);
			schools = (HashMap<String, School>) in.readObject();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Returns true is successfully registered user, false otherwise.
	protected boolean register(String username, String password) {
		// returns false if username not available
		if (users.containsKey(username))
			return false;
		User user = new User(username, password);
		users.put(username, user); // add new user to persistent user list.
		activeUser = user;
		saveUserList();
		return true;
	}

	// Returns true if valid user/pass for login, false otherwise.
	protected boolean login(String username, String password) {
		if (users.containsKey(username)) {
			User user = users.get(username);
			if (user.checkPassword(password)) {
				activeUser = user;
				return true;
			}
		}
		// Invalid user/pass.
		return false;
	}

	// Can be used anywhere but ALWAYS called on exit.
	protected void saveUserList() {
		try {
			FileOutputStream fos = new FileOutputStream(ROOTDIR + "\\userlist");
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(users);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void loadUserList() {
		try {
			FileInputStream fis = new FileInputStream(ROOTDIR + "\\userlist");
			ObjectInputStream in = new ObjectInputStream(fis);
			users = (HashMap<String, User>) in.readObject();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
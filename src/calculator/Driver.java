package calculator;

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		final SystemController controller = new SystemController();
		WelcomeFrame myWindow = new WelcomeFrame(controller);
		myWindow.setVisible(true);

		// FOR TESTING TO SKIP LOGIN -
		//controller.register("test", "test");
		//new MainHub(controller).setVisible(true);

	}
}

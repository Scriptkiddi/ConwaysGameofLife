import java.awt.Dimension;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class ConwaysGameOfLife {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		try {
			//Standart Design von Java ändern
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		//verzichtet auf besseres design
		//http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6833947
		//da sonst bugs auftreten
		
		DatenHandler datenHandler = new DatenHandler(); //DatenHandler erstellen
		FunktionsHandler funktionsHandler = new FunktionsHandler(datenHandler); //funktionshandler erstellen
		new GuiHandler(funktionsHandler, new Dimension(800, 600)); //Gui Handler erstellen

	}
}

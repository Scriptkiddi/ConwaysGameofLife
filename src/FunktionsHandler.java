import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

public class FunktionsHandler {

	private Timer timer;
	private TimerTask task;
	private int generationsnummer = 0; //Dient dazu Generationen zuzaehlen
	private int levelnummer = 0; //nummer des aktuellen levels
	private DatenHandler datenhandler;
	private int gamemode = 0; //Game Modus 0=Endlos 1=Level 2=LevelEditor
	private Level activeLevel;
	private int lebendeZellen;
	private int verbleibendeZusetzendeZellen;

	/**
	 * @param args
	 */
	public FunktionsHandler(DatenHandler datenhandler) {
		this.datenhandler = datenhandler;
	}

	//Startet den Level Modus und das Dazugehörige Grid
	public void startLevel(GuiHandler gui) {
		gui.hideHauptmenue();
		this.gamemode = 1;
		gui.initLevelSelection();
		gui.showLevelSelection();
	}
	
	//Startet den Endlos Modus mit Grid
	public void startEndlos(GuiHandler gui) {
		gui.hideHauptmenue();
		this.gamemode = 0;
		gui.initGameField(this.gamemode);
		gui.displayGameField();
	}
	
	//Minimiert alle Fenster und Maxmiert das Hauptmenü
	public void gotoMainMenu(GuiHandler gui) {
		if (gui.getGameField() != null && gui.getGameField().isShowing()) {
			gui.hideGameField();
		}
		if (gui.getSettings() != null && gui.getSettings().isShowing()) {
			gui.hideSettings();
		}
		gui.displayHauptmenue();
		gui.focusHauptmenu();
	}

	//Simuliert einen Tick des Grids
	//wird wiederholt aufgerufen
	public void simulation(ArrayList<ArrayList<GameField_Grid_Cell>> grid_y,
			GuiHandler gui) {
		//Liste für all die Zellen die in der nächsten Generation leben werden und noch nicht Leben
		ArrayList<GameField_Grid_Cell> setAlive = new ArrayList<GameField_Grid_Cell>();
		
		//Liste für alle Zellen die im Moment Leben und in der Nächsten Generation tot sind
		ArrayList<GameField_Grid_Cell> setDead = new ArrayList<GameField_Grid_Cell>();
		
		//Geht das Grid durch und fordert von jeder Zelle den neuen Status an
		for (ArrayList<GameField_Grid_Cell> grid_x : grid_y) {
			for (GameField_Grid_Cell cell : grid_x) {
				boolean oldState = cell.getState();
				boolean newState = cell.calculateState(grid_x.indexOf(cell),
						grid_y, grid_x);
				if (newState != oldState){ // Kontrolliert ob der Status geändert werden muss
					if (newState) {
						setAlive.add(cell);
					} else {
						setDead.add(cell);
					}
				}
			}
		}
		
		//Reanimiert alle nötigen zellen
		for (GameField_Grid_Cell cell : setAlive) {
			cell.setStateAlive();
		}
		
		//tötet alle Zellen die nötig sind
		for (GameField_Grid_Cell cell : setDead) {
			cell.setStateDead();
		}
		//Ließt die Zahl an Lebenden Zellen aus dem Grid
		lebendeZellen = gui.getGameField().getGrid().getNumberofCellsAlive();

		gui.getGameField().getSidebar().setLebendeZellen(lebendeZellen);
		gui.getGameField().getSidebar().setGeneration(generationsnummer++);

		if (gamemode == 1) { //Extra Berechnungen im Fall des Level Modus
			for (GameField_Grid_Cell cell : setAlive) { // Damit man nicht die
														// Zellen nach einer
														// Generation farmen
														// kann werden diese
														// geblock für use
				cell.setLevelCell(true);
			}

			for (GameField_Grid_Cell cell : setDead) {
				cell.setLevelCell(true);
			}
			
			//Vergleichs Abschnitt wo kontrolliet wird ob das Level Ziel erreicht wurde
			if (generationsnummer >= this.activeLevel.getGenerationGoal()) {
				// String contains da das Lesen von Strings immer sehr
				// komisch ist
				// durch die ganzen escape Zeichen
				if (this.activeLevel.getVergleichsOperator().contains(">")) { // Mehr als
					if (this.activeLevel.getCellsAlive() <= this.lebendeZellen) {//Konrtollieren ob Ziel erreicht ist 
						JOptionPane.showMessageDialog(null, "Level Geschafft.",//Falls erreicht Meldung und nächstes Level laden
								"Erfolgreich", JOptionPane.INFORMATION_MESSAGE);
						stopSimulation();
						this.datenhandler
								.writelevelAccess(this.levelnummer + 1);
						loadLevel(this.levelnummer + 1, gui);
					} else {//Falls nicht Meldung
						JOptionPane.showMessageDialog(null,
								"Level Nicht geschafft.", "Game Over",
								JOptionPane.ERROR_MESSAGE);
						stopSimulation();
						resetLevel(gui);
					}
			
				} else if (this.activeLevel.getVergleichsOperator().contains(
						"=")) { // genau
					if (this.activeLevel.getCellsAlive() == this.lebendeZellen) {//Konrtollieren ob Ziel erreicht ist
						JOptionPane.showMessageDialog(null, "Level Geschafft.", //Falls erreicht Meldung und nächstes Level laden
								"Erfolgreich", JOptionPane.INFORMATION_MESSAGE);
						stopSimulation();
						this.datenhandler
								.writelevelAccess(this.levelnummer + 1);
						loadLevel(this.activeLevel.getLevelnummer() + 1, gui);
					} else { //Falls nicht Meldung
						JOptionPane.showMessageDialog(null,
								"Level Nicht geschafft.", "Game Over",
								JOptionPane.ERROR_MESSAGE);
						stopSimulation();
						resetLevel(gui);
					}
				} else if (this.activeLevel.getVergleichsOperator().contains(
						"<")) { // Weniger als
					if (this.activeLevel.getCellsAlive() <= this.lebendeZellen) {//Konrtollieren ob Ziel erreicht ist
						JOptionPane.showMessageDialog(null, "Level Geschafft.",//Falls erreicht Meldung und nächstes Level laden
								"Erfolgreich", JOptionPane.INFORMATION_MESSAGE);
						stopSimulation();
						this.datenhandler
								.writelevelAccess(this.levelnummer + 1);
						loadLevel(this.activeLevel.getLevelnummer() + 1, gui);
					} else {//Falls nicht Meldung
						JOptionPane.showMessageDialog(null,
								"Level Nicht geschafft.", "Game Over",
								JOptionPane.ERROR_MESSAGE);
						stopSimulation();
						resetLevel(gui);
					}
				}
			}
		}
	}

	public void startSimulation(int zeit, final GuiHandler gui) {
		this.timer = new Timer(); //Erstelle neuen Timer
		this.task = new TimerTask() { //erstelle neue Timer Task
			public void run() {
				simulation(gui.getGameField().getGrid().getGrid_y(), gui);
			}
		};
		this.timer.scheduleAtFixedRate(this.task, 0, zeit); //Lasse den Timer absofort die Timer Task ausführen
	}

	//Beendet die akutelle simulation
	public void stopSimulation() {
		this.timer.cancel();
	}

	public int getGenerationsnummer() {
		return generationsnummer;
	}

	public void setGenerationsnummer(int generationsnummer) {
		this.generationsnummer = generationsnummer;
	}

	//lade die Gui für den Level Modus
	public void loadLevelGui(int levelnummer, GuiHandler guiHandler) {
		guiHandler.hideHauptmenue();
		guiHandler.hideLevelSelection();
		guiHandler.initGameField(1);
		guiHandler.displayGameField();
		loadLevel(levelnummer, guiHandler);
	}

	public void loadLevel(int levelnummer, GuiHandler guiHandler) {
		// Alle Werte werden zur SIcherhiet auf Null gesetzt
		if(levelnummer <= this.getLevelAnzahl()){ //Falls das höchste Level erreicht wurde
			this.generationsnummer = 0;
			this.lebendeZellen = 0;
	
			// Buttons werden zurück gesetzt
			guiHandler.getGameField().getSidebar().getBtnStop().setVisible(false);
			guiHandler.getGameField().getSidebar().getBtnPlay().setVisible(true);
	
			// Leere alle Felder
			guiHandler.getGameField().getGrid().clearFields();
	
			// Setze Alle Felder auf einen editirbaren zustand
			for (ArrayList<GameField_Grid_Cell> cells : guiHandler.getGameField()
					.getGrid().getGrid_y()) {
				for (GameField_Grid_Cell cell : cells) {
					cell.setLevelCell(false);
				}
			}
	
			// Level Informationen werden aus Datei geladen und den Variablen
			// zugewiesen
			this.activeLevel = this.datenhandler.loadLevel(levelnummer);
			this.levelnummer = levelnummer;
			this.verbleibendeZusetzendeZellen = this.activeLevel
					.getZusetzendeZellen();
	
			// Die wichtigen Felder werden gesetzt
			for (ArrayList<Integer> ilist : this.activeLevel.getGrid()) {
				guiHandler.getGameField().getGrid()
						.setStateof(ilist.get(0), ilist.get(1), true, true);
			}
	
			// Die Infos für den User werden an die Gui übertragen
			GameField_Sidebar_Level sidebar = (GameField_Sidebar_Level) guiHandler
					.getGameField().getSidebar();
			sidebar.setTxtpnZielBeschreibung(this.activeLevel.getZielBeschreibung());
			sidebar.setlblZuplazierendeZellen(this.verbleibendeZusetzendeZellen);
			sidebar.setlblLevel(this.levelnummer);
			// Durch zählen der gesetzten Felder wird die Anfangs Zahl der zellen
			// ermittelt
			// sonst müsste ein Simulations Schritt durchlaufen werden
			sidebar.setLebendeZellen(this.activeLevel.getGrid().size());
		}else{
			this.levelnummer--;
			resetLevel(guiHandler);
		}
	}

	public void resetLevel(GuiHandler guiHandler) {
		// setze Buttons zurück
		guiHandler.getGameField().getSidebar().getBtnStop().setVisible(false);
		guiHandler.getGameField().getSidebar().getBtnPlay().setVisible(true);

		// Leere alle Felder
		guiHandler.getGameField().getGrid().clearFields();

		// Setze Alle Felder auf einen editirbaren zustand
		for (ArrayList<GameField_Grid_Cell> cells : guiHandler.getGameField()
				.getGrid().getGrid_y()) {
			for (GameField_Grid_Cell cell : cells) {
				cell.setLevelCell(false);
			}
		}

		// Lade das Anfangs Muster des Levels ins Grid
		for (ArrayList<Integer> ilist : this.activeLevel.getGrid()) {
			guiHandler.getGameField().getGrid()
					.setStateof(ilist.get(0), ilist.get(1), true, true);
		}

		// Generationsnummer und LebendeZellen ebenso wie zusetzende Zellen
		// werden zurück gesetzt
		this.generationsnummer = 0;
		this.lebendeZellen = 0;
		this.verbleibendeZusetzendeZellen = this.activeLevel
				.getZusetzendeZellen();

		// Update der Gui Informationen
		GameField_Sidebar_Level sidebar = (GameField_Sidebar_Level) guiHandler
				.getGameField().getSidebar();
		sidebar.setlblZuplazierendeZellen(this.verbleibendeZusetzendeZellen);
		sidebar.setLebendeZellen(this.activeLevel.getGrid().size());
	}

	public void setLevelnummer(int levelnummer) {
		this.levelnummer = levelnummer;
	}

	public int getLevelnummer() {
		return levelnummer;
	}

	public int getGamemode() {
		return gamemode;
	}

	public Level getActiveLevel() {
		return activeLevel;
	}

	public int getVerbleibendeZusetzendeZellen() {
		return verbleibendeZusetzendeZellen;
	}

	public void setVerbleibendeZusetzendeZellen(int verbleibendeZusetzendeZellen) {
		this.verbleibendeZusetzendeZellen = verbleibendeZusetzendeZellen;
	}

	public DatenHandler getDatenhandler() {
		return datenhandler;
	}

	public void startSettings(GuiHandler guiHandler) {
		if (guiHandler.getSettings() == null) {
			guiHandler.initSettings();
		}
		guiHandler.hideHauptmenue();
		guiHandler.displaySettings();
	}
	
	public int getLevelAnzahl(){
		return this.datenhandler.getLevelAnzahl();
	}

	public void startLevelEditor(GuiHandler gui) {
		gui.hideHauptmenue();
		this.gamemode=2;
		gui.initGameField(this.gamemode);
		gui.displayGameField();
		
	}

	public void saveLevel(String zielGenerationen, String zielZellen,
			String zusetzendeZellen, String vergleichsOperator,
			String zielBeschreibung, ArrayList<ArrayList<GameField_Grid_Cell>> grid) {
		this.datenhandler.saveLevel(zielGenerationen, zielZellen, zusetzendeZellen,vergleichsOperator,zielBeschreibung,grid);
	}
	
	public Timer getTimer() {
		return timer;
	}

	public void deletelastLevel() {
		this.datenhandler.deleteLastLevel();
		
	}

}

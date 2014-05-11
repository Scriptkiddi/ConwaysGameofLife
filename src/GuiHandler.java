import java.awt.Dimension;
import java.util.ArrayList;

public class GuiHandler {

	private FunktionsHandler funktionsHandler;
	private MainMenu mainmenu;
	private LevelSelection levelSelection;
	private GameField gameField;
	private Dimension size;
	private Settings settings;

	public GuiHandler(FunktionsHandler f, Dimension size) {
		this.funktionsHandler = f;
		this.size = size;
		this.mainmenu = new MainMenu(this);
		this.displayHauptmenue();
	}

	public void focusHauptmenu() {
		this.mainmenu.toFront();
	}

	public void startEndlos() {
		this.funktionsHandler.startEndlos(this);
	}

	public void startLevel() {
		this.funktionsHandler.startLevel(this);
	}

	public void startSettings() {
		this.funktionsHandler.startSettings(this);
	}
	
	public void startLevelEditor(){
		this.funktionsHandler.startLevelEditor(this);
	}

	public void initSettings() {
		this.settings = new Settings(this);
	}

	public void initGameField(int gametyp) {
		this.gameField = new GameField(this, gametyp);
	}

	public void displayHauptmenue() {
		this.mainmenu.setVisible(true);
	}

	public void hideHauptmenue() {
		this.mainmenu.setVisible(false);
	}

	public void displayGameField() {
		this.gameField.setVisible(true);
	}

	public void hideGameField() {
		this.gameField.setVisible(false);
	}

	public void displaySettings() {
		this.settings.setVisible(true);
	}

	public void hideSettings() {
		this.settings.setVisible(false);
	}

	public FunktionsHandler getFuctionHandler() {
		return this.funktionsHandler;
	}

	public Dimension getSize() {
		return this.size;
	}

	public void initLevelSelection() {
		this.levelSelection = new LevelSelection(this);
	}

	public void showLevelSelection() {
		this.levelSelection.setVisible(true);
	}

	public void hideLevelSelection() {
		this.levelSelection.setVisible(false);
	}

	public GameField getGameField() {
		return gameField;
	}

	public LevelSelection getLevelSelection() {
		return levelSelection;
	}

	public MainMenu getMainmenu() {
		return mainmenu;
	}

	public Settings getSettings() {
		return settings;
	}

	public void saveLevel(String zielGenerationen, String zielZellen,
			String zusetzendeZellen, String vergleichsOperator,
			String zielBeschreibung, ArrayList<ArrayList<GameField_Grid_Cell>> grid) {
		this.funktionsHandler.saveLevel(zielGenerationen, zielZellen, zusetzendeZellen,vergleichsOperator,zielBeschreibung,grid);
		
	}

}

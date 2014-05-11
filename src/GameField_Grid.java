import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.GridLayout;

public class GameField_Grid extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -376216526666264209L;
	private GameField gameField;
	private Dimension size;
	private ArrayList<ArrayList<GameField_Grid_Cell>> grid_y = new ArrayList<ArrayList<GameField_Grid_Cell>>();

	public GameField_Grid(GameField gamef) {
		this.gameField = gamef;
		//Setze Größe des GameFields
		this.size = new Dimension(650,this.gameField.getGuiHandler().getSize().height);
		this.setSize(this.size);

		this.setBounds(150, 0, 650,	this.gameField.getGuiHandler().getSize().height);
		
		//Berechne die Anzahl der Spalten und Reihen
		int rows = Math.round(this.size.height / 10, 10);
		int columns = Math.round(this.size.width / 10, 10);
		setLayout(new GridLayout(rows, columns, 0, 0));
		
		//Erstellt die Zellen und fügte diese dem Grid hinzu
		for (int i = 0; i < this.size.height; i += 10) {
			ArrayList<GameField_Grid_Cell> grid_x = new ArrayList<GameField_Grid_Cell>();
			for (int k = 0; k < this.size.width; k += 10) {
				GameField_Grid_Cell cell = new GameField_Grid_Cell(k, i);
				grid_x.add(cell);
				this.add(cell);
			}
			grid_y.add(grid_x);
		}

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) { 
				//Mouse Adapter ist veranwortlich ob es noch erlaubt ist Zellen zusetzen und wo diese Gesetzt werden
				//Limitierung der Zellen ist nur bei GameMode 1 aktiv
				int verbleibendeZusetzendeZellen = gameField.getGuiHandler().getFuctionHandler().getVerbleibendeZusetzendeZellen();
				if (gameField.getGuiHandler().getFuctionHandler().getGamemode() != 1) { //Falls Spiel nicht im Level Modus ignoriere zusetzende Zellen
					grid_y.get((int) e.getY() / 10).get((int) e.getX() / 10).toogleState();
				} else if (gameField.getGuiHandler().getFuctionHandler().getGamemode() == 1 
						&& (verbleibendeZusetzendeZellen > 0 || grid_y.get((int) e.getY() / 10).get((int) e.getX() / 10).getState()) 
						&& !grid_y.get((int) e.getY() / 10).get((int) e.getX() / 10).getLevelCell()) {
						//Wenn Gamemode=1 und die Zelle keine Level Zelle ist und die Anzahl der Zusetzenden Zellen >0 ist oder wenn die Zelle schon gesetzt wurde
					
							//Wenn zelle nicht am Leben toogle State und decrementiere rest anzahl
							if (!grid_y.get((int) e.getY() / 10).get((int) e.getX() / 10).getState()) { 									
								grid_y.get((int) e.getY() / 10).get((int) e.getX() / 10).toogleState();
								verbleibendeZusetzendeZellen--;
								gameField.getGuiHandler().getFuctionHandler().setVerbleibendeZusetzendeZellen(verbleibendeZusetzendeZellen);
							} else { // Wenn zelle noch am Leben und nicht eine der
								// Level Zellen wurde Zelle also schon vom User
								// gesetzt und dieser will sie jetzt entfernen
								// damit +1 der verfügbarne Zellen
								grid_y.get((int) e.getY() / 10).get((int) e.getX() / 10).toogleState();
								verbleibendeZusetzendeZellen++;
								gameField.getGuiHandler().getFuctionHandler().setVerbleibendeZusetzendeZellen(verbleibendeZusetzendeZellen);
							}
					//Castet die Sidebar zu Level Sidebar damit man Anzahl der Zuplatzierden Zellen geupdatet wird
					GameField_Sidebar_Level sidebar = (GameField_Sidebar_Level) gameField.getSidebar();
					sidebar.setlblZuplazierendeZellen(verbleibendeZusetzendeZellen);
				}
			}
		});

	}

	public void clearFields() {
		for (ArrayList<GameField_Grid_Cell> grid_x : grid_y) {
			for (GameField_Grid_Cell cell : grid_x) {
				cell.setStateDead();
			}
		}
	}

	public void setSidebar(GameField_Sidebar sidebar) {
	}

	public ArrayList<ArrayList<GameField_Grid_Cell>> getGrid_y() {
		return grid_y;
	}

	public void setStateof(int x, int y, boolean state, boolean levelCell) {
		grid_y.get(y).get(x).setState(state);
		grid_y.get(y).get(x).setLevelCell(levelCell);
	}

	public int getNumberofCellsAlive() {
		int number = 0;
		//Geht das Grid in x richtung durch und zaehlt die Lebenden Zellen
		for (ArrayList<GameField_Grid_Cell> grid_x : grid_y) {
			for (GameField_Grid_Cell cell : grid_x) {
				if (cell.getState()) {
					number++;
				}
			}
		}
		return number;
	}

}

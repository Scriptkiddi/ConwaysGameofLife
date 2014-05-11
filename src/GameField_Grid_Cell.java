import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * @author Fritz
 */
public class GameField_Grid_Cell extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2040743921226672047L;
	private int x;
	private int y;
	private boolean state = false;
	private boolean levelCell = false;

	/**
	 * Creates new form GameField_Grid_Cell
	 */
	public GameField_Grid_Cell(int x, int y) {
		this.setSize(10, 10);
		this.x = x;
		this.y = y;
		this.setBackground(Color.WHITE);
	}

	public Dimension getCords() {
		return new Dimension(this.x, this.y);
	}

	public void toogleState() {
		if (!this.levelCell) {
			if (this.state) { // Wenn im moment True das heiÃŸt die Farbe ist
								// gerade noch schwarz deswegen wird auf weiÃŸt
								// geschaltet
				this.setBackground(Color.white);
			} else { // das selbe nur diesesmal war anfags zustand weiÃŸ und wir
						// nun auf Schwarz gewechselt
				this.setBackground(Color.black);
			}
			this.state = !this.state;
		}
	}

	public void setStateAlive() {
		this.state = true;
		this.setBackground(Color.black);
	}

	public void setStateDead() {
		this.state = false;
		this.setBackground(Color.white);
	}

	public void setState(boolean state) {
		this.state = state;
		if (state) {
			this.setBackground(Color.black);
		} else {
			this.setBackground(Color.white);
		}

	}

	public void setLevelCell(boolean levelCell) {
		this.levelCell = levelCell;
	}

	public boolean getLevelCell() {
		return this.levelCell;
	}

	public boolean getState() {
		return this.state;
	}

	public boolean calculateState(int indexOf,
			ArrayList<ArrayList<GameField_Grid_Cell>> grid_y,
			ArrayList<GameField_Grid_Cell> grid_x) {
		// Nachbarzellen getState

		GameField_Grid_Cell deadCell = new GameField_Grid_Cell(0, 0); // tote
																		// zelle
																		// für
																		// outof
																		// bounds
																		// exceptions
		ArrayList<GameField_Grid_Cell> zeileDarueber;
		ArrayList<GameField_Grid_Cell> zeileDarunter;
		ArrayList<GameField_Grid_Cell> benachbarteZellen = new ArrayList<GameField_Grid_Cell>();

		try {
			zeileDarueber = grid_y.get(grid_y.indexOf(grid_x) - 1);
		} catch (IndexOutOfBoundsException e) {
			// arraylist mit drei toten Zellen wenn abfrage bis zum Rand wandert
			// dort werden dann tote Zellen anstatt echter Zellen
			// genommen zum berechnen

			zeileDarueber = new ArrayList<GameField_Grid_Cell>(Arrays.asList(
					deadCell, deadCell, deadCell));
		}

		try {
			zeileDarunter = grid_y.get(grid_y.indexOf(grid_x) + 1);
		} catch (IndexOutOfBoundsException e) {
			zeileDarunter = new ArrayList<GameField_Grid_Cell>(Arrays.asList(
					deadCell, deadCell, deadCell));
		}

		// Hole zelle oben Links
		// |x|_|_|
		// |_|o|_|
		// |_|_|_|
		try {
			benachbarteZellen.add(zeileDarueber.get(indexOf - 1));
		} catch (IndexOutOfBoundsException e) {
			benachbarteZellen.add(deadCell);
		}

		// |_|x|_|
		// |_|o|_|
		// |_|_|_|
		try {
			benachbarteZellen.add(zeileDarueber.get(indexOf));
		} catch (IndexOutOfBoundsException e) {
			benachbarteZellen.add(deadCell);
		}

		// |_|_|x|
		// |_|o|_|
		// |_|_|_|
		try {
			benachbarteZellen.add(zeileDarueber.get(indexOf + 1));
		} catch (IndexOutOfBoundsException e) {
			benachbarteZellen.add(deadCell);
		}

		// |_|_|_|
		// |x|o|_|
		// |_|_|_|
		// Da die Zellen Links am Rand und Rechts am Rand noch nicht abgedeckt
		// sind
		// wird hier nochmals dafür gesorgt das der Rand wie Tote zellen
		// behandelt werden
		try {
			benachbarteZellen.add(grid_x.get(indexOf - 1));
		} catch (IndexOutOfBoundsException e) {
			benachbarteZellen.add(deadCell);
		}
		// |_|_|_|
		// |_|o|x|
		// |_|_|_|
		try {
			benachbarteZellen.add(grid_x.get(indexOf + 1));
		} catch (IndexOutOfBoundsException e) {
			benachbarteZellen.add(deadCell);
		}
		// |_|_|_|
		// |_|o|_|
		// |x|_|_|
		try {
			benachbarteZellen.add(zeileDarunter.get(indexOf - 1));
		} catch (IndexOutOfBoundsException e) {
			benachbarteZellen.add(deadCell);
		}

		// |_|_|_|
		// |_|o|_|
		// |_|x|_|
		try {
			benachbarteZellen.add(zeileDarunter.get(indexOf));
		} catch (IndexOutOfBoundsException e) {
			benachbarteZellen.add(deadCell);
		}

		// |_|_|_|
		// |_|o|_|
		// |_|_|x|
		try {
			benachbarteZellen.add(zeileDarunter.get(indexOf + 1));
		} catch (IndexOutOfBoundsException e) {
			benachbarteZellen.add(deadCell);
		}
		// auswertung der Anzahl der States der Nachbar zellen

		int lebendeZellen = 0;

		for (GameField_Grid_Cell cell : benachbarteZellen) {
			if (cell.getState()) {
				lebendeZellen++;
			}
		}

		if (!this.getState() && lebendeZellen == 3) {
			// Eine tote Zelle mit genau drei lebenden Nachbarn wird in der
			// Folgegeneration neu geboren.
			return true;
		} else if (this.getState() && lebendeZellen < 2) {
			// Lebende Zellen mit weniger als zwei lebenden Nachbarn sterben in
			// der Folgegeneration an Einsamkeit.
			return false;
		} else if (this.getState() && lebendeZellen > 3) {
			return false; // Lebende Zellen mit mehr als drei lebenden Nachbarn
							// sterben in der Folgegeneration an
							// Überbevölkerung.
		} else {
			return this.state;
		}

		// Eine lebende Zelle mit zwei oder drei lebenden Nachbarn bleibt in der
		// Folgegeneration lebend
		// entfällt da abgedeckt mit 2<=x=<3

	}

}

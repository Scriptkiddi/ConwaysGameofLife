import java.util.ArrayList;
import java.util.Arrays;

public class Level {
	private int levelnummer;
	private int generationGoal;
	private int cellsAlive;
	private String zielBeschreibung;
	private String vergleichsOperator;
	private int zusetzendeZellen;
	private ArrayList<ArrayList<Integer>> cordsofLivingCells = new ArrayList<ArrayList<Integer>>();

	public Level(ArrayList<String> leveldat, int levelnummer,
			int generationGoal, int cellsAlive, String zielBeschreibung,
			String vergleichsOperator, int zusetzendeZellen) {
		//Erstelle ein grid von Lebenden Zellen aus String o=Lebende Zelle
		for (int i = 0; i < leveldat.size(); i++) {
			for (int k = 0; k < leveldat.get(i).length(); k++) {
				if (leveldat.get(i).charAt(k) == 'o') {
					this.cordsofLivingCells.add(new ArrayList<Integer>(Arrays
							.asList(k, i)));
				}
			}
		}
		this.levelnummer = levelnummer;
		this.generationGoal = generationGoal;
		this.cellsAlive = cellsAlive;
		this.zielBeschreibung = zielBeschreibung;
		this.vergleichsOperator = vergleichsOperator;
		this.zusetzendeZellen = zusetzendeZellen;

	}

	public ArrayList<ArrayList<Integer>> getGrid() {
		return this.cordsofLivingCells;
	}

	public int getLevelnummer() {
		return levelnummer;
	}

	public int getGenerationGoal() {
		return generationGoal;
	}

	public int getCellsAlive() {
		return cellsAlive;
	}

	public String getZielBeschreibung() {
		return zielBeschreibung;
	}

	public String getVergleichsOperator() {
		return vergleichsOperator;
	}

	public int getZusetzendeZellen() {
		return zusetzendeZellen;
	}

}

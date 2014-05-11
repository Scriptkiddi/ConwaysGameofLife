import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class DatenHandler {
	
	
	private SqlLiteHandler sql = new SqlLiteHandler("/datenbank.db");
	
	public DatenHandler(){ //Erstellt den Datenhandler und die Datenbanken falls diese noch nicht existieren sollten
		sql.initDBConnection();
        sql.executeUpdate("CREATE TABLE IF NOT EXISTS userprofile (Level INTEGER PRIMARY KEY, Access);");
        sql.executeUpdate("CREATE TABLE IF NOT EXISTS Level (Map, Levelnummer INTEGER PRIMARY KEY, Generationen NUMERIC, LebendeZellen NUMERIC, Levelname TEXT, ZusetzendeZellen NUMERIC, VergleichsOperator TEXT, ZielBeschreibung TEXT);");
        sql.killDBConnection();
	}

	public Level loadLevel(int levelnummer) {
		//Lädt das Level aus der Datenbank und gibt ein fertiges Level Objekt zurück
		sql.initDBConnection();
		ResultSet rs = sql.executeQuery("SELECT Map, Generationen, LebendeZellen, ZusetzendeZellen, VergleichsOperator, ZielBeschreibung FROM Level WHERE Levelnummer='"+Integer.toString(levelnummer)+"';");
		try {
			rs.next(); //Setzt Result set auf erstes Ergebnis
			String map = rs.getString("Map");
			//erstelle die Grid info aus dem Sting in der SQL Datenbank linebreak ist begrenzer einer Zeile
			ArrayList<String> leveldat = new ArrayList<String>(Arrays.asList( map.split("\n")));
			int generationen = Integer.parseInt(rs.getString("Generationen"));
			int lebendeZellen = Integer.parseInt(rs.getString("LebendeZellen"));
			int zusetzendeZellen = Integer.parseInt(rs.getString("zusetzendeZellen"));
			String zielBeschreibung = rs.getString("ZielBeschreibung");
			String vergleichsOperator = rs.getString("VergleichsOperator");
			Level level = new Level(leveldat, levelnummer, generationen, lebendeZellen, zielBeschreibung, vergleichsOperator, zusetzendeZellen);
			sql.killDBConnection();
			return level;
		} catch (SQLException e) {
			e.printStackTrace();
			//Hier wird eine Exception geworfen ResultSet closed diese kann ignoriert werden
			//da das Result Set nur dazu dient informationen auszulesen
		}
		sql.killDBConnection();
		return null;
	}
	
	public boolean checkLevelAccess(int level){ 
		//Kontrolliert ob das Level schon freigeschalten wurde
		sql.initDBConnection();
		ResultSet rs = sql.executeQuery("SELECT Access FROM userprofile WHERE Level='"+Integer.toString(level)+"';");
		try {
			rs.next();
			boolean access = Boolean.parseBoolean(rs.getString(1));
			sql.killDBConnection();
			return access;
		} catch (SQLException e) {
			e.printStackTrace();
			//Hier wird eine Exception geworfen ResultSet closed diese kann ignoriert werden
			//da das Result Set nur dazu dient informationen auszulesen
		}
		sql.killDBConnection();
		return false;
	}
	
	public void writelevelAccess(int level){
		//Schaltet das angegebene Level frei
		sql.initDBConnection();
		sql.execute("INSERT OR REPLACE INTO userprofile (Level, Access) VALUES ('"+Integer.toString(level)+"', 'true');");
		//INSERT OR REPLACE da sqllite nicht über UPDATE ON DUPLICATE KEY verfügt
		sql.killDBConnection();
		
	}

	public void resetLevel() {
		//Setzt alle Level Fortschitte zurück
		sql.initDBConnection();
		sql.executeUpdate("UPDATE userprofile SET Access='false' WHERE 1;");
		sql.killDBConnection();
	}
	
	public int getLevelAnzahl(){
		//Holt die Anzahl der Level aus der Datenbank
		sql.initDBConnection();
		ResultSet rs = sql.executeQuery("SELECT COUNT(levelnummer) AS Anzahl FROM Level");
		try {
			rs.next();
			int levelcount = rs.getInt("Anzahl");
			sql.killDBConnection();
			return levelcount;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.killDBConnection();
		return 0;
	}

	public void saveLevel(String zielGenerationen, String zielZellen,
			String zusetzendeZellen, String vergleichsOperator,
			String zielBeschreibung, ArrayList<ArrayList<GameField_Grid_Cell>> grid) {
		//Speichert ein neues Level in die Datenbank
		String data ="";
		//wandelt das Grid in ein String um welcher dann in der Datenbank gespeichert wird
		for(ArrayList<GameField_Grid_Cell> grid_x:grid){
			for(GameField_Grid_Cell cell : grid_x){
				if(cell.getState()){
					data += "o";
				}else{
					data += "_";
				}
			}
			data += "\n";
		}
		sql.initDBConnection();
		sql.execute("INSERT INTO Level (Map, Generationen, LebendeZellen, ZusetzendeZellen, VergleichsOperator, ZielBeschreibung) VALUES ('"+data+"', '"+zielGenerationen+"', '"+zielZellen+"','"+zusetzendeZellen+"','"+vergleichsOperator+"','"+zielBeschreibung+"');");
		sql.killDBConnection();
		
	}

	public void deleteLastLevel() {
		sql.initDBConnection();
		sql.execute("DELETE FROM Level WHERE Levelnummer=(SELECT MAX(Levelnummer) FROM Level);");
		sql.killDBConnection();
		
	}

}

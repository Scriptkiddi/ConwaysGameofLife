import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;


public class SqlLiteHandler {

    private Connection connection;
    private String db_path;
    
    public SqlLiteHandler(String db_path){
    	this.db_path = db_path;
    }
    
    public void initDBConnection() {
            if (connection != null){
                return;
            }else{
            	//ERstelle SQL Connection mit der sqllite datei in der jar
	            try {
	    			Class.forName("org.sqlite.JDBC");
	    			SQLiteConfig config = new SQLiteConfig();
	    			config.enableFullSync(true);
	    			config.setReadOnly(false);
	    			SQLiteDataSource ds= new SQLiteDataSource(config);
	    	    	ds.setUrl("jdbc:sqlite::resource:"+getClass().getResource(this.db_path).toString());
	    	    	connection = ds.getConnection();
	    		 }catch(SQLException | ClassNotFoundException se){
	    			 se.printStackTrace();
	    		 }
            }
    }
    
    public void killDBConnection(){
    	//killt die Verbindung zur Datenbank
    	try {
			this.connection.close();
			this.connection = null; //Damit eine neue Verbindung Aufgebaut wird
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public void executeUpdate(String query){
    	//Update Statments
    	Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public ResultSet executeQuery(String query){//Select Statments 
    	Statement stmt;
		try {
			stmt = connection.createStatement();
			return stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    public void execute(String query){ //Insert statments usw
    	Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void setAutoCommit(boolean status){
    	//Auto Commit Status
    	try {
			this.connection.setAutoCommit(status);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    } 
}

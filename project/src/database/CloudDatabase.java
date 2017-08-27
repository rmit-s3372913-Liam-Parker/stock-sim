package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.UserDetails;

import java.sql.ResultSetMetaData;


public class CloudDatabase
{
	private static final String EXISTED_NAME = "Username already existed";
	private static final String NO_INTERNET = "Internet connection unavailable";
	
    private static String dbURL = "jdbc:derby://52.65.208.133:1368/Database";
    private static String playerTable = "player";
    
    // jdbc Connection
    private Connection conn = null;
    
    private Statement stmt = null;

    public CloudDatabase(){
    }
    

    public String checkUsername(UserDetails user){
        if (createConnection()){
            if (playerExist(user.getUsername())){
            	shutdown();
            	return EXISTED_NAME;
            }
            shutdown();
            return null;
        }
    	return NO_INTERNET;
    }
    
    public String register(UserDetails user, String pin){
        if (createConnection()){
            insertPlayer(user.getUsername(), user.getPassword(), pin);
            select();
            shutdown();
            return null;
        }
    	return NO_INTERNET;
    }
    
    public String confirm(UserDetails user){
        if (createConnection()){
            confirmPlayer(user.getUsername(), user.getPassword());
            select();
            shutdown();
            return null;
        }
    	return NO_INTERNET;
    }
    
    private boolean createConnection()
    {
        try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            //Get a connection
            conn = DriverManager.getConnection(dbURL); 
        }
        catch (Exception except)
        {
            except.printStackTrace();
            return false;
        }
        return true;
    }
    
    private boolean playerExist(String username){
    	try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("select * from " + playerTable + " where username = '" + username + "'");
            if (results.next()){
                stmt.close();
            	return true;
            }
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    	return false;
    }
    
    private void insertPlayer(String username, String password, String pin)
    {
        try
        {
            stmt = conn.createStatement();
            
            stmt.execute("insert into " + playerTable + " values ('" +
            		username + "','" + password + "', '" + pin + "')");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
    private void confirmPlayer(String username, String confirm)
    {
        try
        {
        	stmt = conn.createStatement();

        	stmt.execute(
          	      "UPDATE " + playerTable + " SET confirm = '" + confirm
          	      + "' WHERE username = '" + username + "'");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
    private void updatePlayer(String username, String password)
    {
        try
        {
        	stmt = conn.createStatement();

        	stmt.execute(
          	      "UPDATE " + playerTable + " SET username = '" + username
          	      + "', password = '" + password + "' WHERE username = '" + username + "'");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
    private void select()
    {
        try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("select * from " + playerTable);
            ResultSetMetaData rsmd = results.getMetaData();
            int numberCols = rsmd.getColumnCount();
            for (int i=1; i<=numberCols; i++)
            {
                //print Column Names
                System.out.print(rsmd.getColumnLabel(i)+"\t\t");  
            }

            System.out.println("\n-------------------------------------------------");

            while(results.next())
            {
                String username = results.getString(1);
                String password = results.getString(2);
                System.out.println(username + "\t\t" + password);
            }
            results.close();
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
    private void shutdown()
    {
        try
        {
            if (stmt != null)
            {
                stmt.close();
            }
            if (conn != null)
            {
                DriverManager.getConnection(dbURL + ";shutdown=true");
                conn.close();
            }           
        }
        catch (SQLException sqlExcept)
        {
        }

    }
}

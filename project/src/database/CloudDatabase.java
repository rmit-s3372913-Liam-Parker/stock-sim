package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import model.UserDetails;


public class CloudDatabase
{
	private static final String EXISTED_NAME = "Username already existed";
	private static final String NO_INTERNET = "Internet connection unavailable";
	private static final String INVALID = "Incorrect username or password";
	
    private static String dbURL = "jdbc:mysql://capstonedatabase.cszu3gvo32mp.ap-southeast-2.rds.amazonaws.com:3306/CapstoneDatabase?user=admin&password=password";
    private static String playerTable = "player";
    
    // jdbc Connection
    private Connection conn = null;
    
    private Statement stmt = null;

    public CloudDatabase(){}
    
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
            insertPlayer(user.getUsername(), user.getPassword(), user.getEmail(), pin);
            //select();
            shutdown();
            return null;
        }
    	return NO_INTERNET;
    }
    
    public String login(UserDetails user){
        if (createConnection()){
            if (!loginCheck(user))
            	return INVALID;
            shutdown();
            return null;
        }
    	return NO_INTERNET;
    }
    
    public String confirm(UserDetails user){
        if (createConnection()){
            confirmPlayer(user.getUsername(), user.getPassword());
            //select();
            shutdown();
            return null;
        }
    	return NO_INTERNET;
    }
    
    private boolean createConnection()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
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
    
    private boolean loginCheck(UserDetails user){
    	try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("select * from "
            + playerTable + " where username = '"
            + user.getUsername() + "' and password = '"
            + user.getPassword() + "'");
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
    
    private void insertPlayer(String username, String password, String email, String pin)
    {
        try
        {
            stmt = conn.createStatement();
            
            stmt.execute("insert into " + playerTable + " values ('" +
            		username + "','" + password + "', '" + email + "', 'No', '" + pin + "')");
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
                String email = results.getString(3);
                String confirm = results.getString(4);
                String pin = results.getString(5);
                System.out.println(username + "\t\t\t" + password + "\t\t\t" + email + "\t\t\t" + confirm + "\t\t" + pin);
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

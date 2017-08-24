package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
    
    public String register(UserDetails user){
        if (createConnection()){
            if (playerExist(user.getUsername())){
            	shutdown();
            	return EXISTED_NAME;
            }
            insertPlayer(user.getUsername(), user.getPassword());
//            updatePlayer(5, "lol", "otz");
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
    
    private void insertPlayer(String username, String password)
    {
        try
        {
        	int id = 1;
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("select * from " + playerTable + " order by id DESC");
            if (results.next()){
	            id = results.getInt(1);
	            id++;
            }
            
            stmt.execute("insert into " + playerTable + " values (" +
                    id + ",'" + username + "','" + password +"')");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
    private void updatePlayer(int id, String username, String password)
    {
        try
        {
        	PreparedStatement pstmt = conn.prepareStatement(
            	      "UPDATE " + playerTable + " SET username = ?, password = ? WHERE id = ?");

            pstmt.setString(1,username);
            pstmt.setString(2,password);
            pstmt.setInt(3,id);
            
        	pstmt.executeUpdate();
            pstmt.close();
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
                int id = results.getInt(1);
                String restName = results.getString(2);
                String cityName = results.getString(3);
                System.out.println(id + "\t\t" + restName + "\t\t" + cityName);
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

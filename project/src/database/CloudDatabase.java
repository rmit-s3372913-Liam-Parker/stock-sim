package database;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.PlayerStats;
import model.Transaction;
import model.TransactionType;
import model.UserDetails;
import ultilities.Hash;


public class CloudDatabase
{
	private static final String EXISTED_NAME = "Username already existed";
	private static final String NO_INTERNET = "Internet connection unavailable";
	private static final String INVALID = "Incorrect username or password";
	private static final String DATABASE_ERROR = "Encountered error when contacting database";
	private static final String WRONG_PIN = "Incorrect PIN";
	public static final String USER_UNCONFIRMED = "Email is not confirmed";
	
    private static String dbURL = "jdbc:mysql://capstonedatabase.cszu3gvo32mp.ap-southeast-2.rds.amazonaws.com:3306/CapstoneDatabase?user=admin&password=password";
    private static String playerTable = "player";
    private static String transactionTable = "transaction";
    
    // jdbc Connection
    private Connection conn = null;
    
    private Statement stmt = null;

    public CloudDatabase() {}
    
    public String checkUsername(UserDetails user)
    {
        if (createConnection())
        {
            if (playerExist(user.getUsername()))
            {
            	shutdown();
            	return EXISTED_NAME;
            }
            
            shutdown();
            return null;
        }
    	return NO_INTERNET;
    }

    public String register(UserDetails user, String pin)
    {
        if (createConnection())
        {
			if (!insertPlayer(user, pin))
				return DATABASE_ERROR;
			
            shutdown();
            return null;
        }
    	return NO_INTERNET;
    }
    
    public String login(UserDetails user)
    {
        if (createConnection())
        {
            if (!loginCheck(user))
            	return INVALID;
            
            shutdown();
            return null;
        }
    	return NO_INTERNET;
    }

    public String confirm(UserDetails user, String pin)
    {
        if (createConnection())
        {
        	if (checkPin(user, pin))
        	{
        		confirmPlayer(user);
                shutdown();
                return null;
        	}
        	shutdown();
        	return WRONG_PIN;
        }
    	return NO_INTERNET;
    }
    
    public String confirmedUser(UserDetails user)
    {
        if (createConnection())
        {
        	if (checkConfirm(user))
        	{
                shutdown();
                return null;
        	}
        	shutdown();
        	return USER_UNCONFIRMED;
        }
    	return NO_INTERNET;
    }
    
    public String executeTransaction(Transaction transaction)
    {
        if (createConnection())
        {
        	if (insertTransaction(transaction))
        	{
                shutdown();
                return null;
        	}
        	shutdown();
        	return USER_UNCONFIRMED;
        }
    	return NO_INTERNET;
    }
    
    public boolean insertTransaction(Transaction transaction)
    {
    	String transType = "Buy";
    	switch (transaction.getTransactionType()){
		case Receive:
    		transType = "Receive";
			break;
		case Sell:
    		transType = "Sell";
			break;
		case Send:
    		transType = "Send";
			break;
		default:
			break;
    	}
    	
    	try
        {
            stmt = conn.createStatement();
            
            stmt.execute("insert into " + transactionTable + " (username, stockID, transactionType, stockQuantity, price, winningAfterTransaction) values ('" +
            		transaction.getUsername() + "','" +
            		transaction.getStockCode() + "','" +
            		transType + "','" +
            		transaction.getQuantity() + "','" +
            		transaction.getPrice() + "','" +
            		transaction.getPostWinnings() + "')");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
            return false;
        } 
    	
        return true;
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
            + Hash.hashPassword(user.getPassword()) + "'");
            if (results.next()){
                stmt.close();
            	return true;
            }
            stmt.close();
        } 
    	catch (SQLException sqlExcept) { sqlExcept.printStackTrace(); }
    	catch (UnsupportedEncodingException e) { e.printStackTrace(); } 
    	catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
    	
    	return false;
    }
    
    private boolean insertPlayer(UserDetails user, String pin)
    {
        try
        {
            stmt = conn.createStatement();
            
            stmt.execute("insert into " + playerTable + " (username, password, email, pin) values ('" +
            		user.getUsername() + "','" + Hash.hashPassword(user.getPassword()) + "', '" + user.getEmail() + "', '" + pin + "')");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
            return false;
        } 
        catch (UnsupportedEncodingException e) { e.printStackTrace(); } 
        catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
        return true;
    }
    
    private boolean checkPin(UserDetails user, String pin){
    	try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("select * from "
            + playerTable + " where username = '"
            + user.getUsername() + "' and pin = '"
            + pin + "'");
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

    private boolean checkConfirm(UserDetails user){
    	try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("select * from "
            + playerTable + " where username = '"
            + user.getUsername() + "' and confirm = 'yes'");
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
    
    private void confirmPlayer(UserDetails user)
    {
        try
        {
        	stmt = conn.createStatement();

        	stmt.execute(
          	      "UPDATE " + playerTable + " SET confirm = 'yes' WHERE username = '" + user.getUsername() + "'");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
    public List<PlayerStats> getHighScore(){
    	try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("select username, winning from " + playerTable + " where confirm = 'yes' order by winning desc");
            List<PlayerStats> players = new ArrayList<PlayerStats>();
            
            if (results.next())
            {
            	
        		try 
        		{
        			while(results.next())
        			{
        				String username = results.getString(1);
        				Double winnings = Double.parseDouble(results.getString(2));
        				players.add(new PlayerStats(username, winnings));
        				
        			}
        		} 
        		catch (SQLException e) { e.printStackTrace(); }
            }
            
            stmt.close();
            return players;
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    	
    	return null;
    }

    private boolean changeWinning(String username, int change)
    {
	    int winning = 0;
    	try {
    		stmt = conn.createStatement();
    		ResultSet results = stmt.executeQuery("SELECT winning FROM " + playerTable + " WHERE username = '" + username + "'");
			while(results.next())
			{
			    winning = Integer.parseInt(results.getString(1));
			    System.out.println(winning);
			}
			results.close();
			stmt.close();
		} 
    	catch (SQLException sqlExcept)
    	{
            sqlExcept.printStackTrace();
            return false;
		}
    	winning += change;
        try
        {
        	stmt = conn.createStatement();

        	stmt.execute(
          	      "UPDATE " + playerTable + " SET winning = '" + winning + "' WHERE username = '" + username + "'");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
            return false;
        }
        return true;
    }
    
//    private void select()
//    {
//        try
//        {
//            stmt = conn.createStatement();
//            ResultSet results = stmt.executeQuery("select * from " + playerTable);
//            ResultSetMetaData rsmd = results.getMetaData();
//            int numberCols = rsmd.getColumnCount();
//            for (int i=1; i<=numberCols; i++)
//            {
//                //print Column Names
//                System.out.print(rsmd.getColumnLabel(i)+"\t\t");  
//            }
//
//            System.out.println("\n-------------------------------------------------");
//
//            while(results.next())
//            {
//                String username = results.getString(1);
//                String password = results.getString(2);
//                String email = results.getString(3);
//                String confirm = results.getString(4);
//                String pin = results.getString(5);
//                System.out.println(username + "\t\t\t" + password + "\t\t\t" + email + "\t\t\t" + confirm + "\t\t" + pin);
//            }
//            results.close();
//            stmt.close();
//        }
//        catch (SQLException sqlExcept)
//        {
//            sqlExcept.printStackTrace();
//        }
//    }
    
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

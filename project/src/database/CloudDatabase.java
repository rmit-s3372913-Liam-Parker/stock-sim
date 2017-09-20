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
	public static final double WINNING_ERROR = -1;
	public static final int QUANTITY_ERROR = -1;
	
    private static String dbURL = "jdbc:mysql://capstonedatabase.cszu3gvo32mp.ap-southeast-2.rds.amazonaws.com:3306/CapstoneDatabase?user=admin&password=password";
    private static String playerTable = "player";
    private static String transactionTable = "transaction";
    private static String buySellDetailTable = "buySellDetail";
    private static String stockTable = "stock";
    
    // jdbc Connection
    private Connection conn = null;
    
    private Statement stmt = null;

    public CloudDatabase() {}
    
    public String usernameExists(String user)
    {
        if (createConnection())
        {
            if (playerExist(user))
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
    	boolean failure = false;
        if (createConnection())
        {
        	if (insertTransaction(transaction))
                if (changeWinning(transaction))
                {
            		switch (transaction.getTransactionType()){
            		case Buy:
					case Sell:
						if (addBuySellDetail(transaction))
		                	if (changeStock(transaction))
		                	{
				        		shutdown();
				                return null;
		                	}
		                	else
		                	{
		                		failure = true;
		                		//undo addBuySellDetail
		                	}
						break;
					case Receive:
					case Send:
						// change other player and add to detail
						break;
            		}
                }
        	if (failure)
        	{
        		//undo insert and change winning
        	}
        	shutdown();
        	return DATABASE_ERROR;
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
        	shutdown();
            return players;
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    	shutdown();
    	return null;
    }
    
    private boolean insertTransaction(Transaction transaction)
    {
    	String transType = "buy";
    	switch (transaction.getTransactionType()){
		case Receive:
    		transType = "receive";
			break;
		case Sell:
    		transType = "sell";
			break;
		case Send:
    		transType = "send";
			break;
		default:
			break;
    	}
    	
    	try
        {
            stmt = conn.createStatement();
            
            stmt.execute("insert into " + transactionTable + " (username, transactionType, postWinning) values ('" +
            		transaction.getUsername() + "','" +
            		transType + "','" +
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

    private boolean changeWinning(Transaction transaction)
    {
        try
        {
        	stmt = conn.createStatement();

        	stmt.execute(
          	      "UPDATE " + playerTable + " SET winning = '" + transaction.getPostWinnings() + "' WHERE username = '" + transaction.getUsername() + "'");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
            return false;
        }
        return true;
    }
    
    private boolean changeStock(Transaction transaction)
    {
    	int quantity = 0;
    	String transType = "buy";
    	switch (transaction.getTransactionType()){
		case Sell:
    		transType = "sell";
			break;
		default:
			break;
    	}

    	try {
    		stmt = conn.createStatement();
    		ResultSet results = stmt.executeQuery("SELECT stockQuantity FROM " + stockTable + " WHERE stockID = '" + transaction.getStockCode() + "' AND username = '" + transaction.getUsername() + "'");
			if (results.next())
			{

				if (transType=="sell")
					quantity = Integer.parseInt(results.getString(1)) - transaction.getQuantity();
				else
					quantity = Integer.parseInt(results.getString(1)) + transaction.getQuantity();
				if (quantity > 0)
					stmt.execute("UPDATE " + stockTable +
							" SET stockQuantity = '" + quantity + 
							"' WHERE stockID = '" + transaction.getStockCode() +
							"' AND username = '" + transaction.getUsername() + "'");
				else
					stmt.execute("DELETE " + stockTable +
							" WHERE stockID = '" + transaction.getStockCode() +
							"' AND username = '" + transaction.getUsername() + "'");
			}
			else
			{
				if (transType=="sell"){
					results.close();
					stmt.close();
					return false;
				}
				else 
				{
					stmt.execute("insert into " + stockTable + " values ('" +
		            		transaction.getStockCode() + "','" +
		            		transaction.getUsername() + "','" +
		            		transaction.getQuantity() + "')");
				}
			}
			results.close();
			stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
            return false;
        }
        return true;
    }
    
    private boolean addBuySellDetail(Transaction transaction)
    {
    	try {
    		stmt = conn.createStatement();
    		ResultSet results = stmt.executeQuery("SELECT transactionID FROM " + transactionTable + 
    				" WHERE username = '" + transaction.getUsername() + 
    				"' AND transactionType = '" + transaction.getTransactionType() + 
    				"' AND transactionType = '" + transaction.getTransactionType() + 
    				"' ORDER BY transactionID DESC");
			if (results.next())
			{
	            stmt.execute("insert into " + buySellDetailTable + " values ('" +
	            		results.getString(1) + "','" +
	            		transaction.getStockCode() + "','" +
	            		transaction.getQuantity() + "','" +
	            		transaction.getPrice() + "')");
			}
			results.close();
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
    
    public double getWinning(UserDetails user){
	    Double winning = WINNING_ERROR;
    	try {
    		stmt = conn.createStatement();
    		ResultSet results = stmt.executeQuery("SELECT winning FROM " + playerTable + " WHERE username = '" + user.getUsername() + "'");
			while(results.next())
			{
			    winning = Double.parseDouble(results.getString(1));
			}
			results.close();
			stmt.close();
		} 
    	catch (SQLException sqlExcept)
    	{
            sqlExcept.printStackTrace();
		}
    	return winning;
    }
    
    public int getStockQuantity(String username, String stockCode){
	    int quantity = QUANTITY_ERROR;
    	try {
    		stmt = conn.createStatement();
    		ResultSet results = stmt.executeQuery("SELECT stockQuantity FROM " + stockTable + 
    				" WHERE username = '" + username + "' AND stockID = '" + stockCode + "'");
			while(results.next())
			{
			    quantity = Integer.parseInt((results.getString(1)));
			}
			results.close();
			stmt.close();
		} 
    	catch (SQLException sqlExcept)
    	{
            sqlExcept.printStackTrace();
		}
    	return quantity;
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

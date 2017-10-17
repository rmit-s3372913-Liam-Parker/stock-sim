package database;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import model.StockTransaction;
import model.PlayerStats;
import model.Message;
import model.MoneyTransaction;
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
	public static final double WINNING_ERROR = -1;
	public static final int ID_ERROR = -1;
	public static final int QUANTITY_ERROR = -1;
	
    private static String dbURL = "jdbc:mysql://capstonedatabase.cszu3gvo32mp.ap-southeast-2.rds.amazonaws.com:3306/CapstoneDatabase?user=admin&password=password";
    private static String playerTable = "player";
    private static String transactionTable = "transaction";
    private static String buySellDetailTable = "buySellDetail";
    private static String sendReceiveDetailTable = "sendReceiveDetail";
    private static String stockTable = "stock";
    private static String messageTable = "message";
    private static String friendTable = "friend";
    
    // jdbc Connection
    private Connection conn = null;
    
    private Statement stmt = null;

    public CloudDatabase() {}
    
    /**
     * Checks for a specific user in the database.
     * @param user The username to check.
     * @return An error in string form or null otherwise.
     */
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
    
    /**
     * Registers a new user with the system
     * @param user user details for registration
     * @param pin An authentication pin to be mailed to the user.
     * @return An error in string form or null otherwise.
     */
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
    
    /**
     * Logs a user into the game.
     * @param user The user to login.
     * @return An error in string form or null otherwise.
     */
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

    public int getNumStockOwned(String username, String stockCode)
    {
	    int quantity = 0;
    	try 
    	{
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
    		quantity = QUANTITY_ERROR;
    		sqlExcept.printStackTrace(); 
    	}
    	
    	return quantity;
    }
    
    public List<Pair<String, String>> getAllStockOwned(String username)
    {
    	List<Pair<String, String>> owned = new ArrayList<>();
    	
    	try 
    	{
    		stmt = conn.createStatement();
    		ResultSet results = stmt.executeQuery("SELECT stockID, stockQuantity FROM " + stockTable + 
    				" WHERE username = '" + username + "'");
    		
			while(results.next())
			{
				owned.add(new Pair<String, String>(results.getString(1), results.getString(2)));
			}
			
			results.close();
			stmt.close();
		} 
    	catch (SQLException sqlExcept) 
    	{ 
    		sqlExcept.printStackTrace(); 
    	}
    	
    	return owned;
    }
    
	public List<String> getNonFriends(UserDetails user) {
		try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT username"
            									+ " FROM " + playerTable
            									+ " WHERE username !='" + user.getUsername() + "' "
            											+ "AND userId NOT IN ("
            											+ "SELECT friendUserId"
            											+ " FROM friend"
            											+ " WHERE userId = '" + user.getUserId() +"')");
            List<String> players = new ArrayList<String>();
            
			while(results.next())
			{
				players.add(results.getString(1));
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
    
    public List<String> getFriends(UserDetails user)
    {
    	try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT username"
            									+ " FROM " + friendTable
            									+ " WHERE friendUserId = " + user.getUserId()
            											+ " AND userId IN ("
            											+ " SELECT friendUserId"
            											+ " FROM friend"
            											+ " WHERE userId = '" + user.getUserId() +"')");
            List<String> players = new ArrayList<String>();
            
			while(results.next())
			{
				players.add(results.getString(1));
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
    
    /**
     * Finds the email owned by a specific username.
     * @param username the username
     * @return The email associated to the username.
     */
    public String getUserEmailByUsername(String username) {
    	
    	if (createConnection())
        {
    		try
            {
                stmt = conn.createStatement();
                ResultSet results = stmt.executeQuery("select * from "
                + playerTable + " where username = '" + username + "'");
                if (results.next()){
                	String email = results.getString("email");
                    stmt.close();
                	return email;
                }
                stmt.close();
            } 
        	catch (SQLException sqlExcept) { sqlExcept.printStackTrace(); }
        	shutdown();
        	return USER_UNCONFIRMED;
        }
    	return NO_INTERNET;
    }
    
    /**
     * Returns the authentication pin associated to a username.
     * @param username The username
     * @return The pin associated to the username
     */
    public String getUserPinByUsername(String username) 
    {
    	if (createConnection())
        {
    		try
            {
                stmt = conn.createStatement();
                ResultSet results = stmt.executeQuery("select * from "
                + playerTable + " where username = '"
                + username + "'");
                if (results.next()){
                	String pin = results.getString("pin");
                    stmt.close();
                	return pin;
                }
                stmt.close();
            } 
        	catch (SQLException sqlExcept) { sqlExcept.printStackTrace(); }
        	shutdown();
        	return USER_UNCONFIRMED;
        }
    	return NO_INTERNET;
    }
    
    public PlayerStats getCurrentPlayerStats(UserDetails user)
    {
	    Double winning = 0.0;
	    
    	try 
    	{
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
    		winning = WINNING_ERROR;
    		sqlExcept.printStackTrace(); 
    	}
    	
    	return new PlayerStats(user.getUsername(), winning);
    }
    
    public int getPlayerUserId(UserDetails user)
    {
	    int userId = 0;
	    
    	try 
    	{
    		stmt = conn.createStatement();
    		ResultSet results = stmt.executeQuery("SELECT userId FROM " + playerTable + " WHERE username = '" + user.getUsername() + "'");
    		
			while(results.next())
			{
			    userId = Integer.parseInt(results.getString(1));
			}
			
			results.close();
			stmt.close();
		} 
    	catch (SQLException sqlExcept) 
    	{ 
    		userId = ID_ERROR;
    		sqlExcept.printStackTrace(); 
    	}
    	
    	return userId;
    }
    
    /**
     * Sets the authentication pin for a given user
     * @param username the username
     * @param pin The authentication pin
     * @return An error in string form or null otherwise.
     */
    public String setUserPin(String username,String pin) 
    {
    	
    	if (createConnection())
        {
    		try
            {
                stmt = conn.createStatement();
                stmt.executeUpdate("UPDATE "
                + playerTable + " SET pin="+ pin +" where username = '" + username + "'");
                stmt.close();
                return "DONE";
            } 
        	catch (SQLException sqlExcept) { sqlExcept.printStackTrace(); }
        	shutdown();
        	return USER_UNCONFIRMED;
        }
    	return NO_INTERNET;
    }
    
    /**
     * Sets the password for a given user
     * @param username The username
     * @param password The new password
     * @return An error in string form or null otherwise.
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public String setUserPassword(String username,String password) throws UnsupportedEncodingException, NoSuchAlgorithmException 
    {
    	
    	if (createConnection())
        {
    		try
            {
                stmt = conn.createStatement();
                stmt.executeUpdate("UPDATE " + playerTable
                		+ " SET password = '" + Hash.hashPassword(password) +
                		"' WHERE username = '" + username + "'");
                stmt.close();
                return "DONE";
            } 
        	catch (SQLException sqlExcept) { sqlExcept.printStackTrace(); }
        	shutdown();
        	return USER_UNCONFIRMED;
        }
    	return NO_INTERNET;
    }
    
    /**
     * Confirms a user with the database by comparing the pin with expected.
     * @param user The username
     * @param pin The given pin
     * @return An error in string form or null otherwise.
     */
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
    
    public String isConfirmedUser(UserDetails user)
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
                if (changeWinning(transaction))
                {
            		switch (transaction.getTransactionType()){
            		case Buy:
					case Sell:
						if (addBuySellDetail((StockTransaction) transaction) 
								&& changeStock((StockTransaction) transaction))
	                	{
			        		shutdown();
			                return null;
	                	}
						break;
					default:
						MoneyTransaction sendTransaction = (MoneyTransaction) transaction;
						MoneyTransaction receiveTransaction = new MoneyTransaction(0, 
																		sendTransaction.getPartnerUsername(),
																		TransactionType.Receive,
																		getPlayerUserId(new UserDetails(sendTransaction.getUsername(), null)),
																		sendTransaction.getUsername(),
																		sendTransaction.getWinningAmount(),
																		getCurrentPlayerStats(new UserDetails(sendTransaction.getPartnerUsername(), null)).getCurrentEarnings() + sendTransaction.getWinningAmount(),
																		null);
						if (insertTransaction(receiveTransaction)
								&& changeWinning(receiveTransaction) 
								&& addSendReceiveDetail(sendTransaction)
								&& addSendReceiveDetail(receiveTransaction))
						{
							shutdown();
			                return null;
						}
            		}
                }
        	shutdown();
        	return DATABASE_ERROR;
        }
    	return NO_INTERNET;
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
    
    public List<PlayerStats> getHighScore()
    {
    	try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT username, winning"
					            				+ " FROM " + playerTable + ""
					            				+ " WHERE confirm = 'yes'"
					            				+ " ORDER BY winning DESC");
            List<PlayerStats> players = new ArrayList<PlayerStats>();

			while(results.next())
			{
				String username = results.getString(1);
				Double winnings = Double.parseDouble(results.getString(2));
				players.add(new PlayerStats(username, winnings));
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
    
    public List<Message> getMessages(UserDetails user)
    {
    	try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT *"
					            				+ " FROM " + messageTable + ""
					            				+ " WHERE senderUserId = '" + user.getUserId()
					            				+ "' OR receiverUserId = '" + user.getUserId() + "'");
            List<Message> messages = new ArrayList<Message>();

			while(results.next())
			{
				boolean read = true;
				if (results.getString(8).equals("no"))
					read = false;
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date date = format.parse(results.getString(7));
				messages.add(new Message(Integer.parseInt(results.getString(1)),
						Integer.parseInt(results.getString(2)),
						results.getString(3), 
						Integer.parseInt(results.getString(4)),
						results.getString(5),
						results.getString(6),
						date,
						read));
			}
            
            stmt.close();
        	shutdown();
            return messages;
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        } 
    	catch (ParseException e) 
    	{
			e.printStackTrace();
		}
    	shutdown();
    	return null;
    } 
    /**
     * @return A list of transactions for a given username.
     */
    public List<Transaction> getTransactions(UserDetails details)
    {
    	List<Transaction> list = new ArrayList<>();
    	
    	try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM transaction NATURAL JOIN buySellDetail WHERE username = '" + details.getUsername() + "'");

			while(results.next())
			{
				// Retrieve transaction data
				int id = results.getInt(1);
				String stock = results.getString(6);
				int quantity = results.getInt(7);
				double price = results.getDouble(8);
				double postWinning = results.getDouble(4);
				Date date = results.getDate(5);
				TransactionType type = null;
				
				String x = results.getString(3);
				if(x.equals("sell"))
					type = TransactionType.Sell;
				else if(x.equals("buy"))
					type = TransactionType.Buy;
				
				// Append transaction to list.
				list.add(new StockTransaction(
						id, 
						details.getUsername(),
						type,
						stock,
						quantity,
						price,
						postWinning,
						date
						));
			}
            
            stmt.close();
        	shutdown();
        	
        	stmt = conn.createStatement();
            results = stmt.executeQuery("SELECT * FROM transaction NATURAL JOIN sendReceiveDetail WHERE username = '" + details.getUsername() + "'");

			while(results.next())
			{
				// Retrieve transaction data
				int id = results.getInt(1);
				int otherUserId = results.getInt(6);
				String otherUsername = results.getString(7);
				double amount = results.getDouble(8);
				double postWinning = results.getDouble(4);
				Date date = results.getDate(5);
				TransactionType type = null;
				
				String x = results.getString(3);
				if(x.equals("send"))
					type = TransactionType.Send;
				else if(x.equals("receive"))
					type = TransactionType.Receive;
				
				// Append transaction to list.
				list.add(new MoneyTransaction(
						id, 
						details.getUsername(),
						type,
						otherUserId,
						otherUsername,
						amount,
						postWinning,
						date
						));
			}
            
            stmt.close();
        	shutdown();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    	shutdown();
    	
    	return list;
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
    
    private boolean changeStock(StockTransaction transaction)
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
					stmt.execute("DELETE FROM " + stockTable +
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
    
    private boolean addBuySellDetail(StockTransaction transaction)
    {
    	try {
    		stmt = conn.createStatement();
    		ResultSet results = stmt.executeQuery("SELECT transactionID FROM " + transactionTable + 
    				" WHERE username = '" + transaction.getUsername() + 
    				"' AND transactionType = '" + transaction.getTransactionType() + 
    				"' AND postWinning = '" + transaction.getPostWinnings() + 
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
    
    private boolean addSendReceiveDetail(MoneyTransaction transaction)
    {
    	try {
    		stmt = conn.createStatement();
    		ResultSet results = stmt.executeQuery("SELECT transactionID FROM " + transactionTable + 
    				" WHERE username = '" + transaction.getUsername() + 
    				"' AND transactionType = '" + transaction.getTransactionType() + 
    				"' AND postWinning = '" + transaction.getPostWinnings() + 
    				"' ORDER BY transactionID DESC");
    		
			if (results.next())
			{
	            stmt.execute("insert into " + sendReceiveDetailTable + " values ('" +
	            		results.getString(1) + "','" +
	            		transaction.getPartnerUserId() + "','" +
	            		transaction.getPartnerUsername() + "','" +
	            		transaction.getWinningAmount() + "')");
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

	public String sendFriendRequest(UserDetails sender, String receiverUsername) {
		if (createConnection())
        {
        	int receiverUserId;
        	if ((receiverUserId=getPlayerUserId(new UserDetails(receiverUsername, null)))!=ID_ERROR)
        	{
        		UserDetails receiver = new UserDetails(receiverUserId, receiverUsername, null, null);
        		if (insertFriendRequest(sender, receiver))
        		{
	                shutdown();
	                return null;
        		}
        	}
        	shutdown();
        }
    	return NO_INTERNET;
	}
    
    private boolean insertFriendRequest(UserDetails sender, UserDetails receiver)
    {    	
    	try
        {
            stmt = conn.createStatement();
            
            stmt.execute("insert into " + friendTable + " (userId, username, friendUserId, friendUsername) values ('" +
            		sender.getUserId() + "','" +
            		sender.getUsername() + "','" +
            		receiver.getUserId() + "','" +
            		receiver.getUsername() + "')");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
            return false;
        } 
    	
        return true;
    } 
    
    public String sendMessage(UserDetails sender, String receiverUsername, String message)
    {
        if (createConnection())
        {
        	int receiverUserId;
        	if ((receiverUserId=getPlayerUserId(new UserDetails(receiverUsername, null)))!=ID_ERROR)
        	{
        		UserDetails receiver = new UserDetails(receiverUserId, receiverUsername, null, null);
        		if (insertMessage(sender, receiver, message))
        		{
	                shutdown();
	                return null;
        		}
        	}
        	shutdown();
        }
    	return NO_INTERNET;
    }
    
    private boolean insertMessage(UserDetails sender, UserDetails receiver, String message)
    {    	
    	try
        {
            stmt = conn.createStatement();
            
            stmt.execute("insert into " + messageTable + " (senderUserId, senderUsername, receiverUserId, receiverUsername, message) values ('" +
            		sender.getUserId() + "','" +
            		sender.getUsername() + "','" +
            		receiver.getUserId() + "','" +
            		receiver.getUsername() + "','" +
            		message + "')");
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
        catch (SQLException e) {} // TODO: Investigate why exceptions throw here upon login

    }
}

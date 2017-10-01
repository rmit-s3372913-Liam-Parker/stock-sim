package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import database.CloudDatabase;
import interfaces.CoreAPI;
import interfaces.TransactionCallback;
import javafx.util.Pair;

public class CoreSystem implements CoreAPI 
{
	private UserDetails curUserSession = null;
	private ASXInterface marketInterface = new ASXInterface();
	private CloudDatabase cloudDatabase = new CloudDatabase();
	
	private List<TransactionCallback> onTransactionList = new ArrayList<>();
	
	@Override
	public String checkUsername(UserDetails details)
	{
		return cloudDatabase.usernameExists(details.getUsername());
	}
	
	@Override
	public String registerNewUser(UserDetails details, String pin) 
	{
    	return cloudDatabase.register(details, pin);
	}
	
	@Override
	public String confirmNewUser(UserDetails details, String pin) 
	{
    	return cloudDatabase.confirm(details, pin);
	}

	@Override
	public String login(UserDetails details) 
	{
		curUserSession = details;
		return cloudDatabase.login(curUserSession);
	}
	
	@Override
	public String confirmedUser(UserDetails details) 
	{
		return cloudDatabase.isConfirmedUser(details);
	}

	@Override
	public String beginSession(UserDetails details) 
	{
		FileWriter writer;
		try 
		{
			writer = new FileWriter("./dataStorage/lastLogin.txt", false);
			writer.write(details.getUsername());
			
			if (details.getRemember())
			{
		        writer.write("\r\n");
		        writer.write(details.getPassword());
			}
			
	        writer.close();
		} 
		catch (IOException e) { e.printStackTrace(); }
		
		return "temp";
	}

	@Override
	public boolean endSession() 
	{
		curUserSession = null;
		return true;
	}

	@Override
	public List<PlayerStats> getPlayerList() 
	{
		List<PlayerStats> players = cloudDatabase.getHighScore();
		return players;
	}
	
	@Override
	public ASXInterface getMarketInterface()
	{
		return marketInterface;
	}

	@Override
	public UserDetails getSessionDetails() 
	{
		return curUserSession;
	}

	@Override
	public PlayerStats getSessionStats() 
	{
		return cloudDatabase.getCurrentPlayerStats(curUserSession);
	}

	@Override
	public String getUserEmailByUsername(String username) 
	{
		return cloudDatabase.getUserEmailByUsername(username);
	}

	@Override
	public String updateUserPinByUsername(String username, String pin) 
	{
		return cloudDatabase.setUserPin(username, pin);
	}

	@Override
	public String updateUserPasswordByUsername(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException 
	{
		return cloudDatabase.setUserPassword(username, password);
	}

	@Override
	public String getUserPinByUsername(String username) 
	{
		return cloudDatabase.getUserPinByUsername(username);
	}

	@Override
	public void registerOnTransactionCallback(TransactionCallback cb) 
	{
		onTransactionList.add(cb);
	}

	@Override
	public String executeTransaction(Transaction transaction) 
	{
		String str = cloudDatabase.executeTransaction(transaction);
		
		if(str == null)
		{
			for(int i = 0; i < onTransactionList.size(); ++i)
			{
				onTransactionList.get(i).onTransactionCallback();
			}
		}
		
		return str;
	}

	@Override
	public String isConfirmedUser(UserDetails user) 
	{
		return cloudDatabase.isConfirmedUser(user);
	}

	@Override
	public int getNumStockOwned(String username, String stockCode) 
	{
		return cloudDatabase.getNumStockOwned(username, stockCode);
	}

	@Override
	public List<Pair<String, String>> getAllStockOwned(String username) 
	{
		return cloudDatabase.getAllStockOwned(username);
	}

	@Override
	public PlayerStats getCurrentPlayerStats(UserDetails user) 
	{
		return cloudDatabase.getCurrentPlayerStats(user);
	}

	@Override
	public List<String> getFriends(String username) 
	{
		return cloudDatabase.getFriends(username);
	}

	@Override
	public List<Transaction> getTransactions(UserDetails details) 
	{
		return cloudDatabase.getTransactions(details);
	}
}

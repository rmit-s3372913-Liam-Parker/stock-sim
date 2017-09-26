package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import database.CloudDatabase;

public class CoreSystem implements CoreAPI 
{
	private UserDetails curUserSession = null;
	private ASXInterface marketInterface = new ASXInterface();
	private CloudDatabase cloudDatabase = new CloudDatabase();
	
	@Override
	public CloudDatabase getCloudDatabase()
	{
		return cloudDatabase;
	}
	
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
		return cloudDatabase.confirmedUser(details);
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
	public String getUserEmailByUsername(String username) {
		// TODO Auto-generated method stub
		return cloudDatabase.getUserEmailByUsername(username);
	}

	@Override
	public String updateUserPinByUsername(String username, String pin) {
		// TODO Auto-generated method stub
		return cloudDatabase.updateUserPin(username, pin);
	}

	@Override
	public String updateUserPasswordByUsername(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		return cloudDatabase.updateUserPassword(username, password);
	}

	@Override
	public String getUserPinByUsername(String username) {
		// TODO Auto-generated method stub
		return cloudDatabase.getUserPinByUsername(username);
	}
}

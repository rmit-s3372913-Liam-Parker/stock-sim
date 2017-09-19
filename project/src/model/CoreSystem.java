package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import database.CloudDatabase;

public class CoreSystem implements CoreAPI 
{
	private UserDetails curUserSession = null;
	private PlayerStats curPlayerStats = null;
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
		curPlayerStats = null;
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
		return curPlayerStats;
	}
}

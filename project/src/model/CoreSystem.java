package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import database.CloudDatabase;

/**
 * A mock core for faster testing and until we get a database running.
 */
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
		return cloudDatabase.checkUsername(details);
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

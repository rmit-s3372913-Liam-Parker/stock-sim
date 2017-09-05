package model;

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
	
	public CoreSystem()
	{
		
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
	public String confirmNewUser(UserDetails details) 
	{
    	return cloudDatabase.confirm(details);
	}

	@Override
	public String beginSession(UserDetails details) 
	{
		curUserSession = details;
		return cloudDatabase.login(curUserSession);
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
		// TODO Auto-generated method stub
		return null;
	}
}

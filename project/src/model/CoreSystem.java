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
	
	public CoreSystem()
	{
		marketInterface.getCurrentCompanyList();
	}
	
	@Override
	public boolean registerNewUser(UserDetails details) 
	{
    	CloudDatabase cd = new CloudDatabase("register", details);
		return true;
	}

	@Override
	public boolean beginSession(UserDetails details) 
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean endSession() 
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<PlayerStats> getPlayerList() 
	{
		// TODO Auto-generated method stub
		return null;
	}

}

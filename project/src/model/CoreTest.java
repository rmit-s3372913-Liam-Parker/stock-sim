package model;

import java.util.ArrayList;
import java.util.List;

/**
 * A mock core for faster testing and until we get a database running.
 */
public class CoreTest implements CoreAPI
{
	private UserDetails curUserSession = null;
	private PlayerStats curPlayerStats = null;
	private ASXInterface marketInterface = new ASXInterface();
	
	@Override
	public boolean registerNewUser(UserDetails details) 
	{
		return true;
	}
	
	@Override
	public boolean beginSession(UserDetails details) 
	{
		curUserSession = details;
		return true;
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
		PlayerStats one = new PlayerStats();
		PlayerStats two = new PlayerStats();
		PlayerStats three = new PlayerStats();
		
		List<PlayerStats> stats = new ArrayList<PlayerStats>();
		stats.add(one);
		stats.add(two);
		stats.add(three);
		
		return stats;
	}
}

package model;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
	
	public CoreSystem()
	{
		
	}
	
	@Override
	public CloudDatabase getCloudDatabase(){
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
		// TODO: Implement correct player stats, currently mocked.
		List<PlayerStats> mockPlayerList = new ArrayList<PlayerStats>()
		{{
			  add(new PlayerStats(123425, new ArrayList<>()));
			  add(new PlayerStats(2321, new ArrayList<>()));
			  add(new PlayerStats(44555, new ArrayList<>()));
			  add(new PlayerStats(1342143, new ArrayList<>()));
			  add(new PlayerStats(35623, new ArrayList<>()));
			  add(new PlayerStats(6523, new ArrayList<>()));
			  add(new PlayerStats(53214, new ArrayList<>()));
			  add(new PlayerStats(1234, new ArrayList<>()));
			  add(new PlayerStats(34534, new ArrayList<>()));
			  add(new PlayerStats(999999, new ArrayList<>()));
		}};
		
		// Sort players based on earnings
		Collections.sort(mockPlayerList, new Comparator<PlayerStats>() {
		    @Override
		    public int compare(PlayerStats lhs, PlayerStats rhs) 
		    {
		    	double lhsValue = lhs.getCurrentEarnings();
		    	double rhsValue = rhs.getCurrentEarnings();
		    	
		        return lhsValue > rhsValue ? -1 : (lhsValue < rhsValue) ? 1 : 0;
		    }
		});
			
		return mockPlayerList;
	}
	
	@Override
	public ASXInterface getMarketInterface()
	{
		return marketInterface;
	}
}

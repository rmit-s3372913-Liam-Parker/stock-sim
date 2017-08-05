package model;

import java.util.List;

public class Core implements CoreAPI
{
	private UserDetails curUserSession;
	private PlayerStats curPlayerStats;
	private ASXInterface marketInterface;
	
	@Override
	public boolean registerNewUser(UserDetails details) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean beginSession(UserDetails details) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean endSession() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public List<PlayerStats> getPlayerList() {
		// TODO Auto-generated method stub
		return null;
	}
}

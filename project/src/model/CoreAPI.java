package model;

import java.util.List;

/**
 * Acts as the interface to all major system functionality.
 */
public interface CoreAPI 
{
	/**
	 * Attempts to register a new user with the system.
	 * @param details The new user to attempt to register with the system.
	 * @return True when the details were accepted, false otherwise.
	 */
	public String registerNewUser(UserDetails details);
	
	/**
	 * Begins a session for the given user.
	 * @param details 
	 * @return True when the session was successfully started, false otherwise.
	 */
	public boolean beginSession(UserDetails details);
	
	/**
	 * Attempts to end the current session.
	 * @return True when the session was ended successfully, false otherwise.
	 */
	public boolean endSession();
	
	/**
	 * A list of all players in the system. Use this function to get stats information
	 * on other players for display in a leader-board or similar system.
	 * @return A List<> containing all players in the system and their stats.
	 */
	public List<PlayerStats> getPlayerList();
	
	//TODO: Create callback functions for different ASX interface events
}

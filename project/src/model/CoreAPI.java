package model;

import java.util.List;

/**
 * Acts as the interface to all major system functionality.
 */
public interface CoreAPI 
{
	/**
	 * Attempts to check if user name already exist with the system.
	 * @param details The new user to attempt to check with the system.
	 * @return Null when the details were accepted, pass along reason for failure otherwise.
	 */
	public String checkUsername(UserDetails details);
	
	/**
	 * Attempts to register a new user with the system.
	 * @param details The new user to attempt to register with the system.
	 * @param pin The pin stored on cloud so that the user can confirm his email from anywhere.
	 * @return Null when the details were accepted, pass along reason for failure otherwise.
	 */
	public String registerNewUser(UserDetails details, String pin);
	
	/**
	 * Attempts to confirm a new user with the system.
	 * @param details The new user to attempt to confirm with the system.
	 * @return null when the details were accepted, pass along reason for failure otherwise. ?? need to work on design
	 */
	public String confirmNewUser(UserDetails details);
	
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

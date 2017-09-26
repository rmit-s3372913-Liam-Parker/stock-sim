package model;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import database.CloudDatabase;

/**
 * Acts as the interface to all major system functionality.
 */
public interface CoreAPI 
{

	public CloudDatabase getCloudDatabase();
	
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
	 * @param pin The pin sent to user
	 * @return null when the details were accepted, pass along reason for failure otherwise. ?? need to work on design
	 */
	public String confirmNewUser(UserDetails details, String pin);
	
	/**
	 * Begins a session for the given user.
	 * @param details The logging in user's details
	 * @return True when the session was successfully started, false otherwise.
	 */
	public String login(UserDetails details);

	public String confirmedUser(UserDetails details);
	
	/**
	 * @return An object containing the session details of the currently logged in user.
	 */
	public UserDetails getSessionDetails();
	
	/**
	 * @return An object containing play stats of currently logged in user. Or NULL if not logged in.
	 */
	public PlayerStats getSessionStats();
	
	/**
	 * Begins a session for the given user.
	 * @param details 
	 * @return True when the session was successfully started, false otherwise.
	 */
	public String beginSession(UserDetails details);
	
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
	
	/**
	 * @return Access to functions for querying the ASX marketplace.
	 */
	public ASXInterface getMarketInterface();
	
	public String getUserEmailByUsername(String username);
	
	public String getUserPinByUsername(String username);
	
	public String updateUserPinByUsername(String username,String pin);
	
	public String updateUserPasswordByUsername(String username,String password) throws UnsupportedEncodingException, NoSuchAlgorithmException;
	
	
}

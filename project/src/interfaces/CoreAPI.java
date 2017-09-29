package interfaces;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import model.ASXInterface;
import model.PlayerStats;
import model.Transaction;
import model.UserDetails;

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
	
	/**
	 * Check a given user has confirmed their account.
	 * @param details The user login to check for confirmation.
	 * @return A string error message if one was create or null otherwise.
	 */
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
	 * Adds a a method to be called when a transaction was made.
	 * @param cb The callback to be notified on transactions being made.
	 */
	public void registerOnTransactionCallback(TransactionCallback cb);
	
	/**
	 * @return Access to functions for querying the ASX marketplace.
	 */
	public ASXInterface getMarketInterface();
	
	/**
	 * @return The email of a given username
	 */
	public String getUserEmailByUsername(String username);
	
	/**
	 * @return The authentication pin of a given username
	 */
	public String getUserPinByUsername(String username);
	
	/**
	 * @return The quantity of a specific stock owned by the player
	 */
	public int getNumStockOwned(String username, String stockCode);
    
	/**
	 * @return All stock owned by the player
	 */
    public List<Pair<String, String>> getAllStockOwned(String username);
    
    /**
     * @return The players current stats
     */
    public PlayerStats getCurrentPlayerStats(UserDetails user);
    
    /**
     * @return A list of the players current friends.
     */
    public List<String> getFriends(String username);
	
    /**
     * Sets the username's current pin
     * @param username
     * @param pin
     * @return An error in string form if one exists or null otherwise.
     */
	public String updateUserPinByUsername(String username,String pin);
	
	/**
	 * Sets the username's current password
	 * @param username
	 * @param password
	 * @return An error in string form if one exists or null otherwise.
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public String updateUserPasswordByUsername(String username,String password) throws UnsupportedEncodingException, NoSuchAlgorithmException;
	
	/**
	 * Executes a transaction on the database.
	 * @param transaction
	 * @return
	 */
	public String executeTransaction(Transaction transaction);
	
	/**
	 * Checks if the user is confirmed.
	 * @param user
	 * @return
	 */
	public String isConfirmedUser(UserDetails user);

}

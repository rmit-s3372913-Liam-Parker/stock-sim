package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Player contains data about a player including transaction history
 * */
public class PlayerStats 
{
	private String username;
	private double currentEarnings;
	private List<Transaction> transactions = new ArrayList<Transaction>();
	
	/**
	 * Constructs a player statistics object without transaction data.
	 * @param username The user name of the player
	 * @param currentEarnings The current earnings of the player.
	 */
	public PlayerStats(String username, double currentEarnings)
	{
		this.username = username;
		this.currentEarnings = currentEarnings;
	}
	
	/**
	 * Constructs a player statistics object with included transaction data.
	 * @param username The user name of the player
	 * @param currentEarnings The current earnings of the player.
	 * @param transactions The historical transactions of this player.
	 */
	public PlayerStats(String username, double currentEarnings, List<Transaction> transactions)
	{
		this(username, currentEarnings);
		this.transactions = transactions;
	}
	
	/**
	 * @return The name of this user.
	 */
	public String getUsername()
	{
		return username;
	}
	
	/**
	 * @return The current earnings of this player.
	 */
	public double getCurrentEarnings()
	{
		return currentEarnings;
	}
	
	/**
	 * Update the current earnings of this player.
	 * @param newVal The new earning amount to be set.
	 */
	public void setCurrentEarnings(double newVal)
	{
		currentEarnings = newVal;
	}
	
	/**
	 * @return All transactions registered in the past for this player.
	 */
	public List<Transaction> getPreviousTransactions()
	{
		return transactions;
	}
}

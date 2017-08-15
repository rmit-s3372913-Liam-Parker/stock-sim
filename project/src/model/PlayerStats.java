package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Player contains data about a player including transaction history
 * */
public class PlayerStats 
{
	private double currentEarnings;
	private List<Transaction> transactions = new ArrayList<Transaction>();
	
	/**
	 * @return The current earnings of this player.
	 */
	public double getCurrentEarnings()
	{
		return currentEarnings;
	}
	
	/**
	 * @return All transactions registered in the past for this player.
	 */
	public List<Transaction> getPreviousTransactions()
	{
		return transactions;
	}
}

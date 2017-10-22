package model;

/**
 * Player contains data about a player
 * */
public class PlayerStats 
{
	private String username;
	private double currentEarnings;
	
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
	
	@Override
	public String toString() 
	{ 
        return username + " - $" + currentEarnings;
    } 
}

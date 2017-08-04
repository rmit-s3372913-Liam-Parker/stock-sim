package model;

import java.util.List;

/**
 * Acts as the interface to all major system functionality.
 * @author Liam Parker
 *
 */
public interface CoreAPI 
{
	/**
	 * 
	 * @return
	 */
	public boolean registerNewUser(UserDetails details);
	
	/**
	 * 
	 * @return
	 */
	public boolean beginSession(UserDetails details);
	
	/**
	 * 
	 * @return
	 */
	public boolean endSession();
	
	/**
	 * 
	 * @return
	 */
	public List<PlayerStats> getPlayerList();
	
	//TODO: Create callback functions for different ASX interface events
}

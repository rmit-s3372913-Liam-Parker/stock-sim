package model;

import java.util.List;

<<<<<<< HEAD:project/src/model/CoreTest.java
import database.CloudDatabase;

/**
 * A mock core for faster testing and until we get a database running.
 */
public class CoreTest implements CoreAPI
=======
public class CoreSystem implements CoreAPI 
>>>>>>> ccf2bcb0d772c4ce527408b97630f724a63a071f:project/src/model/CoreSystem.java
{
	private UserDetails curUserSession = null;
	private PlayerStats curPlayerStats = null;
	private ASXInterface marketInterface = new ASXInterface();
	
	@Override
	public boolean registerNewUser(UserDetails details) 
	{
<<<<<<< HEAD:project/src/model/CoreTest.java
    	CloudDatabase cd = new CloudDatabase("register", details);
=======
		// TODO Auto-generated method stub
>>>>>>> ccf2bcb0d772c4ce527408b97630f724a63a071f:project/src/model/CoreSystem.java
		return true;
	}

	@Override
	public boolean beginSession(UserDetails details) 
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean endSession() 
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<PlayerStats> getPlayerList() 
	{
		// TODO Auto-generated method stub
		return null;
	}

}

package model;

/**
 * Stores login details
 *
 */
public class UserDetails 
{
	private int userId;
	private String username;
	private String password;
	private String email;
	private boolean remember;
	
	/**
	 * Constructs a user data object representing the given userId, username, password and email.
	 * @param username
	 * @param password
	 * @param email
	 */
	public UserDetails(int userId, String username, String password, String email)
	{
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	/**
	 * Constructs a user data object representing the given username and password.
	 * @param username
	 * @param password
	 */
	public UserDetails(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	/**
	 * @param remember
	 */
	public void setRemember(boolean remember){
		this.remember = remember;
	}

	/**
	 * @return The username of this user.
	 */
	public int getUserId()
	{
		return userId;
	}

	/**
	 * @return The username of this user.
	 */
	public String getUsername()
	{
		return username;
	}
	
	/**
	 * @return The password of this user.
	 */
	public String getPassword()
	{
		return password;
	}
	
	/**
	 * @return The email of this user.
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	/**
	 * @return The email of this user.
	 */
	public String getEmail()
	{
		return email;
	}
	
	/**
	 * @return The user remember password setting.
	 */
	public boolean getRemember()
	{
		return remember;
	}
}

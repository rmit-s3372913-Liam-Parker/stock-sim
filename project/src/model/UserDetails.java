package model;

/**
 * Stores login details
 *
 */
public class UserDetails 
{
	private String username;
	private String password;
	private String email;
	
	/**
	 * Constructs a user data object representing the given username and password.
	 * @param username
	 * @param password
	 * @param email
	 */
	public UserDetails(String username, String password, String email)
	{
		this.username = username;
		this.password = password;
		this.email = email;
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
	public String getEmail()
	{
		return email;
	}
}

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
	 * Constructs a user data object representing the given username, password and email.
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
	 * Constructs a user data object representing the given username and password.
	 * @param username
	 * @param password
	 */
	public UserDetails(String username, String password) {
		this.username = username;
		this.password = password;
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
}

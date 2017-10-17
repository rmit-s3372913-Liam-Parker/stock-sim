package model;

import java.util.Date;

public class Message {
	private int id;
	private int senderUserId;
	private String senderUsername;
	private int receiverUserId;
	private String receiverUsername;
	private String message;
	private Date time;
	private boolean read;
	
	public Message(int id, int senderUserId, String senderUsername,
			int receiverUserId, String receiverUsername, String message,
			Date time, boolean read)
	{
		this.id = id;
		this.senderUserId = senderUserId;
		this.senderUsername = senderUsername;
		this.receiverUserId = receiverUserId;
		this.receiverUsername = receiverUsername;
		this.message = message;
		this.time = time;
		this.read = read;
	}
	
	public int getId()
	{
		return id;
	}
	
	public int getSenderUserId()
	{
		return senderUserId;
	}
	
	public String getSenderUsername()
	{
		return senderUsername;
	}
	
	public int getReceiverUserId()
	{
		return receiverUserId;
	}
	
	public String getReceiverUsername()
	{
		return receiverUsername;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public Date getTime()
	{
		return time;
	}
	
	public void read()
	{
		read = false;
	}
	
	public boolean getRead()
	{
		return read;
	}
	
	@Override
	public String toString()
	{
		return "From ("+senderUserId+")"+senderUsername+": "+message;
	}
}

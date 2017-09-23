package model;

import java.util.Date;

/**
 * Stores information about a transaction on the market. Such as timestamps, stock name, value and amount etc...
 * */

public abstract class Transaction 
{
	private int transactionID;
	private String username;
	private TransactionType transactionType;
	private double postWinnings;
	private Date timeOfTransaction;
	
	public Transaction(int transID, String username, TransactionType type, double postWinnings, Date time)
	{
		transactionID = transID;
		this.username = username;
		transactionType = type;
		this.postWinnings = postWinnings;
		timeOfTransaction = time;
	}
	
	public int getTransactionID()
	{
		return transactionID;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public TransactionType getTransactionType()
	{
		return transactionType;
	}
	
	public double getPostWinnings()
	{
		return postWinnings;
	}
	
	public Date getDate()
	{
		return timeOfTransaction;
	}
	
	
}

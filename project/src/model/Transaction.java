package model;

import java.util.Date;

/**
 * Stores information about a transaction on the market. Such as timestamps, stock name, value and amount etc...
 * */

public class Transaction 
{
	private int transactionID;
	private String username;
	private String stockCode;
	private TransactionType transactionType;
	private int quantity;
	private double price;
	private double postWinnings;
	private Date timeOfTransaction;
	
	public Transaction(int transID, String username, String stock, TransactionType type,
			int quantity, double price, double postWinnings, Date time)
	{
		transactionID = transID;
		this.username = username;
		stockCode = stock;
		transactionType = type;
		this.quantity = quantity;
		this.price = price;
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
	
	public String getStockCode()
	{
		return stockCode;
	}
	
	public TransactionType getTransactionType()
	{
		return transactionType;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
	
	public double getPrice()
	{
		return price;
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

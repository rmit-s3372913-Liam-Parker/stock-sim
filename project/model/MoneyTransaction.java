package model;

import java.util.Date;

public class MoneyTransaction extends Transaction 
{
	private int partnerUserId;
	private String partnerUsername;
	private double amount;

	public MoneyTransaction(int transID, String username, TransactionType type, int partnerUserId,
			String partnerUsername, double amount, double postWinnings, Date time) {
		super(transID, username, type, postWinnings, time);
		this.partnerUserId = partnerUserId;
		this.partnerUsername = partnerUsername;
		this.amount = amount;
	}

	public int getPartnerUserId()
	{
		return partnerUserId;
	}

	public String getPartnerUsername()
	{
		return partnerUsername;
	}

	public double getWinningAmount()
	{
		return amount;
	}
	
	@Override
	public String toString()
	{
		switch(getTransactionType())
		{
		case Receive:
			return super.toString() + " - Received $" + amount + " from " + partnerUsername;
		case Send:
			return super.toString() + " - Sent $" + amount + " to " + partnerUsername;
		default:
			return super.toString() + " - ERROR";
		}
	}
}

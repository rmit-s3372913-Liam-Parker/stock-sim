package model;

import java.util.Date;

public class SendReceiveTransaction extends Transaction 
{
	private String partnerUsername;
	private double winningAmount;

	public SendReceiveTransaction(int transID, String username, TransactionType type, String partnerUsername, double winningAmount, double postWinnings, Date time) {
		super(transID, username, type, postWinnings, time);
		this.partnerUsername = partnerUsername;
		this.winningAmount = winningAmount;
	}

	public String getPartnerUsername()
	{
		return partnerUsername;
	}

	public double getWinningAmount()
	{
		return winningAmount;
	}
}

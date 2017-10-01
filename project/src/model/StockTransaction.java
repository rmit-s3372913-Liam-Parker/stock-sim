package model;

import java.util.Date;

public class StockTransaction extends Transaction 
{
	private String stockCode;
	private int quantity;
	private double price;
	
	public StockTransaction(int transID, String username, TransactionType type, String stockCode, int quantity,
			double price, double postWinnings, Date time) {
		super(transID, username, type, postWinnings, time);
		this.stockCode = stockCode;
		this.quantity = quantity;
		this.price = price;
	}

	public String getStockCode()
	{
		return stockCode;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
	
	public double getPrice()
	{
		return price;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + " - " + stockCode + " : " + quantity + " : $" + price;
	}
}

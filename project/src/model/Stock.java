package model;

import org.json.simple.JSONObject;

/**
 * Contains any information related to a specific stock such as the corporation representing the stock, current value etc...
 * */
public class Stock 
{
	private static final double BROKER_FEE = 50.0;
	private static final double PURCHASE_FEE_MULTIPLIER = 0.01;
	
	String code;
	String description;
	boolean suspended;
	
	long volume;
	long averageDailyVolume;
	long marketCap;
	long numShares;
	
	double lastPrice;
	double openPrice;
	double dayHighPrice;
	double dayLowPrice;
	double changePrice;
	double bidPrice;
	double offerPrice;
	
	/**
	 * Builds a stock object from a given JSONObject.
	 * The ASX online interface returns these JSONObjects when requested.
	 * @param obj A Json object containing relevant stock data to be read.
	 */
	public Stock(JSONObject obj)
	{
		code = (String)obj.get("code");
		description = (String)obj.get("desc_full");
		lastPrice = (double)obj.get("last_price");
		volume = (long)obj.get("volume");
		marketCap = (long)obj.get("market_cap");
		numShares = (long)obj.get("number_of_shares");
		suspended = (boolean)obj.get("suspended");
	}
	
	public double calculateBrokerFee()
	{
		return BROKER_FEE;
	}
	
	public double calculatePurchaseFee(int quantity)
	{
		return calculateStockCost(quantity) * PURCHASE_FEE_MULTIPLIER;
	}
	
	public double calculateStockCost(int quantity)
	{
		return quantity * getStockPrice();
	}
	
	public double calculateTotalCost(int quantity)
	{
		return calculateStockCost(quantity) + calculateBrokerFee() + calculatePurchaseFee(quantity);
	}
	
	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public boolean isSuspended() {
		return suspended;
	}

	public long getVolume() {
		return volume;
	}

	public long getAverageDailyVolume() {
		return averageDailyVolume;
	}

	public long getMarketCap() {
		return marketCap;
	}

	public long getNumShares() {
		return numShares;
	}

	public double getStockPrice() {
		return lastPrice;
	}

	public double getOpenPrice() {
		return openPrice;
	}

	public double getDayHighPrice() {
		return dayHighPrice;
	}

	public double getDayLowPrice() {
		return dayLowPrice;
	}

	public double getChangePrice() {
		return changePrice;
	}

	public double getBidPrice() {
		return bidPrice;
	}

	public double getOfferPrice() {
		return offerPrice;
	}
	
}

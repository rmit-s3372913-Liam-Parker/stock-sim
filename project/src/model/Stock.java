package model;

import org.json.simple.JSONObject;

/**
 * Contains any information related to a specific stock such as the corporation representing the stock, current value etc...
 * */
public class Stock 
{
	private static final double BROKER_FEE = 50.0;
	private static final double PURCHASE_FEE_MULTIPLIER = 0.01;
	
	//TODO: Thorough commenting of the meaning of each variable.
	// Unlikely will need many of these variables. I suggest figuring out what
	// we need and removing everything else.
	String code;
	String description;
	boolean suspended;
	
	long volume;
	long averageDailyVolume;
	long marketCap;
	long numShares;
	//long deprecatedMarketCap;
	//long deprecatedNumShares;

	//double annualDividendYield;
	//double pe;
	//double eps;
	double lastPrice;
	double openPrice;
	double dayHighPrice;
	double dayLowPrice;
	double changePrice;
	double bidPrice;
	double offerPrice;
	//double previousClosePrice;
	//String changePercentage;
	//String previousDayChangePercentage;
	//String lastTradeDate;

	//double yearOpenPrice;
	//double yearHighPrice;
	//double yearLowPrice;
	//double yearChangePrice;
	//String yearOpenDate;
	//String yearHighDate;
	//String yearLowDate;
	//String yearChangePercentage;
	
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
		//openPrice = (double)obj.get("open_price");
		//dayHighPrice = (double)obj.get("day_high_price");
		//dayLowPrice = (double)obj.get("day_low_price");
		//changePrice = (double)obj.get("change_price");
		//changePercentage = (String)obj.get("change_in_percent");
		volume = (long)obj.get("volume");
		//bidPrice = (double)obj.get("bid_price");
		//offerPrice = (double)obj.get("offer_price");
		//previousClosePrice = (double)obj.get("previous_close_price");
		//previousDayChangePercentage = (String)obj.get("previous_day_percent_change");
		//yearHighPrice = (double)obj.get("year_high_price");
		//lastTradeDate = (String)obj.get("last_trade_date");
		//yearHighDate = (String)obj.get("year_high_date");
		//yearLowPrice = (double)obj.get("year_low_price");
		//yearLowDate = (String)obj.get("year_low_date");
		//yearOpenPrice = (double)obj.get("year_open_price");
		//yearOpenDate = (String)obj.get("year_open_date");
		//yearChangePrice = (double)obj.get("year_change_price");
		//yearChangePercentage = (String)obj.get("year_change_in_percentage");
		//pe = (double)obj.get("pe");
		//eps = (double)obj.get("eps");
		//averageDailyVolume = (long)obj.get("average_daily_volume");
		//annualDividendYield = (double)obj.get("annual_dividend_yield");
		marketCap = (long)obj.get("market_cap");
		numShares = (long)obj.get("number_of_shares");
		//deprecatedMarketCap = (long)obj.get("deprecated_market_cap");
		//deprecatedNumShares = (long)obj.get("deprecated_number_of_shares");
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
		return quantity * getLastPrice();
	}
	
	public double calculateTotalCost(int quantity)
	{
		return calculateBrokerFee() + calculatePurchaseFee(quantity);
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

	public double getLastPrice() {
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

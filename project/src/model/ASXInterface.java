package model;

import java.net.MalformedURLException;
import java.net.URL;

/*
 * ASXInterface allows for queries of ASX share market data.
 * */
public class ASXInterface 
{
	private static final String STOCK_URL = "http://data.asx.com.au/data/1/share/";
	private static final String COMPANY_LIST = "http://www.asx.com.au/asx/research/ASXListedCompanies.csv";
	
	/*
	 * Returns a Stock object containing all market information related to a specific stock code.
	 * */
	public Stock GetStockData(String stockCode)
	{
		Stock stock = null;
		
		try 
		{
			URL website = new URL(STOCK_URL + stockCode);
			//TODO: Fill out stock information
		} 
		catch (MalformedURLException e) { e.printStackTrace(); }
		
		return stock;
	}
	
	
}

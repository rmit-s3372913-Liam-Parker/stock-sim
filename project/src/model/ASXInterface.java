package model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * ASXInterface allows for queries of ASX share market data.
 * */
public class ASXInterface 
{
	private final String STOCK_URL = "http://data.asx.com.au/data/1/share/";
	private final String COMPANY_LIST = "http://www.asx.com.au/asx/research/ASXListedCompanies.csv";
	
	private static ASXInterface singleton = null;
	
	/**
	 * @param stockCode The stock code to attempt to retrieve data for.
	 * @return Stock information for given code.
	 */
	public Stock getStockData(String stockCode)
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
	
	/**
	 * @return A List<> of companies trading on the ASX.
	 */
	public List<CompanyInfo> getCurrentCompanyList()
	{
		List<CompanyInfo> companyList = new ArrayList<CompanyInfo>();
		
		//TODO: Parse csv file from url COMPANY_LIST and return listed company data
		
		return companyList;
	}
	
}

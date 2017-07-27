package model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/*
 * ASXInterface allows for queries of ASX share market data.
 * */
public class ASXInterface 
{
	private final String STOCK_URL = "http://data.asx.com.au/data/1/share/";
	private final String COMPANY_LIST = "http://www.asx.com.au/asx/research/ASXListedCompanies.csv";
	
	private static ASXInterface singleton = null;
	
	public ASXInterface getSingleton()
	{
		 if(singleton == null)
		 {
			 singleton = new ASXInterface();
			 return singleton;
		 }
		 else
			 return singleton;
	}
	
	/*
	 * Returns a Stock object containing all market information related to a specific stock code.
	 * */
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
	
	/*
	 * Returns a list of CompanyInfo objects containing data on all companies listed on the ASX
	 * */
	public List<CompanyInfo> getCurrentCompanyList()
	{
		List<CompanyInfo> companyList = new ArrayList<CompanyInfo>();
		
		//TODO: Parse csv file from url COMPANY_LIST and return listed company data
		
		return companyList;
	}
	
}

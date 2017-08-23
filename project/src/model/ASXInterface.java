package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.opencsv.CSVReader;

/**
 * ASXInterface allows for queries of ASX share market data.
 * */
public class ASXInterface 
{
	private final String STOCK_URL = "http://data.asx.com.au/data/1/share/";
	private final String COMPANY_LIST = "http://www.asx.com.au/asx/research/ASXListedCompanies.csv";
	
	private String lastUpdate = "";
	private List<CompanyInfo> cachedCompanyInfo = null;
	
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
		if(cachedCompanyInfo != null) 
			return cachedCompanyInfo;
		else
			cachedCompanyInfo = new ArrayList<CompanyInfo>();
		
		// Download and load the companies list from ASX.
		try 
		{
			InputStream csv = new URL(COMPANY_LIST).openStream();
			CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(csv, "UTF-8")), ',', '"', 2);
		}
		catch (MalformedURLException e1) { e1.printStackTrace(); } 
		catch (IOException e1)           { e1.printStackTrace(); }
		
		return cachedCompanyInfo;
	}
	
}

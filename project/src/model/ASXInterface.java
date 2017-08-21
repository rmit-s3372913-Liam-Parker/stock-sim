package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
		File csv = new File(COMPANY_LIST);
			
		try 
		{
			Scanner s = new Scanner(csv);
				
			// Two special cases at the top of csv. Last update and blank line.
			lastUpdate = s.nextLine();
			s.nextLine();
				
			// Iterate over listed companies
			while(s.hasNextLine())
			{
				String rawData = s.nextLine();
				String[] splitData = rawData.split(",");
				CompanyInfo info = new CompanyInfo(splitData[0], splitData[1], splitData[2]);
				cachedCompanyInfo.add(info);
		    }
				
			s.close();
		} 
		catch (FileNotFoundException e) { e.printStackTrace(); }
		
		return cachedCompanyInfo;
	}
	
}

package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.opencsv.CSVReader;

/**
 * ASXInterface allows for queries of ASX share market data.
 * */
public class ASXInterface 
{
	private final static String STOCK_URL = "http://data.asx.com.au/data/1/share/";
	private final static String COMPANY_LIST = "http://www.asx.com.au/asx/research/ASXListedCompanies.csv";
	private final static int LINE_SKIP = 3;
	
	private List<CompanyInfo> cachedCompanyInfo = null;
	
	/**
	 * @param stockCode The stock code to attempt to retrieve data for.
	 * @return Stock information for given code.
	 */
	public Stock getStockData(String stockCode) throws IOException
	{
		Stock stock = null;
		JSONParser parser = new JSONParser();
		
		try 
		{
			InputStream stream = new URL( STOCK_URL + stockCode ).openStream();
			Object parsedData = parser.parse(IOUtils.toString(stream, Charset.forName("UTF-8")));
			JSONObject json = (JSONObject) parsedData;
			stock = new Stock(json);
		} 
		catch (MalformedURLException e) { e.printStackTrace(); }
	    catch (UnsupportedEncodingException e) { e.printStackTrace(); }
		catch (ParseException e) { e.printStackTrace(); }
		catch (IOException e) { throw e; }
		
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
			CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(csv, "UTF-8")));
			List<String[]> lines = reader.readAll();
			
			for(int i = LINE_SKIP; i < lines.size(); ++i)
			{
				CompanyInfo info = new CompanyInfo(lines.get(i)[0], lines.get(i)[1], lines.get(i)[2]);
				cachedCompanyInfo.add(info);
			}
			
			reader.close();
		}
		catch (MalformedURLException e1) { e1.printStackTrace(); } 
		catch (IOException e1)           { e1.printStackTrace(); }
		
		return cachedCompanyInfo;
	}
	
}

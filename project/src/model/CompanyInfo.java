package model;

/**
 * Stores information related to a company that trades on the market.
 * */
public class CompanyInfo 
{
	String companyName;
	String marketCode;
	String description;
	
	/**
	 * Constructs a company info object with the given parameters.
	 * @param name The name of the company.
	 * @param code The share code this company is trading.
	 * @param desc A description used to categorize the company.
	 */
	public CompanyInfo(String name, String code, String desc)
	{
		companyName = name;
		marketCode = code;
		description = desc;
	}
	
	public String getCompanyName()
	{
		return companyName;
	}
	
	public String getStockCode()
	{
		return marketCode;
	}
	
	public String getDescription()
	{
		return description;
	}
}

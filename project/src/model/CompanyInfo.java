package model;

/*
 * Stores information related to a company that trades on the market.
 * */
public class CompanyInfo 
{
	String companyName;
	String marketCode;
	String description;
	
	public CompanyInfo(String name, String code, String desc)
	{
		companyName = name;
		marketCode = code;
		description = desc;
	}
}

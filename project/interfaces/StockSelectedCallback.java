package interfaces;

import model.CompanyInfo;

/**
 * Implemented by classes which expect to update when stock selections change.
 */
public interface StockSelectedCallback 
{
	/**
	 * Called when a new stock is selected. Implementor can then update itself to
	 * reflect new stock codes data as needed.
	 * 
	 * @param stockCode The new stock code.
	 */
	void stockSelected(CompanyInfo data);
}

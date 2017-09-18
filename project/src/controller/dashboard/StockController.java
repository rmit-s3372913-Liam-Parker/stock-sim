package controller.dashboard;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.PlayerStats;
import model.Stock;
import model.Transaction;
import model.TransactionType;
import model.UserDetails;
import view.StockView;

public class StockController extends Controller
{
	private static final int EMPTY = 0;
	
	StockView stockView;
	Stock targetStock;
	
	int quantity = 0;
	double stockCost = 0.0;
	double brokerFee = 0.0;
	double purchaseFee = 0.0;
	double total = 0.0;
	
	double FAKE_EARNINGS = 10000.0;
	
	public StockController(StockView stockView)
	{
		this.stockView = stockView;
	}
	
	@Override
	public void handle(ActionEvent event) 
	{
		TransactionType type = TransactionType.Buy;
		
		if(event.getSource() == stockView.getBuyButton())
		{
			type = TransactionType.Buy;
		}
		else if(event.getSource() == stockView.getSellButton())
		{
			type = TransactionType.Sell;
		}
		else if(event.getSource() == stockView.getQuantityField())
		{
			refreshStockView();
		}
		
		try
		{
			UserDetails curUser = getModel().getSessionDetails();
			PlayerStats stats = getModel().getSessionStats();
			
			//double postWinnings = stats.getCurrentEarnings() + transactionCost;
			double postWinnings;
			if (type==TransactionType.Buy)
				postWinnings = FAKE_EARNINGS - total;
			else
				postWinnings = FAKE_EARNINGS + total;
			
			Transaction transaction = new Transaction(
					EMPTY,
					curUser.getUsername(),
					targetStock.getCode(),
					type,
					quantity,
					targetStock.getLastPrice(),
					postWinnings, null);
			
			getModel().getCloudDatabase().executeTransaction(transaction);
		}
		catch(NumberFormatException e) { e.printStackTrace(); }
		
	}
	
	public void setTargetStock(Stock stock)
	{
		targetStock = stock;
		refreshStockView();
	}
	
	private void refreshStockView()
	{
		// Calculate stock transaction costs
		quantity = Integer.parseInt(stockView.getQuantityField().getText());
		stockCost = targetStock.calculateStockCost(quantity);
		brokerFee = targetStock.calculateBrokerFee();
		purchaseFee = targetStock.calculatePurchaseFee(quantity);
		total = targetStock.calculateTotalCost(quantity);
							
		// Update the stock view
		stockView.setStockCost(stockCost);
		stockView.setBrokerFee(brokerFee);
		stockView.setPurchaseFee(purchaseFee);
		stockView.setTotalFee(total);
	}

}

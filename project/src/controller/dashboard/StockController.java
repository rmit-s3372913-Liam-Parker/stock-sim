package controller.dashboard;


import controller.Controller;
import database.CloudDatabase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import model.PlayerStats;
import model.Stock;
import model.Transaction;
import model.TransactionType;
import model.UserDetails;
import view.StockView;

public class StockController extends Controller implements ChangeListener<String>
{
	private static final int EMPTY = 0;
	
	private StockView stockView;
	private Stock targetStock;
	
	private int quantity = 0;
	private double stockCost = 0.0;
	private double brokerFee = 0.0;
	private double purchaseFee = 0.0;
	private double total = 0.0;
	
	public StockController(StockView stockView)
	{
		this.stockView = stockView;
	}
	
	@Override
	public void handle(ActionEvent event) 
	{
		TransactionType type = TransactionType.Buy;
		final String shareString = (quantity > 1) ? "shares":"share";
		boolean transactionApproved = false;
		
		// Validate input quantity
		if(quantity == 0)
		{
			displayNotificationModal("Please input a quantity greater than zero!");
			return;
		}
		
		// Validate user interaction
		if(event.getSource() == stockView.getBuyButton())
		{
			type = TransactionType.Buy;
			if(displayQuestionModal("Purchasing " + quantity + " " + shareString + " for total of $" + total))
				transactionApproved = true;
		}
		else if(event.getSource() == stockView.getSellButton())
		{
			type = TransactionType.Sell;
			if(displayQuestionModal("Selling " + quantity + " " + shareString + " for total of $" + total))
				transactionApproved = true;
		}
		
		if(transactionApproved)
			executeTransaction(type);
	}
	
	public void setTargetStock(Stock stock)
	{
		targetStock = stock;
		refreshStockView();
	}
	
	// Callback for changes to the quantity field
	@Override
	public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) 
	{
		refreshStockView();
	}
	
	private void refreshStockView()
	{
		// Calculate stock transaction costs
		if (stockView.getQuantityField().getText().isEmpty())
			quantity = 0;
		else
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
	
	private void executeTransaction(TransactionType type)
	{
		try
		{
			UserDetails curUser = getModel().getSessionDetails();
			PlayerStats stats = getModel().getSessionStats();
			CloudDatabase db = getModel().getCloudDatabase();

			int stockQuantity = db.getStockQuantity(curUser.getUsername(),targetStock.getCode());
			double winnings = stats.getCurrentEarnings();
			
			if (type == TransactionType.Buy)
			{
				if (winnings - total < 0)
				{
					displayNotificationModal("You have insufficient funds to complete this transaction!");
				}
				else
					winnings -= total;
			}
			else if(type == TransactionType.Sell)
			{
				if (stockQuantity != CloudDatabase.QUANTITY_ERROR)
				{
					displayNotificationModal("You have insufficient shares to complete this transaction!");
				}
				else
					winnings += total;
			}

			Transaction transaction = new Transaction(
					EMPTY,
					curUser.getUsername(),
					targetStock.getCode(),
					type,
					quantity,
					targetStock.getStockPrice(),
					winnings,
					null);

			getModel().getCloudDatabase().executeTransaction(transaction);
		}
		catch(NumberFormatException e) { e.printStackTrace(); }
	}
}

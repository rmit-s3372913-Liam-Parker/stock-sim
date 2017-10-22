package controller.dashboard;

import controller.Controller;
import database.CloudDatabase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import model.StockTransaction;
import model.PlayerStats;
import model.Stock;
import model.TransactionType;
import model.UserDetails;
import view.PlayerStatsView;
import view.StockView;

public class StockController extends Controller implements ChangeListener<String>
{
	private static final int NOT_USED = 0;
	
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
	
	//validate quantity input and check which button was pressed
	@Override
	public void handle(ActionEvent event) 
	{
		TransactionType type = TransactionType.Buy;
		final String shareString = (quantity > 1) ? "shares":"share";
		boolean transactionApproved = false;
		
		// Validate input quantity
		if(quantity == 0)
		{
			displayErrorModal("Transaction Error", "Please input a quantity greater than zero!");
			return;
		}
		
		// Validate user interaction
		if(event.getSource() == stockView.getBuyButton())
		{
			type = TransactionType.Buy;
			if(displayConfirmationModal("Transaction Confirmation", "Purchasing " + quantity + " " + shareString + " for total of $" + total))
				transactionApproved = true;
		}
		else if(event.getSource() == stockView.getSellButton())
		{
			type = TransactionType.Sell;
			if(displayConfirmationModal("Transaction Confirmation","Selling " + quantity + " " + shareString + " for total of $" + total))
				transactionApproved = true;
		}
		
		//store transaction in cloud database
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
	
	//store transaction in cloud database and notify user upon failure
	private void executeTransaction(TransactionType type)
	{
		try
		{
			UserDetails curUser = getModel().getSessionDetails();
			PlayerStats stats = getModel().getSessionStats();

			int stockQuantity = getModel().getNumStockOwned(curUser.getUsername(),targetStock.getCode());
			double winnings = stats.getCurrentEarnings();
			
			if (winnings == CloudDatabase.WINNING_ERROR)
			{
				displayErrorModal("Couldn't Communicate with Server",
						"We are having trouble connecting to server, check your connection and try again later.");
				return;
			}
			if (type == TransactionType.Buy)
			{
				if (winnings - total < 0)
				{
					displayErrorModal("Transaction Failed","You have insufficient funds to complete this transaction.");
					return;
				}
				else
				{
					winnings -= total;
				}
			}
			else
			{
				if (stockQuantity != CloudDatabase.QUANTITY_ERROR)
				{
					if (stockQuantity < quantity)
					{
						displayNotificationModal("Transaction Failed","You have insufficient shares to complete this transaction!");
						return;
					}
					else
					{
						winnings += total;
					}
				}
				else
				{
					displayNotificationModal("Couldn't Communicate with Server","We are having trouble connecting to server, please try again");
					return;
				}
			}

			StockTransaction transaction = new StockTransaction(
					NOT_USED,
					curUser.getUsername(),
					type,
					targetStock.getCode(),
					quantity,
					targetStock.getStockPrice(),
					winnings,
					null);

			//store in cloud database
			getModel().executeTransaction(transaction);
			//update local user's winning
			PlayerStatsView.SetCurrentEarnings(getModel().getCurrentPlayerStats(curUser).getCurrentEarnings());
		}
		catch(NumberFormatException e) 
		{
			e.printStackTrace();
		}
	}
}

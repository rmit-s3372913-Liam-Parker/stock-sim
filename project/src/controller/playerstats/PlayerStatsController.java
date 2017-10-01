package controller.playerstats;

import java.util.List;

import controller.Controller;
import interfaces.TransactionCallback;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ToggleButton;
import javafx.util.Pair;
import model.Transaction;
import model.UserDetails;

public class PlayerStatsController extends Controller implements TransactionCallback
{
	ToggleButton transactions;
	ToggleButton stocks;
	ObservableList<String> list;
	UserDetails player = getModel().getSessionDetails();
	
	public PlayerStatsController(ObservableList<String> list, ToggleButton transactions, ToggleButton stocks)
	{
		this.transactions= transactions;
		this.stocks = stocks;
		this.list = list;
		
		getModel().registerOnTransactionCallback(this);
		
		if(transactions.isSelected())
			loadTransactions();
		else
			loadStocks();
	}
	
	@Override
	public void handle(ActionEvent event) 
	{
		if(event.getSource() == transactions)
			loadTransactions();
		else if(event.getSource() == stocks)
			loadStocks();
	}
	
	@Override
	public void onTransactionCallback() 
	{
		if(transactions.isSelected())
			loadTransactions();
		else
			loadStocks();
	}
	
	private void loadStocks()
	{
		list.clear();
		List<Pair<String, String>> stockList = getModel().getAllStockOwned(player.getUsername());
		
		for(int i = 0; i < stockList.size(); ++i)
		{
			Pair<String, String> pair = stockList.get(i);
			list.add(pair.getKey() + " - " + pair.getValue());
		}
	}
	
	private void loadTransactions()
	{
		list.clear();
		List<Transaction> transactionList = getModel().getTransactions(player);
		
		for(int i = 0; i < transactionList.size(); ++i)
		{
			Transaction t = transactionList.get(i);
			list.add(t.toString());
		}
	}
}

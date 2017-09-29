package controller.playerstats;

import java.util.List;

import controller.Controller;
import interfaces.TransactionCallback;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ToggleButton;
import javafx.util.Pair;
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
		List<Pair<String, String>> stocks = getModel().getAllStockOwned(player.getUsername());
		
		for(int i = 0; i < stocks.size(); ++i)
		{
			Pair<String, String> pair = stocks.get(i);
			list.add(pair.getKey() + " - " + pair.getValue());
		}
	}
	
	private void loadTransactions()
	{
		list.clear();
	}
}

package view;

import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Stock;

public class StockView extends BorderPane 
{	
	Text title = new Text("No Stock Selected");
	BorderPane mainPane = new BorderPane();
	
	public StockView()
	{
		populate();
	}
	
	public void populate()
	{
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		this.setCenter(title);
	}
	
	public void setCurrentStock(String stockCode)
	{
		Stock data = StockApplication.getModel().getMarketInterface().getStockData(stockCode);
		
		this.setTop(null);
		this.setCenter(null);
		
		title.setText(data.getCode());
		this.setTop(title);
		
		this.setCenter(new Text(String.valueOf(data.getLastPrice())));
	}
}

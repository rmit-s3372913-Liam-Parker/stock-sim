package view;

import controller.dashboard.StockController;
import interfaces.StockSelectedCallback;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import model.Stock;

public class StockView extends BorderPane implements StockSelectedCallback
{	
	Text title = new Text("No Stock Selected");
	
	GridPane stockInfoPane = new GridPane();
	Text stockValue = new Text();
	TextField quantityField = new TextField();
	Button buyBtn = new Button("Buy");
	Button sellBtn = new Button("Sell");
	StockController controller = new StockController(buyBtn, sellBtn, quantityField);
	
	public StockView()
	{
		populate();
	}
	
	public void populate()
	{
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		this.setCenter(title);
		
		buyBtn.setOnAction(controller);
		sellBtn.setOnAction(controller);
		
		// Build stock information view
		stockInfoPane.setAlignment(Pos.CENTER);
		stockInfoPane.add(stockValue, 0, 0);
		stockInfoPane.add(new Text("Quantity: "), 0, 1);
		stockInfoPane.add(quantityField, 1, 1);
		stockInfoPane.add(buyBtn, 0, 2);
		stockInfoPane.add(sellBtn, 1, 2);
	}
	
	@Override
	public void stockSelected(String stockCode)
	{
		Stock data = StockApplication.getModel().getMarketInterface().getStockData(stockCode);
		controller.setTargetStock(data);
		
		stockValue.setText("Last Value: " + String.valueOf(data.getLastPrice()));
		
		this.setTop(null);
		this.setCenter(null);
		
		title.setText(data.getCode());
		this.setTop(title);
		
		this.setCenter(stockInfoPane);
	}
}

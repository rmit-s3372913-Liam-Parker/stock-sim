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
import ultilities.NumberField;

public class StockView extends BorderPane implements StockSelectedCallback
{	
	Text title = new Text("No Stock Selected");
	
	GridPane stockInfoPane = new GridPane();
	
	Text stockValueText = new Text();
	Text brokerFeeText = new Text();
	Text purchaseFeeText = new Text();
	Text totalText = new Text();
	
	TextField quantityField = new TextField("0");
	Button buyBtn = new Button("Buy");
	Button sellBtn = new Button("Sell");
	StockController controller = new StockController(this);
	
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
		
		NumberField.numberField(quantityField);
		quantityField.textProperty().addListener(controller);
		
		// Build stock information view
		stockInfoPane.setAlignment(Pos.CENTER);
		stockInfoPane.add(stockValueText,         0, 0);
		stockInfoPane.add(brokerFeeText,          0, 1);
		stockInfoPane.add(purchaseFeeText,        0, 2);
		stockInfoPane.add(totalText,              0, 3);
		stockInfoPane.add(new Text("Quantity: "), 0, 4);
		stockInfoPane.add(quantityField,          1, 4);
		stockInfoPane.add(buyBtn,                 0, 5);
		stockInfoPane.add(sellBtn,                1, 5);
	}
	
	@Override
	public void stockSelected(String stockCode)
	{
		Stock data = StockApplication.getModel().getMarketInterface().getStockData(stockCode);
		controller.setTargetStock(data);
			
		this.setTop(null);
		this.setCenter(null);
		
		title.setText(data.getCode());
		this.setTop(title);
		
		this.setCenter(stockInfoPane);
	}
	
	public Button getBuyButton()
	{
		return buyBtn;
	}
	
	public Button getSellButton()
	{
		return sellBtn;
	}
	
	public void setStockCost(double stockValue)
	{
		stockValueText.setText(String.format("Share cost: $%.2f", stockValue));
	}
	
	public void setBrokerFee(double brokerFee)
	{
		brokerFeeText.setText(String.format("Broker Fee: $%.2f", brokerFee));
	}
	
	public void setPurchaseFee(double purchaseFee)
	{
		purchaseFeeText.setText(String.format("Purchase Fee: $%.2f", purchaseFee));
	}
	
	public void setTotalFee(double totalFee)
	{
		totalText.setText(String.format("Total: $%.2f", totalFee));
	}
	
	public TextField getQuantityField()
	{
		return quantityField;
	}
}

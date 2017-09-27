package view;

import java.util.Random;

import controller.dashboard.StockController;
import interfaces.StockSelectedCallback;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.CompanyInfo;
import model.Stock;
import ultilities.NumberField;

public class StockView extends BorderPane implements StockSelectedCallback
{	
	Text title = new Text("No Stock Selected");
	
	GridPane stockInfoPane = new GridPane();
	HBox horizontalLayout = new HBox();
	
	Text stockValueText = new Text();
	Text brokerFeeText = new Text();
	Text purchaseFeeText = new Text();
	Text totalText = new Text();
	
	TextField quantityField = new TextField("0");
	Button buyBtn = new Button("Buy");
	Button sellBtn = new Button("Sell");
	StockController controller = new StockController(this);
	
	NumberAxis x = new NumberAxis();
	NumberAxis y = new NumberAxis();
	final LineChart<Number,Number> stockHistoryChart = new LineChart<Number,Number>(x,y);
	
	public StockView()
	{
		populate();
	}
	
	public void populate()
	{
		//setting up title
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		this.setCenter(title);
		BorderPane.setAlignment(title, Pos.CENTER);
		
		//setting up buttons
		buyBtn.setOnAction(controller);
		sellBtn.setOnAction(controller);
		
		//setting up input field
		NumberField.numberField(quantityField);
		quantityField.textProperty().addListener(controller);
		
		// Build stock information view
		stockInfoPane.add(stockValueText,         0, 0);
		stockInfoPane.add(brokerFeeText,          0, 1);
		stockInfoPane.add(purchaseFeeText,        0, 2);
		stockInfoPane.add(totalText,              0, 3);
		stockInfoPane.add(new Text("Quantity: "), 0, 4);
		stockInfoPane.add(quantityField,          1, 4);
		stockInfoPane.add(buyBtn,                 0, 5);
		stockInfoPane.add(sellBtn,                1, 5);
		
		//TODO: REMOVE ONCE WE HAVE HISTORY WORKING
		generateFakeChartData();
		
		horizontalLayout.getChildren().addAll(stockInfoPane, stockHistoryChart);
		HBox.setHgrow(stockHistoryChart, Priority.ALWAYS);  
	}
	
	//create a view upon clicking on a stock
	@Override
	public void stockSelected(CompanyInfo data)
	{
		Stock stock = StockApplication.getModel().getMarketInterface().getStockData(data.getStockCode());
		controller.setTargetStock(stock);
			
		this.setTop(null);
		this.setCenter(null);
		
		title.setText(stock.getCode());
		this.setTop(title);
		
		this.setCenter(horizontalLayout);
		generateFakeChartData();
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
	
	/*
	 * Purely for testing purposes.
	 * Utilizing: https://stackoverflow.com/questions/40431966/what-is-the-best-way-to-generate-a-random-float-value-included-into-a-specified
	 * Unintended for any commercial release.
	 */
	private void generateFakeChartData()
	{
		stockHistoryChart.getData().clear();
		
		XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
		series1.setName("Last Price");
		for(int i = 1; i <= 12; ++i)
		{
			double random = 10.0 + Math.random() * (90.0 - 10.0);
			series1.getData().add(new XYChart.Data<>(i, random));
		}
		stockHistoryChart.getData().add(series1);
		
		XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
		series2.setName("Open Price");
		for(int i = 1; i <= 12; ++i)
		{
			double random = 0.1 + Math.random() * (100.0);
			series2.getData().add(new XYChart.Data<>(i, random));
		}
		stockHistoryChart.getData().add(series2);
		
		XYChart.Series<Number, Number> series3 = new XYChart.Series<>();
		series3.setName("Close Price");
		for(int i = 1; i <= 12; ++i)
		{
			double random = 0.1 + Math.random() * (100.0);
			series3.getData().add(new XYChart.Data<>(i, random));
		}
		stockHistoryChart.getData().add(series3);

	}
}

package view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import controller.dashboard.StockController;
import interfaces.StockSelectedCallback;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import model.CompanyInfo;
import model.Stock;
import ultilities.NumberField;

public class StockView extends BorderPane implements StockSelectedCallback
{	
	private Text title = new Text("No Stock Selected");

	private GridPane stockInfoPane = new GridPane();
	private HBox horizontalLayout = new HBox();

	private Text stockValueText = new Text();
	private Text brokerFeeText = new Text();
	private Text purchaseFeeText = new Text();
	private Text totalText = new Text();

	private TextField quantityField = new TextField("1");
	private Button buyBtn = new Button("Buy");
	private Button sellBtn = new Button("Sell");
	private StockController controller = new StockController(this);

	private CategoryAxis x = new CategoryAxis();
	private NumberAxis y = new NumberAxis();
	private LineChart<String,Number> stockHistoryChart = new LineChart<String, Number>(x,y);;
	private final static String FILES_PATH = "./dataStorage/stockHistory/";
	
	public StockView()
	{
		//setting up title
		title.setFont(StockApplication.APP_HEADING_FONT);
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

		horizontalLayout.getChildren().addAll(stockInfoPane, stockHistoryChart);
		HBox.setHgrow(stockHistoryChart, Priority.ALWAYS);
	}
	
	//create a view upon clicking on a stock
	@Override
	public void stockSelected(CompanyInfo data)
	{
		Stock stock;
		try 
		{
			stock = StockApplication.getModel().getMarketInterface().getStockData(data.getStockCode());
		} 
		catch (IOException e) 
		{
			this.setTop(null);
			this.setCenter(null);
			
			title.setText(data.getStockCode() + " stocks currently unavailable!");
			this.setCenter(title);
			
			return;
		}
		
		controller.setTargetStock(stock);
			
		this.setTop(null);
		this.setCenter(null);
		
		title.setText(stock.getCode());
		this.setTop(title);
		
		this.setCenter(horizontalLayout);
		generateChartData(data.getStockCode());
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
	
	private void generateChartData(String stockCode)
	{
		stockHistoryChart.getData().clear();

		XYChart.Series<String, Number> series1 = new XYChart.Series<>();
		series1.setName("Close Price");
		for (int j=0; j<4; j++)
			for(int i = 1; i <= 5; i++)
			{
				LocalDate previousWeekDay = 
					LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)).minusWeeks(5-j)
						.with(TemporalAdjusters.nextOrSame(DayOfWeek.of(i))) ;
				String file = previousWeekDay.toString().replace("-", "");
				try(FileReader reader = new FileReader(FILES_PATH+file+".txt"))
				{
					BufferedReader bufferedReader = new BufferedReader(reader);
	
					String line;
					String[] stock = null;
					while ((line = bufferedReader.readLine()) != null)
					{
						stock = line.split(",");
						if (stock[0].equals(stockCode))
						{
							series1.getData().add(
									new XYChart.Data<>(previousWeekDay.format(DateTimeFormatter.ofPattern("dd/MM")),
									Double.parseDouble(stock[5])));
							
							break;
						}
					}
				}
				catch (FileNotFoundException e) 
				{
					e.printStackTrace();
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		stockHistoryChart.getXAxis().setAnimated(false);
		stockHistoryChart.getData().add(series1);
	}
}

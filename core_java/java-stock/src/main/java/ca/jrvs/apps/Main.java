package ca.jrvs.apps;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;


public class Main {

  public static void main(String[] args) throws IOException {

    StockQuote2CSV getData = new StockQuote2CSV();
    String jsonBody = getData.response.body().string();
    String csvFile = "stock-data.csv";


    try {
      FileWriter writer = new FileWriter(csvFile);
      writer.write("Symbol,Open,High,Low,Price,Volume,LatestTradingDay,PreviousDay,Change,ChangePercent\n");

      // Parse JSON response into a Java object
      JSONObject jsonObject = new JSONObject(jsonBody);
      JSONObject globalQuote = jsonObject.getJSONObject("Global Quote");

// Extract required fields from the JSON object
      String symbolValue = globalQuote.getString("01. symbol");
      String openValue = globalQuote.getString("02. open");
      String highValue = globalQuote.getString("03. high");
      String lowValue = globalQuote.getString("04. low");
      String priceValue = globalQuote.getString("05. price");
      String volumeValue = globalQuote.getString("06. volume");
      String latestTradingDay = globalQuote.getString("07. latest trading day");
      String previousCloseValue = globalQuote.getString("08. previous close");
      String changeValue = globalQuote.getString("09. change");
      String changePercentValue = globalQuote.getString("10. change percent");

      writer.write(symbolValue + "," + openValue + "," + "," + highValue + "," + lowValue + "," + priceValue + "," + volumeValue + "," + latestTradingDay + "," + previousCloseValue + "," + changeValue + "," +  changePercentValue + "\n");
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
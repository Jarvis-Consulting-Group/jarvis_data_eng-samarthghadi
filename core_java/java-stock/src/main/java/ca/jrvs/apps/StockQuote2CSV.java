package ca.jrvs.apps;


import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StockQuote2CSV {
  OkHttpClient client = new OkHttpClient();

  Request request = new Request.Builder()
      .url("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=MSFT&datatype=json")
      .get()
      .addHeader("X-RapidAPI-Key", "0bb85e3b46msh08f713d4a2436c3p1ee870jsn47fd864cbb1b")
      .addHeader("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
      .build();

  Response response = client.newCall(request).execute();


  public StockQuote2CSV() throws IOException {
  }
}

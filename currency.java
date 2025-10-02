import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

class CurrencyConverter {
    public static double getExchangeRate(String baseCurrency, String targetCurrency) {
    try {
        String urlStr = "https://open.er-api.com/v6/latest/" + baseCurrency;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        JSONObject json = new JSONObject(response.toString());
        if (!"success".equals(json.optString("result"))) {
            System.out.println("API Error: " + json.toString(2));
            return -1;
        }
        JSONObject rates = json.getJSONObject("rates");
        return rates.getDouble(targetCurrency);

    } catch (Exception e) {
        e.printStackTrace();
        return -1;
    }
}
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter base currency (e.g. USD): ");
        String baseCurrency = scanner.nextLine().toUpperCase();
        System.out.print("Enter target currency (e.g. INR): ");
        String targetCurrency = scanner.nextLine().toUpperCase();
        System.out.print("Enter amount to convert: ");
        double amount = scanner.nextDouble();
        double rate = getExchangeRate(baseCurrency, targetCurrency);

        if (rate != -1) {
            double convertedAmount = amount * rate;
            System.out.println(amount + " " + baseCurrency + " = " + convertedAmount + " " + targetCurrency);
        } else {
            System.out.println("Failed to fetch exchange rate :( Please try again.");
        }

        scanner.close();
    }
}

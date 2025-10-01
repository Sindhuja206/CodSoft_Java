import java.util.HashMap;
import java.util.Scanner;

class CurrencyConverter {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        HashMap<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.0);
        rates.put("INR", 83.5);
        rates.put("EUR", 0.93);
        rates.put("JPY", 149.0);
        rates.put("GBP", 0.81);

        System.out.println("Supported currencies: USD, INR, EUR, JPY, GBP");

        System.out.print("Enter base currency: ");
        String base = s.next().toUpperCase();
        System.out.print("Enter target currency: ");
        String target = s.next().toUpperCase();

        if (!rates.containsKey(base) || !rates.containsKey(target)) {
            System.out.println("Error: Unsupported currency.");
            s.close();
            return;
        }
        System.out.print("Enter amount to convert: ");
        double amount = s.nextDouble();

        double amountInUSD = amount / rates.get(base);
        double convertedAmount = amountInUSD * rates.get(target);

        System.out.printf("%.2f %s = %.2f %s\n", amount, base, convertedAmount, target);

        s.close();
    }
}

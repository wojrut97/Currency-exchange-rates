package KursWalut.KursWalut;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CurrencyManager {

	public void call_me() throws Exception {
		String url = GenerateURL();
		if (url == null) {
			System.out.println("Niepoprawny format danych wejściowych!");
			return;
		}
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		int responseCode = con.getResponseCode();
		if (responseCode != 200) {
			System.out.println("Wystąpił błąd przy połączeniu z serwerem NBP!");
			return;
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String response = in.readLine();
		in.close();
		float[] mids = jsonParse(response.toString());
		System.out.println("Średnia wartość waluty: " + midValue(mids));
		System.out.println("Odchylenie standardowe: " + standardDeviation(mids));
	}

	public String GenerateURL() {
		Validator validator = new Validator();
		Scanner reader = new Scanner(System.in);
		System.out.println("Waluta: (np.: EUR)");
		String currency = reader.next();
		if (!validator.isCurrency(currency))
			return null;
		System.out.println("Data poczatkowa: (np.: 2017-11-20)");
		String start_date = reader.next();
		if (!validator.isDate(start_date))
			return null;
		System.out.println("Waluta: (np.: 2017-11-24)");
		String end_date = reader.next();
		if (!validator.isDate(end_date))
			return null;
		String URL = "http://api.nbp.pl/api/exchangerates/rates/a/" + currency + "/" + start_date + "/" + end_date
				+ "?format=json";
		return URL;
	}

	public float[] jsonParse(String jsonLine) {
		JsonElement jelement = new JsonParser().parse(jsonLine);
		JsonObject jobject = jelement.getAsJsonObject();
		JsonArray jarray = jobject.getAsJsonArray("rates");
		JsonObject cena;
		float mid = 0;
		float[] currency_array = new float[jarray.size()];
		for (int i = 0; i < jarray.size(); i++) {
			cena = jarray.get(i).getAsJsonObject();
			mid = cena.get("mid").getAsFloat();
			currency_array[i] = mid;
		}
		return currency_array;
	}

	public float midValue(float[] values) {
		float sum = 0;
		for (int i = 0; i < values.length; i++) {
			sum += values[i];
		}
		float mid_value = sum / values.length;
		return mid_value;
	}

	public float standardDeviation(float[] values) {
		float mid_value = midValue(values);
		float sum = mid_value * values.length;
		float standard_deviation = 0;
		for (int i = 0; i < values.length; i++) {
			standard_deviation += Math.pow((values[i] - mid_value), 2);
		}
		standard_deviation = standard_deviation / sum;
		return standard_deviation;
	}
}
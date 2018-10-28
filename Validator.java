package KursWalut.KursWalut;

import java.util.Arrays;
import java.util.List;

public class Validator {
	public boolean isDate(String data) {
		if (data.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})"))
			return true;
		else
			return false;
	}

	public boolean isCurrency(String data) {
		String[] allCurrencies = { "THB", "USD", "AUD", "HKD", "CAD", "NZD", "SGD", "EUR", "CHF", "GBP", "UAH", "JPY",
				"CZK", "DKK", "ISK", "NOK", "SEK", "HRK", "RON", "BGN", "TRY", "ILS", "CLP", "MXN", "PHP", "ZAR", "BRL",
				"MYR", "RUB", "IDR", "INR", "KRW", "CNY", "XDR" };
		List<String> list = Arrays.asList(allCurrencies);

		if (list.contains(data)) {
			return true;
		}
		return false;
	}
}

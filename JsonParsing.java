package KursWalut.KursWalut;

public class JsonParsing {

	public static void main(String[] args) {
		try {
			new CurrencyManager().call_me();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
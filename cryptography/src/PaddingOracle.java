import java.io.*;
import java.net.*;

public class PaddingOracle {
	private String cipherText;
	
	public PaddingOracle (String chiperText) {
		this.cipherText = chiperText;
	}
	
	private String ask (String request) {
		try {
			URL url = new URL("http://crypto-class.appspot.com/po?er=" + request);
			URLConnection con = url.openConnection();
			con.getContent();
		} catch (IOException e) {
			return e.getMessage().substring(36, 39);
		}
		return "OK";
	}
	
	public void guessOne (int n) {
		String request = this.cipherText;
		String respond;
		char l, r, l0, r0;
		for (int i = 0; i < 16; i ++) {
			l0 = GeneralRoutines.int2hex(i);
			for (int j = 0; j < 16; j ++) {
				if (j != 1) {
					r0 = GeneralRoutines.int2hex(j);
					request = this.cipherText;
					l = GeneralRoutines.xor(l0, request.charAt(request.length() - 32 - 2 * n - 2));
					r = GeneralRoutines.xor(GeneralRoutines.int2hex(1), GeneralRoutines.xor(r0, request.charAt(request.length() - 32 - 2 * n - 1)));
					request = request.substring(0, request.length() - 32 - 2 * n - 2) + l + r + request.substring(request.length() - 32 - 2 * n, request.length());
					respond = this.ask(request);
					if (!respond.equals("403")) {
						cipherText = cipherText.substring(0, cipherText.length() - 32 - 2 * n - 2) + GeneralRoutines.int2hex(i)
								+ GeneralRoutines.int2hex(j) + request.substring(request.length() - 32 - 2 * n, request.length());
						return;
					}
				}
			}
		}
	}
}

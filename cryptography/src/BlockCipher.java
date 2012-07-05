import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.*;
import javax.crypto.spec.*;

public class BlockCipher {
	public String decyptCBC(String key, String message) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		byte[] IV = GeneralRoutines.hexStringToAscii(message.substring(0, 32));
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
		SecretKeySpec secretKeySpec = new SecretKeySpec(GeneralRoutines.hexStringToAscii(key), "AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

		byte[] curr = IV;
		byte[] prev = curr;
		String s = "";
		for (int i = 32; i < message.length(); i += 32) {
			curr = GeneralRoutines.hexStringToAscii(message.substring(i, i + 32));
			s += new String(GeneralRoutines.xor(cipher.doFinal(curr), prev));
			prev = curr;
		}
		return s.substring(0, s.length() - s.charAt(s.length() - 1));
	}
	
	public String decyptCRT(String key, String message) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		byte[] IV = GeneralRoutines.hexStringToAscii(message.substring(0, 32));
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
		SecretKeySpec secretKeySpec = new SecretKeySpec(GeneralRoutines.hexStringToAscii(key), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

		byte[] curr = IV;
		byte[] prev = curr;
		String s = "";
		for (int i = 32; i < message.length(); i += 32) {
			if (i + 32 > message.length()) {
				curr = GeneralRoutines.hexStringToAscii(message.substring(i));				
			} else {
				curr = GeneralRoutines.hexStringToAscii(message.substring(i, i + 32));
			}
			s += new String(GeneralRoutines.xor(curr, cipher.doFinal(prev)));
			prev = GeneralRoutines.nextByteArray(prev);
		}
		return s;
	}
}

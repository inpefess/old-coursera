public class GeneralRoutines {
	public static byte[] hexStringToAscii (String hex) {
		byte[] ascii = new byte[hex.length() / 2];
		for (int i = 0; i < hex.length(); i += 2) {
			int l = hex.charAt(i);
			int r = hex.charAt(i + 1);
			if (l - '0' > 9) l = l - 'a' + 10;
			else l -= '0';
			if (r - '0' > 9) r = r - 'a' + 10;
			else r -= '0';			
			ascii[i / 2] = (byte) (l * 16 + r);
		}
		return ascii;
	}

	public static byte[] xor (byte[] x, byte[] y) {
		byte[] line = new byte[x.length];
		for (int i = 0; i < x.length; i ++) {
			line[i] = (byte) (x[i] ^ y[i]);
		}
		return line;
	}

	public static String asciiToHexString(byte[] ascii) {
		String hex = "";
		for (int i = 0; i < ascii.length; i ++) {
			int t = ascii[i];
			if (t < 0) t += 256;
			int l = t / 16;
			int r = t % 16;
			if (l < 10) hex += String.valueOf((char) ('0' + l));
			else hex += String.valueOf((char)('a' + l - 10));
			if (r < 10) hex += String.valueOf((char)('0' + r));
			else hex += String.valueOf((char)('a' + r - 10));
		}
		return hex;
	}
	
	public static Boolean isLetter(byte x) {
		return x >= 'A' && x <= 'Z' || x >= 'a' && x <= 'z';
	}
	
	public static byte[] nextByteArray(byte[] x) {
		byte[] y = x;
		for (int i = x.length - 1; i >=0; i ++) {
			y[i] ++;
			if (y[i] != 0) {
				i = -2;
			}
		}
		return y;
	}
}

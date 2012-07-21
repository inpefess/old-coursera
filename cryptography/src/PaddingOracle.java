import java.io.*;
import java.net.*;

public class PaddingOracle {
	private String cipherText;
	private byte[] base;
	private byte[] pad;
	private byte[] msg;
	private int curByte;
	private int blockSize = 16;
	
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
	
	private void setPad () {
		for (int i = blockSize - 1; i >= curByte; i --) {
			pad[i] = (byte) (blockSize - curByte);
		}
	}
	
	private Boolean successfulGuess () {
		if (msg[curByte] == 1) return true;
		byte[] request = GeneralRoutines.xor(msg, GeneralRoutines.xor(base, pad));
		String respond = this.ask(GeneralRoutines.asciiToHexString(request));
		return !(respond.equals("403") || respond.equals("OK"));
	}
	
	private void setMsg () {
		msg[curByte]--;
	}
	
	private void addPad () {
		for (int i = curByte - 1; i >= blockSize - msg[curByte]; i --) {
			msg[i] = msg[curByte];
		}
		curByte = blockSize - msg[curByte] - 1;
	}

	private void initMsgAndPad () {
		msg = new byte[base.length];
		pad = new byte[base.length];
		for (int i = 0; i < base.length; i ++) {
			msg[i] = pad[i] = 0;
		}
	}
	
	private String guessBlock (Boolean firstBlock) {
		Boolean success;
		curByte = blockSize - 1;
		initMsgAndPad();
		while(curByte >= 0) {
			setPad();
			msg[curByte] = 127;
			success = false;
			while (!success) {
				setMsg();
				success = successfulGuess();
			}
			if (firstBlock && curByte == blockSize - 1) {
				addPad();
			} else {
				curByte --;
			}
		}
		String s = "";
		for (int i = 0; i < blockSize; i ++) {
			s += (char) msg[i];
		}
		return s;
	}
	
	public String guess () {
		int numberOfBlokcs = cipherText.length() / blockSize / 2;
		base = GeneralRoutines.hexStringToAscii(cipherText.substring((numberOfBlokcs - 2) * 2 * blockSize, numberOfBlokcs * 2 * blockSize));
		String s = guessBlock (true);
		String next = "";
		for (int i = numberOfBlokcs - 3; i >= 0; i --) {
			base = GeneralRoutines.hexStringToAscii(cipherText.substring(i * 2 * blockSize, (i + 2) * 2 * blockSize));
			next = guessBlock(false);
			s = next + s;
		}
		return s;
	}
}
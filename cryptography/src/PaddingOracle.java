import java.io.*;
import java.net.*;

public class PaddingOracle {
	private String cipherText;
	private byte[] base;
	private byte[] pad;
	private byte[] msg;
	private int curBlock;
	private int curByte;
	
	public PaddingOracle (String chiperText) {
		this.cipherText = chiperText;
		this.base = GeneralRoutines.hexStringToAscii(cipherText);
		this.pad = new byte[base.length];
		this.msg = new byte[base.length];
		for (int i = 0; i < base.length; i ++) {
			pad[i] = msg[i] = 0;
		}
		curBlock = base.length / 16 - 2;
		curByte = 15;
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
		for (int i = 15; i >= curByte; i --) {
			pad[16 * curBlock + i] = (byte) (16 - curByte);
		}
	}
	
	private Boolean successfulGuess () {
		byte[] request = GeneralRoutines.xor(msg, GeneralRoutines.xor(base, pad));
		String respond = this.ask(GeneralRoutines.asciiToHexString(request));
		return !(respond.equals("403") || respond.equals("OK"));
	}
	
	private void setMsg () {
		msg[16 * curBlock + curByte]++;
	}
	
	public void guess () {
		Boolean success;
		while (curBlock >= 0) {
			while(curByte >= 0) {
				setPad();
				success = false;
				while (!success) {
					setMsg();
					success = successfulGuess();
				}
				curByte --;
			}
			curByte = 15;
			curBlock --;
		}
	}
}

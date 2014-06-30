import java.io.*;
import java.security.*;
import java.util.*;

public class VideoHash {
	private ArrayList<byte[]> movie;
	private int lastBlockSize;
	
	public VideoHash (String filename) throws IOException {
		FileInputStream fis = new FileInputStream(filename);
 
		movie = new ArrayList<byte[]>(20000);
		byte[] dataBytes = new byte[1024];
		int blockSize = 0;
		while ((blockSize = fis.read(dataBytes)) != -1) {
			lastBlockSize = blockSize;
			movie.add(dataBytes.clone());
		};
		
		fis.close();
	}
	
	public String getHash () throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] lastBlock = new byte[lastBlockSize];
		for (int i = 0; i < lastBlockSize; i ++) {
			lastBlock[i] = movie.get(movie.size() - 1)[i];
		}
		byte[] h = md.digest(lastBlock);
		for (int i = movie.size() - 2; i >= 0; i --) {
			byte[] dataBlock = new byte[movie.get(i).length + 32];
			for (int j = 0; j < movie.get(i).length; j ++) {
				dataBlock[j] = movie.get(i)[j];
			}
			for (int j = 0; j < h.length; j ++) {
				dataBlock[movie.get(i).length + j] = h[j];
			}
			h = md.digest(dataBlock);
		}
		return GeneralRoutines.asciiToHexString(h);
	}
}
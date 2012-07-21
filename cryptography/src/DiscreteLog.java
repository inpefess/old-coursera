import java.math.*;
import java.util.*;

public class DiscreteLog {
	private BigInteger h;
	private BigInteger g;
	private BigInteger p;
	private BigInteger B;
	private int b;
	private Hashtable<BigInteger, Integer> hashTable;
	
	public DiscreteLog (String h, String g, String p) {
		this.h = new BigInteger(h);
		this.g = new BigInteger(g);
		this.p = new BigInteger(p);
		B = new BigInteger ("1024");
		B = B.multiply(B);
		b = 1024 * 1024;
		hashTable = new Hashtable<BigInteger, Integer>(2 * b);
	}

	private void buildHashTable () {
		BigInteger gPow = h;
		BigInteger gInv = g.modInverse(p);
		for (int i = 0; i < b; i ++) {
			hashTable.put(gPow, i);
			gPow = gPow.multiply(gInv).mod(p);			
		}
	}
	
	public String getIt () {
		buildHashTable();
		int x0;
		Integer x1;
		BigInteger gB = g.modPow(B, p);
		BigInteger gBPow = new BigInteger("1");
		for (x0 = 0; x0 < b; x0 ++) {
			x1 = hashTable.get(gBPow);
			if (x1 != null) {
				return (new BigInteger(String.valueOf(x0))).multiply(B).add(new BigInteger(String.valueOf(x1))).mod(p.subtract(new BigInteger("1"))).toString();
			}
			gBPow = gBPow.multiply(gB).mod(p);
		}
		return "-1";
	}
}
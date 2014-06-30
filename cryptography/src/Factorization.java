import java.math.*;

public class Factorization {
	private BigInteger N;

	public Factorization (String N) {
		this.N = new BigInteger(N);
	}

	private BigInteger mySqrt (BigInteger x) {
		BigInteger a = BigInteger.ONE;
		BigInteger b = x;
		for(;b.compareTo(a.add(BigInteger.ONE)) > 0;) {
			BigInteger mid = a.add(b).shiftRight(1);
			if(mid.multiply(mid).compareTo(x) > 0) b = mid;
			else a = mid;
		}
		for (;a.multiply(a).compareTo(x) == -1;) {
			a = a.add(BigInteger.ONE);
		}
		return a;
	}

	public String case1 () {
		BigInteger A = mySqrt(N);
		BigInteger x = mySqrt(A.multiply(A).subtract(N));
		return A.subtract(x).toString();
	}

	public String case2 () {
		BigInteger A = mySqrt(N);
		BigInteger x = mySqrt(A.multiply(A).subtract(N));
		int percent = 0;
		for(int i = 0;!x.multiply(x).equals(A.multiply(A).subtract(N));i++) {
			if (i / 1024 / 100 > percent) {
				System.out.println(percent);
			}
			A = A.add(BigInteger.ONE);
			x = mySqrt(A.multiply(A).subtract(N));
		}
		return A.subtract(x).toString();
	}

	public String case3 () {
		BigInteger A = mySqrt(N.multiply(new BigInteger("24")));
		BigInteger S = A.multiply(A).subtract(N.multiply(new BigInteger("24")));
		BigInteger x = mySqrt(S);
		BigInteger p = A.subtract(x).divide(new BigInteger("6"));
		return p.toString();
	}

	public String case4 () {
		BigInteger A = mySqrt(N);
		BigInteger x = mySqrt(A.multiply(A).subtract(N));
		BigInteger p = A.subtract(x).subtract(BigInteger.ONE);
		BigInteger q = A.add(x).subtract(BigInteger.ONE);
		BigInteger phi = p.multiply(q);
		BigInteger e = new BigInteger("65537");
		BigInteger d = e.modInverse(phi);
		BigInteger c = new BigInteger("22096451867410381776306561134883418017410069787892831071731839143676135600120538004282329650473509424343946219751512256465839967942889460764542040581564748988013734864120452325229320176487916666402997509188729971690526083222067771600019329260870009579993724077458967773697817571267229951148662959627934791540");
		BigInteger m = c.modPow(d, N);
		String mes = m.toString(16);
		mes = mes.substring(mes.indexOf("00") + 2, mes.length());
		return new String(GeneralRoutines.hexStringToAscii(mes));
	}
}
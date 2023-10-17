package br.cin.ufpe.hybridschnorr;


import java.math.BigInteger;
import br.cin.ufpe.hybridschnorr.IPrivateKey;
import br.cin.ufpe.hybridschnorr.IPublicKey;
import java.security.SecureRandom;
import java.lang.Math;

public class SchnorrHelper {
	
		public static BigInteger generateRi(BigInteger g, BigInteger q, BigInteger p) {
			SecureRandom sr = new SecureRandom();
	        	BigInteger ri = new BigInteger(q.bitLength(), sr);
        		
        		//BigInteger Ri = g.modPow(ri, p);

			return ri;
		}

		public static void generatePrivatePublicKeys(int blq, IPublicKey pubKey, IPrivateKey privKey,BigInteger g, BigInteger p, BigInteger q) {
	        System.out.println("generating:");

	        BigInteger one = new BigInteger("1");
	        BigInteger two = new BigInteger("2");
	        BigInteger qp, w, a, y;
	        int certainty = 100;

	        SecureRandom sr = new SecureRandom();

		if (g==null && p==null && q==null) {
			q = new BigInteger(blq, certainty, sr);

			qp = BigInteger.ONE;

			do {
			    p = q.multiply(qp).multiply(two).add(one);
			    if (p.isProbablePrime(certainty)) break;
			    qp = qp.add(BigInteger.ONE);
			} while (true);

			while (true) {
			    a = (two.add(new BigInteger(blq, 100, sr))).mod(p);
			    BigInteger ga = (p.subtract(BigInteger.ONE)).divide(q);
			    g = a.modPow(ga, p);
			    if (g.compareTo(BigInteger.ONE) != 0)
			        break;
			}

			w = new BigInteger(blq, sr);
			System.out.println("Private key w = " + w);			
			y = g.modPow(w, p);
			System.out.println("Public key y = " + y);
		} else {
			int random = 1 + (int)(Math.random()*((p.intValue() - 1)+1));
 			w = new BigInteger("" + random);
			System.out.println("Private key w = " + w);			
			y = g.modPow(w, p);
			System.out.println("Public key y = " + y);
		}

		pubKey.getPubKey().set(0, g);
		pubKey.getPubKey().set(1, p);
		pubKey.getPubKey().set(2, q);
		pubKey.getPubKey().set(3, y);

	        System.out.println("public key:");
	        System.out.println("g = " + pubKey.getPubKey().get(0));
	        System.out.println("p = " + pubKey.getPubKey().get(1));
	        System.out.println("q = " + pubKey.getPubKey().get(2));
	        System.out.println("y = " + pubKey.getPubKey().get(3));
		

		privKey.getPrivKey().set(0, w);
		privKey.getPrivKey().set(1, p);
		privKey.getPrivKey().set(2, q);
		privKey.getPrivKey().set(3, g);
	        System.out.println("private key:");
	        System.out.println("w = "+ privKey.getPrivKey().get(0));
	    }

}

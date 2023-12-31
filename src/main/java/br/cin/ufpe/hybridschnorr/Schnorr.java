package br.cin.ufpe.hybridschnorr;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Schnorr {
	
	public static void checkSign(String path, String pathPublicKey, String pathSign) throws IOException, NoSuchAlgorithmException {

        System.out.println("cheking sign");
        Key PublicKey = new Key(pathPublicKey);
        Key Sign = new Key(pathSign);
        //BigInteger q = PublicKey.get(0);
        BigInteger p = PublicKey.get(1);
        BigInteger g = PublicKey.get(2);
        BigInteger y = PublicKey.get(3);
        BigInteger s1 = Sign.get(0);
        BigInteger s2 = Sign.get(1);

        BigInteger X1 = g.modPow(s2, p);
        BigInteger X2 = (y.modPow(s1, p)).mod(p);
        BigInteger X = X1.multiply(X2).mod(p);
        
        System.out.println("X = " + X);

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(Files.readAllBytes(Paths.get(path)));;
        md5.update(X.toString().getBytes());

        byte[] digest55 = md5.digest();
        BigInteger HH = new BigInteger(1, digest55);
        if (s1.equals(HH))
            System.out.println("Schnorr signature is valid");
        else
            System.out.println("Schnorr signature is not valid");
    }

	
	 public static void makeSign(String path, String pathPublicKey, String pathPrivateKey, String pathSign) throws IOException, NoSuchAlgorithmException {
	        Key PublicKey = new Key(pathPublicKey);
	        Key PrivateKey = new Key(pathPrivateKey);
	        BigInteger q = PublicKey.get(0);
	        BigInteger p = PublicKey.get(1);
	        BigInteger g = PublicKey.get(2);
	        BigInteger y = PublicKey.get(3);
	        BigInteger w = PrivateKey.get(0);

	        SecureRandom sr = new SecureRandom();
	        BigInteger r, x, W, s2, s1;
	        r = new BigInteger(q.bitLength(), sr);
	        x = g.modPow(r, p);
	        
	        System.out.println("x = " + x);

	        MessageDigest md5 = MessageDigest.getInstance("MD5");
	        md5.update(Files.readAllBytes(Paths.get(path)));;
	        md5.update(x.toString().getBytes());
	        byte[] digest = md5.digest();
	        s1 = new BigInteger(1, digest);
	        s2 = (r.subtract(w.multiply(s1))).mod(q);
	        System.out.println("s2 = " + s2);
	        System.out.println("s2' = " + s2.subtract(w.multiply(s1)));
	        //System.out.println("r' = " + s2.subtract(w.multiply(s1)).mod(q));
	        System.out.println("r = " +  g.modPow(s2, p).multiply(g.modPow(w.multiply(s1), p)).mod(p));
	        System.out.println("r' = " + g.modPow(r, p));
	        
	        
	        //System.out.println("r = " + r.mod(q));
	        //System.out.println("r' = " + s2.subtract(w.multiply(s1).mod(q)).mod(q));
	        

	        Key Sign = new Key(new BigInteger[]{s1, s2});
	        Sign.writeToFile(pathSign);
	        System.out.println("Success!");
	    }
	 
	 public static void generate(int blq, String pathPublicKey, String pathPrivateKey) throws FileNotFoundException {
	        System.out.println("generating:");

	        BigInteger one = new BigInteger("1");
	        BigInteger two = new BigInteger("2");
	        BigInteger q, qp, p, a, g, w, y;
	        int certainty = 100;
	        SecureRandom sr = new SecureRandom();
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
	        y = g.modPow(w, p);

	        Key PublicKey = new Key(new BigInteger[]{q, p, g, y});
	        PublicKey.writeToFile(pathPublicKey);
	        System.out.println("public key:");
	        System.out.println("q = " + q);
	        System.out.println("p = " + p);
	        System.out.println("g = " + g);
	        System.out.println("y = " + y);

	        Key PrivateKey = new Key(new BigInteger[]{w});
	        PrivateKey.writeToFile(pathPrivateKey);
	        System.out.println("private key:");
	        System.out.println("w = "+ w);
	    }
	 
	 static String pathSign = "Sign.txt";
	    static String pathPublicKey = "PublicKey.txt";
	    static String pathPrivateKey = "PrivateKey.txt";
	    static String pathFile = "C:\\desenvolvimento\\OUTROS\\doutorado\\teste.txt";
	    
	 public static void main(String[] args) throws NoSuchAlgorithmException, IOException  {
		
		 Schnorr.generate(5, pathPublicKey, pathPrivateKey);
		 
		 Schnorr.makeSign(pathFile, pathPublicKey, pathPrivateKey, pathSign);

		 Schnorr.checkSign(pathFile, pathPublicKey, pathSign);
	 }

}

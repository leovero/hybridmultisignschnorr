package hybridschnorr;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class MultiSignSchnorr {

	public static Key aggregate(List<Key> publicKeys) throws NoSuchAlgorithmException {
		

		MessageDigest md5 = MessageDigest.getInstance("MD5");

		byte[] digest = null;
		for(Key key: publicKeys) {
	        md5.update(key.get(0).toByteArray());
		}

        digest = md5.digest();
        
        BigInteger s1 = new BigInteger(1, digest);
        
        Key agKey  = new Key(new BigInteger[]{s1});
	
        return agKey;
	}
	

	public static Key multiSign(List<Key> publicKeys, byte[] message, List<BigInteger> risOthers,  List<BigInteger> xisOthers, Key xi, Key Xi, BigInteger q, BigInteger p, BigInteger g, BigInteger ri) throws NoSuchAlgorithmException {		
		
	    //SecureRandom sr = new SecureRandom();
        //BigInteger r, x, W, s, sADD, e;
        BigInteger R, si, siOther, e;
        //r = new BigInteger(q.bitLength(), sr);
        //x = g.modPow(r, p);
        //BigInteger R = g.modPow(r, p);
        //System.out.println("x = " + x);
        
        BigInteger RiTotal = BigInteger.valueOf(1);
        //int i = 0;
		System.out.println("ri = " + ri);
        for(BigInteger riOther : risOthers) {
//        	if (i>=0) {
        		R = g.modPow(riOther, p);
        		RiTotal = RiTotal.multiply(R);
        		System.out.println("RiOther = " + R);
  //      	}
    //    	i++;
        }
		R = g.modPow(ri, p);
        RiTotal = RiTotal.multiply(R);

		System.out.println("RiTotal = " + RiTotal);

        
        BigInteger SiTotal = BigInteger.valueOf(1);
        int i = 0;
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(message);
        md5.update(R.toByteArray());
        byte[] digest = md5.digest();
        
        e = new BigInteger(1, digest);
		System.out.println("e = " + e);

        si = (ri.add(xi.get(0).multiply(e))).mod(q);
        System.out.println("ri = " + ri);
        System.out.println("si = " + si);
        System.out.println("xi = " + xi.get(0));
        SiTotal = SiTotal.multiply(g.modPow(si, p));
        for(BigInteger xiOther:xisOthers) {
	        	md5 = MessageDigest.getInstance("MD5");
	            md5.update(message);
	            md5.update(R.toByteArray());
	            digest = md5.digest();
	            e = new BigInteger(1, digest);
	            siOther = (risOthers.get(i).add(xiOther.multiply(e))).mod(q);
	            System.out.println("riOther = " + risOthers.get(i));
	            System.out.println("xiOther = " + xiOther);
	            System.out.println("siOther = " + siOther);

	            SiTotal = SiTotal.multiply(g.modPow(siOther, p));
	            
	        i++;
        }
        md5 = MessageDigest.getInstance("MD5");
        md5.update("2".getBytes());
        digest = md5.digest();
        e = new BigInteger(1, digest);
        
        SiTotal = SiTotal.multiply(g.modPow(e, p));
        SiTotal = SiTotal.mod(p);
        System.out.println("SiTotal = " + SiTotal);

        //sADD = (r.add(xi.get(0).multiply(e))).mod(q);        
        //System.out.println("e = " + e);
        
       	//BigInteger HXADD = g.modPow(e.multiply(xi.get(0)),p).mod(p);
        //BigInteger HXADD =  ((publicKeys.get(0).get(0)).pow(e.intValue()).mod(p)).mod(p);
        //BigInteger HXADD = (publicKeys.get(0).get(0).modPow(e, p)).mod(p);
        //BigInteger V = R.multiply(HXADD).mod(p);
        //System.out.println("V = " + V);
        
        //BigInteger sG =  g.modPow(sADD, p);
		//System.out.println("sG = " + sG);

        //Key Sign = new Key(new BigInteger[]{e, s});
        Key Sign = new Key(new BigInteger[]{RiTotal, SiTotal});
		
		return Sign;
	}
	
	
	public static boolean multVerify(List<Key> publicKeysOthers, Key pubKey, Key sign, byte[] message, BigInteger p, BigInteger g, BigInteger ri) throws NoSuchAlgorithmException {
		
			     
		//BigInteger X1 = g.modPow(sign.get(1), p);
        //BigInteger X2 = (publicKeys.get(0).get(0).modPow(sign.get(0), p)).mod(p);
        //BigInteger X = X1.multiply(X2).mod(p);
        
        //System.out.println("X = " + X);

		BigInteger R = g.modPow(ri, p);
		
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(message);
        //md5.update(sign.get(0).toByteArray());
        md5.update(R.toByteArray());
        byte[] digest = md5.digest();
        //byte[] digest = null;
        BigInteger e = new BigInteger(1, digest);

        System.out.println("e2 = " + e);
        
        BigInteger pubKeyTotal = BigInteger.valueOf(1);
        for(Key pubKeyOther: publicKeysOthers) {
        	//pubKeyTotal.multiply(pubKeyOther.get(0));
        	pubKeyTotal = pubKeyTotal.multiply(pubKeyOther.get(0).modPow(e, p));
        	System.out.println("PUBi = " + pubKeyTotal);
        }
        pubKeyTotal = pubKeyTotal.multiply(pubKey.get(0).modPow(e, p));
        //pubKeyTotal = pubKeyTotal.mod(p);
        /*BigInteger MULTI = BigInteger.valueOf(1);
        BigInteger e = null;
        for(BigInteger riOther : risOthers) {        	
        	for(Key pubKeyOther: publicKeysOthers) {
        	       md5.update("oi".getBytes());
        	        md5.update(riOther.toByteArray());
        	        digest = md5.digest();
        	        e = new BigInteger(1, digest);
        	        MULTI = MULTI.multiply(pubKeyOther.get(0).modPow(e, p).mod(p));
 
//        	}
        }*/
        
       
        /*BigInteger V3 = R.multiply(R).multiply(pubKey.get(0).modPow(e, p)).multiply(pubKey.get(0).modPow(e, p)).mod(p);
        //BigInteger V3 = R.multiply(pubKey.get(0).modPow(e, p)).mod(p);
        System.out.println("R = " + R.multiply(R));
        System.out.println("PUB = " + pubKey.get(0).modPow(e, p));
        System.out.println("PUBTOTAL = " + pubKey.get(0).modPow(e, p).multiply(pubKey.get(0).modPow(e, p)));
        System.out.println("V3 = " + V3);
        */

        md5 = MessageDigest.getInstance("MD5");
        md5.update("2".getBytes());
        digest = md5.digest();
        e = new BigInteger(1, digest);

        BigInteger V2 = sign.get(0).multiply(pubKeyTotal).multiply(g.modPow(e, p)).mod(p);
        System.out.println("pubTotal = " + pubKeyTotal);
        System.out.println("Verification value = " + V2);

        
        //BigInteger sG =  g.modPow(sign.get(1), p);
		//System.out.println("sG2 = " + sG);


        byte[] digest55 = md5.digest();
        BigInteger HH = new BigInteger(1, digest55);
        
        

		return false;
	}
	
	public static void main(String[] args) {
		
		
		List<Key> pubKeys = new ArrayList<Key>();
		
        Key pubKey1  = new Key(new BigInteger[]{new BigInteger("105")});
		
       pubKeys.add(pubKey1);
        
        Key privKey1  = new Key(new BigInteger[]{new BigInteger("18")});
              
        List<BigInteger> ris = new ArrayList<BigInteger>();
        //ris.add(new BigInteger("15"));
        List<BigInteger> xis = new ArrayList<BigInteger>();
        
        // g(r) mod q
        BigInteger g = new BigInteger("243");
        BigInteger p = new BigInteger("311");
        BigInteger q = new BigInteger("31");
        
        //BigInteger ri = g.modPow(new BigInteger("15"), p);
        //BigInteger ri = new BigInteger("15");

        SecureRandom sr = new SecureRandom();
        BigInteger ri = new BigInteger(q.bitLength(), sr);
        //ri = new BigInteger(q.bitLength(), sr);
        BigInteger Ri = g.modPow(ri, p);
            
        sr = new SecureRandom();
        BigInteger rii = new BigInteger(q.bitLength(), sr);
        rii = new BigInteger(q.bitLength(), sr);
        BigInteger Rii = g.modPow(rii, p);
        
        ris.add(ri);
        
        xis.add(privKey1.get(0));
        
        try {
            Key sign = MultiSignSchnorr.multiSign(pubKeys, "oi".getBytes(), ris, xis, privKey1, pubKey1, q, p, g, ri);
        	MultiSignSchnorr.multVerify(pubKeys, pubKey1, sign, "oi".getBytes(), p, g, ri);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
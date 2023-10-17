package br.cin.ufpe.hybridschnorr;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import pqcsignature.Sign;
import java.lang.StringBuffer;
import java.lang.Math;
import br.cin.ufpe.util.Log;
import java.io.IOException;

public class MultiSignSchnorr {

	private static byte[] signaturePQC;	

	private Log log;

	private boolean hybrid;

	private boolean printSize;

	private boolean printPerformance;

	public MultiSignSchnorr(boolean hybrid, boolean printSize, boolean printPerformance) {

		this.hybrid = hybrid;
		this.printSize = printSize;
		this.printPerformance = printPerformance;

		if (hybrid){
			log = new Log("/root/doutorado/cryptocurrency/dilithium/java","ms_hybrid");
		} else {
			log = new Log("/root/doutorado/cryptocurrency/dilithium/java","ms_simple");
		}
		try {
			log.initializeLogFile();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public Key multiSign(byte[] message, List<IPrivateKey> xisOthers, IPrivateKey xi, byte[] privateKeyPQC) throws NoSuchAlgorithmException{		
		
	Sign signPQC = new Sign();

	long beginningTime = System.currentTimeMillis();
	System.out.println("#### BEGINNING SIGNATURE PROCESSSS:" + beginningTime);

	try {
		log.printLine("SIGINING " + (hybrid?"HYBRID":"COMUM"));
	} catch(IOException ioe) {
		ioe.printStackTrace();
	}

	BigInteger R, si, siOther, siteste, e, hashPQC, g, p, q;
	g = xi.getPrivKey().get(3);
	p = xi.getPrivKey().get(1);
	q = xi.getPrivKey().get(2);
	
        BigInteger RiTotal = BigInteger.valueOf(1);
        for(IPrivateKey riOther : xisOthers) {
        	R = g.modPow(riOther.getRi().getRi(), p);
        	RiTotal = RiTotal.multiply(R);
        	System.out.println("RiOther = " + R);
        }
	R = g.modPow(xi.getRi().getRi(), p);
	System.out.println("Ri = " + R);
        RiTotal = RiTotal.multiply(R);
	System.out.println("RiTotal = " + RiTotal);
        
	BigInteger SiTotal = BigInteger.valueOf(0);
        int i = 0;

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(message);
	md5.update(RiTotal.toByteArray());
        byte[] digest = md5.digest();
        
        e = new BigInteger(1, digest);
	System.out.println("e = " + e);

        si = (xi.getRi().getRi().add(xi.getPrivKey().get(0).multiply(e))).mod(q);
	
        System.out.println("ri = " + xi.getRi().getRi());
        System.out.println("si = " + si);
        System.out.println("xi = " + xi.getPrivKey().get(0));
        SiTotal = SiTotal.add(si);
	for(IPrivateKey xiOther:xisOthers) {

	        md5 = MessageDigest.getInstance("MD5");
	        md5.update(message);
	        md5.update(RiTotal.toByteArray());
	        digest = md5.digest();
	        e = new BigInteger(1, digest);
	        siOther = (xiOther.getRi().getRi().add(xiOther.getPrivKey().get(0).multiply(e))).mod(q);
	        System.out.println("riOther = " + xiOther.getRi().getRi());
	        System.out.println("xiOther = " + xiOther.getPrivKey().get(0));
	        System.out.println("siOther = " + siOther);

	        SiTotal = SiTotal.add(siOther);
	            
	        i++;
        }

	BigInteger t = null;
	if (hybrid) {

        md5 = MessageDigest.getInstance("MD5");
	signaturePQC = new byte[4595 + message.length];

	try {	
	   if (hybrid && printSize) {
		System.out.println("Private Key PQC: " + privateKeyPQC.length + " bytes");
	   	log.printLineSize("SK_PQC", privateKeyPQC.length, "");
	   }
	} catch(IOException ioe) {
		ioe.printStackTrace();
	}

	if (hybrid) {
		signPQC.crypto_sign_sign(signaturePQC, 4595, message, message.length, privateKeyPQC, 4880);

		t = new BigInteger(1, encodeHexString(signaturePQC).getBytes());

        	md5.update(signaturePQC);
        	digest = md5.digest();
        	e = new BigInteger(1, digest);
		System.out.println("ePQC = " + e);
	}

        SiTotal = SiTotal.add(e);

	}
	System.out.println("Message: " + message.length  + " bytes");
	System.out.println("SiTotal = " + SiTotal);

	System.out.println("#### SIZE RI_TOTAL SIGNATURE MULTISIGNN:" + RiTotal.toByteArray().length + " BYTES");
	System.out.println("#### SIZE SI_TOTAL SIGNATURE MULTISIGN:" + SiTotal.toByteArray().length + " BYTES");
	//System.out.println("#### SIZE BIG INTEGER:" + new BigInteger("1234456789").toByteArray().length+ " BYTES");


	if (printSize) {
		try {
			log.printLine("TYPE;SIZE");
			log.printLineSize("MESSAGE", message.length, "");
			log.printLineSize("SIGNATURE_MS_P1", RiTotal.toByteArray().length, "");
			log.printLineSize("SIGNATURE_MS_P2", SiTotal.toByteArray().length, "");
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} 
		if (hybrid) {
			System.out.println("#### SIZE HASH_PQC_SIGNATURE MULTISIGN HYBRID:" + e.toByteArray().length + " BYTES");
System.out.println("#### SIZE HASH_PQC_SIGNATURE MULTISIGN HYBRID:" + signaturePQC + " BYTES");
		System.out.println("#### SIZE PQC_SIGNATURE MULTISIGN HYBRID:" + t.toByteArray().length + " BYTES");
		try {
				log.printLineSize("SIGNATURE_MS_P3", e.toByteArray().length, "");
				log.printLineSize("SIGNATURE_MS_P4", signaturePQC.length, "");
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} 
		}
        }

        Key Sign = new Key(new BigInteger[]{RiTotal, SiTotal, e, t});

	long endingTime = System.currentTimeMillis();
		
	System.out.println("#### ENDING SIGNATURE PROCESS:" + endingTime);
	System.out.println("#### TIME SIGNATURE PROCESS:" + (endingTime-beginningTime));

	try {
		 if (printPerformance) {
	//		log.printLine("TIME");
			log.printLinePerformance((endingTime-beginningTime),"");
		}
	
	} catch(IOException ioe) {
		ioe.printStackTrace();
	}

	return Sign;
}

public String encodeHexString(byte[] byteArray) {
    StringBuffer hexStringBuffer = new StringBuffer();
    for (int i = 0; i < byteArray.length; i++) {
        hexStringBuffer.append(byteToHex(byteArray[i]));
    }
    return hexStringBuffer.toString();
}


public byte[] decodeHexString(String hexString) {
    if (hexString.length() % 2 == 1) {
        throw new IllegalArgumentException(
          "Invalid hexadecimal String supplied.");
    }
    
    byte[] bytes = new byte[hexString.length() / 2];
    for (int i = 0; i < hexString.length(); i += 2) {
        bytes[i / 2] = hexToByte(hexString.substring(i, i + 2));
    }
    return bytes;
}

public byte hexToByte(String hexString) { 
	int firstDigit = toDigit(hexString.charAt(0)); int secondDigit = toDigit(hexString.charAt(1)); return (byte) ((firstDigit << 4) + secondDigit); }

public String byteToHex(byte num) { 
	char[] hexDigits = new char[2]; 
	hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16); 
	hexDigits[1] = 	Character.forDigit((num & 0xF), 16); return new String(hexDigits); } 

private int toDigit(char hexChar) { 
	int digit = Character.digit(hexChar, 16); 
	if(digit == -1) { 
		throw new IllegalArgumentException( "Invalid Hexadecimal Character: "+ hexChar); } 
	return digit; 
} 

	
	
	public boolean multiVerify(List<IPublicKey> publicKeysOthers, IPublicKey pubKey, Key signature, byte[] message, byte[] publicKeyPQC) throws NoSuchAlgorithmException {
		
	BigInteger g, p, q;

	long beginningTime = System.currentTimeMillis();

	g = pubKey.getPubKey().get(0);
	p = pubKey.getPubKey().get(1);
	q = pubKey.getPubKey().get(2);


	Sign signPQC = new Sign();
		
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(message);
	md5.update(signature.get(0).toByteArray());
	System.out.println("RiTotal2 = " + signature.get(0));        
	byte[] digest = md5.digest();
        BigInteger e = new BigInteger(1, digest);

        System.out.println("e2 = " + e);
        
        BigInteger pubKeyTotal = BigInteger.valueOf(1);
	for(IPublicKey pubKeyOther: publicKeysOthers) {
        	pubKeyTotal = pubKeyTotal.multiply(pubKeyOther.getPubKey().get(3));
        	System.out.println("PUBi = " + pubKeyTotal);
        }


        pubKeyTotal = pubKeyTotal.multiply(pubKey.getPubKey().get(3));

	System.out.println("#### SIZE TOTAL PUBLIC KEY:" + pubKeyTotal.toByteArray().length + " BYTES");

	try {
		log.printLine("VERIFYING " + (hybrid?"HYBRID":"COMUM"));
		if (printSize) {				
			log.printLineSize("PKTOTAL_MS", pubKeyTotal.toByteArray().length, "");
			log.printLineSize("PK_PQC", publicKeyPQC.length, "");
		}
	} catch(IOException ioe) {
		ioe.printStackTrace();
	}

  
         BigInteger V = null;
	if (hybrid) {
		md5 = MessageDigest.getInstance("MD5");
		md5.update(signaturePQC);
        	digest = md5.digest();
        	BigInteger ePQC = new BigInteger(1, digest);
		System.out.println("ePQC = " + ePQC);
        	V =  (signature.get(0).multiply(pubKeyTotal.modPow(e,p))).multiply(g.modPow(ePQC, p)).mod(p);

	} else {
  		V =  (signature.get(0).multiply(pubKeyTotal.modPow(e,p))).mod(p); 
	}

        System.out.println("pubTotal = " + pubKeyTotal);
        System.out.println("Verification value = " + V);
	System.out.println("SiTotal G = " + g.modPow(signature.get(1), p));
        
	int verificationPQC = 0;

	if (hybrid) {
		byte[] sig =  decodeHexString(new String(signature.get(3).toByteArray()));
	System.out.println("SIGNATURE PQC SIZE = " + sig.length);		
	signPQC.crypto_sign_verify(sig, 4595 + message.length, message, message.length, publicKeyPQC, 2592);
		System.out.println("verification pqc = " + verificationPQC);
	}
	
	boolean validSignature = V.equals(g.modPow(signature.get(1), p)) && (verificationPQC==0); 

	long endingTime = System.currentTimeMillis();

	try {
		if (printPerformance) {
		//	log.printLine("TIME");
			log.printLinePerformance((endingTime-beginningTime),"");	
		}
	} catch(IOException ioe) {
		ioe.printStackTrace();
	}

	
	return validSignature;

}

	public Log getLog() {
	  return this.log;
	}
	
	public static void main(String[] args) {
		
		
	//List<Key> pubKeys = new ArrayList<Key>();
	List<IPublicKey> pubKeysOthers = new ArrayList<IPublicKey>();

        //BigInteger g = new BigInteger("243");
	BigInteger g = new BigInteger("17");
        //BigInteger p = new BigInteger("311");
        BigInteger p = new BigInteger("59");
        //BigInteger q = new BigInteger("31");
	BigInteger q = new BigInteger("29");

      	List<Ri> risOthers = new ArrayList<Ri>();
        List<IPrivateKey> privKeysOthers = new ArrayList<IPrivateKey>();
        
        SecureRandom sr = new SecureRandom();
        BigInteger ri = new BigInteger(q.bitLength(), sr);
	Ri ri1 = new Ri(ri);
            
	ri = new BigInteger(q.bitLength(), sr);
	Ri ri2 = new Ri(ri);
        risOthers.add(ri2);

        //Key pubKey1  = new Key(new BigInteger[]{new BigInteger("105")});
	//int random1 = 1 + (int)(Math.random()*((311 - 1)+1));
	int random1 = 1 + (int)(Math.random()*((59 - 1)+1));
 System.out.println("random1 = " + random1);
	//BigInteger privKeyBI1 = new BigInteger("" + random1);
	BigInteger privKeyBI1 = new BigInteger("" + 6);
	//Key privKey1  = new Key(new BigInteger[]{privKeyBI1});
	//Key pubKey1  = new Key(new BigInteger[]{g.modPow(privKeyBI1, p)});
	IPrivateKey privKey1  = new PrivateKeyMultiSchnorr(privKeyBI1, p, q, g, ri1.getRi());
	//PublicKey pubKey1  = new PublicKey(g.modPow(privKeyBI1, p), g, p, q);
  	IPublicKey pubKey1  = new PublicKeyMultiSchnorr(new BigInteger("" + 20), g, p, q);

	//int random2 = 1 + (int)(Math.random()*((311 - 1)+1));
	int random2 = 1 + (int)(Math.random()*((59 - 1)+1));
 System.out.println("random2 = " + random2);
	//BigInteger privKeyBI2 = new BigInteger("" + random2);
	BigInteger privKeyBI2 = new BigInteger("" + 1);
	//Key privKey2  = new Key(new BigInteger[]{privKeyBI2});
	//Key pubKey2  = new Key(new BigInteger[]{g.modPow(privKeyBI2, p)});
	IPrivateKey privKey2  = new PrivateKeyMultiSchnorr(privKeyBI2,  p, q, g, ri2.getRi());
	//PublicKey pubKey2  = new PublicKey(g.modPow(privKeyBI2, p), g, p, q);
	IPublicKey pubKey2  = new PublicKeyMultiSchnorr(new BigInteger("" + 17), g, p, q);


	pubKeysOthers.add(pubKey2);
//	pubKeysOthers.add(pubKey3);

	privKeysOthers.add(privKey2);
//	privKeysOthers.add(privKey3);

        byte[] sk = null;
	byte[] pk = null;

        try {
	    Sign signPQC = new Sign();
	    pk = new byte[2592];
	    sk = new byte[4880];
	    signPQC.crypto_sign_keypair(pk,sk);
	    MultiSignSchnorr msHybrid = new MultiSignSchnorr(true, true, false);
            Key sign = msHybrid.multiSign("oii".getBytes(), privKeysOthers, privKey1, sk);
        	msHybrid.multiVerify(pubKeysOthers, pubKey1, sign, "oii".getBytes(), pk);


		//sign = msHybrid.multiSign("oi".getBytes(), privKeysOthers, privKey1, sk);
        	//msHybrid.multiVerify(pubKeysOthers, pubKey1, sign, "oi".getBytes(), pk);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

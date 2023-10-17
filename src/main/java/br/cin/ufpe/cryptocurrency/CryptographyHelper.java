package br.cin.ufpe.cryptocurrency;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import br.cin.ufpe.hybridschnorr.IPublicKey;
import br.cin.ufpe.hybridschnorr.IPrivateKey;
import br.cin.ufpe.hybridschnorr.KeyPairSchnorr;
import br.cin.ufpe.hybridschnorr.PublicKeyMultiSchnorr;
import br.cin.ufpe.hybridschnorr.PrivateKeyMultiSchnorr;
import br.cin.ufpe.hybridschnorr.Ri;
import br.cin.ufpe.hybridschnorr.SchnorrHelper;
import br.cin.ufpe.hybridschnorr.MultiSignData;
import br.cin.ufpe.hybridschnorr.MultiSignSchnorr;
import br.cin.ufpe.hybridschnorr.Key;
import java.security.SecureRandom;

import java.util.List;
import java.util.ArrayList;
import java.math.BigInteger;
import signature.Sign;
import java.io.IOException;

public class CryptographyHelper {

	// SHA-256 hash (256 bits = 64 hexadecimal characters)
	public static String generateHash(String data) {
		try {
			
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(data.getBytes("UTF-8"));

			//we want to end up with hexadecimal values not bytes
			StringBuilder hexadecimalString = new StringBuilder();
		
			for (int i = 0; i < hash.length; i++) {
				String hexadecimal = Integer.toHexString(hash[i] & 0xff);
				if (hexadecimal.length() == 1) hexadecimalString.append('0');
				hexadecimalString.append(hexadecimal);
			}
			
			return hexadecimalString.toString();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static MultiSignData multiSignSchnorrCrypto() {

			List<KeyPairSchnorr> listKPs = new ArrayList<KeyPairSchnorr>();
			List<Ri> listRis = new ArrayList<Ri>();

			IPublicKey pubKey1 = new PublicKeyMultiSchnorr();
			IPrivateKey privKey1 = new PrivateKeyMultiSchnorr();
			//System.out.println("Private key " + privKey1.getPrivKey().get(1)); 
			SchnorrHelper.generatePrivatePublicKeys(5,pubKey1,privKey1, null, null, null);		
			IPublicKey pubKey2 = new PublicKeyMultiSchnorr();
			IPrivateKey privKey2 = new PrivateKeyMultiSchnorr();
			IPublicKey pubKey3 = new PublicKeyMultiSchnorr();
			IPrivateKey privKey3 = new PrivateKeyMultiSchnorr();
			IPublicKey pubKey4 = new PublicKeyMultiSchnorr();
			IPrivateKey privKey4 = new PrivateKeyMultiSchnorr();
			IPublicKey pubKey5 = new PublicKeyMultiSchnorr();
			IPrivateKey privKey5 = new PrivateKeyMultiSchnorr();

			IPublicKey pubKey6 = new PublicKeyMultiSchnorr();
			IPrivateKey privKey6 = new PrivateKeyMultiSchnorr();
			IPublicKey pubKey7 = new PublicKeyMultiSchnorr();
			IPrivateKey privKey7 = new PrivateKeyMultiSchnorr();
			IPublicKey pubKey8 = new PublicKeyMultiSchnorr();
			IPrivateKey privKey8 = new PrivateKeyMultiSchnorr();
			IPublicKey pubKey9 = new PublicKeyMultiSchnorr();
			IPrivateKey privKey9 = new PrivateKeyMultiSchnorr();
			IPublicKey pubKey10 = new PublicKeyMultiSchnorr();
			IPrivateKey privKey10 = new PrivateKeyMultiSchnorr();

			KeyPairSchnorr kp1 = new KeyPairSchnorr(pubKey1, privKey1);
			KeyPairSchnorr kp2 = new KeyPairSchnorr(pubKey2, privKey2);
			KeyPairSchnorr kp3 = new KeyPairSchnorr(pubKey3, privKey3);
			KeyPairSchnorr kp4 = new KeyPairSchnorr(pubKey4, privKey4);
			KeyPairSchnorr kp5 = new KeyPairSchnorr(pubKey5, privKey5);
			KeyPairSchnorr kp6 = new KeyPairSchnorr(pubKey6, privKey6);
			KeyPairSchnorr kp7 = new KeyPairSchnorr(pubKey7, privKey7);
			KeyPairSchnorr kp8 = new KeyPairSchnorr(pubKey8, privKey8);
			KeyPairSchnorr kp9 = new KeyPairSchnorr(pubKey9, privKey9);
			KeyPairSchnorr kp10 = new KeyPairSchnorr(pubKey10, privKey10);

			listKPs.add(kp1);
			listKPs.add(kp2);
			listKPs.add(kp3);
			listKPs.add(kp4);
			listKPs.add(kp5);
			listKPs.add(kp6);
			listKPs.add(kp7);
			listKPs.add(kp8);
			listKPs.add(kp9);
			listKPs.add(kp10);

			SchnorrHelper.generatePrivatePublicKeys(5,pubKey2,privKey2, 				pubKey1.getPubKey().get(0), pubKey1.getPubKey().get(1), 			pubKey1.getPubKey().get(2));		
											  				SchnorrHelper.generatePrivatePublicKeys(5,pubKey3,privKey3, 				pubKey1.getPubKey().get(0), pubKey1.getPubKey().get(1), 			pubKey1.getPubKey().get(2));

			SchnorrHelper.generatePrivatePublicKeys(5,pubKey4,privKey4, 				pubKey1.getPubKey().get(0), pubKey1.getPubKey().get(1), 			pubKey1.getPubKey().get(2));

			SchnorrHelper.generatePrivatePublicKeys(5,pubKey5,privKey5, 				pubKey1.getPubKey().get(0), pubKey1.getPubKey().get(1), 			pubKey1.getPubKey().get(2));
													                             
			SchnorrHelper.generatePrivatePublicKeys(5,pubKey6,privKey6, 				pubKey1.getPubKey().get(0), pubKey1.getPubKey().get(1), 			pubKey1.getPubKey().get(2));

			SchnorrHelper.generatePrivatePublicKeys(5,pubKey7,privKey7, 				pubKey1.getPubKey().get(0), pubKey1.getPubKey().get(1), 			pubKey1.getPubKey().get(2));

			SchnorrHelper.generatePrivatePublicKeys(5,pubKey8,privKey8, 				pubKey1.getPubKey().get(0), pubKey1.getPubKey().get(1), 			pubKey1.getPubKey().get(2));
			
			SchnorrHelper.generatePrivatePublicKeys(5,pubKey9,privKey9, 				pubKey1.getPubKey().get(0), pubKey1.getPubKey().get(1), 			pubKey1.getPubKey().get(2));
			
			SchnorrHelper.generatePrivatePublicKeys(5,pubKey10,privKey10, 				pubKey1.getPubKey().get(0), pubKey1.getPubKey().get(1), 			pubKey1.getPubKey().get(2));


		BigInteger ri1BI = SchnorrHelper.generateRi(pubKey1.getPubKey().get(0),pubKey1.getPubKey().get(1),pubKey1.getPubKey().get(2));
		System.out.println("ri1 " + ri1BI); 
		Ri ri1 = new Ri(ri1BI);
		privKey1.setRi(ri1);

		BigInteger ri2BI = SchnorrHelper.generateRi(pubKey1.getPubKey().get(0),pubKey1.getPubKey().get(1),pubKey1.getPubKey().get(2));
		System.out.println("ri2 " + ri2BI);
		Ri ri2 = new Ri(ri2BI);
		privKey2.setRi(ri2);

		BigInteger ri3BI = SchnorrHelper.generateRi(pubKey1.getPubKey().get(0),pubKey1.getPubKey().get(1),pubKey1.getPubKey().get(2));
		System.out.println("ri3 " + ri3BI);
		Ri ri3 = new Ri(ri3BI);
		privKey3.setRi(ri3);

		BigInteger ri4BI = SchnorrHelper.generateRi(pubKey1.getPubKey().get(0),pubKey1.getPubKey().get(1),pubKey1.getPubKey().get(2));
		System.out.println("ri4 " + ri4BI);
		Ri ri4 = new Ri(ri4BI);
		privKey4.setRi(ri4);

		BigInteger ri5BI = SchnorrHelper.generateRi(pubKey1.getPubKey().get(0),pubKey1.getPubKey().get(1),pubKey1.getPubKey().get(2));
		System.out.println("ri5 " + ri5BI);
		Ri ri5 = new Ri(ri5BI);
		privKey5.setRi(ri5);

		BigInteger ri6BI = SchnorrHelper.generateRi(pubKey1.getPubKey().get(0),pubKey1.getPubKey().get(1),pubKey1.getPubKey().get(2));
		System.out.println("ri6 " + ri6BI);
		Ri ri6 = new Ri(ri6BI);
		privKey6.setRi(ri6);

		BigInteger ri7BI = SchnorrHelper.generateRi(pubKey1.getPubKey().get(0),pubKey1.getPubKey().get(1),pubKey1.getPubKey().get(2));
		System.out.println("ri7 " + ri7BI);
		Ri ri7 = new Ri(ri7BI);
		privKey7.setRi(ri7);

		BigInteger ri8BI = SchnorrHelper.generateRi(pubKey1.getPubKey().get(0),pubKey1.getPubKey().get(1),pubKey1.getPubKey().get(2));
		System.out.println("ri8 " + ri8BI);
		Ri ri8 = new Ri(ri8BI);
		privKey8.setRi(ri8);

		BigInteger ri9BI = SchnorrHelper.generateRi(pubKey1.getPubKey().get(0),pubKey1.getPubKey().get(1),pubKey1.getPubKey().get(2));
		System.out.println("ri9 " + ri9BI);
		Ri ri9 = new Ri(ri9BI);
		privKey9.setRi(ri9);

		BigInteger ri10BI = SchnorrHelper.generateRi(pubKey1.getPubKey().get(0),pubKey1.getPubKey().get(1),pubKey1.getPubKey().get(2));
		System.out.println("ri10 " + ri10BI);
		Ri ri10 = new Ri(ri10BI);
		privKey10.setRi(ri10);

		listRis.add(ri1);
		listRis.add(ri2);
		listRis.add(ri3);
		listRis.add(ri4);
		listRis.add(ri5);
		listRis.add(ri6);
		listRis.add(ri7);
		listRis.add(ri8);
		listRis.add(ri9);
		listRis.add(ri10);


	Sign signPQC = new Sign();
	    byte[] pk = new byte[2592];
	    byte[] sk = new byte[4880];
	    signPQC.crypto_sign_keypair(pk,sk);			

	   MultiSignData msData = new MultiSignData(listKPs, sk, pk);

			return msData;
	}
	
	// ECC to sign the given transaction (message)
	// elliptic curve digital signature algorithm (ECDSA)
	/*public static byte[] sign(PrivateKey privateKey, String input) {
		Signature signature;
		byte[] output = new byte[0];
		
		try {
			// we use bouncy castle for ECC
			signature = Signature.getInstance("ECDSA", "BC");
			signature.initSign(privateKey);
			signature.update(input.getBytes());
			output = signature.sign();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		return output;
	}*/


	// Hybrid Schnorr to sign the given transaction (message)
	public static Key sign(byte[] message, IPrivateKey privKeyMSSchnorr, List<IPrivateKey> privKeysOthers, byte[] privateKeyPQC, boolean usePQC) {


		MultiSignSchnorr msSingSchnorr = new MultiSignSchnorr(usePQC, false, true);
		Key k = null;
		try {
		k = msSingSchnorr.multiSign(message, privKeysOthers, privKeyMSSchnorr, privateKeyPQC);
		} catch(NoSuchAlgorithmException ne) {
			ne.printStackTrace();
		}
		return k;	
	}
	
	// checks whether the given transaction belongs to the sender based on the signature
	public static boolean verify(List<IPublicKey> publicKeysOthers, IPublicKey pubKey, Key signature, byte[] message, byte[] publicKeyPQC, boolean usePQC) {

		MultiSignSchnorr msSingSchnorr = new MultiSignSchnorr(usePQC, false, true);

		try {
		msSingSchnorr.multiVerify(publicKeysOthers, pubKey, signature, message, publicKeyPQC);
		} catch(NoSuchAlgorithmException ne) {
			ne.printStackTrace();
		}

			return false;			
	}

	public static void main(String[] args) {

	List<IPublicKey> pubKeysOthers = new ArrayList<IPublicKey>();      
        List<IPrivateKey> privKeysOthers = new ArrayList<IPrivateKey>();
        
	MultiSignData ms = CryptographyHelper.multiSignSchnorrCrypto();	
	int i = 0;	

	i = 0;
	List<KeyPairSchnorr> keyspairs = ms.getKeysPairsMSSchnorr();
	KeyPairSchnorr kp1 = null;	
	for(KeyPairSchnorr kp:keyspairs) {
			if (i==0) {							
				kp1 = kp;
			} else {
				privKeysOthers.add(kp.getPrivKeySchnorr());
				pubKeysOthers.add(kp.getPubKeySchnorr());
			}
			i++;
	}
	IPrivateKey privKey1 = kp1.getPrivKeySchnorr();
	IPublicKey pubKey1 = kp1.getPubKeySchnorr();

	BigInteger g = pubKey1.getPubKey().get(0);
        BigInteger p = pubKey1.getPubKey().get(1);
	BigInteger q = pubKey1.getPubKey().get(2);

        byte[] sk = null;
	byte[] pk = null;

        try {
	    Sign signPQC = new Sign();
	    pk = new byte[2592];
	    sk = new byte[4880];
	    signPQC.crypto_sign_keypair(pk,sk);
	    MultiSignSchnorr msHybrid = new MultiSignSchnorr(true, true, false);

            Key sign = null;

	     for(int r = 1; r<=1; r++) { 

		msHybrid.getLog().printLine("ROUND " + r);

	     for(int s = 1; s<=1; s++) { 
		sign = msHybrid.multiSign("oi".getBytes(), privKeysOthers, privKey1, sk);
        	msHybrid.multiVerify(pubKeysOthers, pubKey1, sign, "oi".getBytes(), pk);

	     }	
}
	
		msHybrid.getLog().closeLog();
		
	} catch (NoSuchAlgorithmException e) {
		e.printStackTrace();
	}  catch (IOException e) {
		e.printStackTrace();
	}

	
	}
}








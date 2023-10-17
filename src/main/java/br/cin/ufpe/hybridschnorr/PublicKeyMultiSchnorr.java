package br.cin.ufpe.hybridschnorr;

import java.math.BigInteger;
import java.util.List;
import br.cin.ufpe.hybridschnorr.Key;

public class PublicKeyMultiSchnorr implements IPublicKey {

	private Key pubKey;
	//private List<Key> pubKeysOthers;

	public PublicKeyMultiSchnorr() {
		BigInteger[] keys = new BigInteger[4];
		pubKey = new Key(keys); 	
	}

	public PublicKeyMultiSchnorr(BigInteger y, BigInteger g, BigInteger p, BigInteger q) {
		BigInteger[] keys = new BigInteger[4];
		keys[0] = g;
		keys[1] = p;
		keys[2] = q;
		keys[3] = y;
		pubKey = new Key(keys); 
	}


	/*public PublicKeyMultiSchnorr(BigInteger y, BigInteger g, BigInteger p, BigInteger q, List<BigInteger> ys) {
		BigInteger[] keys = new BigInteger[4];
		keys[0] = g;
		keys[1] = p;
		keys[2] = q;
		keys[3] = y;
		pubKey = new Key(keys); 
		for(BigInteger yi:ys) {
			keys = new BigInteger[1];
			keys[0] = yi;
			pubKeysOthers.add(new Key(keys));
		}
	}*/


	public void setPubKey(Key pubKey) {
		this.pubKey = pubKey;
	}

	public Key getPubKey() {
		return pubKey;
	}

	/*public void setPubKeysOthers(List<Key> pubKeysOthers) {
		this.pubKeysOthers = pubKeysOthers;
	}

	public List<Key> getPubKeysOthers() {
		return pubKeysOthers;
	}*/

	public String getAlgorithm() {
		return "SCHNORR MULTI SIGNATURE";
	}

	public byte[] getEncoded() {
		return null;
	}

	public String getFormat() {
		return null;
	}
}

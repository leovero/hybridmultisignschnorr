package br.cin.ufpe.hybridschnorr;

import java.math.BigInteger;
import br.cin.ufpe.hybridschnorr.Key;
import java.util.List;

public class PrivateKeyMultiSchnorr implements IPrivateKey {

	private Key privKey;
	private Ri ri;
	//private List<Key> privateKeysOthers;
	//private List<Key> risOthers;

	public PrivateKeyMultiSchnorr(){
		BigInteger[] keys = new BigInteger[4];
		privKey = new Key(keys); 
	}
	
	/*public PrivateKeySchnorr(BigInteger x) {
		BigInteger[] keys = new BigInteger[1];
		keys[0] = x;
		privKey = new Key(keys); 
	}*/

	/*public PrivateKeyMultiSchnorr(BigInteger x, BigInteger p, BigInteger q, BigInteger g, BigInteger ri, List<BigInteger> ris, List<BigInteger> xs) {
		BigInteger[] keys = new BigInteger[4];
		keys[0] = x;
		keys[1] = p;
		keys[2] = q;
		keys[3] = g;
		privKey = new Key(keys);
		keys = new BigInteger[1];
		keys[0] = ri;
		this.ri = new Key(keys); 

		for(BigInteger xi:xs) {
			keys = new BigInteger[1];
			keys[0] = xi;
			privateKeysOthers.add(new Key(keys));
		}
		for(BigInteger rii:ris) {
			keys = new BigInteger[1];
			keys[0] = rii;
			risOthers.add(new Key(keys));
		}

	}*/

	public PrivateKeyMultiSchnorr(BigInteger x, BigInteger p, BigInteger q, BigInteger g, BigInteger ri) {
		BigInteger[] keys = new BigInteger[4];
		keys[0] = x;
		keys[1] = p;
		keys[2] = q;
		keys[3] = g;
		privKey = new Key(keys);
		this.ri = new Ri(ri); 
	}


	public void setPrivKey(Key privKey) {
		this.privKey = privKey;
	}

	public Key getPrivKey() {
		return privKey;
	}

	public void setRi(Ri ri) {
		this.ri = ri;
	}

	public Ri getRi() {
		return ri;
	}

	/*public void setPrivateKeysOthers(List<Key> privateKeysOthers) {
		this.privateKeysOthers = privateKeysOthers;
	}

	public List<Key> getPrivateKeysOthers() {
		return privateKeysOthers;
	}*/

	/*public void setRisOthers(List<Key> risOthers) {
		this.risOthers = risOthers;
	}

	public List<Key> getRisOthers() {
		return risOthers;
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

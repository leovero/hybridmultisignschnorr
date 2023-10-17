package br.cin.ufpe.hybridschnorr;

import java.math.BigInteger;
import java.util.List;
import br.cin.ufpe.hybridschnorr.IPublicKey;

public class MultiSignPublicKey {

	
	private List<IPublicKey> publicKeys;

	public void MultiSignPublicKey(List<IPublicKey> publicKeys) {
		this.publicKeys = publicKeys;		
	}
	
	public BigInteger getCompositePublicKey(BigInteger e, BigInteger p) {
		 BigInteger pubKeyTotal = BigInteger.valueOf(1);
        	for(IPublicKey pubKeyOther: publicKeys) {
        		pubKeyTotal = pubKeyTotal.multiply(pubKeyOther.getPubKey().get(0));
        	}
        	System.out.println("PublicKeyTotal = " + pubKeyTotal.modPow(e, p));

		return pubKeyTotal;
	}
}

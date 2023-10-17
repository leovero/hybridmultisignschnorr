package br.cin.ufpe.hybridschnorr;

import java.math.BigInteger;
import br.cin.ufpe.hybridschnorr.IPublicKey;
import br.cin.ufpe.hybridschnorr.IPrivateKey;
import java.util.List;

public class KeyPairSchnorr {

	private IPrivateKey privKeySchnorr;
	private IPublicKey  pubKeySchnorr;
	
	public KeyPairSchnorr(IPublicKey pubKeySchnorr, IPrivateKey privKeySchnorr) {
		this.pubKeySchnorr= pubKeySchnorr;
		this.privKeySchnorr = privKeySchnorr;
	}

	public void setPubKeySchnorr(IPublicKey pubKeySchnorr) {
		this.pubKeySchnorr = pubKeySchnorr;
	}

	public IPublicKey getPubKeySchnorr() {
		return pubKeySchnorr;
	}

	public void setPrivKeySchnorr(IPrivateKey privKeySchnorr) {
		this.privKeySchnorr = privKeySchnorr;
	}

	public IPrivateKey getPrivKeySchnorr() {
		return privKeySchnorr;
	}


}

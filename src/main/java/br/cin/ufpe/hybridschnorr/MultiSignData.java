package br.cin.ufpe.hybridschnorr;

import java.math.BigInteger;
import br.cin.ufpe.hybridschnorr.IPublicKey;
import br.cin.ufpe.hybridschnorr.IPrivateKey;
import java.util.List;

public class MultiSignData {

	private List<KeyPairSchnorr> keysPairsMSSchnorr;
//	private List<Ri> risMSSchnorr;
	private	byte[] privKeyPQC;
	private byte[] pubKeyPQC; 

/*	public MultiSignData(List<KeyPairSchnorr> keysPairsMSSchnorr, List<Ri> risMSSchnorr, byte[] privKeyPQC, byte[] pubKeyPQC) {
		this.keysPairsMSSchnorr = keysPairsMSSchnorr;
		this.risMSSchnorr = risMSSchnorr;
		this.pubKeyPQC = pubKeyPQC;
		this.privKeyPQC = privKeyPQC;
	}
*/
public MultiSignData(List<KeyPairSchnorr> keysPairsMSSchnorr, byte[] privKeyPQC, byte[] pubKeyPQC) {
		this.keysPairsMSSchnorr = keysPairsMSSchnorr;
		this.pubKeyPQC = pubKeyPQC;
		this.privKeyPQC = privKeyPQC;
	}

	public void setKeysPairsMSSchnorr(List<KeyPairSchnorr> keysPairsMSSchnorr) {
		this.keysPairsMSSchnorr = keysPairsMSSchnorr;
	}

	public List<KeyPairSchnorr> getKeysPairsMSSchnorr() {
		return this.keysPairsMSSchnorr;
	}

	/*public void setRisMSSchnorr(List<Ri> risMSSchnorr) {
		this.risMSSchnorr = risMSSchnorr;
	}

	public List<Ri> getRisMSSchnorr() {
		return risMSSchnorr;
	}*/

	public void setPubKeyPQC(byte[] pubKeyPQC) {
		this.pubKeyPQC = pubKeyPQC;
	}

	public byte[] getPubKeyPQC() {
		return pubKeyPQC;
	}

	public void setPrivbKeyPQC(byte[] privKeyPQC) {
		this.privKeyPQC = privKeyPQC;
	}

	public byte[] getPrivKeyPQC() {
		return privKeyPQC;
	}	

}

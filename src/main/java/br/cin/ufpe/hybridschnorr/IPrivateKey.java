package br.cin.ufpe.hybridschnorr;

import java.math.BigInteger;
import java.util.List;
import java.security.PrivateKey;
import br.cin.ufpe.hybridschnorr.Key;

public interface IPrivateKey extends PrivateKey {
	
	public void setPrivKey(Key privKey);

	public Key getPrivKey();

	public void setRi(Ri ri);

	public Ri getRi();

	//public void setPrivateKeysOthers(List<Key> privateKeysOthers);

	//public List<Key> getPrivateKeysOthers();

	//public void setRisOthers(List<Key> risOthers);

	//public List<Key> getRisOthers();

	
}

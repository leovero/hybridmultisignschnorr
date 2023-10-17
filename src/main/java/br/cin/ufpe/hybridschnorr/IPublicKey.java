package br.cin.ufpe.hybridschnorr;

import java.math.BigInteger;
import java.util.List;
import java.security.PublicKey;
import br.cin.ufpe.hybridschnorr.Key;

public interface IPublicKey extends PublicKey {

	public void setPubKey(Key pubKey);

	public Key getPubKey();

	//public void setPubKeysOthers(List<Key> pubKeysOthers);

	//public List<Key> getPubKeysOthers();

}

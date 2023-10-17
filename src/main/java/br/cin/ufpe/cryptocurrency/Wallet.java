package br.cin.ufpe.cryptocurrency;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import br.cin.ufpe.hybridschnorr.IPrivateKey;
import br.cin.ufpe.hybridschnorr.IPublicKey;
import br.cin.ufpe.hybridschnorr.KeyPairSchnorr;
import br.cin.ufpe.hybridschnorr.MultiSignData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.cin.ufpe.blockchain.Blockchain;

public class Wallet {

	// users of the network
	// used for signature
	private PrivateKey privateKey;
	private List<PrivateKey> privateKeysOthers;
	private byte[] privateKeyPQC;
	private byte[] publicKeyPQC;	
	// verification
	// address: RIPMD public key (160 bits)
	private PublicKey publicKey;
	private List<PublicKey> publicKeysOthers;
	
	public Wallet() {
		//KeyPair keyPair = CryptographyHelper.ellipticCurveCrypto();
		MultiSignData msData = CryptographyHelper.multiSignSchnorrCrypto();
		List<KeyPairSchnorr> keyPairs = msData.getKeysPairsMSSchnorr();		
		this.privateKey = keyPairs.get(0).getPrivKeySchnorr();
		this.publicKey = keyPairs.get(0).getPubKeySchnorr();
		privateKeysOthers = new ArrayList<PrivateKey>();
		publicKeysOthers = new ArrayList<PublicKey>();
		int i = 0;
		for(KeyPairSchnorr kp:keyPairs) {
			if (i>0) {			
				privateKeysOthers.add(kp.getPrivKeySchnorr());
				publicKeysOthers.add(kp.getPubKeySchnorr());
			}
			i++;
		}
		publicKeyPQC = msData.getPubKeyPQC();
		privateKeyPQC = msData.getPrivKeyPQC();

	}
	
	// WE ARE ABLE TO TRANSFER MONEY !!!
	// miners of the blockchain will put this transaction into the blockchain
	public Transaction transferMoney(PublicKey receiver, double amount) {
		
		if(calculateBalance() < amount) {
			System.out.println("Invalid transactin because of not enough money...");
			return null;
		}
		
		//we store the inputs for the transaction in this array
		List<TransactionInput> inputs = new ArrayList<TransactionInput>();
		
		// let's find our unspent transactions (the blockchain stores all the UTXOs)
		for (Map.Entry<String, TransactionOutput> item: Blockchain.UTXOs.entrySet()) {
			TransactionOutput UTXO = item.getValue();
			
			if(UTXO.isMine(this.publicKey))
				inputs.add(new TransactionInput(UTXO.getId()));
		}
		
		//let's create the new transaction
		Transaction newTransaction = new Transaction(publicKey, receiver , amount, inputs);
		//the sender signs the transaction
	newTransaction.generateSignature(privateKey,  privateKeysOthers, privateKeyPQC);
		//newTransaction.generateSignature(privateKey);
		
		return newTransaction;
	}
	
	// there is no balance associated with the users
	// UTXOs and consider all the transactions in the past
	public double calculateBalance() {
		
		double balance = 0;	
		
		for (Map.Entry<String, TransactionOutput> item: Blockchain.UTXOs.entrySet()) {
			TransactionOutput transactionOutput = item.getValue();
			
			if(transactionOutput.isMine(publicKey))
				balance += transactionOutput.getAmount() ;
		}
		
		return balance;
	}
	
	public PublicKey getPublicKey() {
		return publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public List<PrivateKey> getPrivateKeysOthers() {
		return privateKeysOthers;
	}

	
	public void setPrivateKeysOthers(List<PrivateKey> privateKeysOthers) {
		this.privateKeysOthers = privateKeysOthers;
	}

	public List<PublicKey> getPublicKeysOthers() {
		return publicKeysOthers;
	}

	
	public void setPublicKeysOthers(List<PublicKey> publicKeysOthers) {
		this.privateKeysOthers = privateKeysOthers;
	}


	public byte[] getPrivateKeyPQC() {
		return privateKeyPQC;
	}

	
	public void setPrivateKeyPQC(byte[] privateKeyPQC) {
		this.privateKeyPQC = privateKeyPQC;
	}
	
}







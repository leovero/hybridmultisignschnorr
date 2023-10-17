package br.cin.ufpe.cryptocurrency;

import java.security.PublicKey;
import java.security.PrivateKey;
import br.cin.ufpe.hybridschnorr.IPublicKey;
import br.cin.ufpe.hybridschnorr.IPrivateKey;
import br.cin.ufpe.hybridschnorr.Key;
import java.util.ArrayList;
import java.util.List;

import br.cin.ufpe.blockchain.Blockchain;

public class Transaction {

	// id of the transaction is a hash
	private String transactionId;
	// we use PublicKeys to reference the sender or receiver
	private PublicKey sender; 
	private PublicKey receiver;
	private List<PublicKey> sendersOthers;
	private Key signatureMS;
	private byte[] publicKeyPQC;
	//amount of coins the transaction sends to the receiver from the sender
	private double amount;
	// make sure the transaction is signed to prevent anyone else from spending the coins
	private byte[] signature;
	
	// every transaction has inputs and outputs
	public List<TransactionInput> inputs;
	public List<TransactionOutput> outputs;
	
	public Transaction(PublicKey sender, PublicKey receiver, double amount,  
			List<TransactionInput> inputs) {
		this.inputs = new ArrayList<TransactionInput>();
		this.outputs = new ArrayList<TransactionOutput>();
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
		this.inputs = inputs;
		calulateHash();
	}
	
	public boolean verifyTransaction() {
		
		if(!verifySignature()) {
			System.out.println("Invalid transaction because of invalid signature...");
			return false;
		}
		
		// let's gather unspent transactions (we have to consider the inputs)
		for(TransactionInput transactionInput : inputs)
			transactionInput.setUTXO(Blockchain.UTXOs.get(transactionInput.getTransactionOutputId()));
		
		//transactions have 2 part: send an amount to the receiver + send the (balance-amount)
		// back to the sender
		//send value to recipient
		outputs.add(new TransactionOutput( this.receiver, amount, transactionId));
		//send the left over 'change' back to sender		
		outputs.add(new TransactionOutput( this.sender, getInputsSum() - amount, transactionId)); 
			
		// WE HAVE TO UPDATE THE UTXOs
		//the outputs will be inputs for other transactions (so put them in the blockchain's UTXOs)
		for(TransactionOutput transactionOutput : outputs)
			Blockchain.UTXOs.put(transactionOutput.getId() , transactionOutput);
			
		// remove transaction inputs from blockchain's UTXOs list because they've been spent
		for(TransactionInput transactionInput : inputs) 
			if(transactionInput.getUTXO() != null)   
				Blockchain.UTXOs.remove(transactionInput.getUTXO().getId());
		
		return true;
	}
	
	// this is how we calculate that how much money the sender has
	// we have to consider transactions in the past
	private double getInputsSum() {
		double sum = 0;
		
		for(TransactionInput transactionInput : inputs)
			if(transactionInput.getUTXO() != null) 
				sum += transactionInput.getUTXO().getAmount();
		
		return sum;
	}
	
	public void generateSignature(PrivateKey privateKey, List<PrivateKey> privateKeysOthers,byte[] privateKeyPQC) {
		String data = sender.toString() + receiver.toString() + Double.toString(amount);
		if (privateKeysOthers!=null){
			List<IPrivateKey> listPKs = new ArrayList<IPrivateKey>();
			for(PrivateKey pk:privateKeysOthers) {
				listPKs.add((IPrivateKey)pk);
			}			
			signatureMS = CryptographyHelper.sign("oi".getBytes(), (IPrivateKey)privateKey, listPKs, privateKeyPQC);
		} else{
			//signature = CryptographyHelper.sign(privateKey, data);		
		}		
		
	}
	
	public boolean verifySignature() {
		String data = sender.toString() + receiver.toString() + Double.toString(amount);
		boolean verify = false;		
		if (sendersOthers!=null) {
			List<IPublicKey> listPKs = new ArrayList<IPublicKey>();
			for(PublicKey pk:sendersOthers) {
				listPKs.add((IPublicKey)pk);
			}
			verify = CryptographyHelper.verify(listPKs, (IPublicKey)sender, signatureMS, data.getBytes(), publicKeyPQC);
		}
 			//CryptographyHelper.verify(sender, data, signature);
		
		return verify;
	}

	private void calulateHash() {
		String hashData = sender.toString()+receiver.toString()+Double.toString(amount);
		this.transactionId = CryptographyHelper.generateHash(hashData);
	}
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public PublicKey getSender() {
		return sender;
	}

	public void setSender(PublicKey sender) {
		this.sender = sender;
	}

	public PublicKey getReceiver() {
		return receiver;
	}

	public void setReceiver(PublicKey receiver) {
		this.receiver = receiver;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	public List<TransactionInput> getInputs() {
		return inputs;
	}

	public void setInputs(List<TransactionInput> inputs) {
		this.inputs = inputs;
	}

	public List<TransactionOutput> getOutputs() {
		return outputs;
	}

	public void setOutputs(List<TransactionOutput> outputs) {
		this.outputs = outputs;
	}
}

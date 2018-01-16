import java.io.*;
import java.util.Arrays;

public class Miner extends Wallet{
	public Miner(String outFile, int walletNumber) {
		super(outFile, walletNumber);
	}
	
	public Block makeBlock(int previousHash, File fileSource) throws Exception {
		int transNumber = fileSource.list().length;
		String[] fileArray = fileSource.list();
		Transaction[] transaction = setupArray(fileSource.getName(), fileArray, transNumber);
		//Figure out way to credit miners account
		transaction[transaction.length - 1] = new Transaction(getPublicKey(), Block.getCashout());
		
		return new Block(previousHash, transaction, getPublicKey());
	}
	
	public Transaction[] setupArray(String fileSource, String[] fileArray, int transNumber) throws Exception {
		Transaction[] trans = new Transaction[transNumber + 1];
		String pathway = "\\" + fileSource + "\\";
		for(int i = 0; i < transNumber; i++) {
			if(validateTransaction(pathway + fileArray[i])) {
				trans[i] = new Transaction(pathway + fileArray[i]);
			} else {
				throw new FailedValidationException(pathway);
			}
		}
		return trans;
	}
	
	public boolean validateTransaction(String pathway) {
		VerSig verify = new VerSig();
		return verify.verifySig(pathway + "\\sentKey.pub", pathway + "\\sigFile.txt", pathway + "\\dataFile.txt");
	}
}

class FailedValidationException extends Exception {
	
	private static final long serialVersionUID = 6712233247943089645L;
	private String signatureFile;
	private String dataFile;
	private String pubKeyFile;
	private String pathway;
	
	public FailedValidationException(String pathway) {
		this.pathway = pathway;
		signatureFile = pathway + "\\sigFile.txt";
		dataFile = pathway + "\\dataFile.txt";
		pubKeyFile = pathway + "\\pubKeyFile.pub";
	}
	
	@Override
	public String toString() {
		return "The signature provided does not match the sender's address and/or the data file provided at " + pathway;
	}
}

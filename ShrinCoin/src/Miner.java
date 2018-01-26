import java.io.*;

/**
 * @author Shrink
 *
 * Extension of wallet class, adds block mining functionality
 */

public class Miner extends Wallet{
	public Miner(String outFile, int walletNumber) {
		super(outFile, walletNumber);
	}
	
	//Creates a block from the hash of the previous block and the directory holding the transaction list
	
	public Block makeBlock(int previousHash, File fileSource) throws Exception {
		
		//Creates string array of file names
		
		String[] fileArray = fileSource.list();
		int transNumber = fileArray.length;
		Transaction[] transaction = setupArray(fileSource.getName(), fileArray, transNumber);
		transaction[transaction.length - 1] = new Transaction(getPublicKey(), Block.getCashout()); // Credits miner's account
		
		return new Block(previousHash, transaction, getPublicKey());
	}
	
	//Builds transaction array
	
	public Transaction[] setupArray(String fileSource, String[] fileArray, int transNumber) throws Exception {
		
		Transaction[] trans = new Transaction[transNumber + 1]; // Creates transaction array with room for miner credit
		String pathway = "\\" + fileSource + "\\";
		
		//Iterates through each transaction folder and converts transaction folder to transaction object and validates transaction
		
		for(int i = 0; i < transNumber; i++) {
			if(validateTransaction(pathway + fileArray[i])) {
				trans[i] = new Transaction(pathway + fileArray[i]);
			} else {
				throw new FailedValidationException(pathway + fileArray[i]);
			}
		}
		return trans;
	}
	
	//Validates transaction
	
	public boolean validateTransaction(String pathway) {
		
		//Creates signature verification object then uses it on transaction
		
		VerSig verify = new VerSig();
		return verify.verifySig(pathway + "\\sentKey.pub", pathway + "\\sigFile.txt", pathway + "\\dataFile.txt");
	}
}

//Creates new exception used only in this class

class FailedValidationException extends Exception {
	
	private static final long serialVersionUID = 6712233247943089645L;
	private String pathway;
	
	public FailedValidationException(String pathway) {
		this.pathway = pathway;
	}
	
	//Prints the issue and the transaction that caused the issue
	
	@Override
	public String toString() {
		return "The signature provided does not match the sender's address and/or the data file provided at " + pathway;
	}
}

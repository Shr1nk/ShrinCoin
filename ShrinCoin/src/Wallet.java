import java.io.*;
import java.security.*;

/**
 * @author Shrink
 *
 * Standard wallet constructor. outFile is the pathway you 
 * want the keys to be written too. WalletNumber is temporary. 
 * Currently just used to differentiate wallet files in my 
 * eclipse directory.
 */

public class Wallet {
	private int walletNumber;
	private GenSig signature;
	
	public Wallet(String outFile, int walletNumber) {
		
		//GenSig probably should be renamed. Generates and holds keys and signatures in an object.
		
		this.signature = new GenSig(outFile + walletNumber + ".pub", outFile + walletNumber + ".key");
		this.walletNumber = walletNumber;
	}
	
	public PublicKey getPublicKey() throws Exception {
		return signature.getPublicKey();
	}
	
	public PrivateKey getPrivateKey() throws Exception {
		return signature.getPrivateKey();
	}
	
	public int getWalletNumber() {
		return walletNumber;
	}
	
	public File getPubKeyFile() {
		return signature.getPubKeyFile();
	}
	
	//Allows wallet owner to send a transaction. Wouldn't be a currency if it couldn't be spent.
	
	public void sendCoin(double amount, File recievingAddress, String pathway) throws Exception {
		
		//Creates a new transaction, as well as a new folder to hold said transaction. Loads transaction data into the folder and then signs the transaction.
		
		Transaction transaction = new Transaction(signature.getPublicKey(), recievingAddress, amount);
		TransactionFolder transactionFolder = new TransactionFolder(pathway, signature.getPubKeyFile(), recievingAddress);
		transaction.writeToFile(pathway);
		signature.sign(pathway + "\\" + transactionFolder.getDataFile(), pathway + "\\" + transactionFolder.getSigFile());
	}
	
	/*
	 * Validates the signature of the transaction against the senders public key and the data file.
	 * Prevents a malicious user from sending a transaction from somebody else's account.
	 */
	
	public boolean validateTransaction(String pathway) {
		
		//Generates a signature verification object, then tests the signature.
		
		VerSig verify = new VerSig();
		return verify.verifySig(pathway + "\\sentKey.pub", pathway + "\\sigFile.txt", pathway + "\\dataFile.txt");
	}
	
}

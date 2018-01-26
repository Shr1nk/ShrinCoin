import java.security.PublicKey;
import java.util.Arrays;

/**
 * @author Shrink
 *
 * Code used for creating a block object.
 * Blocks have an associated hash unique to that block.
 * The hash is generated using an Array List of 
 * transaction items and the hash of the previous block.
 */

public class Block {
	
	private int previousHash;
	Transaction[] transactionList;
	private int blockHash;
	private static double currentCashout = 1000;
	
	public Block(int previousHash, Transaction[] transactionList, PublicKey creatorAddress) {
		this.previousHash = previousHash;
		this.transactionList = transactionList;
		
		//Generates the hash for the block. Contents is actually a hash code array, and blockHash is the hash code of that array.
		
		Object[] contents = {Arrays.hashCode(transactionList), previousHash};
		this.blockHash = Arrays.hashCode(contents);
		
	}
	
	public int getPreviousHash() {
		return previousHash;
	}
	
	public int getBlockHash() {
		return blockHash;
	}
	
	public Transaction[] getTransactionList() {
		return transactionList;
	}
	
	public static double getCashout() {
		return currentCashout;
	}
	
}

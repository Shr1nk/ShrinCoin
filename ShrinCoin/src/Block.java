import java.security.PublicKey;
import java.util.Arrays;

public class Block {
	private int previousHash;
	Transaction[] transactionList;
	private int blockHash;
	private static double currentCashout = 1000;
	
	public Block(int previousHash, Transaction[] transactionList, PublicKey creatorAddress) {
		this.previousHash = previousHash;
		//transactionList[transactionList.length - 1] = "new transaction";
		this.transactionList = transactionList;
		
		/*
		 * Make sure that the creation of a block leaves extra room for the reward transaction.
		 */
		
		Object[] contents = {Arrays.hashCode(transactionList), previousHash};
		this.blockHash = Arrays.hashCode(contents);
		
		
		//Transaction award = awardCoin(creatorAddress);
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
	
	/*private Transaction awardCoin(PublicKey creatorAddress) {
		Transaction awardTransaction = new Transaction(creatorAddress, currentCashout);
		return awardTransaction;
	}*/
	
}

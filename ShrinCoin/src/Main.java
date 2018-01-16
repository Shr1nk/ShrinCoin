import java.io.File;
import java.util.ArrayList;

public class Main {
	
	static ArrayList<Block> blockchain = new ArrayList<>();
	
	//Make transaction list out of actual sent transactions.

	public static void main(String[] args) throws Exception {
		Wallet myWallet1 = new Wallet("pubkey", 1);
		Wallet myWallet2 = new Wallet("pubkey", 2);
		Wallet myWallet3 = new Wallet("pubkey", 3);
		Miner miner = new Miner("pubkey", 4);
		
		myWallet1.sendCoin(10, myWallet2.getPubKeyFile(), "\\block1\\transactionFolder");
		myWallet3.sendCoin(50, myWallet1.getPubKeyFile(), "\\block1\\transactionFolder2");
		myWallet2.sendCoin(24, myWallet3.getPubKeyFile(), "\\block2\\transactionFolder3");
		myWallet1.sendCoin(75, myWallet3.getPubKeyFile(), "\\block2\\transactionFolder4");
		myWallet3.sendCoin(90, myWallet2.getPubKeyFile(), "\\block3\\transactionFolder5");
		myWallet2.sendCoin(75, myWallet1.getPubKeyFile(), "\\block3\\transactionFolder6");
		
		
		Transaction[] genesisTransactions = {new Transaction("\\block1\\transactionFolder"), new Transaction("\\block1\\transactionFolder2")};
		Block genesisBlock = new Block(0, genesisTransactions, myWallet1.getPublicKey());
		
		blockchain.add(genesisBlock);
		
		try {
			Block block2 = miner.makeBlock(genesisBlock.getBlockHash(), new File("\\block2"));
			blockchain.add(block2);

			Block block3 = miner.makeBlock(block2.getBlockHash(), new File("\\block3"));
			blockchain.add(block3);
			
			System.out.println(genesisBlock.getBlockHash());
			System.out.println(block2.getBlockHash());
			System.out.println(block3.getBlockHash());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println();
	}
		
}

/* TODO :
 * 
 * Create Transaction Class
 * 
 * Further randomize seed generation to prevent random number repetition.
 * Need to find other data points to increase randomness.
 * 
 * Create mining system. Need to read blockchain book to find out how the hash
 * for the next block is generated and how to scale the difficulty of the problem
 * based on the mining pool.
 * 
 * Create ledger. Blockchain arraylist should be printed to a file.
 * 
 * Create CLI so the users can trade coin.
 * 
 */

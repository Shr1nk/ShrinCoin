import java.io.File;
import java.util.ArrayList;

/**
 * @author Shrink
 *
 */
public class Main {
	
	static ArrayList<Block> blockchain = new ArrayList<>();

	public static void main(String[] args) throws Exception {
		
		Wallet myWallet1 = new Wallet("pubkey", 1);
		Wallet myWallet2 = new Wallet("pubkey", 2);
		Wallet myWallet3 = new Wallet("pubkey", 3);
		Wallet DaxtonsWallet = new Wallet("DaxtonsKey", 4);
		Miner miner = new Miner("pubkey", 5);
		
		//myWallet1.sendCoin(10, myWallet2.getPubKeyFile(), "\\block1\\transactionFolder");
		//myWallet3.sendCoin(50, myWallet1.getPubKeyFile(), "\\block1\\transactionFolder2");
		//myWallet2.sendCoin(24, myWallet3.getPubKeyFile(), "\\block2\\transactionFolder3");
		//myWallet1.sendCoin(75, myWallet3.getPubKeyFile(), "\\block2\\transactionFolder4");
		//myWallet3.sendCoin(90, myWallet2.getPubKeyFile(), "\\block3\\transactionFolder5");
		//myWallet2.sendCoin(75, myWallet1.getPubKeyFile(), "\\block3\\transactionFolder6");
		//myWallet1.sendCoin(1000000, DaxtonsWallet.getPubKeyFile(), "\\block3\\transactionFolder7");
		
		
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
		
	}
		
}

/* TODO :
 * 
 * The wallet should generate a public and private key only once. Once that is done, it should read the data from a file.
 * 
 * Transaction system probably shouldn't read receiving key from file. Should just be input by the user.
 * Maybe do separate systems, one for something like a qr reader and one for manual input.
 * 
 * Try to create mnemonic seed generation system.
 * 
 * Create mining system. Need to read blockchain book to find out how the hash
 * for the next block is generated and how to scale the difficulty of the problem
 * based on the mining pool.
 * 
 * Create ledger. Blockchain arraylist should be printed to a file.
 * 
 * Validate user has credits when currency is sent by referencing previous blocks.
 * 
 * Create CLI so the users can trade coin.
 * 
 */

import java.io.*;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Shrink
 *
 * Class used to create a transaction. Transactions are
 * made up of a data file with the coin sent, a file for the
 * public keys of the sender and receiver, and a file for
 * signing the transaction.
 */

public class Transaction {
	
	private double sentCoin;
	private PublicKey sendingAddress;
	private PublicKey receivingAddress;
	
	//This constructor is used to generate a transaction from an existing file. Intended for making blocks.
	
	public Transaction(String directory) throws Exception{
		
		//Gets the amount of coin sent as a double from the data file.
		
		FileReader dataFile = new FileReader(directory + "\\dataFile.txt");
		BufferedReader br = new BufferedReader(dataFile);
		sentCoin = Double.parseDouble(br.readLine());
		
		br.close();
		
		//Gets the public addresses of the individuals involved with the transaction
		
		sendingAddress = getKey(directory + "\\sentKey.pub");
		receivingAddress = getKey(directory + "\\receiveKey.pub");
		
	}
	
	//Normal transaction constructor. Contains the sending and receiving addresses
	
	public Transaction(PublicKey sendingAddress, File receivingAddress, double sentCoin) throws Exception {
		
		//Reads receiving address from a file
		
		FileInputStream keyfis = new FileInputStream(receivingAddress.getName());
		byte[] encKey = new byte[keyfis.available()];
		keyfis.read(encKey);
		
		keyfis.close();
		
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		//Applies address and coin values. keyFactory.generatePublic generates the key from the file encoding.
		
		this.sendingAddress = sendingAddress;
		this.receivingAddress = keyFactory.generatePublic(pubKeySpec);
		this.sentCoin = sentCoin;
		
	}
	
	//Transaction constructor used for awarding coin to the miner for generating a block. Probably subject to change.
	
	public Transaction(PublicKey receivingAddress, double sentCoin) {
		this.receivingAddress = receivingAddress;
		this.sentCoin = sentCoin;
	}
	
	//Writes the data to the transaction file
	
	public File writeToFile(String pathway) throws Exception{
		File file = new File("dataFile.txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter(pathway + "\\" + file.getName()));
		writer.write("" + sentCoin);
		writer.newLine();
		writer.write(receivingAddress.toString());
		writer.newLine();
		if(sendingAddress != null) {
			writer.write(sendingAddress.toString());
		}
		writer.close();
		
		return file;
		
	}
	
	//Gets a public key from a file
	
	private PublicKey getKey(String file) throws Exception {
		
		//Reads the data from the file as a byte array
		
		FileInputStream keyfis = new FileInputStream(file);
		byte[] encKey = new byte[keyfis.available()];
		keyfis.read(encKey);
		
		keyfis.close();
		
		//Uses the byte array to construct the key using X509 encoding algorithm, as that is how the key is saved
		
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(pubKeySpec);
	}
}


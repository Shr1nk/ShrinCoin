import java.io.*;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class Transaction {
	
	private double sentCoin;
	private PublicKey sendingAddress;
	private PublicKey recievingAddress;
	
	public Transaction(String directory) throws Exception{
		FileReader dataFile = new FileReader(directory + "\\dataFile.txt");
		BufferedReader br = new BufferedReader(dataFile);
		sentCoin = Double.parseDouble(br.readLine());
		
		br.close();
		
		sendingAddress = getKey(directory + "\\sentKey.pub");
		recievingAddress = getKey(directory + "\\recieveKey.pub");
	}
	
	public Transaction(PublicKey sendingAddress, File recievingAddress, double sentCoin) throws Exception {
		FileInputStream keyfis = new FileInputStream(recievingAddress.getName());
		byte[] encKey = new byte[keyfis.available()];
		keyfis.read(encKey);
		
		keyfis.close();
		
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		this.sendingAddress = sendingAddress;
		this.recievingAddress = keyFactory.generatePublic(pubKeySpec);
		this.sentCoin = sentCoin;
		
		/* Need to validate sending address has needed currency using blockchain. Probably make separate methods for that */
	}
	
	public Transaction(PublicKey recievingAddress, double sentCoin) {
		this.recievingAddress = recievingAddress;
		this.sentCoin = sentCoin;
	}
	
	public File writeToFile(String pathway) throws Exception{
		File file = new File("dataFile.txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter(pathway + "\\" + file.getName()));
		writer.write("" + sentCoin);
		writer.newLine();
		writer.write(recievingAddress.toString());
		writer.newLine();
		if(sendingAddress != null) {
			writer.write(sendingAddress.toString());
		}
		writer.close();
		
		return file;
	}
	
	private PublicKey getKey(String file) throws Exception {
		FileInputStream keyfis = new FileInputStream(file);
		byte[] encKey = new byte[keyfis.available()];
		keyfis.read(encKey);
		
		keyfis.close();
		
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(pubKeySpec);
	}
}

/*
 * If awarding miner coin, add any remaining coin from the previous transaction so it doesn't have to run all the way across the chain.
 *
 * When you create a block, you need to validate every transaction from a user by combining all positives and negatives from that user
 * 
 * 
 */

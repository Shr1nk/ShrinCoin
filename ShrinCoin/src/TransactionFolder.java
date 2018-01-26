import java.io.*;

/**
 * @author Shrink
 *
 * Sets up the folder the transaction will be stored in.
 */

public class TransactionFolder {

	private File sentKey;
	private File receiveKey;
	private File dataFile;
	private File sigFile;

	/*
	 * Transaction constructor. Builds the folder, then writes the
	 * sender's public key and the receiver's public key.
	 */
	
	public TransactionFolder(String pathway, File sentKey, File receiveKey) throws IOException {

		/*
		 * Sets up the directory, sets class data to the data passed by the constructor.
		 * Generates the files for the data file and the signature file.
		 */
		
		File dir = new File(pathway);
		dir.mkdir();
		InputStream input = null;
		OutputStream output = null;
		this.sentKey = sentKey;
		this.receiveKey = receiveKey;
		this.dataFile = new File(pathway + "\\dataFile.txt"); //Makes a data file in the transaction directory
		this.sigFile = new File(pathway + "\\sigFile.txt"); //Makes a signature file in the transaction directory
		this.dataFile.createNewFile();
		this.sigFile.createNewFile();

		//Reads the data from the sender's key and writes it to a file stored in the transaction folder.
		
		input = new FileInputStream(this.sentKey);
		File tempKey = new File(pathway + "\\sentKey.pub");
		output = new FileOutputStream(tempKey);
		byte[] buf = new byte[2048];
		int bytesRead;
		while ((bytesRead = input.read(buf)) > 0) {
			output.write(buf, 0, bytesRead);
		}

		input.close();
		output.close();

		//Reads the data from the receiver's key and writes it to a file stored in the transaction folder.
		
		input = new FileInputStream(this.receiveKey);
		tempKey = new File(pathway + "\\receiveKey.pub");
		output = new FileOutputStream(tempKey);
		buf = new byte[1024];
		while ((bytesRead = input.read(buf)) > 0) {
			output.write(buf, 0, bytesRead);
		}

		input.close();
		output.close();

		this.sentKey = tempKey;
	}

	public String getPubKeyFile() {
		return "\\transactionFolder\\" + sentKey.getName();
	}

	public String getDataFile() {
		return dataFile.getName();
	}

	public String getSigFile() {
		return sigFile.getName();
	}

}

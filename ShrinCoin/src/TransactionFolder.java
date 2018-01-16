import java.io.*;

public class TransactionFolder {

	private File sentKey;
	private File recieveKey;
	private File dataFile;
	private File sigFile;

	public TransactionFolder(String pathway, File sentKey, File recieveKey) throws IOException {
		//IOException e = new IOException("Failed to make directory");
		
		File dir = new File(pathway);
		boolean successful = dir.mkdir();
		//if (successful) {
			InputStream input = null;
			OutputStream output = null;
			this.sentKey = sentKey;
			this.recieveKey = recieveKey;
			this.dataFile = new File(pathway + "\\dataFile.txt");
			this.sigFile = new File(pathway + "\\sigFile.txt");
			this.dataFile.createNewFile();
			this.sigFile.createNewFile();

			input = new FileInputStream(this.sentKey);
			File tempKey = new File(pathway + "\\sentKey.pub");
			output = new FileOutputStream(tempKey);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}

			input.close();
			output.close();
			
			input = new FileInputStream(this.recieveKey);
			tempKey = new File(pathway + "\\recieveKey.pub");
			output = new FileOutputStream(tempKey);
			buf = new byte[1024];
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}

			input.close();
			output.close();

			//this.sentKey.delete();
			this.sentKey = tempKey;
		//} else {
			//throw e;
		//}
	}

	public TransactionFolder(String pathway, File sentKey, File dataFile, File recieveKey) throws IOException {
		IOException exc = new IOException("Failed to make directory");
		File dir = new File(pathway);
		boolean successful = dir.mkdir();
		if (successful) {
			InputStream input = null;
			OutputStream output = null;
			this.sentKey = sentKey;
			this.dataFile = dataFile;
			this.sigFile = new File(pathway + "\\sigFile.txt");

			File tempKey;

			try {
				input = new FileInputStream(this.sentKey);
				tempKey = new File("\\pubKey.pub");
				output = new FileOutputStream(tempKey);
				byte[] buf = new byte[1024];
				int bytesRead;
				while ((bytesRead = input.read(buf)) > 0) {
					output.write(buf, 0, bytesRead);
				}
			} catch (Exception e) {
				throw exc;
			} finally {
				input.close();
				output.close();
			}

			File tempData;

			try {
				input = new FileInputStream(this.dataFile);
				tempData = new File("dataFile.txt");
				output = new FileOutputStream(tempData);
				byte[] buf = new byte[1024];
				int bytesRead;
				while ((bytesRead = input.read(buf)) > 0) {
					output.write(buf, 0, bytesRead);
				}
			} catch (Exception e) {
				throw exc;
			} finally {
				input.close();
				output.close();
			}
			this.dataFile.delete();
			this.sentKey.delete();
			this.dataFile = tempData;
			this.sentKey = tempKey;
		} else {
			throw exc;
		}

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

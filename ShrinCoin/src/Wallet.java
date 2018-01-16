import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Wallet
{
	private String outFile;
	private int walletNumber;
	private Signature sign;
	private GenSig signature;
	
	public Wallet(String outFile, int walletNumber) {
		this.signature = new GenSig(outFile + walletNumber + ".pub", outFile + walletNumber + ".key");
		this.outFile = outFile;
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
	
	public void sendCoin(double amount, File recievingAddress, String pathway) throws Exception {
		Transaction transaction = new Transaction(signature.getPublicKey(), recievingAddress, amount);
		TransactionFolder transactionFolder = new TransactionFolder(pathway, signature.getPubKeyFile(), recievingAddress);
		transaction.writeToFile(pathway);
		signature.sign(pathway + "\\" + transactionFolder.getDataFile(), pathway + "\\" + transactionFolder.getSigFile());
	}
	
	public boolean validateTransaction(String pathway) {
		VerSig verify = new VerSig();
		return verify.verifySig(pathway + "\\sentKey.pub", pathway + "\\sigFile.txt", pathway + "\\dataFile.txt");
	}
	
	public void recieveCoin() {
		
	}
}

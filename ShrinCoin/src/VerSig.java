import java.io.*;
import java.security.*;
import java.security.spec.*;

public class VerSig {
	/*
	 * private VerSig() { try { FileInputStream keyfis = new
	 * FileInputStream(pubKey); byte[] encKey = new byte[keyfis.available()];
	 * keyfis.read(encKey);
	 * 
	 * keyfis.close();
	 * 
	 * X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey); KeyFactory
	 * keyFactory = KeyFactory.getInstance("RSA"); PublicKey pubKey =
	 * keyFactory.generatePublic(pubKeySpec);
	 * 
	 * FileInputStream sigfis = new FileInputStream(sigFile); byte[] sigToVerify =
	 * new byte[sigfis.available()]; sigfis.read(sigToVerify); sigfis.close();
	 * 
	 * Signature sign = Signature.getInstance("SHA256withRSA");
	 * sign.initVerify(pubKey);
	 * 
	 * FileInputStream datafis = new FileInputStream(dataFile); BufferedInputStream
	 * bufin = new BufferedInputStream(datafis);
	 * 
	 * byte[] buffer = new byte[2048]; int len; while(bufin.available() != 0) { len
	 * = bufin.read(buffer); sign.update(buffer, 0, len); };
	 * 
	 * bufin.close();
	 * 
	 * boolean verifies = sign.verify(sigToVerify);
	 * 
	 * System.out.println("signature verifies: " + verifies); } catch (Exception e)
	 * { System.err.println("Caught exception " + e.toString()); }
	 */
	public VerSig() {}

	public boolean verifySig(String pubKeyPath, String sigFile, String dataFile) {
		try {
			FileInputStream keyfis = new FileInputStream(pubKeyPath);
			byte[] encKey = new byte[keyfis.available()];
			keyfis.read(encKey);

			keyfis.close();

			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);

			FileInputStream sigfis = new FileInputStream(sigFile);
			byte[] sigToVerify = new byte[sigfis.available()];
			sigfis.read(sigToVerify);
			sigfis.close();

			Signature sign = Signature.getInstance("SHA256withRSA");
			sign.initVerify(pubKey);

			FileInputStream datafis = new FileInputStream(dataFile);
			BufferedInputStream bufin = new BufferedInputStream(datafis);

			byte[] buffer = new byte[2048];
			int len;
			while (bufin.available() != 0) {
				len = bufin.read(buffer);
				sign.update(buffer, 0, len);
			}
			;

			bufin.close();

			boolean ver = sign.verify(sigToVerify);
			return ver;
		} catch (Exception e) {
			System.err.println("Caught exception " + e.toString());
		}
		return false;
	}
}

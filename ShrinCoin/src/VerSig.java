import java.io.*;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Shrink
 *
 * Creates signature validation object. Used
 * to verify sender owns account they send from.
 */

public class VerSig {
	
	//Inherent default constructor needed. This method verifies a signature
	
	public boolean verifySig(String pubKeyPath, String sigFile, String dataFile) {
		try {
			
			//Converts public key file into byte array
			
			FileInputStream keyfis = new FileInputStream(pubKeyPath);
			byte[] encKey = new byte[keyfis.available()];
			keyfis.read(encKey);

			keyfis.close();

			//Decodes byte array to generate public key object
			
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);

			//Converts signature file into byte array
			
			FileInputStream sigfis = new FileInputStream(sigFile);
			byte[] sigToVerify = new byte[sigfis.available()];
			sigfis.read(sigToVerify);
			sigfis.close();

			//Initializes signature verification object with SHA256withRSA algorithm
			
			Signature sign = Signature.getInstance("SHA256withRSA");
			sign.initVerify(pubKey);

			//Fills signature object with data file bytes
			
			FileInputStream datafis = new FileInputStream(dataFile);
			BufferedInputStream bufin = new BufferedInputStream(datafis);
			
			byte[] buffer = new byte[2048];
			int len;
			while (bufin.available() != 0) {
				len = bufin.read(buffer);
				sign.update(buffer, 0, len);
			};

			bufin.close();

			//Verifies signature
			
			boolean ver = sign.verify(sigToVerify);
			return ver;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}

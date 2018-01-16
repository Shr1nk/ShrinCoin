import java.io.*;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class GenSig {
	
	private PrivateKey pvt;
	private PublicKey pub;
	private Signature sign;
	
	private File pubKey;
	private File pvtKey;
	private String pubKeyFile;
	private String pvtKeyFile;
	
	public GenSig(String pubKeyFile, String pvtKeyFile) {
		try {
			
			this.pubKeyFile = pubKeyFile;
			this.pvtKeyFile = pvtKeyFile;
			
			//System.out.println(pubKeyFile + "pub");
			
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = new SecureRandom();
		
			kpg.initialize(2048, random);
		
			KeyPair pair = kpg.generateKeyPair();
			pvt = pair.getPrivate();
			pub = pair.getPublic();
									
			sign = Signature.getInstance("SHA256withRSA");
			sign.initSign(pvt);
			
			this.pubKey = new File(pubKeyFile);
			if(this.pubKey.exists()) {
				this.pubKey.delete();
				this.pubKey.createNewFile();
			} else {
				this.pubKey.createNewFile();
			}
			this.pvtKey = new File(pvtKeyFile);
			if(this.pvtKey.exists()) {
				this.pvtKey.delete();
				this.pvtKey.createNewFile();
			} else {
				this.pvtKey.createNewFile();
			}
			
			FileOutputStream out;
			
			out = new FileOutputStream(pubKeyFile);
			out.write(pub.getEncoded());
			out.close();
			
			out = new FileOutputStream(pvtKeyFile);
			out.write(pvt.getEncoded());
			out.close();
			
		} catch (Exception e) {
			System.err.println("Caught exception " + e.toString());
		}
		
		
		
	}
	
	public File getPubKeyFile() {
		return pubKey;
	}
	
	public PrivateKey getPrivateKey() throws Exception {
		
		FileInputStream keyfis = new FileInputStream(pvtKeyFile);
		byte[] encKey = new byte[keyfis.available()];
		keyfis.read(encKey);
		
		keyfis.close();
		
		PKCS8EncodedKeySpec pvtKeySpec = new PKCS8EncodedKeySpec(encKey);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(pvtKeySpec);
	}
	
	public PublicKey getPublicKey() throws Exception {
		FileInputStream keyfis = new FileInputStream(pubKey.getName());
		byte[] encKey = new byte[keyfis.available()];
		keyfis.read(encKey);
		
		keyfis.close();
		
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(pubKeySpec);
	}
	
	public void sign(String dataFile, String sigFile) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(dataFile);
			BufferedInputStream bufin = new BufferedInputStream(fis);
			byte[] buffer = new byte[2048];
			int len;
			while (bufin.available() != 0) {
				len = bufin.read(buffer);
				sign.update(buffer, 0, len);
			};
			bufin.close();
	
			byte[] realSig = sign.sign();
				
			FileOutputStream sigfos = new FileOutputStream(sigFile);
			sigfos.write(realSig);
			sigfos.close();
		} catch (Exception e) {
			System.err.println("Caught exception " + e.toString());
		}
	}
}

package ultilities;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	//hashing password with MD5
	public static String hashPassword(String password) 
			throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
    	byte[] bytesOfPassword;
		bytesOfPassword = password.getBytes("UTF-8");
    	MessageDigest md = MessageDigest.getInstance("MD5");
    	byte[] digest = md.digest(bytesOfPassword);
    	BigInteger bigInt = new BigInteger(1,digest);
    	String hash = bigInt.toString(16);
    	
    	//Zero pad for the full 32 chars.
    	while(hash.length() < 32 )
    	{
    	  hash = "0" + hash;
    	}
    	return hash;
    }
}

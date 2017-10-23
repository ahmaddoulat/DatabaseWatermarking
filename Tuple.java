import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class Tuple implements Comparable
{
	public int pkey;
	public byte[] pkey_bytes, pkey_hash_bytes;
	public BigInteger pkey_hash_BigInt;
	public double data;
	public double wdata;
	public double new_data;
	public boolean type;
	public int section;
	
	public Tuple(int i, MessageDigest md, BigInteger mykey, double init_data ){
		data = init_data;
		wdata = 0.0;
		pkey = i;
		generate_hash(md, mykey);
		type = false;
		section  = 0 ;
	}
	
    public void generate_hash(MessageDigest md, BigInteger mykey){
    	String tempString;
    	md.reset(); // reseting the hashing algorithm
    	tempString = mykey.toString() + Integer.toBinaryString(pkey) + mykey.toString();
    	pkey_bytes = tempString.getBytes();
    	md.update(pkey_bytes);
    	pkey_hash_bytes = md.digest();
    	pkey_hash_BigInt = new BigInteger(pkey_hash_bytes).abs();
    	pkey_hash_bytes = pkey_hash_BigInt.toByteArray();
    }
      
    public int compareTo(Object _tuple){
    	Tuple t = (Tuple)_tuple;
    	return new Integer(section).compareTo(new Integer(t.section));
    }
    
    public void setAsMarker(){
    	type = true;
    }
}

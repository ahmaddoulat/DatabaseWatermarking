
import java.math.BigInteger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author doulat
 */
public class DecodeInfo {
    public double T;
    public BigInteger SecretKey;
    public long seed_random_data;

    public DecodeInfo(double T, BigInteger mySecretKey, long seed_random_data) {
        this.T = T;
        this.SecretKey = mySecretKey;
        this.seed_random_data = seed_random_data;
    }

    public double getT() {
        return T;
    }

    public void setT(double T) {
        this.T = T;
    }

    public BigInteger getSecretKey() {
        return SecretKey;
    }

    public void setSecretKey(BigInteger SecretKey) {
        this.SecretKey = SecretKey;
    }

    public long getSeed_random_data() {
        return seed_random_data;
    }

    public void setSeed_random_data(long seed_random_data) {
        this.seed_random_data = seed_random_data;
    }
    
}


import java.math.BigInteger;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author doula
 */
class Settings {

    private double T;
    private BigInteger SecretKey;
    private long seed_random_data;
    private ArrayList<Tuple> watermarkedTuple;

    public ArrayList<Tuple> getWatermarkedTuple() {
        return watermarkedTuple;
    }

    public void setWatermarkedTuple(ArrayList<Tuple> watermarkedTuple) {
        this.watermarkedTuple = watermarkedTuple;
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


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
public class Setup {

    private String myline;
    private String myWatermark;
    private int section_min_size;
    private int datasize;
    private int numSections;
    private int avgsection_size;
    private double alphaf;
    private double threshold;
    private long seed_random_data;
    private BigInteger mySecretKey;
    private String input_path;
    private String private_output;

    public String getPrivate_output() {
        return private_output;
    }

    public void setPrivate_output(String private_output) {
        this.private_output = private_output;
    }

    public String getMyWatermark() {
        return myWatermark;
    }

    public void setMyWatermark(String myWatermark) {
        this.myWatermark = myWatermark;
    }

    public String getInput_path() {
        return input_path;
    }

    public void setInput_path(String input_path) {
        this.input_path = input_path;
    }
    private String output_path;

    public String getOutput_path() {
        return output_path;
    }

    public void setOutput_path(String output_path) {
        this.output_path = output_path;
    }

    public BigInteger getMySecretKey() {
        return mySecretKey;
    }

    public void setMySecretKey(BigInteger mySecretKey) {
        this.mySecretKey = mySecretKey;
    }

    public long getSeed_random_data() {
        return seed_random_data;
    }

    public void setSeed_random_data(long seed_random_data) {
        this.seed_random_data = seed_random_data;
    }

    public int getSection_min_size() {
        return section_min_size;
    }

    public void setSection_min_size(int section_min_size) {
        this.section_min_size = section_min_size;
    }

    public int getDatasize() {
        return datasize;
    }

    public void setDatasize(int datasize) {
        this.datasize = datasize;
    }

    public int getNumSections() {
        return numSections;
    }

    public void setNumSections(int numSections) {
        this.numSections = numSections;
    }

    public int getAvgsection_size() {
        return avgsection_size;
    }

    public void setAvgsection_size(int avgsection_size) {
        this.avgsection_size = avgsection_size;
    }

    public double getAlphaf() {
        return alphaf;
    }

    public void setAlphaf(double alphaf) {
        this.alphaf = alphaf;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public String getMyline() {
        return myline;
    }

    public void setMyline(String myline) {
        this.myline = myline;
    }

    @Override
    public String toString() {
        return myline;
    }

}

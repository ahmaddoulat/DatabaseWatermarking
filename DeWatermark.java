
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
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
public class DeWatermark {
    public static void main(String[] args) throws IOException {
    double T;
    BigInteger SecretKey;
    long seed_random_data;
    ArrayList<Tuple> watermarkedData;
    
    Tuples myTuples = new Tuples();
    Gson gson = new Gson();
    Reader jsonReader = new FileReader("C:\\Users\\doula\\Desktop\\testJson\\output\\settings.json");
    Reader privateAttrsJsonReader = new FileReader("C:\\Users\\doula\\Desktop\\testJson\\output\\Private.json");
    Settings settings = gson.fromJson(jsonReader, Settings.class);
    T = settings.getT();
    SecretKey = settings.getSecretKey();
    seed_random_data = settings.getSeed_random_data();
    watermarkedData = settings.getWatermarkedTuple();
    
    
    
    
}
}

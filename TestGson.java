
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.Writer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author doula
 */
public class TestGson {
    public static void main(String []args) throws Exception{
        Writer writer = new FileWriter("C:\\Users\\doula\\Desktop\\Output.json");

        Gson gson = new GsonBuilder().create();
        gson.toJson("Hello", writer);
        gson.toJson(123, writer);

        writer.close();
    }
}

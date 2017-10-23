/*
 * Created on Jan 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

 /*
 * Created on Jan 13, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
/**
 * @author Mohamed Shehab
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.security.*;
import java.math.*;
import java.util.*;
import java.lang.*;

/**
 * @author Mohamed Shehab
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
// working
public class watermark {

    public static void main(String[] args) throws IOException {
        int i;
        double test;
        boolean MAX;
        int numiter;
        int avgnum_sections;
        Gson gson = new Gson();
        Reader jsonReader = new FileReader("C:\\Users\\doula\\Desktop\\testJson\\setup.json");
        Setup setup = gson.fromJson(jsonReader, Setup.class);
        int section_min_size = setup.getSection_min_size();
        int datasize = setup.getDatasize();
        int numSections = setup.getNumSections();
        int avgsection_size = setup.getAvgsection_size();
        double alphaf = setup.getAlphaf();
        double threshold = setup.getThreshold();
        long seed_random_data = setup.getSeed_random_data();
        String output_path = setup.getOutput_path();
        String input_path = setup.getInput_path();        
        Random Random_Data = new Random(seed_random_data);

        File outputVmax = new File("outVmax.txt");
        BufferedWriter outVmax = new BufferedWriter(new FileWriter(outputVmax));

        File outputVmin = new File("outVmin.txt");
        BufferedWriter outVmin = new BufferedWriter(new FileWriter(outputVmin));

        // Sorting key initializations
        SecureRandom secure_random = new SecureRandom();
        BigInteger mySecretKey = new BigInteger(160, secure_random);
        mySecretKey = setup.getMySecretKey();

        // Hashing inintializations
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.reset(); // reseting the hashing algorithm
        } catch (NoSuchAlgorithmException exc) {
            System.out.println("Algorithm not found");
        }

        // Create the random set of data and populate the Tuples vector
        Tuples mytuples = new Tuples();

        File inFile = new File(input_path);  // read from first file specified
        BufferedReader inDataFile = new BufferedReader(new FileReader(inFile));

        String line = null;

        datasize = 0;
        // to get the size
        //line = inDataFile.readLine(); 
        i = 0;
        while ((line = inDataFile.readLine()) != null) {
            //System.out.println(line);
            double data = Double.parseDouble(line);
            mytuples.add(new Tuple(i, md, mySecretKey, data));
            i++;
            datasize++;
        }

        System.out.println("No. Tuples " + datasize);
        inDataFile.close();
        avgnum_sections = datasize / avgsection_size;

        //sorts the tuples according to the hash of the primary key.
        //adjust the size of the sections to ensure section_min_size
        mytuples.get_sections_step1(numSections);
        mytuples.sort();
        mytuples.get_sections_step2();

        // Inserting watermark		
        //String myWatermark = "101000011101100";
        String myWatermark = setup.getMyWatermark();

        PatternSearch patternAlg = new PatternSearch();

        int sindex;
        double[] vmax = new double[mytuples.sections.length];
        double[] vmin = new double[mytuples.sections.length];

        for (i = 0; i < vmax.length; i++) {
            vmax[i] = -1.0;
            vmin[i] = -1.0;
        }
        int vmaxindex = 0;
        int vminindex = 0;

        for (sindex = 0; sindex < mytuples.sections.length; sindex++) {
            double s[];
            double splusdelta[];
            double thresh[];

            s = new double[mytuples.sections[sindex].length];
            thresh = new double[mytuples.sections[sindex].length];
            splusdelta = new double[mytuples.sections[sindex].length];

            for (i = 0; i < s.length; i++) {
                Tuple t = (Tuple) mytuples.get(mytuples.sections[sindex].start + i);
                s[i] = t.data;
                thresh[i] = threshold * s[i];
            }

            MAX = true;
            if (myWatermark.charAt(sindex % myWatermark.length()) == '0') {
                MAX = false;
            }

            System.out.println("working on " + sindex + " out of " + mytuples.sections.length);

            numiter = s.length * 5;
            test = patternAlg.runPattern(s, thresh, splusdelta, 0.0, alphaf, numiter, MAX);

            if (MAX) {
                vmax[vmaxindex] = ((double) test) / ((double) s.length);
                outVmax.write(String.valueOf(vmax[vmaxindex]));
                outVmax.newLine();
                vmaxindex++;
            } else {
                vmin[vminindex] = ((double) test) / ((double) s.length);
                outVmin.write(String.valueOf(vmin[vminindex]));
                outVmin.newLine();
                vminindex++;
            }

            for (i = 0; i < s.length; i++) {
                Tuple t = (Tuple) mytuples.get(mytuples.sections[sindex].start + i);
                t.new_data = splusdelta[i];

            }
        }

        outVmax.close();
        outVmin.close();

        // Storing the output tuple.
        File outTupleFile = new File("C:\\Users\\doula\\Desktop\\testJson\\outTuple.txt");
        BufferedWriter outTuple = new BufferedWriter(new FileWriter(outTupleFile));
        ArrayList<JsonOutputHelper> outputList = new ArrayList<>();

        for (i = 0; i < mytuples.size(); i++) {
            Tuple t = (Tuple) mytuples.get(i);
            outTuple.write(String.valueOf(t.pkey) + ",");
            outTuple.write(String.valueOf(t.new_data));
            outTuple.newLine();
            JsonOutputHelper outHelper= new JsonOutputHelper(t.pkey, t.new_data);
            outputList.add(outHelper);
        }
        
        FileWriter settings = new FileWriter(output_path);
        String json = new Gson().toJson(outputList);
        gson.toJson(outputList, settings);
        settings.close();

        double[] x0 = new double[vminindex];
        double[] x1 = new double[vmaxindex];
        double meanx0, meanx1, varx0, varx1, stdx0, stdx1;
        double Pe1, Pe0, a, b, c;
        double root0, root1, det, T = 0.0;

        for (i = 0; i < x0.length; i++) {
            x0[i] = vmin[i];
        }

        for (i = 0; i < x1.length; i++) {
            x1[i] = vmax[i];
        }

        meanx0 = patternAlg.findmean(x0);
        stdx0 = patternAlg.findstd(x0, meanx0);
        varx0 = Math.pow(stdx0, 2);
        meanx1 = patternAlg.findmean(x1);
        stdx1 = patternAlg.findstd(x1, meanx1);
        varx1 = Math.pow(stdx1, 2);

        Pe1 = ((double) x1.length) / ((double) (x1.length + x0.length));
        Pe0 = 1 - Pe1;

        a = (varx0 - varx1) / (2 * varx0 * varx1);
        b = (meanx0 * varx1 - meanx1 * varx0) / (varx0 * varx1);
        c = (Math.pow(meanx1, 2) * varx0 - Math.pow(meanx0, 2) * varx1) / (2 * varx0 * varx1) + Math.log((Pe0 * stdx1) / (Pe1 * stdx0));

        det = Math.pow(b, 2) - 4.0 * a * c;

        if (det >= 0) {

            root0 = (-b - Math.sqrt(det)) / (2.0 * a);
            root1 = (-b + Math.sqrt(det)) / (2.0 * a);

            T = root0;
            if ((root1 >= meanx0) && (root1 <= meanx1)) {
                T = root1;
            }
            System.out.println("T =" + T);
        } else {
            System.out.println("Negative Determinant T cannot be calculated");
        }
        //outTuple.write(String.valueOf(T));
        DecodeInfo df = new DecodeInfo(T, mySecretKey, seed_random_data);
        String Private_output = setup.getPrivate_output();
        FileWriter privateDecodeInfo = new FileWriter(Private_output);
        gson.toJson(df, privateDecodeInfo);
        privateDecodeInfo.close();
        outTuple.close();
    }
}

import java.math.BigInteger;
import java.util.BitSet;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;


public class Chromosome implements Comparable ,Cloneable{

	public BitSet[] cvector;
	public int length;
	public int bsize;
	public int objective_value;
	public int intvalue[];
	public boolean selectMating;
	public boolean mutated;
	public double delta[];
	public double rolletteOrdering;
	
	public Chromosome copy(Chromosome t) {
		int i;
		
		t.bsize = bsize;
		t.objective_value = objective_value;
		t.selectMating = selectMating;
		t.mutated = mutated;
		t.rolletteOrdering = rolletteOrdering;
		t.length = length;
		
		for(i=0;i<cvector.length;i++){
			t.intvalue[i] = intvalue[i];
			t.delta[i] = delta[i];
			t.cvector[i] = (BitSet)cvector[i].clone();
		}
		return t;
	}
	
	Chromosome(int maxsize, int _length, int bitsize){
	int i;
	bsize = bitsize;
	cvector = new BitSet[maxsize];
	delta = new double[maxsize];
	intvalue = new int[maxsize];
	selectMating = false;
	mutated = false;
	rolletteOrdering = 0.0;
		for(i=0;i<maxsize;i++){
			cvector[i] = new BitSet(bitsize);
		}
		length = _length;
		
	}
	
	public void clearvector(){
		int i;
		for(i=0;i<cvector.length;i++){
			cvector[i].clear();}
	}
	
	public void randomize(Random RandomIdentifier){
		int i;		
		for(i=0;i<length;i++){
			IntegerToBitSet(RandomIdentifier.nextInt((int)Math.pow(2,bsize)), cvector[i]);
		}
	}
	
	public void IntegerToBitSet(int val, BitSet tochange){
		String temp;
		char one = '1', test;
		int i, maxi;
		
		temp = Integer.toBinaryString(val);
		
		//System.out.println(temp);
		
		maxi = temp.length();
		if(bsize < maxi){
			maxi = bsize;
		}
		tochange.clear(0,bsize);
		
		for(i= maxi - 1;i>=0;i--){	
				test = temp.charAt(i);
				
				if(test == '1'){
					tochange.set(i, true); 
				}
				else{
					tochange.set(i,false);
				}
		}
	}
	
	public void printVector(int start, int end){
		int i,j;
		String temp;
		if(start < 0) start = 0;
		if(end >= length) end = length -1;
		
		for(i=start;i<=end;i++){			
			temp = changeString(i);
			System.out.println(i + " " + temp + " " + changeInt(i));
		}
	}
	
	public String changeString(int index){
		int i;
		String temp;
		
			temp = "";
			
			for(i = 0;i< bsize;i++){
				if(cvector[index].get(i)){
					temp = temp + "1";
				}
				else{
					temp = temp + "0";
				}
			}
		return temp;			
	}
	
	public int changeInt(int index){
		int i;
		int temp, power2;

			temp = 0;
			power2 = 1;
			
			for(i = bsize-1;i>=0 ;i--){
				if(cvector[index].get(i)){
					temp = temp + power2;
				}
				power2 = power2*2;
			}
		return temp;
	}
	
	// assuming length = s.length
	
	public int evaluate_objective(double[] s, double[] thresh, double[] splusdelta, double c){
		BigInteger mybigint;
		int i, value;
		double beta, mean, std, ref;

		for(i=0;i<s.length;i++){
			intvalue[i] = changeInt(i);
			beta = thresh[i] * s[i];
			delta[i] = ((double)intvalue[i]) * (2.0 * beta)/(Math.pow(2,bsize)) - beta;
			splusdelta[i] = s[i] + delta[i];
		}
		
		mean = findmean(splusdelta);
		std = findstd(splusdelta, mean);
		ref = mean + c*std;
		
		value = 0;
		for(i=0;i<s.length;i++){
			if(splusdelta[i] >= ref){
				value = value + 1 ;
			}
		}
		
		objective_value = value;
		return value;
	}
	
	public double findmean(double[] dataset){
		int i;
		double value = 0.0;
		
		for(i=0;i<dataset.length;i++){
			value = value + dataset[i];
		}
		value = value /dataset.length ;
		return value;
	}
	
	public double findstd(double[] dataset, double mean){
		int i;
		double value = 0.0;
		
		for(i=0;i<dataset.length;i++){
			value = value + Math.pow(dataset[i] - mean, 2);
		}
		value = Math.sqrt(value/dataset.length) ;
		return value;		
	}
	
	public void mutateChromosome(double pm, Random RandomIdentifier){
		int i, j;
		double toss;
		
		mutated = false;
		
		for(i=0;i<length;i++){
			for(j=0;j<bsize;j++){
				toss = RandomIdentifier.nextDouble() ;
				if(toss <= pm){
					cvector[i].flip(j);
					mutated = true;
				}
			}
		}
	}

	public Object clone(){ 
	    try {
		  return super.clone(); 
	    } catch (CloneNotSupportedException e) { // Dire trouble!!!
	         throw new InternalError("But we are Cloneable!!!");
	    }
	  }

    public int compareTo(Object _tuple){
    	Chromosome t = (Chromosome)_tuple;
    	return (new Double(objective_value)).compareTo((new Double(t.objective_value)));
    }
    
    
    
}
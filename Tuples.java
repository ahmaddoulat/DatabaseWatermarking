import java.io.*;
import java.security.*;
import java.math.*;
import java.util.*;
import java.lang.*;


public class Tuples extends Vector{
    public Section sections[];
	
	public void sort(){
		Collections.sort(this);
	}
	
	public void get_sections_step1(int N){
	    int i, e;
		String temp;
		BigInteger tempkey;

		tempkey = new BigInteger(String.valueOf(N));

		for(i=0;i<this.size();i++){
		Tuple t = (Tuple)get(i);
		t.section = t.pkey_hash_BigInt.mod(tempkey).intValue();
		}

		sections = new Section[N];
		for(i=0;i<N;i++){
			sections[i] = new Section();		
		}
	}
		
	
	//assumes they are sorted by sections
	public void get_sections_step2(){
		int i;
		
		for(i=0;i<sections.length;i++){
		    sections[i].start = this.size();
		    sections[i].end = -1;
		    sections[i].length = 0;
		}

		for(i=0;i<this.size();i++){
		    Tuple t = (Tuple)get(i);
		    sections[t.section].length = sections[t.section].length + 1;
		    if((i>sections[t.section].end)) sections[t.section].end = i;
		    if(i<sections[t.section].start) sections[t.section].start = i;
		}	    
	}
	
}

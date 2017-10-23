import java.util.Collections;
import java.util.Random;
import java.util.Vector;

/*
 * Created on Jan 18, 2005
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
public class Chromosomes extends Vector{
	public double Fm;
	
	public Chromosomes(){
		Fm = 0.0;
	}
	
	public void sortascending(){
		Collections.sort(this);
	}
	
	public void sortdescending(){
		int i, k;
		Collections.sort(this);
		
		for(i=0;i<=(int)Math.floor(((double)this.size())/2.0);i++){
			Collections.swap(this, i, this.size()-1 - i);
		}
	}
	
	// computingFm based on the Population
	
	public void computeFm(boolean MAX, int endindex){
	int i;
	Fm = 0.0;
	for(i=0;i<endindex;i++){
		Chromosome t = (Chromosome)this.get(i);
		if(MAX){
			Fm = Fm + (double)t.objective_value;}
		else{
			Fm = Fm + (double) t.length - (double)t.objective_value;}
		t.rolletteOrdering = Fm;
		}
	}
	
	
	// Finds candidate in the Population with the rollette wheel toss
	// endindex is the size of the main population	
	public int findRollette(double toss, int endindex){
		int i;
			for(i=0;i<endindex;i++){
				Chromosome t = (Chromosome)this.get(i);				
				if (t.rolletteOrdering >= (toss * Fm)){
					break;
				}
			}
			if(i>=endindex) i = endindex-1;
			return i;
	}
	
	
	public void mutatePopulation(int start, int end, double pm, Random RandomIdentifier){
		int i;
		for(i=start;i<end;i++){
			Chromosome t = (Chromosome) this.get(i);
			t.mutateChromosome(pm,RandomIdentifier);
		}
	}
	
	public void removeNotMutated(int start, int end, int large){
		int i;
		for(i=start;i<end;i++){
			Chromosome t = (Chromosome) this.get(i);
			if(!t.mutated){
			    t.objective_value = large;
			}
		}
	}
}


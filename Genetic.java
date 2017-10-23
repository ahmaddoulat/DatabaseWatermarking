
import java.io.*;
import java.security.*;
import java.math.*;
import java.util.*;
import java.lang.*;

public class Genetic {

	public long myseed = 1234567890;
	public Random Random_Generator;
	public int bitsize;
	public int pop_size;
	public int totalbits;
	public int num_generations;
	public int num_elite;
	public int num_cross;
	public int num_immig;
	public int num_mating;
	public int maxsetsize;
	Chromosomes population;
	Chromosomes matingpool;
	Chromosomes crossoverRes;
	public double c = 0.7;
	public double pm = 0.01;
	public double pc = 0.75;

	public Genetic(int _maxsetsize, int _bitsize, int _pop_size, int _num_generations, double _pm, double _pc){
		Random_Generator = new Random(myseed);
		bitsize = _bitsize;
		pop_size = _pop_size;
		num_generations = _num_generations;
		num_elite = (int)(pop_size *0.50);
		num_cross = (int)(pop_size *0.40);
		num_immig = pop_size - num_elite - num_cross;
		num_mating = 2*num_cross;
		pm = _pm;
		pc = _pc;
		population = new Chromosomes();
		maxsetsize = _maxsetsize;
		num_cross = 2*(int)Math.floor(pc * ((double)num_mating)/2.0);
		
		// create the total population
		int i;
	    for(i=0;i<pop_size+num_mating+num_cross;i++){
			population.add(new Chromosome(maxsetsize, maxsetsize,bitsize));
	    }
	    /*
	    System.out.println("Genetic Algorithm");
	    System.out.println("Population size " + pop_size);
	    System.out.println("Mating pool size " + num_mating);
	    System.out.println("Crossover pool size " + num_cross);
	    */
	}
	
	// creates the population and sorts it
	public void initialize(double _s[], double _thresh[], double _splusdelta[], double c, boolean MAX){
	    int i;
	    totalbits = _s.length * bitsize;
	    //System.out.println("Initializing the population ");
	    
	    // setting the correct length
	    for(i=0;i<pop_size+num_mating+num_cross;i++){
	        Chromosome t = (Chromosome)population.get(i);
	        t.length = _s.length;
	        if(MAX){
	            t.objective_value = 0;
	        }
	        else{
	            t.objective_value = _s.length;
	        }
	    }
	    
		// adding the initial population
		Chromosome initialChrom = (Chromosome)population.get(0);
		initialChrom.length = _s.length;
		initialChrom.clearvector();
		initialChrom.evaluate_objective(_s,_thresh,_splusdelta,c);

		for(i=1;i<pop_size;i++){
			Chromosome t = (Chromosome)population.get(i);
			t.length = _s.length;
			t.randomize(Random_Generator);
			t.evaluate_objective(_s,_thresh,_splusdelta,c);
		}
		
		if(MAX){
			population.sortdescending();			
		}
		else{
			population.sortascending();			
		}
	}
	
	public int RunGenetic(double _s[], double _thresh[], double _splusdelta[], double c, boolean MAX){

		int i,j,k;
		int generation;
		int best;
				
		initialize(_s, _thresh, _splusdelta, c, MAX);
		
		generation =0;
		while(generation <= num_generations){
		
		// selection for mating  
		population.computeFm(MAX, pop_size);
		
		// adding entries to the mating pool
		for(i=0;i<num_mating;i++){
			int ii = population.findRollette(Random_Generator.nextDouble(),pop_size);
			Chromosome t1 = (Chromosome)population.get(ii);
			Chromosome t2 = (Chromosome)population.get(pop_size + i);
			t1.copy(t2);
		}
		
		// crossover stage
		
		k = num_cross/2;
		for(i=0;i<k;i++){
		
			int chrom1 = Random_Generator.nextInt(num_mating);
			int chrom2 = Random_Generator.nextInt(num_mating);
			Chromosome p1 = (Chromosome)population.get(pop_size + chrom1);			
			Chromosome p2 = (Chromosome)population.get(pop_size + chrom2);
			
			Chromosome c1 = (Chromosome)population.get(pop_size + num_mating + 2*i);			
			Chromosome c2 = (Chromosome)population.get(pop_size + num_mating + 2*i + 1);

			crossover(p1, p2, c1, c2);
		}

		
		// mutation stage
		population.mutatePopulation(pop_size, pop_size + num_mating, pm, Random_Generator);

		// generating the new population
		for(i=0;i<population.size();i++){
			Chromosome t = (Chromosome)population.get(i);
			t.evaluate_objective(_s,_thresh,_splusdelta,c);
		}

		if(MAX){
		    population.removeNotMutated(pop_size, pop_size + num_mating, 0);
		    population.sortdescending();			
		}
		else{
		    population.removeNotMutated(pop_size, pop_size + num_mating, _s.length);
			population.sortascending();			
		}
		generation = generation + 1;
	}
		
		if(MAX){
			population.sortdescending();			
		}
		else{
			population.sortascending();			
		}
		
		Chromosome bestChrom = (Chromosome)population.get(0);		
		best = bestChrom.evaluate_objective(_s,_thresh,_splusdelta,c);
		//System.out.println("Best is " + best);
		return best;
	}
	
	public void crossover(Chromosome p1, Chromosome p2, Chromosome c1, Chromosome c2){
		int crossoverPoint;
		int crossoverIndex;
		int crossoverBit;
		int j;
		

		p1.copy(c1);
		p2.copy(c2);
				
		crossoverPoint = Random_Generator.nextInt(totalbits);		
		crossoverIndex = crossoverPoint/bitsize;
		crossoverBit = crossoverPoint % bitsize;
		
		for(j=0;j<crossoverIndex;j++){
			c1.cvector[j] = (BitSet)p2.cvector[j].clone();
			c2.cvector[j] = (BitSet)p1.cvector[j].clone();
		}
		
		// inner crossover 		
		for(j=0;j<crossoverBit;j++){
		    if(p2.cvector[crossoverIndex].get(j)){
		        c1.cvector[crossoverIndex].set(j);}
		    else{
		        c1.cvector[crossoverIndex].clear(j);}

		    if(p1.cvector[crossoverIndex].get(j)){
		        c2.cvector[crossoverIndex].set(j);}
		    else{
		        c2.cvector[crossoverIndex].clear(j);}
		}
	}
}

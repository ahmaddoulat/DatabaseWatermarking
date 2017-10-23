/*
 * Created on Jan 20, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * Algorithm Performs Pattern Search.
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * Assumes 
 * C = [ I -I]
 *
 */


public class PatternSearch {
    public double runPattern(double[] s , double thresh[], double[] splusdelta, double c, 
            double alphaf, int numiter, boolean MAX){
        int i,k;
        double[] deltatemp = new double[s.length];
        double[] deltabest = new double[s.length];
        double[] alpha = new double[2*s.length];
        double fbest, ftemp;
        double multiplier;
        
        clear(deltatemp);
        clear(deltabest);
        
        fbest = computeF(deltabest, s, splusdelta);
        multiplier = 1.0;
        for(i=0;i<numiter;i++){
            boolean flag = false;
            
            for(k=0;k<2*s.length;k++){
                double sign = 1.0;

                if(k > s.length){
                    sign = -1.0;   
                }
                copy(deltabest, deltatemp);
                deltatemp[k%thresh.length] = deltatemp[k%thresh.length] + sign * multiplier * thresh[k%thresh.length];
                
                if(feasible(thresh, deltatemp)){
                    ftemp = computeF(deltatemp, s, splusdelta);
                    if(MAX){
                        if(ftemp > fbest){
                            fbest = ftemp;
                            deltabest[k%thresh.length] = deltatemp[k%thresh.length];
                            flag = true;
                        }  
                    }
                    else{
                        if(ftemp < fbest){
                            fbest = ftemp;
                            deltabest[k%thresh.length] = deltatemp[k%thresh.length];
                            flag = true;
                        }
                    }
                }
            }
            
            if(flag){
                // managed to find better point
                multiplier = 1.0/2.0;
            }
            else{
                multiplier = multiplier / 2.0;
                
                if(multiplier < 1.0/128.0){
                    //System.out.println("multiplier is very small terminating at " + i);
                    break;
                }
            }
        }
        
        //System.out.println("Pattern Search Best is " + computeF(deltabest, s , splusdelta, c));
        
        return computeF(deltabest, s, splusdelta);
    }
    
    public boolean feasible(double thresh[], double deltatest[]){
        boolean value;
        int i;
        value = true;
        
        for(i=0;i<thresh.length;i++){
            if(Math.abs(deltatest[i]) > Math.abs(thresh[i])){
                value = false;
                break;
            }
        }
        return value;
    }
    
    public double computeF(double[] delta, double[] s, double[] splusdelta){
        double value = 0;
        int i,j;
        
        add(s,delta,splusdelta);
        
        for(i=0;i<splusdelta.length;i++){
            for(j=0;j<splusdelta.length;j++){
                value = value + Math.pow(splusdelta[i]-splusdelta[j],2);
            }
        }
        
        value = value/(Math.pow(splusdelta.length,2));
        return value;
    }
    
    public double computeFapprox(double[] delta, double[] s , double[] splusdelta, double c, double alphaf){
        int i;
        double meantemp;
        double stdtemp;
        double ref;
        double value;
        
        add(s,delta,splusdelta);
        
        meantemp = findmean(splusdelta);
        stdtemp = findstd(splusdelta, meantemp);
        ref = meantemp + c*stdtemp;
        
        value = 0.0;
        for(i=0;i<s.length;i++){
            value = value + sigmoid(splusdelta[i],ref,alphaf);
        }
        return value;
    }
    
    public double sigmoid(double x, double tau, double alphaf){
        return (1.0 - 1.0/(1 + Math.exp(alphaf*(x-tau))));
    }
    
    public void clear(double a[]){
        int i;
        for(i=0;i<a.length;i++){
            a[i] = 0.0;
        } 
    }
    
    public double findmean(double dataset[]){
        double result = 0.0;
        int i;
        for(i=0;i<dataset.length;i++){
            result = result + dataset[i];
        }
        return result/dataset.length;
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

    public void add(double a[], double b[], double c[]){
        int i;
        for(i=0;i<a.length;i++){
            c[i] = a[i] + b[i];
        }
    }
    
    public void copy(double source[], double dest[]){
        int i;
        for(i=0;i<source.length;i++){
            dest[i] = source[i];
        }
    }
    
    public double sum(double a[]){
     int i;
     double mysum = 0.0;
     for(i=0;i<a.length;i++){
         mysum = mysum + a[i];
     }
     return mysum;
    }
    

}

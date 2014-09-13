
/**
 * Facilitates dispensing stamps for a postage stamp machine.
 */
public class StampDispenser
{
	/**
	 * Keeps count of the number of stamps dispensed for every run. eg: countStampsDispensed[i] - > number of stamps dispensed for run 'i'
	 */
	int[] countStampsDispensed;
	
	
	/**
	 * Holds the value of the current run/pass
	 */
	int run;
	
	/**
	 * Array of values holding the unique denomination supported by the StampDispenser
	 */
	int[] stampDenominations;
	
	/**
	 * Holds the value of the start index of the stampDenominations array for the current run
	 */
	int runStartIndex=0;
	
	/**
	 * Increments the count of Stamps Dispensed by dispensing a stamp that is closest to the request
	 * @param index  The index of stampDenominations to search for the appropriate stamp Denomination to be dispersed
	 * @param request The value of stamps to be dispensed
	 */
	void dispenseStamp(int index, int request, int remaining)
	{		
		for(int i=index;i<stampDenominations.length;i++)
		{
			if(stampDenominations[i]>remaining)
				continue;
			int product = stampDenominations[i];
			int j=1,temp=0;
			//try multiples of stampDenomination to check for the one nearest to the request
			while(product<=remaining)
			{
				temp = product;
				j++;
				product = stampDenominations[i]*j;
			}
			if(countStampsDispensed[run]==9999)
			{
				runStartIndex=i;
				countStampsDispensed[run] = j-1;
			}
			else
				countStampsDispensed[run] += j-1;
			remaining -= temp;
			// Increment and start a new run if the request has been completed
			if(remaining==0)
			{
				//min=countStampsDispensed[run];
				run++;
				if(i+1 >= stampDenominations.length)
					break;
				dispenseStamp(runStartIndex+1, request, request);
			}		
			else
				dispenseStamp(i+1,request,remaining);	
			return;
		}
	}
    /**
     * Constructs a new StampDispenser that will be able to dispense the given 
     * types of stamps.
     *
     * @param stampDenominations The values of the types of stamps that the 
     *     machine should have.  Should be sorted in descending order and 
     *     contain at least a 1.
     */
    public StampDispenser(int[] stampDenominations)
    {
    	this.stampDenominations = stampDenominations;
    	countStampsDispensed = new int[10];
    	for(int i=0;i<10;i++)
    		countStampsDispensed[i]=9999;
    }
 
    /**
     * Returns the minimum number of stamps that the machine can dispense to
     * fill the given request.
     *
     * @param request The total value of the stamps to be dispensed.
     */
    public int calcMinNumStampsToFillRequest(int request)
    {  
    	dispenseStamp(0, request,request);
    	//find the smallest element in countStampsDispensed and return it
    	int min = countStampsDispensed[0];
    	for(int i=1;i<countStampsDispensed.length;i++){
    		if(countStampsDispensed[i]<min)
    			min = countStampsDispensed[i];
    	}
    	System.out.println("min: "+min);
        return min;
    }
    
    public static void main(String[] args)
    {
        int[] denominations = { 90, 30, 24, 10, 6, 2, 1 };
        StampDispenser stampDispenser = new StampDispenser(denominations);
        //assert stampDispenser.calcMinNumStampsToFillRequest(18) == 3;
        stampDispenser.calcMinNumStampsToFillRequest(18);
    }
}

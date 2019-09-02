/**
 * Random code modified from:
 * Title: The GLIBC Random Number Generator
 * Author: Peter Selinger
 * Date: 4 January 2007
 * Availability: http://www.mathstat.dal.ca/~selinger/random/
 *
 * Class creates psudo-random numbers and stores it in an array. These numbers are retrieved for the SRPN random function
 * Origional code was found by typing the random numbers from SRPN into google.
 *
 * methods include: random, and retrieveRandom
 */

public class Random {
	private int randomCount = 1;
	//array of integers r is used to store random numbers
	public int[] r;
	
	//Constructor, Calculates 100 random numbers and stores it in the array r
	public Random() {
		r = new int[444];
    	//Set the origional value as 0. this is to be the first value
    	r[0] = 1;
    	//Initialise values and stores them into r
    	for (int i = 1; i < 31; i++) {
    		r[i] = (int) ((16807l * r[i-1]) % 2147483647);
    		if (r[i] < 0) {
    			r[i] += 2147483647;
    		}
  	  	}
    	for (int i=31; i<34; i++) {
    		r[i] = r[i-31];
    	}
    	for (int i=34; i<344; i++) {
    		r[i] = r[i-31] + r[i-3];
    	}
  	  
    	//Final values
    	for (int i=344; i<344 + 100; i++) {
    		r[i] = r[i-31] + r[i-3];
    	}
	}

	/*
	Method that retrieves the random number from the array
	@return r[342 + randomCount] >>> 1
	*/
	public int retrieveRandom() {
		if(randomCount < 100) {
			randomCount++;
		}
		// >>> is an unsigned right bit-shift from java and is from cited code
		return r[342 + randomCount] >>> 1;
	}
}

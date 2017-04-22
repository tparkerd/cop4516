// Solution Candidate for Haircut for COP 4516, Spring 2017

import java.util.*;
public class haircut {
	public static final boolean DEBUG = false;
	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		int nCases = stdin.nextInt();
		for (int i = 1; i <= nCases; i++) {
			// Get an array of all the times it takes each barber to cut someone's hair
			int[] barbers = new int[stdin.nextInt()];
			long nthPlace = stdin.nextInt();
			// Insert the actual times for time to cut hair
			for (int j = 0; j < barbers.length; j++)
				barbers[j] = stdin.nextInt();
			if (DEBUG) System.out.println("Barbers: " + Arrays.toString(barbers));

			// Solve!
			System.out.printf("Case #%d: %d\n", i, binarySearch(barbers, nthPlace));
		}
		stdin.close();
	}

	public static long estimateStartingPosition(int[] barbers, long t) {
		long nthPerson = 0;
		// For each barber, add the number of persons for which they would be able
		// cut their hair.
		for (int i = 0; i < barbers.length; i++)
			nthPerson += (t / barbers[i] + 1);
		return nthPerson;
	}

	public static long binarySearch(int[] barbers, long nthPlace) {
		long lo = 0;	      // Could possibly be 1, but not sure about small cases
		long hi = (long)1e12; // NOTE(timp): uuhhh, raise the bounds?
		long nextCustomer = 1;
		// Find the best time to start the simulation. We want the the next person
		// in line to be as close to the actual nth person as possible.
		// So, low ball the time it would take to get to said nth person
		// This is done by summing the number of persons each barber could see
		// given an ending time
		while (lo < hi) {
			long mid = (lo + hi) / 2;
			nextCustomer = estimateStartingPosition(barbers, mid);
			// If we haven't the nth person in the queue, keep trying to narrow
			// down the time frame to start the simulation
			if (nextCustomer < nthPlace) {
				lo = mid + 1;
			} else {
				hi = mid;
			}
		}

		if (DEBUG) System.out.println("Lo: " + lo);
		if (DEBUG) System.out.println("Hi: " + hi);

		// Now that we have some number of persons already served, run the
		// simulation until we get to the desired nth person in the queue
		// Get the state of the barbers with respect to the lower bound of the time
		// range. So, go through each of the barbers and see how many people they
		// have served so far, from here, we'll count the number of people that can
		// be seated one-by-one by the barbers as they become available
		long nthPerson = 0;
		for (int i = 0; i < barbers.length; i++) {
			if (DEBUG) System.out.printf("%d += (%d + %d - 1) / %d = %d\n", nthPerson, lo, barbers[i], barbers[i], (lo + barbers[i] - 1) / barbers[i]);
 		// God I hated this. It took forever to realize that I needed to subtract 1
		// #NotProud
			nthPerson += (lo + barbers[i] - 1) / barbers[i];
		}

		if (DEBUG) System.out.println("Person at Front of Line: " + nthPerson);

		// Assume that the first barber will be the next available
		int result = 0;
		// For each barber, see who is next available to cut hair
		for (int i = 0; i < barbers.length; i++) {
			// If the current barber just because available, i.e., the time is a
			// multiple of their 'time to cut hair', have the next person sit down
			// Make sure to start at the low ball starting point
			// NOTE(timp): this seemed easier than following Arup's suggestion
			// to sort the barbers since this will just count the people and
			// save the barber they went to. Technically, I think this could be used
			// to save a recent history of the order people were seated
			if (lo % barbers[i] == 0) {
				if (DEBUG) System.out.printf("%d has sat down at %d\n", nthPerson, i);
				// If we find the person that we need to seat, that's it! Bail out!
				if (nthPerson == nthPlace - 1) {
					// Save which barber the person went to
					result = i;
					break;
				}
				// Otherwise, keep counting how many people we've seated
				nthPerson++;
			}
		}

		// Return the barber that cut the hari for the nth person
		// +1 to shift the index of the barber to counting number
		return result + 1;
	}
}

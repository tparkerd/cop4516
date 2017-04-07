// By Jack W.
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class sticks {
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		int cases = in.nextInt();

		for(int j = 0; j < cases; j++){
			int l = in.nextInt();
			int cutCount = in.nextInt();

			ArrayList<Integer> cuts = new ArrayList<Integer>();
			for(int i = 0; i < cutCount; i++){
				cuts.add(in.nextInt());
			}

			long[][] memo = new long[l + 1][l + 1];
			for(int i = 0; i < memo.length; i++){
				Arrays.fill(memo[i], -1);
			}

			System.out.println(solve(0, l, cuts, memo));
		}
	}

	// We're basically solving for the minimum value for any given start&end point.
	public static long solve(int start, int end, ArrayList<Integer> cuts, long[][] memo){
		if(memo[start][end] != -1){
			// we've already solved this segment before, so return that value
			return memo[start][end];
		}

		// cut will determine whether or not we've made a cut.
		// I was running into a problem where `result` was being returned when there were no possible cuts that could be made.
		// So this value is used at the end of this function to determine if we should return 0, or `result`.
		boolean cut = false;


		// used to hold the minimum result of the recursive call
		long result = Long.MAX_VALUE;


		// This loop essentially finds all cuts that are between the start and end point.
		// It finds the cut that results in the minimum value by just recursively calling `solve` for all possible cuts
		for(int i = 0; i < cuts.size(); i++){
			int curr = cuts.get(i);

			if(curr > start && curr < end){
				cut = true;

				// If we select a cut, we need to check both of the resulting sticks
				long temp = solve(start, curr, cuts, memo) + solve(curr, end, cuts, memo) + (end - start);

				result = Math.min(temp, result);
			}
		}

		// if we made a cut, then store `result` and return it. Otherwise store and return 0
		if(cut){
			memo[start][end] = result;
			return result;
		}else{
			memo[start][end] = 0l;
			return 0l;
		}
	}
}

import java.math.BigInteger;
import java.util.Scanner;

public class christmas {
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		long[] nums = new long[1000001];
		int maxN = 3;
		
		nums[0] = 0;
		nums[1] = 1;
		nums[2] = 4;
		
		while(true){
			int n = in.nextInt();
			if(n == 0) return;

			if( n < maxN){
				System.out.println(nums[n]);
			}else{
				for(int i = maxN; i <= n; i++){
					nums[i] = nums[i - 1] + i + (nums[i-1] - nums[i-2]);
				}
				System.out.println(nums[n]);
				maxN = n + 1;
			}
			
			
		}
		
	}
}

/*
1
2
3
0

*/
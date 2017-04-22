import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;

public class bullseye {
/*
5
1 9
1 10
3 40
1 1000000000000000000
10000000000000000 1000000000000000000

 */
	
	
	static HashMap<Long, Double> Rings;
	public static void main(String[] args){
		Scanner R = new Scanner(System.in);
		Rings = new HashMap<>();
		
		int T = R.nextInt();
		Long Rad;
		Long Mili;
	
		for(int i = 0 ;i < T; i++){
			Rad = R.nextLong();
			Mili = R.nextLong();
			Calculate(Rad, Mili, i+1);
		}
	}
	
	public static void Calculate(Long Rad, Long Mili, int Case){
		long R = Rad;
		double ink = Mili;
		
		long WhiteArea = R*R;
		//R++;
		long BlackArea = R*R;
		long Ringcount=0;
		long base = (BlackArea-WhiteArea);

		while(true){
				WhiteArea = R*R;
				R++;
				BlackArea = R*R;
				ink = ink - (BlackArea-WhiteArea);
				R++;
			
			if(ink < 0){
				//System.out.println(ink+(BlackArea-WhiteArea));
				//System.out.println(ink);
				System.out.println("Case #"+ Case + ": " + Ringcount);
				return;
			}else{
				Ringcount++;;
			}
		}
	}
	
}

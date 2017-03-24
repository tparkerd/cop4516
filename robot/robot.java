import java.util.ArrayList;
import java.util.Scanner;

class pair{
	int x;
	int y;
	int penalty;
	double time;
	
	public pair(int a, int b, int p){
		this.x = a;
		this.y = b;
		this.penalty = p;
		time = 0;
	}
	
}

public class robot {

	public static void main(String args[]){
		Scanner R = new Scanner(System.in);
		int n;
		ArrayList<pair> Targets;
		pair temp;
		int x, y, z;
		int Total;
		while(true){
			n = R.nextInt();
			if(n == 0){
				return;
			}
			Targets = new ArrayList<>();
			Total = 0;
			for(int i = 0; i < n; i++){
				x = R.nextInt();
				y=R.nextInt();
				z=R.nextInt();
				Total = Total+z;
				temp = new pair(x,y,z);
				Targets.add(temp);
			}
			Calculate(Targets, Total);
		}
	}
	
	
	
	public static void Calculate(ArrayList<pair> Targets, int TotalPenalty){
		pair start = new pair(0,0,0);
		pair end = new pair(100,100,0);
		
		double TimeTo = distance(start, Targets.get(0))+1;
		double distance1;
		double distance2;
		
		while(Targets.size() > 1){
			distance1 = TimeTo + distance(Targets.get(0), Targets.get(1))+1;		
			distance2 = distance(start, Targets.get(1)) + Targets.get(0).penalty+1;
			
			
			if(distance1 > distance2){
				TimeTo = distance2;
			} else{
				TimeTo = distance1;
			}
			Targets.remove(0);
		}

		distance1 = TimeTo + distance(Targets.get(0), end)+1;
		distance2 = TotalPenalty+distance(start,end)+1;
		
		if(distance1 > distance2){
			System.out.printf("%.3f\n", distance2);
		} else{
			System.out.printf("%.3f\n", distance1);
		}
		
	}
	
	public static double distance(pair One, pair Two){
		if(One == Two){
			return 0;
		}
		
		int Newx = One.x - Two.x;
		int Newy = One.y - Two.y;
		double out = Math.sqrt(  (Newx*Newx)+(Newy*Newy)  );
		return out;
	}
	
}

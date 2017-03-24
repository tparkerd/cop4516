import java.util.Scanner;

public class nametag {

	public static void main(String args[]){
		Scanner R = new Scanner(System.in);
		
		int n = R.nextInt();
		
		for(int i = 0; i < n; i++){
			String Name = R.next().toUpperCase();
			Calculate(Name);
		}
		
	}
	
	public static void Calculate(String name){
		char Earliest = 'Z';
		int index = 0;
		
		for(int i = 0;i < name.length(); i++){
			if(name.charAt(i) < Earliest){
				Earliest = name.charAt(i);
				index = i;
			} else if (name.charAt(i) == Earliest){
				index = Earliest(name, index, i);
			}
		}
		String One = name.substring(0, index);
		String Two = name.substring(index, name.length());
		System.out.println(Two + One);
		
	}
	
	public static int Earliest(String Name, int x, int y){
		String One = Name.substring(x, Name.length()) + Name.substring(0, x);
		String Two = Name.substring(y, Name.length()) + Name.substring(0, y);

		for(int i = 0; i < Name.length(); i++){
			if(One.charAt(i) < Two.charAt(i)){
				return x;
			} else if(Two.charAt(i) < One.charAt(i)){
				return y;
			}
			
		}
		
		return x;
	}
	
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class countseq {

	public static void main(String args[]){
		Scanner R = new Scanner(System.in);
		
		int n = R.nextInt();
		String Word;
		String Sub;
		for(int i = 0; i < n; i++){
			Word 	= R.next().toUpperCase();
			Sub		= R.next().toUpperCase();
			Seek(Word, Sub);
		}
		
		
	}
	
	static HashMap <String, Long> substrings;
	static ArrayList<String> valstrings;
	public static void Seek(String Word, String Sub){
		substrings = new HashMap<String, Long>();
		valstrings = new ArrayList<String>();
		Disassemble(Sub);
		
		int out = 0;
		char temp;
		Long last;
		Long count;
		String SubString;
		//Loop through entire Word
		for(int i = 0; i < Word.length();i++){
			temp = Word.charAt(i);
			
			//If Letter is found in Sub
			if(Sub.indexOf(temp) != -1){
				last = 1l;
				SubString = Character.toString(temp);
				//loop through all valid substrings to see which it fulfills
				for(int j = 0; j < valstrings.size();j++){
					count = substrings.get(valstrings.get(j));
					
					if(SubString.equals(valstrings.get(j))){
						substrings.put(SubString, count+last);
					}
					
					SubString = valstrings.get(j) + temp;
					last = count;
					if(count == 0){
						j = valstrings.size();
					}
					
				}//End valid substring loop	
			}
		}//End word loop
		
		System.out.println(substrings.get(Sub));
	}
	
	public static void Disassemble(String Sub){
		String temp;
		for(int i = 1; i <= Sub.length(); i++){
			temp = Sub.substring(0, i);
			substrings.put(temp, 0l);
			valstrings.add(temp);
		}
	}
			
	
}

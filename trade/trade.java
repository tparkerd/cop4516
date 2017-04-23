import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class trade {
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		
		int cases = in.nextInt();
		for(int i = 0; i < cases; i++){
			int n = in.nextInt();
			int s = in.nextInt();
			
			ArrayList<Student>[] sandwiches = new ArrayList[s + 1];
			
			long[] vals = new long[s + 1];
			
			vals[1] = in.nextInt();
			Student me = new Student(1, vals[1]);
			sandwiches[1] = new ArrayList<Student>();
			sandwiches[1].add(me); // "me"
			me.accepted.add(1);
			for(int j = 2; j < s + 1; j++){
				sandwiches[j] = new ArrayList<Student>();
				vals[j] = in.nextInt();
				me.accepted.add(j);
			}
			

			for(int j = 0; j < n; j++){
				int temp = in.nextInt();
				Student student = new Student(temp, vals[temp]);
				sandwiches[student.sandwich].add(student);

				int t = in.nextInt();
				student.accepted.add(temp);
				for(int f = 0; f < t; f++){
					int sand = in.nextInt();
					student.accepted.add(sand);
				}
			}
			
			solve(me, sandwiches);
		}
	}
	
	private static void solve(Student me, ArrayList<Student>[] sandwiches) {
		long maxVal = me.value;
		ArrayDeque<Student> queue = new ArrayDeque<Student>();
		queue.add(me);
		
		//System.out.println(Arrays.deepToString(sandwiches));

		while(!queue.isEmpty()){
			Student curr = queue.pop();

			if(curr.visited) continue;
			curr.visited = true;

			if(curr.value > maxVal) maxVal = curr.value;

			
			ArrayList<Integer> accepted = curr.accepted;

			for(int i = 0; i < accepted.size(); i++){
				int sand = accepted.get(i);
								
				for(Student s : sandwiches[sand]){

					queue.addLast(s);
				}
			}
		}
		
		System.out.println(maxVal);
	}

	public static class Student {
		public int sandwich;
		public ArrayList<Integer> accepted;
		public boolean visited;
		public long value;
		
		public Student(int sand, long val){
			sandwich = sand;
			value = val;
			accepted = new ArrayList<Integer>();
			visited = false;
		}
		
		public String toString(){
			return sandwich + "-" + value + " --- " + accepted.toString();
		}
	}
}

/*
2
1 2
3 5
2 1 1
4 3
4 8 9
1 2 2 3
2 1 3
2 2 1 3
3 1 2

1
4 4
4 8 9 10
1 2 2 3
2 1 3
2 2 1 3
3 1 2
4 1 4

1
4 2
1 2
2 1 2
2 1 2
2 1 2
*/

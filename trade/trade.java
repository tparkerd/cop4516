// Solution candidate for Trade for COP 4516, Spring 2017

import java.util.*;

public class trade {
	public static Student[] students;
	public static int[] sandwiches;

	@SuppressWarnings("unchecked")
	public static void main(String[] args){
		Scanner stdin = new Scanner(System.in);
		int nCases = stdin.nextInt();

		// For each case...
		for (int i = 0; i < nCases; i++) {
			int n = stdin.nextInt();
			int s = stdin.nextInt();

			// Sandwich Dictionary to hold values of sandwiches
			sandwiches = new int[s + 1];
			for (int j = 1; j < sandwiches.length; j++) {
				int value = stdin.nextInt();
				sandwiches[j] = value;
			}


			// Make a graph~!
			students = new Student[n + 1];

			// Populate graph~!
			// I accept any sandwiches!
			students[0] = new Student(0, 1);
			for (int j = 1; j <= s; j++)
				students[0].accepts.add(j);

			// Create a student each time and add them to the graph with the sandwich
			// that they start out with
			for (int j = 1; j <= n; j++) {
				int d = stdin.nextInt();
				int t = stdin.nextInt();
				students[j] = new Student(j, d);
				for (int k = 0; k < t; k++) {
					int tmp = stdin.nextInt();
					students[j].accepts.add(tmp);
				}
			}

			int[][] matrix = new int[n + 1][n + 1];

			ArrayList<Integer>[] graph = new ArrayList[n + 1];
			for (int j = 0; j < graph.length; j++)
				graph[j] = new ArrayList<Integer>();

			// For each student...
			for (int j = 0; j < matrix.length; j++) {
				// Get all the sandwiches that they accept
				for (int k = 0; k < students[j].accepts.size(); k++) {
					// For all the sandwiches that they accept, see who has them to start
					for (int l = 0; l < students.length; l++) {
						// Does this student have the desired sandwich?
						// If so, I can trade with them.
						if (l == j) continue; // skip self cycles
						if (students[j].accepts.get(k) == students[l].sandwichType) {
							matrix[l][j] = 1;
							graph[l].add(j);
						}
					}
				}
			}




			// for (int j = 0; j < matrix.length; j++) {
			// 	for (int k = 0; k < matrix.length; k++) {
			// 		if (matrix[j][k] == 1) {
			// 			graph[j].add(k);
			// 		}
			// 	}
			// }
			System.out.println(DFS(graph, 0));
		}
	}

	// Try to connect each of the students by means of trading their sandwiches
	public static int DFS(ArrayList<Integer>[] graph, int s) {
		// s = start node, the PBn'J sandwich
		 // Assume that the PBn'J is the best at first
		int max = sandwiches[1];

		// Keep track of which students have traded with a stack
		Stack<Integer> stack = new Stack<Integer>();
		boolean[] visited = new boolean[graph.length];

		// Start us off at the PBn'J
		stack.push(s);
		// So long as there is someone else that is willing to trade with the
		// current student, try to make a trade
		while(!stack.empty()) {
			s = stack.pop();
			if (!visited[s]) {
				visited[s] = true;
				// Keep track of the more valuable sandwich thus far
				max = Math.max(max, sandwiches[students[s].sandwichType]);
			}
			// Get the next students that are willing to trade
			for (Integer n : graph[s])
				if (!visited[n])
					stack.push(n);
		}
		return max;
	}
}

class Student {
	int id;											// No. of student
	int sandwichType;						// Starting sandwich
	ArrayList<Integer> accepts; // Willing to accept these sandwiches

	public Student(int i, int s) {
		this.id = i; this.sandwichType = s;
		accepts = new ArrayList<Integer>();
	}
}

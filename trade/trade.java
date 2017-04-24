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

			// Sandwich value look-up table
			sandwiches = new int[s + 1];
			for (int j = 1; j < sandwiches.length; j++)
				sandwiches[j] = stdin.nextInt();

			// Gather trading info for all the students
			students = new Student[n + 1];
			// Starting student accepts any sandwiches!
			students[0] = new Student(1);
			for (int j = 1; j <= s; j++)
				students[0].accepts.add(j);

			// Add remaining students
			for (int j = 1; j <= n; j++) {
				int d = stdin.nextInt();
				int t = stdin.nextInt();
				students[j] = new Student(d);
				// Get all the sandwiches they are willing to accept
				for (int k = 0; k < t; k++)
					students[j].accepts.add(stdin.nextInt());
			}

			// Make a graph~!
			ArrayList<Integer>[] graph = new ArrayList[n + 1];
			for (int j = 0; j < graph.length; j++)
				graph[j] = new ArrayList<Integer>();

			// Populate graph~!
			// Convert all the student data to a graph
			// For each student...
			for (int j = 0; j < graph.length; j++) {
				// Get all the sandwiches that they accept
				for (int k = 0; k < students[j].accepts.size(); k++) {
					// For all the sandwiches that they accept, see who has them to start
					for (int l = 0; l < students.length; l++) {
						if (l == j) continue; // skip self cycles, possibly unnecessary***
						// If the main student (J-th) accepts the starting sandwich of the
						// current student (L-th student of all students)
						if (students[j].accepts.get(k) == students[l].sandwichType) {
							graph[l].add(j);
						}
					}
				}
			}

			// Solve!
			System.out.println(DFS(graph, 0));
		}
	}

	// Try to connect each of the students by means of trading their sandwiches
	// Return the value of the most valuable sandwich found
	public static int DFS(ArrayList<Integer>[] graph, int s) {
		// s = start node, the PBn'J sandwich
		 // Assume that the PBn'J is the best at first
		int max = sandwiches[1];

		// Iterative DFS
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
	int sandwichType;						// Starting sandwich
	ArrayList<Integer> accepts; // Willing to accept these sandwiches

	public Student(int s) {
		this.sandwichType = s;
		accepts = new ArrayList<Integer>();
	}
}

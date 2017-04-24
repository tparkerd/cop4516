// Solution candidate (super messy) for Trade for COP 4516, Spring 2017

import java.util.*;

public class trade {
	public static final boolean DEBUG = true;
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

			if (DEBUG) System.out.println("Sandwich values: " + Arrays.toString(sandwiches));

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

			// For each student...
			for (int j = 0; j < matrix.length; j++) {
				// Get all the sandwiches that they accept
				if (DEBUG) System.out.printf("S_%d accepts %s\n", j, students[j].accepts);
				for (int k = 0; k < students[j].accepts.size(); k++) {
					// For all the sandwiches that they accept, see who has them to start
					for (int l = 0; l < students.length; l++) {
						// Does this student have the desired sandwich?
						// If so, I can trade with them.
						if (students[j].accepts.get(k) == students[l].sandwich) {
							if (DEBUG) System.out.printf("S_%d has %d\n", l, students[l].sandwich);
							matrix[l][j] = 1;
						}
					}
				}
			}
			matrix[0][0] = 0;
			for (int[] row : matrix)
				if (DEBUG) System.out.println(Arrays.toString(row));


			ArrayList<Integer>[] graph = new ArrayList[n + 1];
			for (int j = 0; j < graph.length; j++)
				graph[j] = new ArrayList<Integer>();

			for (int j = 0; j < matrix.length; j++) {
				for (int k = 0; k < matrix.length; k++) {
					if (matrix[j][k] == 1) {
						graph[k].add(j);
					}
				}
			}



			for (int j = 0; j < graph.length; j++) {
				if (DEBUG) System.out.println("S_" + j + ": " +  (graph[j]));
			}

			if (DEBUG) System.out.println("=========================");
			System.out.println(DFS(graph, 0));
			if (DEBUG) System.out.println("=========================");

		}
	}

	public static int DFS(ArrayList<Integer>[] graph, int s) {
		// s = start node, the PBn'J sandwich

		int max = 1;
		boolean[] visited = new boolean[graph.length];

		Stack<Integer> stack = new Stack<Integer>();

		stack.push(s);
		while(!stack.empty()) {
			if (DEBUG) System.out.println("Stack: " + stack);
			s = stack.pop();
			if (!visited[s]) {
				visited[s] = true;
				// Get the greatest max
				max = Math.max(max, sandwiches[students[s].sandwich]);
			}

			for (int i = 0; i < graph[s].size(); i++)
				if (!visited[i]) {
					stack.push(i);
				}
		}
		return max;
	}
}

class Sandwich {
	int id;
	int value;
	boolean traded;
	ArrayList<Integer> accepts;

	public Sandwich(int i, int v) {
		this.id = i; this.value = v;
		traded = false;
		accepts = new ArrayList<Integer>();
	}

	@Override
	public String toString() {
		// return this.id + "::" + this.value;
		return
		"{"+
			"\n\tid:\t  " + this.id + ",\n" +
			"\tvalue:\t  " + this.value + ",\n" +
			"\ttraded:\t  " + traded + ",\n" +
			"\taccepts: " + accepts.toString() + "\n" +
		"}";
	}
}

class Student {
	int id;
	int sandwich;
	boolean traded;
	ArrayList<Integer> accepts; // Will to accept these sandwiches

	public Student(int i, int s) {
		this.id = i; this.sandwich = s;
		traded = false;
		accepts = new ArrayList<Integer>();
	}

	@Override
	public String toString() {
		// return this.id + "::" + this.value;
		return
		"{"+
			"\n\tid:\t  " + this.id + ",\n" +
			"\ttype:\t  " + this.sandwich + ",\n" +
			"\ttraded:\t  " + traded + ",\n" +
			"\taccepts:  " + accepts.toString() + "\n" +
		"}";
	}
}

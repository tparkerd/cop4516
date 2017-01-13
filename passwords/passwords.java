import java.util.*;

public class passwords {
  public static ArrayList<String> passwordList;
  public static int callCount = 0;
  public static int passwordCount;
  public static int rank;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    // Get number of test cases
    int n = Integer.parseInt(stdin.nextLine());

    // For each test case...
    for (int i = 0; i < n; i++) {
      // Empty the password list for each case
      passwordList = new ArrayList<String>();

      // Password counter: this is used to stop solving once the desired
      // rank is reached
      passwordCount = 0;

      // Get the length of the password
      int length = Integer.parseInt(stdin.nextLine());

      // Create the array to hold all the sets
      // subsets can hold up to 26 lowercase letters
      char[][] sets = new char[length][26];

      // Get the set of letters for each position
      for (int j = 0; j < length; j++) {
        // Get set of letters and then convert to character array
        String set = stdin.nextLine();
        char[] setChars = set.toCharArray();
        sets[j] = setChars;
      }

      // Populate the list of passwords
      buildPasswords(sets, 0, new String());

      // Get rank of password
      rank = Integer.parseInt(stdin.nextLine());

      System.out.println(passwordList.get(rank - 1));
    }
  }

  // Solver function
  public static void buildPasswords(char[][] sets, int level, String str) {
    // Base case: if we already found the password at the requested rank,
    // stop solving for more passwords
    if (passwordCount > rank)
      return;


    // Base case: all sets have been used, the string is created, add to
    // the master password list

    if (level >= sets.length) {
      System.out.println(str);
      passwordList.add(str);
      passwordCount++;
      return;
    }

    // Given a valid level, make candidate prefixes using its children letters

    // Add each letter of the current set to create a candidate password
    int child = 0;
    while (child < sets[level].length) {
      buildPasswords(sets, level + 1, str + String.valueOf(sets[level][child]));
      child++;
    }
  }
}

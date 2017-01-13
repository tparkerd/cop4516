import java.util.*;

public class passwords {
  public static ArrayList<String> passwordList;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    // Get number of test cases
    int n = Integer.parseInt(stdin.nextLine());

    // For each test case...
    for (int i = 0; i < n; i++) {
      // Empty the password list for each case
      passwordList = new ArrayList<String>();

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
    System.out.printf("Level\tChild\tString\tSets[level].length\n");
      buildPasswords(sets, 0, 0, new String());

      // Get rank of password
      int rank = Integer.parseInt(stdin.nextLine());
    }
  }

  // Not sure if this should be...
  // I want to pass the set of sets of character, so I can just tick off
  // which set has been used, so after the first set of letters is used,
  // the currentSet would increment.
  public static void buildPasswords(char[][] sets, int level, int child, String str) {
    System.out.printf("%d\t%d\t%s\t", level, child, str);
    // Base case: all sets have been used, the string is created, add to
    // the master password list
    if (level >= sets.length) {
      System.out.println("Password: " + str);
      return;
    }

    System.out.printf("%c\n", sets[level][child]);
    // Base case: all children elements (letters) used in the current level
    // Start at the first child/element of the next set

    // So long as the current level has letters in its set, add them one by one
    // but make sure to move to the next level
    while (child < sets[level].length) {
      buildPasswords(sets, level, child + 1, str + String.valueOf(sets[level][child]));
    }

    // Otherwise, add the next possible letter
    // buildPasswords(sets, level + 1, 0, str + String.valueOf(sets[level][child]));
  }


}

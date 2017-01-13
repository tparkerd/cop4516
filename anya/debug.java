import java.util.*;


public class debug {
  public static boolean DEBUG = true;

  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);


    String tmp = new String("this is a test");
    String[] array = tmp.split(" ");
    System.out.println(array.length);

    // Number of cases
    int n = Integer.parseInt(stdin.nextLine());

    String testPalindrome = new String("tattarrattat");
    String reverse = new StringBuilder(testPalindrome).reverse().toString();
    System.out.println("Compare " + testPalindrome + " with " + reverse);
    System.out.println(testPalindrome.equals(reverse));

    // For each case...
    for (int i = 0; i < n; i++) {
      // Get the sentence
      String str = stdin.nextLine();
      String currentCase = (DEBUG) ? new String("Checking case: " + str) : new String();
      System.out.println(currentCase);
      // Check if there is a palindrome in the sentence
      if(containsPalindrome(str))
        System.out.println("Ay");
      else
        System.out.println("Nap");
    }
  }

  public static boolean containsPalindrome(String str) {

    // Get list of all the words in the string
    String[] words = str.split("\\s+");
    int count = 0;
    for (String word : words) {
      if (isPalindromic(word)) {
        String currentCase = (DEBUG) ? new String("Word: " + word + " is palindromic") : new String();
        System.out.println(currentCase);
        count++;
      }
    }

    System.out.println("Palindrome count for current case: " + count);

    // Remove spaces to check entire sentence
    str = str.replaceAll("\\s", "");

    return (count > 1) || isPalindromic(str);
  }

  private static boolean isPalindromic(String str) {
    String wordstr = (DEBUG) ? new String("Word: " + str) : new String();
    System.out.println(wordstr);

    // If word is too short, it's not a valid palindrome
    if (str.length() < 3) {
      String currentCase = (DEBUG) ? new String(str + " is too short") : new String();
      System.out.println(currentCase);
      return false;
    }

    // Check to see if the word is a palindrome
    // From the first character to the midpoint...
    for (int i = 0; i <= str.length() / 2; i++) {
      // From the last chracter to the midpoint...
      for (int j = str.length() - 1; j >= str.length() / 2; j--) {
        // If the end characters are not equal, it's not a palindrome
        // Otherwise, assume it is until proven otherwise
        System.out.println(str.charAt(i) + " compared to " + str.charAt(j));
        if (i >= j) {
          System.out.println("Same index");
          return true;
        }
        else if (str.charAt(i) != str.charAt(j)) {
          System.out.println("Character mismatch");
          return false;
        }
        else
          i++;
      }
    }
    // If it is never disproved, it's probably a palindrome
    return true;
  }
}

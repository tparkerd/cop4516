import java.util.Scanner;


public class anya {
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);

    // Number of cases
    int n = Integer.parseInt(stdin.nextLine());

    // For each case...
    for (int i = 0; i < n; i++) {
      // Get the sentence
      String str = stdin.nextLine();
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
      if (isPalindromic(word))
        count++;
    }

    // Remove spaces to check entire sentence
    str = str.replaceAll("\\s", "");

    return (count > 1) || isPalindromic(str);
  }

  private static boolean isPalindromic(String str) {
    // If word is too short, it's not a valid palindrome
    if (str.length() < 3)
      return false;

    // Check to see if the word is a palindrome
    // From the first character to the midpoint...
    for (int i = 0; i <= str.length() / 2; i++) {
      // From the last chracter to the midpoint...
      for (int j = str.length() - 1; j >= str.length() / 2; j--) {
        // If the end characters are not equal, it's not a palindrome
        // Otherwise, assume it is until proven otherwise
        if (i >= j) {
          return true;
        }
        else if (str.charAt(i) != str.charAt(j)) {
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

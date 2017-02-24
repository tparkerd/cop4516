import java.util.*;

public class preorder {
  public static final boolean DEBUG = true;
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    int nCases = stdin.nextInt();

    // For each case...
    for (int i = 0; i < nCases; i++) {
      // Get the array length
      int length = stdin.nextInt();

      int[] inorder = new int[length];
      // Get the inorder traversal
      for (int j = 0; j < length; j++) {
        inorder[j] = stdin.nextInt();
      }

      int[] postorder = new int[length];
      // Get the postorder traversal
      for (int j = 0; j < length; j++) {
        postorder[j] = stdin.nextInt();
      }

      if (DEBUG) System.out.println("Inorder: " + Arrays.toString(inorder));
      if (DEBUG) System.out.println("Postorder: " + Arrays.toString(postorder));


    }
  }
}

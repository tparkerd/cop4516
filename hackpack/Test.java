import java.util.*;

public class Test {
  public static void main(String[] args) {
    Scanner stdin = new Scanner(System.in);
    BST bst = new BST();

    int nCases = Integer.parseInt(stdin.nextLine());
    int[] list = new int[1];
    for (int i = 0; i < nCases; i++) {
      String[] StringList = stdin.nextLine().split(" ");
      list = new int[StringList.length];
      System.out.println(Arrays.toString(StringList));
      for (int j = 0; j < StringList.length; j++) {
        list[j] = Integer.parseInt(StringList[j]);
        bst.insert(Integer.parseInt(StringList[j]));
      }
    }



    bst.preorder();
    bst.inorder();
    bst.postorder();


  }
}

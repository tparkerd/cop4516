public class BST {
  private Node root;

  public void insert(Integer data) {
    this.root = insert(root, data);
  }

  private Node insert(Node root, Integer data) {
    if (root == null) {
      return new Node(data);

    // If smaller, insert to the left
    } else if (data < root.data) {
      root.left = insert(root.left, data);

    // Otherwise, insert to the right, and allow for duplicates
    } else {
      root.right = insert(root.right, data);
    }
    return root;
  }

  public void preorder() {
    System.out.println("Pre-order Traversal");
    preorder(this.root);
    System.out.println();

  }

  private void preorder(Node root) {
    if (root == null)
      return;

    System.out.printf("%s ", root.data);
    preorder(root.left);
    preorder(root.right);

  }

  public void inorder() {
    System.out.println("In-order Traversal");
    inorder(this.root);
    System.out.println();

  }

  private void inorder(Node root) {
    if (root == null)
      return;

    inorder(root.left);
    System.out.printf("%s ", root.data);
    inorder(root.right);

  }

  public void postorder() {
    System.out.println("Post-order Traversal");
    postorder(this.root);
    System.out.println();

  }

  private void postorder(Node root) {
    if (root == null)
      return;

    postorder(root.left);
    postorder(root.right);
    System.out.printf("%s ", root.data);

  }

  public Node peak() {
    return this.root;
  }

}

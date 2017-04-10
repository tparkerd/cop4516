public class Node implements Comparable<Node> {
  Integer data;
  Node parent, left, right, next;

  public Node(Integer data) {
    this.data = data;
    this.parent = null;
    this.left = null;
    this.right = null;
    this.next = null;
  }

  public int compareTo(Node o) {
    if (this.data > o.data)
      return 1;
    else if (this.data < o.data)
      return -1;
    else
      return 0;
  }

  public String toString() {
    return data.toString();
  }
}

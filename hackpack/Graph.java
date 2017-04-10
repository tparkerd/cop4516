import java.util.*;

public class Graph<AnyType> {
  private ArrayList<AnyType>[] list;

  @SuppressWarnings("unchecked")
  public Graph(int capacity) {
    this.list = new ArrayList[capacity];
    for (int i = 0; i < capacity; i++)
      list[i] = new ArrayList<AnyType>();
  }

  public void add(AnyType T, Integer start, Integer end) {
    this.list[start].add(end);
  }

  public static void main(String[] args) {

  }
}

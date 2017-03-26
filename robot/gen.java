import java.util.*;

public class gen {
  public static void main(String[] args) {
    int randN = (int)(Math.random() * 1001);
    randN = 1000;
    System.out.println(randN);
    ArrayList<Target> tmpList = new ArrayList<Target>();
    for (int i = 0; i < randN; i++) {
      int randX = (int)(Math.random() * 99) + 1;
      int randY = (int)(Math.random() * 99) + 1;
      int randP = (int)(Math.random() * 101) + 1;
      tmpList.add(new Target(i, randX, randY, randP));
    }

    for (Target t : tmpList) {
      System.out.println(t);
    }

    System.out.println(0);
  }
}

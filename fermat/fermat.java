import java.util.*;

public class fermat {
  public static void main(String[] args) {

    boolean[] primes = primes();
    Scanner stdin = new Scanner(System.in);
    int lower, upper;
    lower = stdin.nextInt();
    upper = stdin.nextInt();
    while (lower != -1 && upper != -1) {
      int primeCounter = 0, fermatPrimeCounter = 0;
      for (int i = lower; i <= upper; i++) {
        if (primes[i]) {
          primeCounter++;
          if (isFermatPrime(i))
          fermatPrimeCounter++;
        }
      }
      System.out.printf("%d %d %d %d\n", lower, upper, primeCounter, fermatPrimeCounter);
      lower = stdin.nextInt();
      upper = stdin.nextInt();
    }


  }

  public static boolean[] primes() {
    int max = 1000000;
    boolean[] isPrime = new boolean[max + 1];
    isPrime[0] = false;
    isPrime[1] = false;
    for (int i = 2; i <= max; i++)
      isPrime[i] = true;

    for (int i = 2; i * i <= max; i++) {
      if (isPrime[i]) {
        for (int j = i; i * j <= max; j++) {
          isPrime[j * i] = false;
        }
      }
    }
    return isPrime;
  }

  public static boolean isFermatPrime(int a) {
    if (a == 2)
      return true;

    int p = a, c = a;
    c -= 1;
    c /= 4;
    c = c * 4 + 1;

    if (c == p)
      return true;
    else
      return false;
  }
}

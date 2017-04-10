// Sean Szumlanski
// COP 3503, Summer 2016

// Cluster.java
// ============
// In this example, we write a generic class (i.e., a class that deals with
// objects of any type) that calls Collections.sort() on a collection of that
// object type. In order to call Collections.sort(), we must ensure that the
// object type being sorted implements Comparable. So, we extend our typical
// generic syntax from this:
//
//    public class MyClass<AnyType> {
//
//       Collection<AnyType> myCollection;
//
//       public void sort() {
//          Collections.sort(myCollection);  // compile-time error! what if
//       }                                   // AnyType is not Comparable?
//    }
//
// ...to this syntax, which specifies that AnyType implements Comparable:
//
//    public class MyClass<AnyType extends Comparable> {
//
//       Collection<AnyType> myCollection;
//
//       public void sort() {
//          Collections.sort(myCollection);  // it's now okay to call sort()
//       }                                   // because we know AnyType is
//    }                                      // Comparable.
//
// Now the compiler will check at compile time to ensure that any time we
// create an object of MyClass, the AnyType parameter is a class that implements
// Comparable.
//
// Notice that we used "extends Comparable" above instead of "implements
// Comparable." That's just how Java does. When we implement an interface, we
// use "implements." When dealing with type parameters like this, we drop the
// distinction between extending a superclass and implementing an interface, and
// just tell Java that the class in question "extends" whatever superclass /
// interface we're dealing with.
//
// But wait. There's one more problem. What we saw earlier is that Comparable is
// now generic in Java. So, if we create SomeClass, we don't say that it
// implements Comparable; we say that it implements Comparable<SomeClass>. So,
// let's be more specific with that type parameter attached to our new generic
// class:
//
//    public class MyClass<AnyType extends Comparable<AnyType>> {
//
//       Collection<AnyType> myCollection;
//
//       public void sort() {
//          Collections.sort(myCollection);
//       }
//    }
//
// That's the syntax we'll use here to create a generic container called a
// Cluster. The Cluster isn't really doing anything exciting in terms of how
// it's organizing data. It's just a basic container with an ArrayList inside,
// and all you can really do is put things in it and sort them, but it gets the
// point across about how to mix generics and Comparable.


import java.io.*;
import java.util.*;

public class Cluster<AnyType extends Comparable<AnyType>> {

	// we'll store our elements in an ArrayList
	private ArrayList<AnyType> myList;

	// the constructor creates an ArrayList with initial size 'initCapacity'
	Cluster(int initCapacity) {
		myList = new ArrayList<AnyType>(initCapacity);
	}

	// add new objects to the Cluster's ArrayList
	public void put(AnyType myObject) {
		myList.add(myObject);
	}

	// sort the objects in the Cluster's ArrayList
	public void sort() {
		Collections.sort(myList);
	}

	// print the objects in the Cluster's ArrayList
	void print() {
		for (AnyType a : myList)
			System.out.println(a);
	}

	public static void main(String [] args) {

		// create a new Cluster object
		Cluster<Person> myCluster = new Cluster<Person>(4);

		// throw some stuff into the Cluster
		myCluster.put(new Person("Cada St-Merrein", "04/22/1961"));
		myCluster.put(new Person("Regulus Arcturus Black", "01/30/1961"));
		myCluster.put(new Person("Perceval Thoreau", "08/08/1450"));
		myCluster.put(new Person("Magdeleine Corriander Grabb", "05/19/1960"));

		// print the Cluster's contents
		System.out.println("Unsorted Cluster:");
		System.out.println("=================");
		myCluster.print();

		// sort the Cluster; it's a good thing Person objects are Comparable ;)
		myCluster.sort();

		// print the Cluster's contents (now sorted)
		System.out.println();
		System.out.println("Sorted Cluster:");
		System.out.println("===============");
		myCluster.print();
	}
}

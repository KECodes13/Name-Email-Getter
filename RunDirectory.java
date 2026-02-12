package cs340a1;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Iterator;

/*
 * Reads from a file given, and will print the last names, first names, and email address of all names in the file in alphabetical order
 * 
 */
public class RunDirectory {
	
	public static void main(String[] args) throws FileNotFoundException {
		if(args.length != 1) {
			System.err.print("File Not Found");
			System.exit(1);
		}
		FileReader f = new FileReader(args[0]); // file stuff
		Scanner s = new Scanner(f);
		LinkedList[] list = new LinkedList[26]; // new linked list class
		
		
		/*
		 * adding a new linked list instance to every index in array
		 */
		
		for(int i = 0; i < list.length; i++) {
			list[i] = new LinkedList();
		}

		// stop reading when encountering a , or \n
		s.useDelimiter(",|\\n");
		
		String lastName = "", firstName = "", email = "";
		
		/*
		 * read the first, last, email
		 * create new instant with previously read data
		 * sort and add to sorted spot
		 */
		while(s.hasNext()) {
			lastName = s.next();
			firstName = s.next();
			email = s.next();
			
			Person p = new Person(lastName, firstName, email);
			
			int index = p.getLastName().charAt(0) - 'A';
			
			if(index >= 0 && index < 26) {
				list[index].add(p);
			}
		

		}
				
				
		s.close();
		
		// final printing
		for(int i = 0; i < list.length; i++) {
			char letter = (char)('A' + i);
			System.out.print(letter + "\n");
			
			for(Person per : list[i]) {
				System.out.println("   " + per);

			}
			
		}
		
	}
}
class Person { // creating person class
	private String first_name;
	private String last_name;
	private String email_address;
	
	public Person(String last, String first, String email) { // constructor to assign names and email
		first_name = first;
		last_name = last;
		email_address = email;

	}
	public String getFirstName() {
		return first_name;
	}
	public String getLastName() {
		return last_name;
	}
	public String getEmail() {
		return email_address;
	}
	
	public int compare(Person p) { // comparing last names
		if (this.last_name.compareTo(p.last_name) == 0) { // they are equal
			return 0;
		}
		else if (this.last_name.compareTo(p.last_name) < 0) { // String 1 is less then String 2
			return -1;
		} else { // String 1 is ahead of String 2 in alphabet 'c' compared to 'd'
			return 1;
		}
	}
	
	public String toString() { // make person into a string
	    return last_name + ", " + first_name + ", " + email_address;
	}
}
/*
 * Self implemented Linked List that sorts by last name
 * Implements an iterator for performance when reading each name
 */
class LinkedList implements Iterable<Person> { // self implemented linked list
	Node head;
	Node curr;
	int length;
	
	public LinkedList() {
		this.head = null;
		length = 0;
		
	}
	
	public void add(Person p) { // adding to linked list sorted alphabetically by last name
		Node newNode = new Node(p);
		
		if (head == null) {
			head = newNode;
		}
		else if (newNode.data.compare(head.data) <= 0) {
			newNode.next = head;
			head = newNode;
		}
		else {
			Node prev = null;
			curr = head;
			while(curr != null && p.compare(curr.data) > 0) {
				prev = curr;
				curr = curr.next;
			}
			if (prev != null) {
				prev.next = newNode;
			}
			newNode.next = curr;
			
			length++;
		}
			
	}
	public Iterator<Person> iterator() {
		return new LLIterator();
	}
	
	private class Node {
		private Node next;
		private Person data;
		
		public Node(Person data) {
			this.data = data;
			this.next = null;
		}
		
		public boolean hasNextName() {
			if(next != null) {
				return true;
			}
			return false;
		}
		
	}
	private class LLIterator implements Iterator<Person> {
		private Node curr;
		
		public LLIterator() {
			this.curr = head;
		}
		public boolean hasNext() {
			return curr != null;
		}
		public Person next() {
			Person x = curr.data;
			curr = curr.next;
			return x;
		}
	}
	
}
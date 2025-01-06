package rentalsystem;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	static Scanner scanner = new Scanner(System.in);
	static String input = "";
	static ArrayList<MediaItem> myCollection = new ArrayList<>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//creating a predefined collection

		
		//populating the collection
		myCollection.add(new Book("Maugli", 120));
		myCollection.add(new Book("English express", 300));
		myCollection.add(new DVD("This is life", 65));
		myCollection.add(new DVD("Animals", 340));
		
		System.out.println(findMediaItem("book", "Maugli"));
		
		int choice = 0;
		boolean exitMenu = false;
		
		//user menu
		while (!exitMenu) {
			System.out.println("-".repeat(50));
			System.out.println("Choose an option from the following list:");
			System.out.println("To VIEW ITEMS enter 1");
			System.out.println("To RENT ITEMS enter 2");
			System.out.println("To RETURN ITEMS enter 3");
			System.out.println("To ADD ITEMS TO THE LIST enter 4");
			System.out.println("To QUIT THE MENU enter something else: ");
			System.out.println("-".repeat(50));
			System.out.print("YOUR CHOICE: ");
			System.out.println();

			input = scanner.nextLine().toUpperCase();

			try {
				choice = Integer.parseInt(input);
				System.out.println("choice = " + choice);
				switch (choice) {
				case 1 -> printItems(myCollection);
				case 2 -> {
					doRentAndReturnMenu(true);
				}
				case 3 -> {
					doRentAndReturnMenu(false);
				}
				case 4 -> {
					doAddItemMenu();
				}
				default -> {
					exitMenu = true;
					System.out.println("Exiting the menu...");
				}
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Exiting the menu...");
				System.out.println("Input = " + input);
				exitMenu = true;
			}		
			}
	}

public static void printItems(ArrayList<MediaItem> array) {
	//printing all the items
	System.out.println("-".repeat(50));
	for (MediaItem item : array) {
		item.getDetails();
	}
}

public static void doRentAndReturnMenu(boolean isRent) {
	
	String itemTitle;
	String personName;
	MediaItem myItem; 
	
	System.out.println("-".repeat(50));
	System.out.println(isRent ? "Entered the renting menu" : "Entered the return menu");
	System.out.println("Choose what type of an item you want to " 
	+ (isRent ? "rent ": "return ") + "book or DVD: ");
	input = scanner.nextLine().toUpperCase();
	if (!input.equalsIgnoreCase("book") && !input.equalsIgnoreCase("dvd")) {
		System.out.println("Neither a book nor a DVD selected. "
				+ "Returning to the main menu...");
		return;
	} else {
		System.out.println("-".repeat(50));
		System.out.println("A " + input +  " is selected!");
		System.out.println("Enter a title of the item: ");
		itemTitle = scanner.nextLine().toUpperCase();
		System.out.println("-".repeat(50));
		System.out.println("The title is " + itemTitle);
		
		//searching for the book entered in MyCollection
		myItem = findMediaItem(input, itemTitle);
		if (myItem == null) {
			System.out.println("No such a " + input + "!");
			return;
		} else {
			System.out.println("-".repeat(50));
			System.out.println("Enter your name: ");
			personName = scanner.nextLine().toUpperCase();
			
			if (isRent) 
			{
				myItem.rent(personName);
				} else {
					myItem.returnItem(personName);
				}
		}
	}
}

public static void doAddItemMenu() {
	
	String itemTitle;
	MediaItem myItem; 
	int pagesOrRuntime;
	String pagesOrRuntimeString;
	
	System.out.println("-".repeat(50));
	System.out.println("Entered the items adding menu...");
	System.out.println("Enter the type of an item to add: book or DVD: ");
	input = scanner.nextLine();
	System.out.println();
	if (!input.equalsIgnoreCase("book") && !input.equalsIgnoreCase("dvd")) {
		System.out.println("Neither a book nor a DVD selected. "
				+ "Returning to the main menu...");
		return;
	} else {
		System.out.println("-".repeat(50));
		System.out.println("A " + input +  " is selected!");
		System.out.println("Enter a title of the item: ");
		itemTitle = scanner.nextLine().toUpperCase();
		System.out.println("-".repeat(50));
		System.out.println("The title is " + itemTitle);
		
		//searching for the book entered in MyCollection
		myItem = findMediaItem(input, itemTitle);
		System.out.println("-".repeat(50));
		if (myItem != null) {
			System.out.println("Such a " + input + " already exists!");
			return;
		} else {
			System.out.println("Enter " 
					+ (input.equalsIgnoreCase("book") ? " a number of pages: "
							: "the record length"));
			pagesOrRuntimeString = scanner.nextLine();
			try {
				pagesOrRuntime = Integer.parseInt(pagesOrRuntimeString);
				if (input.equalsIgnoreCase("book")) {
					myCollection.add(new Book(itemTitle, pagesOrRuntime));
				} else {
					myCollection.add(new DVD(itemTitle, pagesOrRuntime));
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Not an integer number entered");
			}	
		}
	}
}

public static MediaItem findMediaItem(String itemType, String titleComparison) {
	for (MediaItem item : myCollection) {
		if (item.getTitle().equalsIgnoreCase(titleComparison)
&& item.getClass().getSimpleName().equalsIgnoreCase(itemType)) {
			return item;
		} 
	}
	return null;
}
}
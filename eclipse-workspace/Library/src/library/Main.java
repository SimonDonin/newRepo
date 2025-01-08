package library;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	static ArrayList<Book> myCollection  = new ArrayList<>();
	static Scanner scan = new Scanner(System.in);
	static String inputString = "";
	static int inputInt = -1;
	static boolean isMenuExit = false;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		myCollection.add(new PaperBook("THE STORY", new Author("S. KING", "born, lived and died"), "A53"));
		myCollection.add(new PaperBook("THE GAME OF THRONES", new Author("G. MARTIN"), "B12"));
		myCollection.add(new EBook("HARRY POTTER", new Author("J. ROLLING"), 345.5));
		
		printMyCollection();
		
		while (!isMenuExit) {
			printUserMenu();
			inputString = scan.nextLine();
			inputInt = tryStringToInt(inputString);
			
			if (inputInt == -1) {
				continue;
			}
			
			switch (inputInt) {
			case 1 -> createBook();
			case 2 -> rentBook();
			case 3 -> returnBook();
			case 5 -> printMyCollection();
			default -> {
				isMenuExit = true;
				System.out.println("Exiting the menu...");
			}
			}
		}
		
	}


	private static boolean returnBook() {
		// TODO Auto-generated method stub
		Book myBook;
		String bookType;
		String bookTitle;
		String bookAuthor;
		
		// TODO Auto-generated method stub
		System.out.println("-".repeat(50) + "\n"
				+ "Book returning menu is chosen..." + "\n" + "\n"
				+ "Please choose the type of the book to return:" + "\n"
				+ "   - Paper book - choose 1" + "\n"
				+ "   - EBook - choose 2" + "\n"
				+ "YOUR CHOICE: " );
		inputString = scan.nextLine();
		inputInt = tryStringToInt(inputString);
		
		if (inputInt == 1) {
			bookType = "PaperBook";
		} else if (inputInt == 2) {
			bookType = "EBook";
		} else {
			System.out.println("The value entered is not correct. Returning to the main menu...");
			return false;
		}
		
		System.out.println("-".repeat(50) + "\n"
				+ "The " + bookType + " is chosen" + "\n");
		
		System.out.println("-".repeat(50) + "\n"
				+ "Please enter a book title: " + "\n"
				+ "The title: " );
		bookTitle = scan.nextLine();
		
		if (bookTitle.isBlank()) {
			System.out.println("A book title can't be blank" + "\n"
					+ "Returning to the main menu...");
			return false;
		}
		
		System.out.println("-".repeat(50) + "\n"
				+ "Please enter a book author: " + "\n"
				+ "The author: " );
		bookAuthor = scan.nextLine();
		
		if (bookAuthor.isBlank()) {
			System.out.println("A book author can't be blank" + "\n"
					+ "Returning to the main menu...");
			return false;
		}
		
		myBook = findBook(bookTitle, bookAuthor, bookType);
		
		if (myBook == null) {
			System.out.println("Such a " + bookType + " with title " + bookTitle
					 + " and author " + bookAuthor + " wasn't found");
			return false;
		}
		
		if (myBook.isAvailable()) {
			System.out.println("Such a book with title " + bookTitle
					 + " and author " + bookAuthor + " is not rented yet");
			return false;
		}
		
		myBook.returnBook();
		return true;
	}


	private static boolean rentBook() {
		
		Book myBook;
		String bookType;
		String bookTitle;
		String bookAuthor;
		
		// TODO Auto-generated method stub
		System.out.println("-".repeat(50) + "\n"
				+ "Book renting menu is chosen..." + "\n" + "\n"
				+ "Please choose the type of the book to rent:" + "\n"
				+ "   - Paper book - choose 1" + "\n"
				+ "   - EBook - choose 2" + "\n"
				+ "YOUR CHOICE: " );
		inputString = scan.nextLine();
		inputInt = tryStringToInt(inputString);
		
		if (inputInt == 1) {
			bookType = "PaperBook";
		} else if (inputInt == 2) {
			bookType = "EBook";
		} else {
			System.out.println("The value entered is not correct. Returning to the main menu...");
			return false;
		}
		
		System.out.println("-".repeat(50) + "\n"
				+ "The " + bookType + " is chosen" + "\n");
		
		System.out.println("-".repeat(50) + "\n"
				+ "Please enter a book title: " + "\n"
				+ "The title: " );
		bookTitle = scan.nextLine();
		
		if (bookTitle.isBlank()) {
			System.out.println("A book title can't be blank" + "\n"
					+ "Returning to the main menu...");
			return false;
		}
		
		System.out.println("-".repeat(50) + "\n"
				+ "Please enter a book author: " + "\n"
				+ "The author: " );
		bookAuthor = scan.nextLine();
		
		if (bookAuthor.isBlank()) {
			System.out.println("A book author can't be blank" + "\n"
					+ "Returning to the main menu...");
			return false;
		}
		
		myBook = findBook(bookTitle, bookAuthor, bookType);
		
		if (myBook == null) {
			System.out.println("Such a " + bookType + " with title " + bookTitle
					 + " and author " + bookAuthor + " wasn't found");
			return false;
		}
		
		if (!myBook.isAvailable()) {
			System.out.println("Such a book with title " + bookTitle
					 + " and author " + bookAuthor + " is already rented");
			return false;
		}
		
		System.out.println("-".repeat(50) + "\n"
				+ "Please enter the name of a person who rents the book: " + "\n"
				+ "The name: " );
		inputString = scan.nextLine();
		
		myBook.rentBook(inputString);
		return true;
	}

	public static void printMyCollection() {
		
		System.out.println("-".repeat(50));
		for (Book book : myCollection) {
			book.displayDetails();
		}
	}
	
	public static void printUserMenu() {
		
		System.out.println("-".repeat(50) + "\n"
				+ "The available options are the following:" + "\n"
				+ "   - to CREATE A BOOK choose 1" + "\n"
				+ "   - to RENT A BOOK choose 2" + "\n"
				+ "   - to RETURN A BOOK choose 3" + "\n"
				+ "   - to DISPLAY BOOK DETAILS choose 5" + "\n"
				+ "   - to EXIT choose 6" + "\n"
				+ "-".repeat(50) + "\n" + "\n"
				+ "YOUR CHOICE: " );
	}
	
	public static Book findBook(String title, String authorName, String BookType) {
		
		for (Book book : myCollection) {
			if (book.getTitle().equalsIgnoreCase(title) 
					&& book.getAuthor().getName().equalsIgnoreCase(authorName)
					&& book.getClass().getSimpleName().equalsIgnoreCase(BookType)) {
				return book;
			}
		}
		return null;
	}
	
	public static boolean createBook() {
		
		Book myBook;
		String bookType;
		String bookTitle;
		String bookAuthor;
		double inputDouble;
		
		System.out.println("-".repeat(50) + "\n"
				+ "Book creating menu is chosen..." + "\n" + "\n"
				+ "Please choose the type of the book:" + "\n"
				+ "   - Paper book - choose 1" + "\n"
				+ "   - EBook - choose 2" + "\n"
				+ "YOUR CHOICE: " );
		inputString = scan.nextLine();
		inputInt = tryStringToInt(inputString);
		if (inputInt == 1) {
			bookType = "PaperBook";
		} else if (inputInt == 2) {
			bookType = "EBook";
		} else {
			System.out.println("The value entered is not correct." + "\n"
					+ "Returning to the main menu...");
			return false;
		}
		
		System.out.println("-".repeat(50) + "\n"
				+ "The " + bookType + " is chosen" + "\n");
				
		System.out.println("-".repeat(50) + "\n"
				+ "Please enter a book title: " + "\n"
				+ "The title: " );
		bookTitle = scan.nextLine();
		
		if (bookTitle.isBlank()) {
			System.out.println("A book title can't be blank" + "\n"
					+ "Returning to the main menu...");
			return false;
		}
		
		System.out.println("-".repeat(50) + "\n"
				+ "Please enter a book author: " + "\n"
				+ "The author: " );
		bookAuthor = scan.nextLine();
		
		if (bookAuthor.isBlank()) {
			System.out.println("A book author can't be blank" + "\n"
					+ "Returning to the main menu...");
			return false;
		}
		
		myBook = findBook(bookTitle, bookAuthor, bookType);
		
		if (myBook != null) {
			System.out.println("-".repeat(50) + "\n"
					+ "Such a " + bookType + " with title " + bookTitle
					+ " and author " + bookAuthor + " already exists" + "\n"
					+ "Returning to the main menu...");
			return false;
		}
		
		if (bookType.equalsIgnoreCase("PaperBook")) {
			System.out.println("-".repeat(50) + "\n"
					+ "Please enter a place on the shelf: " + "\n"
					+ "The place: " );
			inputString = scan.nextLine();
			
			if (inputString.isBlank()) {
				System.out.println("A place on the shelf can't be blank" + "\n"
						+ "Returning to the main menu...");
				return false;
			}
			
			myCollection.add(new PaperBook(bookTitle, new Author(bookAuthor), inputString));
			System.out.println("The " + bookType + " " + "\"" + bookTitle 
					+ "\" by " + bookAuthor + " is created successfully");
		} else {
			System.out.println("-".repeat(50) + "\n"
					+ "Please enter the size of the book: " + "\n"
					+ "The size: " );
			inputString = scan.nextLine();
			
			if (inputString.isBlank()) {
				System.out.println("A book size can't be blank" + "\n"
						+ "Returning to the main menu...");
				return false;
			}
			
			inputDouble = tryStringToDouble(inputString);
			if (inputDouble == -1.0) {
				System.out.println("A book size is not correct");
				return false;
			} else if (inputDouble <= 0) {
				System.out.println("A book size can't be not positive");
				return false;
			} else if (inputDouble >= 20000) {
				System.out.println("A book size can't be too big");
				return false;
			} else {
				myCollection.add(new EBook(bookTitle, new Author(bookAuthor), inputDouble));
				System.out.println("The " + bookType + " " + "\"" + bookTitle 
						+ "\" by " + bookAuthor + " is created successfully");
				}
		}
		
		return true;
	}
	
	public static int tryStringToInt(String myInput) {
		
		int inputInt = 0;
		
		try {
			inputInt = Integer.parseInt(myInput);
			return inputInt;
			} catch (NumberFormatException nfe) {
				System.out.println("\n" + "The number entered is not integer");
				return -1;
					}
	}
	
public static double tryStringToDouble(String myInput) {
		
		double inputDouble = 0.0;
		
		try {
			inputDouble = Double.parseDouble(myInput);
			return inputDouble;
			} catch (NumberFormatException nfe) {
				System.out.println("\n" + "The number entered is not double");
				return -1.0;
					}
	}
	
}

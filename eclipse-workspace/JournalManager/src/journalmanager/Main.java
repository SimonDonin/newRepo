package journalmanager;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	static JournaManager myJournalManager = new JournaManager();
	static final String line = "-".repeat(60);

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		String inputString;
		int inputInt;

		System.out.println("Welcome to The Journal Manager Program!");

		try (Scanner scan = new Scanner(System.in);) {
			while (true) {
				printUserMenu();
				inputInt = scan.nextInt();
				scan.nextLine();
				System.out.println("Your answer was \"" + inputInt + "\"");
				switch (inputInt) {
				case 1 -> createJournal(scan);
				case 2 -> deleteJournal(scan);
				case 3 -> createEntry(scan);
				case 4 -> deleteEntry(scan);
				case 5 -> printEntry(scan);
				case 6 -> printJournal(scan);
				case 7 -> serializeJournal(scan);
				case 8 -> deserializeJournal(scan);
				case 9 -> printJournalsList();
				case 10 -> {
					System.out.println("Exiting the application...");
					System.exit(0);
				}
				default -> {
					System.out.println("Not a correct choice. Please, try again.");
				}
				}

			}
			/*
			 * myJournalManager.createJournal("testJournaL");
			 * myJournalManager.createEntry("testJournaL", "first Entry",
			 * "some content\nand a little more!..");
			 * myJournalManager.createEntry("testJournaL", "2nd Entry",
			 * "another content\nand also more!..");
			 * myJournalManager.createEntry("testJournaL", "fiNal entrY",
			 * "fiNAL content\n\tand finally the end!");
			 * //myJournalManager.deleteJournal("TestJournal"); File myJ =
			 * myJournalManager.findJournal("testjournal"); System.out.println(myJ);
			 * myJournalManager.createEntry("testJournaL", "OldEntry",
			 * "2Something like first" +"line\n2and the second one...");
			 * myJournalManager.createEntry("testJournaL", "newEntry",
			 * "Something like first" +"line\n and the second one...");
			 */
			// myJournalManager.printJournal("testJournal");
			// myJournalManager.saveJournalAsBytes("testjournal", "bytesArchive");
			// myJournalManager.loadJournalFromBytes("bytesArchive", "testJournal");

			// myJournalManager.printJournal("testJournal");
		}
	}

	private static void createJournal(Scanner scan) {
		String inputString;
		System.out.println("""
				"CREATE a JOURNAL" menu was chosen!
				Please, enter a journal's name: """);
		inputString = scan.nextLine();
		System.out.println("Your answer was \"" + inputString + "\"");
		//scan.next();
		myJournalManager.createJournal(inputString);
	}

	private static void deleteJournal(Scanner scan) {
		String inputString;
		System.out.println("""
				"DELETE a JOURNAL" menu was chosen!
				Please, enter a journal's name: """);
		inputString = scan.nextLine();
		System.out.println("Your answer was \"" + inputString + "\"");
		//scan.next();
		myJournalManager.deleteJournal(inputString);
	}

	private static Object createEntry(Scanner scan) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Object deleteEntry(Scanner scan) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Object printEntry(Scanner scan) {
		// TODO Auto-generated method stub
		return null;
	}

	private static void printJournal(Scanner scan) {
		String inputString;
		System.out.println("""
				"PRINT a JOURNAL" menu was chosen!
				Please, enter a journal's name: """);
		inputString = scan.nextLine();
		System.out.println("Your answer was \"" + inputString + "\"");
		myJournalManager.printJournal(inputString);

	}

	private static Object serializeJournal(Scanner scan) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Object deserializeJournal(Scanner scan) {
		// TODO Auto-generated method stub
		return null;
	}

	private static void printJournalsList() {
		String inputString;
		System.out.println("\"PRINT a JOURNALS LIST\" menu was chosen!");
		myJournalManager.printJournalsList();
	}
	
	private static void printUserMenu() {
		System.out.println(line + """
				\nPlease, enter a following number to perform the desired action:
				\t- 1 - TO CREATE a JOURNAL
				\t- 2 - TO DELETE a JOURNAL
				\t- 3 - TO CREATE an ENTRY INSIDE a JOURNAL
				\t- 4 - TO DELETE an ENTRY FROM a JOURNAL
				\t- 5 - TO PRINT an ENTRY's CONTENT
				\t- 6 - TO PRINT a JOURNAL
				\t- 7 - TO SAVE a JOURNAL as a SERIALIZED BYTE STREAM (archives)
				\t- 8 - TO LOAD a JOURNAL from a SERIALIZED FILE (archives)
				\t- 9 - TO PRINT a JOURNALS LIST
				\t- 10 - TO EXIT the APPLICATION\n
				YOUR CHOICE:""");
	}
}

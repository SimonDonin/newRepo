package journalmanager;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class JournaManager {
	private final static String ROOT_DIRECTORY = "C://Users//sdoni//eclipse-workspace//base//journalManagerProject";
	private final static String BITE_ARCHIVE_DIRECTORY = "bytesArchive";
	private static String line = "-".repeat(60);

	// Creates a new journal (folder)
	public void createJournal(String journalName) {
		File journal = new File(ROOT_DIRECTORY, journalName);
		if (journal.mkdirs()) {
			System.out.println("Journal " + journalName + " was created succesfully");
			return;
		}
		System.out.println("Journal " + journalName + " already exists");
	}

	// Deletes a journal and all its entries
	public void deleteJournal(String journalName) {
		File journal = new File(ROOT_DIRECTORY, journalName);
		if (journal.delete()) {
			System.out.println("Journal " + journalName + " was deleted succesfully");
			return;
		}
		System.out.println("Journal " + journalName + " doesn't exist");
	}

	// searches for a journal by the name
	public File findJournal(String journalName) {
		File journal = new File(ROOT_DIRECTORY, journalName);
		if (journal.isDirectory()) {
			return journal;
		}
		return null;
	}

	// Creates a new text entry in a journal. If already exists, overwrites its
	// content
	public void createEntry(String journalName, String entryName, String content) {
		createJournal(journalName);
		Entry entryFound = findEntry(journalName, entryName);
		if (entryFound != null) {
			if (entryFound.getContent().equalsIgnoreCase(content)) {
				System.out.println(
						"Journal " + journalName + " already has entry " + entryName + " with the same content");
				return;
			}
		}

		File file = new File(ROOT_DIRECTORY, journalName + "\\" + entryName + ".ser");
		try {
			System.out.println(file.createNewFile());
			try (FileOutputStream fileOutStream = new FileOutputStream(file);
					ObjectOutputStream outStream = new ObjectOutputStream(fileOutStream);) {
				outStream.writeObject(new Entry(entryName, content));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// searches the entry in a journal by the entry name
	public Entry findEntry(String journalName, String journalEntry) {
		File journal = new File(ROOT_DIRECTORY, journalName);
		if (!journal.isDirectory()) {
			System.out.println("No such a journal " + journalName + "!");
		}
		File[] filesInside = journal.listFiles();
		if (filesInside.length == 0) {
			System.out.println("Journal " + journalName + " is empty");
		}
		for (File file : filesInside) {
			if (file.getName().equalsIgnoreCase(journalEntry)) {
				try (FileInputStream fileInStream = new FileInputStream(file);
						ObjectInputStream inStream = new ObjectInputStream(fileInStream);) {
					return (Entry) inStream.readObject();
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public void deleteEntry(String journalName, String entryName) {
		Entry entryFound = findEntry(journalName, entryName);
		if (entryFound == null) {
			System.out.println("Journal \" + journalName + \" has no the entry \" + entryName + \"!");
		}
		File file = new File(ROOT_DIRECTORY, journalName + "\\" + entryName + ".ser");
		if (file.delete()) {
			System.out.println("Entry \"" + entryName + "\" was deleted successfully");
		} else {
			System.out.println("Didn't manage to delete entry \"" + entryName + "\"");
		}
	}

	public void printEntry(String journalName, String entryName) {
		Entry entryFound = findEntry(journalName, entryName);
		if (entryFound == null) {
			System.out.println("Journal \"" + journalName + "\" has no entry \"" + entryName + "\"!");
		} else {
			System.out.println(
					"Entry \"" + entryName + "\" has the following content:\n\t" + entryFound.getContent() + "\n");
		}
	}

	public void printJournal(String journalName) {
		File journal = findJournal(journalName);
		if (journal == null) {
			System.out.println("There is no such a journal + \"" + journalName + "\"!");
		} else {
			String[] entryNames = journal.list();
			if (entryNames.length == 0) {
				System.out.println(line + "\nJournal " + journalName + " is empty");
			} else {
				System.out.println(line + "\nJournal \"" + journalName + "\" consists of the following entries:\n");
				for (String entryName : entryNames) {
					printEntry(journalName, entryName);
				}
			}
		}
	}

	public void printJournalsList() {
		File rootDir = new File(ROOT_DIRECTORY);
		if (!rootDir.isDirectory()) {
			if (!rootDir.mkdirs()) {
				System.out.println("Unable to create a root folder \"" + rootDir.getAbsolutePath() + "\"");
				return;
			}
		}
		String[] journalsList = rootDir.list();
		if (journalsList.length == 0) {
			System.out.println("There no journals so far");
			return;
		}
		StringBuilder str = new StringBuilder();
		str.append(line + "\nThe list of the journals is following:");
		for (String journalName : journalsList) {
			if (!journalName.equalsIgnoreCase(BITE_ARCHIVE_DIRECTORY)) {
				str.append("\n\t- " + journalName);
			}
		}
		System.out.println(str);
	}

	// Serializes and saves a journal as byte data
	public void saveJournalAsBytes(String journalName, String journalPath) throws IOException {
		// check if the journal exists
		File journalFound = findJournal(journalName);
		if (journalFound == null) {
			System.out.println("There is no such a journal + \"" + journalName + "\"!");
			return;
		}

		// check if the journal is empty
		String[] entryNames = journalFound.list();
		if (entryNames.length == 0) {
			System.out.println("Journal " + journalName + " is empty");
			return;
		}

		// check if the journalPath exists and create it if not
		File dirJournalPath = new File(ROOT_DIRECTORY, journalPath);
		if (!dirJournalPath.isDirectory()) {
			if (!dirJournalPath.mkdirs()) {
				System.out.println("Unable to create a folder \"" + journalName + "\\" + journalPath);
				return;
			}
		}

		// create a byte file for archived journal
		File journalSerialized = new File(ROOT_DIRECTORY, journalPath + "\\" + journalName + ".byte");
		if (!journalSerialized.isFile()) {
			try {
				if (!journalSerialized.createNewFile())
					System.out.println("Unable to create a byte file + \"" + journalName + "\".byte!");
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// create out streams for exporting journal's entries into the byte file
		try (FileOutputStream fileOutStream = new FileOutputStream(journalSerialized);
				ObjectOutputStream outStream = new ObjectOutputStream(fileOutStream);) {

			// cycle over all the entries of the journal: serialize each of the entries into
			// a single .byte file
			for (String entryName : entryNames) {
				Entry entryFound = findEntry(journalName, entryName);

				// if wasn't able to find an entry by entryName
				if (entryFound == null) {
					System.out.println("Unable to find an entry with name \"" + entryName + "\"!");
					continue;
				}

				// serialize entry found into the byte file
				outStream.writeObject(entryFound);
			}
		}

		// successful ending feedback
		System.out.println("Journal \"" + journalName + "\" was serialized into the file \"" + journalName
				+ ".byte\" successfully");

	}

	// Serializes and saves a journal as byte data
	public void loadJournalFromBytes(String journalPath, String journalName)
			throws IOException, ClassNotFoundException {
		// check if a source byte file exists
		File journalSerialized = new File(ROOT_DIRECTORY, journalPath + "\\" + journalName + ".byte");
		if (!journalSerialized.isFile()) {
			System.out.println("Unable to find a source byte file \"" + ROOT_DIRECTORY + "\\" + journalPath + "\\"
					+ journalName + ".byte");
			return;
		}

		// check if the journal exists and if not - create one
		File journalForImport = new File(ROOT_DIRECTORY, journalName);
		if (!journalForImport.isDirectory()) {
			if (!journalForImport.mkdirs()) {
				System.out.println("Unable to create a folder \"" + ROOT_DIRECTORY + "\\" + journalName + "\"");
				return;
			}
		}

		// check if the journal for import is empty, if not - clear it out
		File[] filesInside = journalForImport.listFiles();
		if (filesInside.length != 0) {
			for (File file : filesInside) {
				file.delete();
			}
		}

		// if wasn't able to clear out the destination journal - notify user and return
		/*
		 * filesInside = journalForImport.listFiles(); if (filesInside.length != 0) {
		 * System.out.println("Unable to delete entries in the destination journal \""
		 * +journalName + "\""); return; }
		 */

		// create input streams for importing entries into the destination journal
		try (FileInputStream fileInStream = new FileInputStream(journalSerialized);
				ObjectInputStream inStream = new ObjectInputStream(fileInStream);) {

			// cycle over all the entries in the source byte file: deserialize each of the
			// entries into a single entry file
			while (true) {
				try {
					Entry entryImported = (Entry) inStream.readObject();

					// create a file for saving the entry being imported
					File fileToSave = new File(ROOT_DIRECTORY,
							journalName + "\\" + entryImported.getEntryName() + ".ser");
					if (!fileToSave.createNewFile()) {
						System.out.println("Unable to create a file\"" + ROOT_DIRECTORY + "\"\\" + journalName + "\"");
						return;
					}

					// create out streams for saving each entry separately in the destination
					// journal
					FileOutputStream fileOutStream = new FileOutputStream(fileToSave);
					ObjectOutputStream outStream = new ObjectOutputStream(fileOutStream);

					// deserialize the entry into the file
					outStream.writeObject(entryImported);
				} catch (EOFException e) {
					break;
				}
			}

			// successful ending feedback
			System.out.println("Journal \"" + journalName + "\" was deserialized successfully");
		}
	}

}
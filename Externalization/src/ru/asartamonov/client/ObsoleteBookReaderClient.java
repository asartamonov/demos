package ru.asartamonov.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import ru.asartamonov.domain.Book;

public class ObsoleteBookReaderClient {

	public static void main(String[] args) {
		FileInputStream fInputStream;
		ObjectInputStream oInputStream;
		Book failedToReadBook; // we will read book with obsolete author's serialVersionUID
		
		try {
			fInputStream = new FileInputStream("obsoleteBook.ser"); // throws FileNotFoundException
			oInputStream = new ObjectInputStream(fInputStream); // throws IOException
			
			failedToReadBook = (Book) oInputStream.readObject(); // throws ClassNotFoundException
			
			fInputStream.close();
			oInputStream.close();
			
			System.out.println(failedToReadBook);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

/*
 * Output:
 * 
 * java.io.InvalidClassException: ru.asartamonov.domain.Author; local class incompatible: stream classdesc serialVersionUID = -2022913685447852709, local class serialVersionUID = -2032913685447852709
 * 
 * */

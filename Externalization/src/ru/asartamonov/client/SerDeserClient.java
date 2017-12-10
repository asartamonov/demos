package ru.asartamonov.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

import ru.asartamonov.domain.Author;
import ru.asartamonov.domain.Book;

public class SerDeserClient {
	
	public static void main(String[] args) {
		Book bookSerialized = new Book();
		bookSerialized.setAuthor(new Author("Alex Cook", LocalDate.of(1988, 10, 12)));
		bookSerialized.setName("How to make it right?");

		FileOutputStream fOutputStream;
		ObjectOutputStream oOutputStream;

		/* Plain writing author - serializable, book externalizable */
		try {
			fOutputStream = new FileOutputStream("book.ser");
			oOutputStream = new ObjectOutputStream(fOutputStream);
			oOutputStream.writeObject(bookSerialized);
			
			oOutputStream.close();
			fOutputStream.close();

			System.out.println("Serialized book: " + bookSerialized);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/* Plain reading author - serializable, book externalizable */
		Book bookDeserialized;
		
		try {
			FileInputStream fInputStream = new FileInputStream("book.ser"); // throws FileNotFoundException
			ObjectInputStream oInputStream = new ObjectInputStream(fInputStream); // throws IOException
			bookDeserialized = (Book) oInputStream.readObject();  // throws ClassNotFoundException

			oInputStream.close();
			fInputStream.close();
			
			System.out.println("Deserialized book: " + bookDeserialized);
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
 * Serialized book: Book [name=How to make it right?, author=Author [born=1988-10-12, name=Alex Cook]]
 * Deserialized book: Book [name=How to make it right?, author=Author [born=1988-10-12, name=Alex Cook]]
 * 
 * */

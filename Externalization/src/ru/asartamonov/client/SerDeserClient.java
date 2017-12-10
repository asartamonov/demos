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
		Book book = new Book();
		book.setAuthor(new Author("Alex Cook", LocalDate.of(1988, 10, 12)));
		book.setName("How to make it right?");

		FileOutputStream fOutputStream;
		ObjectOutputStream oOutputStream;

		try {
			fOutputStream = new FileOutputStream("book.ser");
			oOutputStream = new ObjectOutputStream(fOutputStream);
			oOutputStream.writeObject(book);
			oOutputStream.close();
			fOutputStream.close();
			System.out.println("Serialized book: " + book);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			FileInputStream fInputStream = new FileInputStream("book.ser"); // throws FileNotFoundException
			ObjectInputStream oInputStream = new ObjectInputStream(fInputStream); // throws IOException
			Book book2 = (Book) oInputStream.readObject(); // throws ClassNotFoundException
			System.out.println("Deserialized book: " + book2);
			oInputStream.close();
			fInputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

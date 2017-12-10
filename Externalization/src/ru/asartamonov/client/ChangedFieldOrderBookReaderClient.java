package ru.asartamonov.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

import ru.asartamonov.domain.Author;
import ru.asartamonov.domain.Book;

/*
 * writeExternal and readExternal must be coordinated with one another.
 * This case will not work (methods from Book class are not coordinated now, 
 * we read author first, while write author last):
 * 
 * 
 * 	public void writeExternal(ObjectOutput out) throws IOException {
 *		out.writeObject(name);
 *		out.writeObject(author);
 *		}
 *	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
 *		this.author = (Author) in.readObject();
 *		this.name = (String) in.readObject();
 *	}
 *	
 * 
 * */
public class ChangedFieldOrderBookReaderClient {
	public static void main(String[] args) {
		FileInputStream fInputStream;
		ObjectInputStream oInputStream;
		Book failedToReadBook; // read book after modifying readExternal method: see above

		try {
			fInputStream = new FileInputStream("obsoleteBook.ser"); // throws FileNotFoundException
			oInputStream = new ObjectInputStream(fInputStream); // throws IOException
			failedToReadBook = (Book) oInputStream.readObject(); // throws ClassNotFoundException
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
 * Exception in thread "main" java.lang.ClassCastException:
 * java.base/java.lang.String cannot be cast to ru.asartamonov.domain.Author
 * 
 */

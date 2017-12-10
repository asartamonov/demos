package ru.asartamonov.domain;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Book implements Externalizable {

	/*
	 * When you serialize any object and if it contain any other
	 * object reference then Java serialization serialize that object’s entire object graph.
	 * But, class must also be serializable.
	 * */  
	private Author author;
	private String name;

	/*
	 * This must be here because https://docs.oracle.com/javase/8/docs/platform/serialization/spec/serial-arch.html#a4539
	 * 
	 *  The class of an Externalizable object must do the following: 
	 *     Implement the java.io.Externalizable interface
	 *     Implement a writeExternal method to save the state of the object 
	 *       (It must explicitly coordinate with its supertype to save its state.)
	 *     Implement a readExternal method to read the data written by the writeExternal 
	 *     method from the stream and restore the state of the object
	 *     Have the writeExternal and readExternal methods be solely responsible for the format, 
	 *     if an externally defined format is written
	 *     Have a public no-arg constructor
	 * */
	public Book() {
		super();
	}

	/*
	 * If you do something like private Book() {super();}
	 * with Externalizable class you will get exception below.
	 * Or you have constructor with args (and no no-args will be generated) you will get the same.
	 * 
	 * java.io.InvalidClassException: ru.asartamonov.domain.Book; no valid constructor
	 * 
	 * */

	public Book(Author author, String name) {
		super();
		this.author = author;
		this.name = name;
	}

	public Author getAuthor() {
		return author;
	}

	public String getName() {
		return name;
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.author = (Author) in.readObject();
		this.name = (String) in.readObject();
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Book [name=" + name + ", author=" + author + "]";
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(author);
		out.writeObject(name);
	}

}

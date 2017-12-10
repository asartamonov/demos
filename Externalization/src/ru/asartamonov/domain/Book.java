package ru.asartamonov.domain;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Book implements Externalizable {

	private Author author;
	private String name;

	public Book() {
		super();
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

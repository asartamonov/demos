package ru.asartamonov.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Author implements Serializable {
	/**
	 * Serializable is marker interface, but you can provide implementation for
	 * readObject(ObjectInputStream is) and writeObject(ObjectOutputStream os)
	 * methods very similar to readExternal and writeExternal
	 * 
	 * VERY IMPORTANT NOTICE ObjectInputStream should read data in same sequence in
	 * which we have written data to ObjectOutputStream !!!
	 * 
	 * If you declare your class as serializable, you better to provide this
	 * serialVersionUID so that Java can track it and throw an exception if you are
	 * trying to deserialize persisted object with different serialVersionUID
	 * 
	 * InvalidClassException - will be thrown in case of changes
	 */
	private static final long serialVersionUID = -2022913685447852709L;
	// private static final long serialVersionUID = -2032913685447852709L;

	private LocalDate born;

	private String name;

	/*
	 * This is ok because https://docs.oracle.com/javase/8/docs/platform/serialization/spec/serial-arch.html#a4539
	 * A Serializable class must do the following: 
	 *   Implement the java.io.Serializable interface
	 *   Identify the fields that should be serializable
	 *    Have access to the no-arg constructor of its first nonserializable superclass (Object in our case)
	 * */
	private Author() {}
	
	public Author(String name, LocalDate born) {
		super();
		this.name = name;
		this.born = born;
	}

	/**
	 * If you will not declare no-args constructor you'll get an error
	 */

	public LocalDate getBorn() {
		return born;
	}

	public String getName() {
		return name;
	}

	public void setBorn(LocalDate born) {
		this.born = born;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Author [born=" + born + ", name=" + name + "]";
	}
}

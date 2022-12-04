package com.ex.model.vo;

import java.sql.Date;

public class Book {

	private int bookNo;
	private String title;
	private String author;
	private String publisher;
	private int price;
	private int stock;
	private Date registDate;
	
	public Book() {}

	public Book(int bookNo, String title, String author, String publisher, int price, int stock, Date registDate) {
		super();
		this.bookNo = bookNo;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
		this.stock = stock;
		this.registDate = registDate;
	}
	
	public Book(String title, String author, String publisher, int price, int stock) {
		super();
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
		this.stock = stock;
	}

	public int getBookNo() {
		return bookNo;
	}

	public void setBookNo(int bookNo) {
		this.bookNo = bookNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	@Override
	public String toString() {
		return "Book [bookNo=" + bookNo + ", title=" + title + ", author=" + author + ", publisher=" + publisher
				+ ", price=" + price + ", stock=" + stock + ", registDate=" + registDate + "]";
	}
	
}

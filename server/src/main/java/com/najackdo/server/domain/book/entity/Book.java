package com.najackdo.server.domain.book.entity;

import java.sql.Date;

import com.najackdo.server.core.util.BookData;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id", nullable = false)
	private Long id;

	@Column(name = "isbn", nullable = false)
	private Long isbn;

	@Column(name = "description")
	private String description;

	@Column(name = "genre")
	private String genre;

	@Column(name = "title")
	private String title;

	@Column(name = "author")
	private String author;

	@Column(name = "cover")
	private String cover;

	@Column(name = "pub_date")
	private Date pubDate;

	@Column(name = "price_standard")
	private int priceStandard;

	@Column(name = "itempage")
	private int itemPage;

	@Column(name = "star_point")
	private double starPoint;

	@Column(name = "publisher")
	private String publisher;

	public static Book BookfromBookData(BookData bookData){
		Book book = new Book();
		book.isbn = bookData.getIsbn();
		book.description = bookData.getDescription();
		book.genre = bookData.getCategoryName();
		book.title = bookData.getTitle();
		book.author = bookData.getAuthor();
		book.cover = bookData.getCover();
		book.pubDate = Date.valueOf(bookData.getPubDate());
		book.priceStandard = bookData.getPriceStandard();
		book.itemPage = bookData.getItemPage();
		book.starPoint = bookData.getStarPoint();
		book.publisher = bookData.getPublisher();
		return book;
	}
}

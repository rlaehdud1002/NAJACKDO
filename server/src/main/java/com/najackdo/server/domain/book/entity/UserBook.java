package com.najackdo.server.domain.book.entity;

import static com.najackdo.server.domain.book.entity.BookStatus.*;

import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.najackdo.server.domain.location.entity.Location;
import com.najackdo.server.domain.rental.entity.RentalLog;
import com.najackdo.server.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_book")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBook {

	@OneToMany(mappedBy = "userBook", fetch = FetchType.LAZY)
	private List<RentalLog> bookRentalHistories;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_book_id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id", nullable = false)
	private Book book;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_code", nullable = false)
	private Location locationCode;

	@Column(name = "book_damage_checked", nullable = false)
	@ColumnDefault("false")
	private Boolean bookDamageChecked = false;

	@Column(name = "book_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private BookStatus bookStatus = UNAVAILABLE;

	@Column(name = "is_deleted", nullable = false)
	@ColumnDefault("false")
	private Boolean isDeleted = false;
}
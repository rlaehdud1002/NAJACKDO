package com.najackdo.server.domain.book.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.najackdo.server.core.annotation.CurrentUser;
import com.najackdo.server.core.response.SuccessResponse;
import com.najackdo.server.domain.book.dto.BookData;
import com.najackdo.server.domain.book.dto.UserBookData;
import com.najackdo.server.domain.book.entity.Book;
import com.najackdo.server.domain.book.service.BookService;
import com.najackdo.server.domain.book.service.UserBooksService;
import com.najackdo.server.domain.user.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/book")
@Slf4j
public class BookController {
	private final UserBooksService userBooksService;
	private final BookService bookService;

	/**
	 * 책 제목으로 다중 등록
	 *
	 * @param user
	 * @param create
	 * @return
	 */
	@PostMapping("/regist-books")
	public SuccessResponse<Map<String, List<String>>> registBooks(@CurrentUser User user,
		@RequestBody UserBookData.Create create) {
		Map<String, List<String>> result = userBooksService.addBookList(user, create);
		return SuccessResponse.of(result);
	}

	/**
	 * 책 ISBN으로 단일 등록
	 *
	 * @param user
	 * @param create
	 * @return
	 */
	@PostMapping("/regist-book")
	public SuccessResponse<Void> registBooks(@CurrentUser User user, @RequestBody UserBookData.CreateByISBN create) {
		userBooksService.addBook(user, create);
		return SuccessResponse.empty();
	}

	/**
	 * 유저의 관심 도서들 반환
	 *
	 * @param user
	 * @return
	 */
	@GetMapping("/interest")
	public SuccessResponse<List<BookData.Search>> getBookCase(@CurrentUser User user) {
		return SuccessResponse.of(userBooksService.getInterestBooks(user));
	}

	/**
	 * 유저의 관심 도서 추가
	 *
	 * @param user
	 * @param interest
	 * @return
	 */
	@PostMapping("/interest/{bookId}")
	public SuccessResponse<Void> addInterestBook(@CurrentUser User user,
		@PathVariable("bookId") Long interest) {
		userBooksService.addInterestBook(user, interest);
		return SuccessResponse.empty();
	}

	@DeleteMapping("/interest/{bookId}")
	public SuccessResponse<Void> deleteInterestBook(@CurrentUser User user,
		@PathVariable("bookId") Long interest) {
		userBooksService.deleteInterestBook(user, interest);
		return SuccessResponse.empty();
	}

	/**
	 * 관심 책장 목록 조회 API
	 *
	 * @param user
	 * @return {@link  List<  BookData.BookCase>}
	 */
	@GetMapping("/bookcase/interest")
	public SuccessResponse<List<BookData.BookCase>> getUserInterest(@CurrentUser User user) {
		return SuccessResponse.of(bookService.getBookCaseInterest(user));
	}

	/**
	 * 이름으로 책장 목록 조회 API
	 *
	 * @param user
	 * @param nickname
	 * @return {@link BookData.BookCase}
	 */
	@GetMapping("/bookcase/{nickname}")
	public SuccessResponse<BookData.BookCase> getUserBookCaseByNickName(
		@CurrentUser User user,
		@PathVariable String nickname) {
		return SuccessResponse.of(bookService.getBookCaseByNickName(nickname));
	}
}
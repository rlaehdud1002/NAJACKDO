package com.najackdo.server.domain.search.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.najackdo.server.core.annotation.CurrentUser;
import com.najackdo.server.core.response.SuccessResponse;
import com.najackdo.server.domain.book.dto.BookData;
import com.najackdo.server.domain.search.dto.AutocompleteResponse;
import com.najackdo.server.domain.search.service.SearchService;
import com.najackdo.server.domain.user.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {

	private final SearchService searchService;

	/**
	 * 책 검색 검색
	 * @param user
	 * @param keyword
	 * @return
	 */
	@GetMapping
	public SuccessResponse<List<BookData.Search>> search(@CurrentUser User user,
		@RequestParam("keyword") String keyword) {
		return SuccessResponse.of(searchService.searchKeyword(user.getId(), keyword));
	}

	/**
	 * 최근 검색어 조회
	 * @param user
	 * @return
	 */
	@GetMapping("/recent")
	public SuccessResponse<List<String>> getRecentSearchList(@CurrentUser User user) {
		// 최근 검색어 조회 서비스 호출
		return SuccessResponse.of(searchService.getResentSearchList(user.getId()));
	}

	/**
	 * 최근 검색어 삭제
	 */

	@DeleteMapping("/recent/{keyword}")
	public SuccessResponse<Void> deleteRecentSearchList(@CurrentUser User user,
		@PathVariable("keyword") String keyword) {
		// 최근 검색어 삭제 서비스 호출
		searchService.deleteRecentKeyword(user.getId(), keyword);

		return SuccessResponse.empty();
	}


	/**
	 * 인기 검색어 조회
	 * @return
	 */
	@GetMapping("/popularity")
	public SuccessResponse<List<String>> getPopularSearchList() {

		// 인기 검색어 조회 서비스 호출
		return SuccessResponse.of(searchService.getPopularKeywords());
	}

	/**
	 * 자동완성 검색어 조회
	 * @param keyword
	 * @return
	 */
	@GetMapping("/auto-complete")
	public AutocompleteResponse getAutoCompleteList(@RequestParam("keyword") String keyword) {
		// 자동완성 검색어 조회 서비스 호출
		log.info("keyword: {}", keyword);
		return searchService.getAutocomplete(keyword);
	}
}
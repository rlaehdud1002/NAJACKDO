package com.najackdo.server.domain.rental.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.najackdo.server.core.annotation.CurrentUser;
import com.najackdo.server.core.response.SuccessResponse;
import com.najackdo.server.domain.rental.dto.ReviewData;
import com.najackdo.server.domain.rental.service.ReviewService;
import com.najackdo.server.domain.user.entity.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/review")
@RequiredArgsConstructor
@Tag(name = "리뷰 관련 API ")
public class RentalReviewController {

	private final ReviewService reviewService;

	/**
	 * 리뷰 등록 api
	 *
	 * @param reviewRequest
	 * @return {@link SuccessResponse<Void>}
	 */
	@PostMapping("")
	@Operation(summary = "리뷰 등록", description = "리뷰 등록")
	public SuccessResponse<Void> review(@CurrentUser User user, @RequestBody ReviewData.ReviewRequest reviewRequest) {
		return reviewService.review(user, reviewRequest);
	}
}
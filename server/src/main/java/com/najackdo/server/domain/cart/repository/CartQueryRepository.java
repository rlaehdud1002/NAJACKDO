package com.najackdo.server.domain.cart.repository;

import java.util.List;
import java.util.Optional;

import com.najackdo.server.domain.cart.dto.CartData;
import com.najackdo.server.domain.cart.entity.Cart;

public interface CartQueryRepository {

	/**
	 * 사용자 id와 도서 id로 장바구니 조회
	 *
	 * @param userId 사용자 id
	 * @param bookId 도서 id
	 * @return {@link Optional<Cart>} 장바구니
	 */
	Optional<Cart> findCartByUserIdAndBookId(Long userId, Long bookId);

	/**
	 * 사용자 ID로 사용자의 카트 조회
	 *
	 * @param id 사용자 ID
	 */
	List<CartData.CartInfo> findCartsByUserId(Long id);
}
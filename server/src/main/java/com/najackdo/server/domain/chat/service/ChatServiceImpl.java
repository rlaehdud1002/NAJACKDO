package com.najackdo.server.domain.chat.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.najackdo.server.core.configuration.RootConfig;
import com.najackdo.server.domain.chat.dto.ChatRoomDTO;
import com.najackdo.server.domain.chat.entity.Chat;
import com.najackdo.server.domain.chat.entity.ChatRoom;
import com.najackdo.server.domain.chat.repository.ChatRepository;
import com.najackdo.server.domain.chat.repository.ChatRoomRepository;
import com.najackdo.server.domain.user.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
	private final ChatRoomRepository chatRoomRepository;
	private final RootConfig rootConfig;
	private final ChatRepository chatRepository;

	@Override
	public List<ChatRoomDTO> chatRoomList() {
		List<ChatRoom> chatRoomList = chatRoomRepository.findAll();
		List<ChatRoomDTO> chatRoomDTOS = chatRoomList.stream().map(chatRoom -> new ChatRoomDTO(chatRoom)).collect(
			Collectors.toList());
		return chatRoomDTOS;
	}

	@Override
	@Transactional
	public ChatRoomDTO createRoom(String roomName) {
		ChatRoom chatRoom = ChatRoom.builder()
			.roomId(UUID.randomUUID().toString())
			.roomName(roomName)
			.build();
		chatRoomRepository.save(chatRoom);
		return rootConfig.getMapper().map(chatRoom, ChatRoomDTO.class);
	}

	@Override
	public List<Chat.Message> getChatList(String roomId, User user) {
		// chatRoomRepository.findById(roomId)
		// 	.orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

		// List<User> usersByRoomId = chatRoomRepository.findUsersByRoomId(roomId);
		// if (!usersByRoomId.contains(user)) {
		// 	throw new IllegalArgumentException("사용자가 채팅방에 존재하지 않습니다.");
		// }

		return chatRepository.findByRoomId(roomId).getMessages();

	}
}
package com.najackdo.server.domain.chat.service;

import java.util.List;

import com.najackdo.server.domain.chat.dto.ChatDTO;
import com.najackdo.server.domain.chat.dto.ChatRoomDTO;
import com.najackdo.server.domain.chat.entity.Chat;
import com.najackdo.server.domain.user.entity.User;

public interface ChatService {
	List<ChatRoomDTO> chatRoomList();
	ChatRoomDTO createRoom(String name);
	List<Chat.Message> getChatList(String roomId, User user);
}
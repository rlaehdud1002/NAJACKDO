package com.najackdo.server.domain.chat.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

import com.najackdo.server.core.configuration.RootConfig;
import com.najackdo.server.domain.chat.dto.ChatDTO;
import com.najackdo.server.domain.chat.entity.Chat;
import com.najackdo.server.domain.chat.repository.ChatRepository;
import com.najackdo.server.domain.chat.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {
	private final static String CHAT_EXCHANGE_NAME = "chat.exchange";
	private final static String CHAT_QUEUE_NAME = "chat.queue";

	private final RabbitTemplate rabbitTemplate;

	private final ChatService chatService;

	private final ChatRepository chatRepository;

	private final RootConfig rootConfig;


	// /pub/chat.message.{roomId} 로 요청하면 브로커를 통해 처리
	// /exchange/chat.exchange/room.{roomId} 를 구독한 클라이언트에 메시지가 전송된다.
	@MessageMapping("chat.enter.{chatRoomId}")
	public void enterUser(@Payload ChatDTO chat, @DestinationVariable String chatRoomId) {

		chat.setTime(LocalDateTime.now());
		chat.setMessage(chat.getSenderId() + " 님 입장!!");
		rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, chat);

	}

	@MessageMapping("chat.message.{chatRoomId}")
	public void sendMessage(@Payload ChatDTO chat, @DestinationVariable String chatRoomId) {
		log.info("CHAT {}", chat);
		chat.setTime(LocalDateTime.now());
		chat.setMessage(chat.getMessage());
		rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, chat);
	}

	//기본적으로 chat.queue가 exchange에 바인딩 되어있기 때문에 모든 메시지 처리
	@RabbitListener(queues = CHAT_QUEUE_NAME)
	public void receive(ChatDTO chatDTO){
		log.info("received : " + chatDTO.getSenderNickname());
		log.info("received : " + chatDTO.getMessage());

		// 새로운 메시지 생성
		Chat.Message newMessage = new Chat.Message();
		newMessage.setSenderId(chatDTO.getSenderId());
		newMessage.setSenderNickname(chatDTO.getSenderNickname());
		newMessage.setMessage(chatDTO.getMessage());
		newMessage.setTime(chatDTO.getTime());  // ChatDTO에 LocalDateTime이 있어야 함

		Chat existingChat = chatRepository.findByRoomId(chatDTO.getRoomId());

		if (existingChat != null) {
			// 기존 대화방이 있을 경우 messages 배열에 메시지 추가
			existingChat.getMessages().add(newMessage);
			chatRepository.save(existingChat);
		} else {
			// 새로운 대화방 생성
			Chat newChat = new Chat();
			newChat.setRoomId(chatDTO.getRoomId());
			newChat.setMessages(List.of(newMessage));
			chatRepository.save(newChat);
		}
	}
}
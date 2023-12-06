package com.project.pescueshop.service;

import com.project.pescueshop.model.entity.ChatMessage;
import com.project.pescueshop.model.entity.ChatRoom;
import com.project.pescueshop.model.entity.User;
import com.project.pescueshop.repository.dao.ChatDAO;
import com.project.pescueshop.util.constant.EnumMessageStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {
    private final ChatDAO chatDAO;
    private final UserService userService;
    public Optional<String> getChatId(String senderId, String recipientId, boolean createIfNotExist){
        User sender = userService.findById(senderId);
        User recipient = userService.findById(recipientId);

        return chatDAO
                .findRoomByUser1AndUser2(senderId, recipientId)
                .map(ChatRoom::getChatRoomId)
                .or(() -> {
                    if(!createIfNotExist)
                        return Optional.empty();

                    return chatDAO.findRoomByUser1AndUser2(recipientId, senderId)
                            .map(ChatRoom::getChatRoomId)
                            .or(() -> {
                                ChatRoom newChatRoom = new ChatRoom(sender, recipient);

                                chatDAO.saveAndFlushRoom(newChatRoom);
                                return Optional.of(newChatRoom.getChatRoomId());
                            });
                });
    }

    public List<ChatRoom> findAllChatRoomByUserID(String userID) {
        return chatDAO.findAllRoomByUser(userID);
    }

    public List<ChatMessage> findAllChatMessageByChatRoomID(String chatRoomID, String senderID, String recipientID) {
        updateStatus(recipientID, senderID, EnumMessageStatus.RECEIVED.toString());
        return chatDAO.findAllMessageByRoomID(chatRoomID);
    }

    public void updateStatus(String senderId, String recipientId, String status) {
        chatDAO.updateChatStatus(senderId, recipientId, status);
    }

    public List<ChatMessage> findAllMessageBySenderIDAndRecipientID(String senderId, String recipientId) {
        updateStatus(recipientId, senderId, EnumMessageStatus.RECEIVED.getValue());
        return chatDAO.findAllMessageBySenderIdAndRecipientId(senderId, recipientId);
    }
}

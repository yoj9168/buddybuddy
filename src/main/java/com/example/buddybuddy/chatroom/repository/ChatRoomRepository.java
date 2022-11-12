package com.example.buddybuddy.chatroom.repository;

import com.example.buddybuddy.chatroom.model.ChatRoom;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ChatRoomRepository {
    private final Map<String, ChatRoom> chatRoomMap;

    public ChatRoomRepository() {
        chatRoomMap = new HashMap<>();
    }

    public void createRoom(String roomName){
        ChatRoom chatRoom = ChatRoom.create(roomName);
        chatRoomMap.put(chatRoom.getId(), chatRoom);
    }
    
    public ChatRoom getChatRoom(String id) {
        return chatRoomMap.get(id);
    }

    public Collection<ChatRoom> getChatRooms(){
        return chatRoomMap.values();
    }
    public void remove(WebSocketSession session) {
        this.chatRoomMap.values().parallelStream().forEach(chatRoom -> chatRoom.remove(session));
    }

}

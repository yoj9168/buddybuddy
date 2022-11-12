package com.example.buddybuddy.chatroom.controller;

import com.example.buddybuddy.chatroom.model.ChatRoom;
import com.example.buddybuddy.chatroom.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomRepository repository;
    private final String listViewName;
    private final String detailViewName;
    private final String startViewName;
    private final AtomicInteger seq = new AtomicInteger(0);

    @Autowired
    public ChatRoomController(ChatRoomRepository repository, @Value("${viewname.chatroom.list}") String listViewName, @Value("${viewname.chatroom.detail}") String detailViewName, @Value("${viewname.chatroom.start}") String startViewName) {
        this.repository = repository;
        this.listViewName = listViewName;
        this.detailViewName = detailViewName;
        this.startViewName = startViewName;
    }


    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("welcome", "추억의 버디버디");
        return startViewName;
    }


    @GetMapping("/rooms")
    public String rooms(Model model) {
        model.addAttribute("rooms", repository.getChatRooms());
        return listViewName;
    }

    @GetMapping("/rooms/{id}")
    public String room(@PathVariable String id, Model model) {
        ChatRoom room = repository.getChatRoom(id);
        model.addAttribute("room", room);
        model.addAttribute("member", "member" + seq.incrementAndGet());

        return detailViewName;
    }

    @ResponseBody
    @RequestMapping(value="/rooms", method= RequestMethod.POST)
    public String createRoom(@RequestBody Map<String,String> requestData) {
        repository.createRoom(requestData.get("roomId"));
        return null;
    }

}

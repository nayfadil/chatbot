package com.simple.chatbot.controller;

import com.simple.chatbot.dto.BaseResponse;
import com.simple.chatbot.dto.RequestMessage;
import com.simple.chatbot.dto.ResponseMessage;
import com.simple.chatbot.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("chat")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/question")
    public BaseResponse<Object> helloWorld(@RequestBody RequestMessage requestMessage){
            return chatService.responseMessage(requestMessage.getQuestion());
    }
}

package com.simple.chatbot.service;

import com.simple.chatbot.dto.BaseResponse;
import com.simple.chatbot.dto.ResponseMessage;

public interface ChatService {

    BaseResponse<Object> responseMessage(String question);
}

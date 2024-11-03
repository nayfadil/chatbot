package com.simple.chatbot.service.impl;

import com.simple.chatbot.dto.BaseResponse;
import com.simple.chatbot.dto.ResponseMessage;
import com.simple.chatbot.model.FAQ;
import com.simple.chatbot.model.FallbackResponse;
import com.simple.chatbot.model.Message;
import com.simple.chatbot.repository.FAQRepository;
import com.simple.chatbot.repository.FallBackRepository;
import com.simple.chatbot.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceImplTest {


    @Mock
    private FAQRepository faqRepository;

    @Mock
    private FallBackRepository fallBackRepository;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private ChatServiceImpl chatbotService;

    @Test
    void testResponseMessage_WhenFAQExists() {
        String question = "What is your name?";
        FAQ faq = new FAQ();
        faq.setQuestion(question);
        faq.setAnswer("I am Yu.");

        when(faqRepository.findByQuestionIgnoreCase(question)).thenReturn(Optional.of(faq));

        BaseResponse<Object> response = chatbotService.responseMessage(question);

        assertEquals("00", response.getResponseCode());
        assertEquals("Success", response.getMessage());
        assertNotNull(response.getResult());

        ResponseMessage responseMessage = (ResponseMessage) response.getResult();
        assertEquals("I am Yu.", responseMessage.getMessage());
        assertEquals("Answer", responseMessage.getType());

        // Verify that messageRepository.save() was called
        verify(messageRepository, times(1)).save(any(Message.class));
    }

    @Test
    void testResponseMessage_WhenFAQDoesNotExist() {
        String question = "How are you?";
        FallbackResponse fallbackResponse = new FallbackResponse();
        fallbackResponse.setResponse("I am here to assist you!");

        when(faqRepository.findByQuestionIgnoreCase(question)).thenReturn(Optional.empty());
        when(fallBackRepository.findAll()).thenReturn(List.of(fallbackResponse));

        BaseResponse<Object> response = chatbotService.responseMessage(question);

        assertEquals("00", response.getResponseCode());
        assertEquals("Success", response.getMessage());
        assertNotNull(response.getResult());

        ResponseMessage responseMessage = (ResponseMessage) response.getResult();
        assertEquals("I am here to assist you!", responseMessage.getMessage());
        assertEquals("Answer", responseMessage.getType());

        // Verify that messageRepository.save() was called
        verify(messageRepository, times(1)).save(any(Message.class));
    }

    @Test
    void testResponseMessage_WhenExceptionOccurs() {
        String question = "Any question";

        // Mock an exception in the repository call
        when(faqRepository.findByQuestionIgnoreCase(question)).thenThrow(new RuntimeException("Database error"));

        BaseResponse<Object> response = chatbotService.responseMessage(question);

        assertEquals("99", response.getResponseCode());
        assertEquals("Database error", response.getMessage());
        assertNull(response.getResult());

        // Verify that messageRepository.save() was never called
        verify(messageRepository, never()).save(any(Message.class));
    }
}
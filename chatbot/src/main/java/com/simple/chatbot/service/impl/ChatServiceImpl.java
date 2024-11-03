package com.simple.chatbot.service.impl;

import com.simple.chatbot.dto.BaseResponse;
import com.simple.chatbot.dto.ResponseMessage;
import com.simple.chatbot.model.FAQ;
import com.simple.chatbot.model.FallbackResponse;
import com.simple.chatbot.model.Message;
import com.simple.chatbot.repository.FAQRepository;
import com.simple.chatbot.repository.FallBackRepository;
import com.simple.chatbot.repository.MessageRepository;
import com.simple.chatbot.service.ChatService;
import com.simple.chatbot.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

    private final MessageRepository messageRepository;
    private final FallBackRepository fallBackRepository;
    private final FAQRepository faqRepository;

    private static final int SIMILARITY_THRESHOLD = 3;

    @Override
    public BaseResponse<Object> responseMessage(String question) {
        try {
            logger.info("Question : {} ", question);
            ResponseMessage response = new ResponseMessage();
            String responseMessage;

            String answer = getSimilarFAQResponse(question);
            List<FallbackResponse> fallback = fallBackRepository.findAll();
            if (!StringUtils.isEmpty(answer))
                responseMessage = answer;
            else
                responseMessage = fallback.getFirst().getResponse();


            Message message = Message.builder()
                    .botResponse(responseMessage)
                    .userMessage(question)
                    .timestamp(LocalDateTime.now())
                    .build();

            messageRepository.save(message);

            response.setMessage(responseMessage);
            response.setType("Answer");
            logger.info("Answer question : {}", responseMessage);

            return ResponseUtils.buildResponse("00", "Success", response);
        }catch (Exception e){
            logger.error("Error catch : {} ", e.getMessage(), e);
            return ResponseUtils.buildResponse("99", e.getMessage(), null);
        }
    }

    /**
     * Retrieves a response from the FAQ database for a user message that closely matches an existing FAQ question.
     * Uses the Levenshtein Distance algorithm to calculate the similarity between the user message and each FAQ question.
     * If the similarity (edit distance) is within a specified threshold, the corresponding FAQ answer is returned.
     *
     * @param userMessage The message input by the user that needs a similar response from the FAQ.
     * @return The answer of the closest matching FAQ question if the similarity threshold is met;
     *         {@code null} if no similar question is found within the threshold.
     */
    private String getSimilarFAQResponse(String userMessage) {
        List<FAQ> faqs = faqRepository.findAll();
        LevenshteinDistance levenshtein = new LevenshteinDistance();

        for (FAQ faq : faqs) {
            int distance = levenshtein.apply(userMessage.toLowerCase(), faq.getQuestion().toLowerCase());
            if (distance <= SIMILARITY_THRESHOLD) {
                return faq.getAnswer();
            }
        }
        return null;
    }
}

package com.simple.chatbot.repository;

import com.simple.chatbot.model.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FAQRepository extends JpaRepository<FAQ, Long> {

    Optional<FAQ> findByQuestionIgnoreCase(String question);
}

package com.simple.chatbot.repository;

import com.simple.chatbot.model.FallbackResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FallBackRepository extends JpaRepository<FallbackResponse, Long> {
}

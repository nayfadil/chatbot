package com.simple.chatbot.util;

import com.simple.chatbot.dto.BaseResponse;
import org.springframework.stereotype.Component;

@Component
public class ResponseUtils {

    /**
     * Builds a standardized response object containing a response code, a message, and a result.
     *
     * @param responseCode The code representing the response status (e.g., "00" for success, "99" for error).
     * @param message A descriptive message providing additional context about the response.
     * @param object The result object of generic type T, which may contain data relevant to the response.
     * @param <T> The generic type of the result object.
     * @return A {@link BaseResponse} object populated with the provided response code, message, and result.
     */
    public static<T> BaseResponse<T> buildResponse(String responseCode, String message, T object){
        BaseResponse<T> response = new BaseResponse<>();
        response.setResponseCode(responseCode);
        response.setMessage(message);
        response.setResult(object);

        return response;
    }
    private ResponseUtils(){}
}

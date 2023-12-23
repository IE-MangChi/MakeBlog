package com.blog.api.resonse;


import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * {
 *     "code" : "400",
 *     "message" : "잘못된 요청",
 *     "validation" : {
 *         "title" : 값을 입력해주세요,
 *     }
 * }
 *
 */
@RequiredArgsConstructor
@Getter
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Map<String, String> validation = new HashMap<>(); // Map을 쓰면 안좋다. 클래스로 넣어주자

    public void addValidation(String fieldName, String errorMessage) {
        validation.put(fieldName, errorMessage);
    }
}

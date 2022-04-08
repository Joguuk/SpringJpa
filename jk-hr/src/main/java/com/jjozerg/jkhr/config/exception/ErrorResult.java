package com.jjozerg.jkhr.config.exception;

import lombok.Getter;
import lombok.ToString;

/**
 * packageName : com.jjozerg.jkhr.config.exception
 * fileName : ErrorResult
 * author : joguk
 * date : 2022/02/11
 * description : Error 결과 응답
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2022/02/11 joguk 최초 생성
 * -----------------------------------------------------------
 */

@ToString @Getter
public class ErrorResult {
    private String code;
    private String message;

    public ErrorResult(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

package com.jjozerg.jkhr.config.exception;

/**
 * packageName : com.jjozerg.jkhr.config.exception
 * fileName : BusinessException
 * author : joguk
 * date : 2022/02/13
 * description : 로직 처리 중 실패하는 경우 발생하는 BusinessException
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2022/02/13 joguk 최초 생성
 * -----------------------------------------------------------
 */

public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
}

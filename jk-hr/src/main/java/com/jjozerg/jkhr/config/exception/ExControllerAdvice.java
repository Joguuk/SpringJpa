package com.jjozerg.jkhr.config.exception;

import com.jjozerg.jkhr.common.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.util.NoSuchElementException;

/**
 * packageName : com.jjozerg..config.exception
 * fileName : ExControllerAdvice
 * author : joguk
 * date : 2022/02/11
 * description : Exception Handler
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2022/02/11 joguk 최초 생성
 * -----------------------------------------------------------
 */

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    /**
     * 정의되지 않은 에러는 500 Error
     *
     * @author joguk
     * @date 2022/02/14 12:03 오전
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler] ex",e);
        return new ErrorResult(MessageUtils.getMessages("exception.exception"), e.getMessage());
    }

    /**
     * 요청 처리 중 로직에서 실패하는 경우 발생할 Business Exception
     *
     * @author joguk
     * @date 2022/02/14 12:03 오전
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(BusinessException.class)
    public ErrorResult BusinessExHandler(BusinessException e) {
        log.error("[exceptionHandler] ex",e);
        return new ErrorResult(MessageUtils.getMessages("exception.businessException"), e.getMessage());
    }

    /**
     * 요청한 데이터를 찾지 못한 경우 발생할 NoSuchElementException
     *
     * @author joguk
     * @date 2022/02/14 12:04 오전
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResult noSuchElementExHandler(NoSuchElementException e) {
        log.error("[exceptionHandler] ex",e);
        return new ErrorResult(MessageUtils.getMessages("exception.noSuchElementException", new Object[]{e.getMessage()}), e.getMessage());
    }

    /**
     * 인증 실패 시 발생할 AuthenticationException
     *
     * @author joguk
     * @date 2022/02/14 12:04 오전
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AuthenticationException.class)
    public ErrorResult AuthenticationExHandler(AuthenticationException e) {
        log.error("[exceptionHandler] ex",e);
        return new ErrorResult(MessageUtils.getMessages("exception.authenticationException"), e.getMessage());
    }
}

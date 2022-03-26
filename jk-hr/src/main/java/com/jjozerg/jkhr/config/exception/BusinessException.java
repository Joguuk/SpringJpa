package com.jjozerg.jkhr.config.exception;

/** 
 * packageName : com.jjozerg.jkhr.config.exception 
 * fileName : BusinessException 
 * author : joguk 
 * date : 2022/02/13 
 * description : 
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

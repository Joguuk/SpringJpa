package com.jjozerg.jkhr.common;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * packageName : com.jjozerg.jkhr.common
 * fileName : MessageUtils
 * author : joguk
 * date : 2022/02/13
 * description : message Utils
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2022/02/13 joguk 최초 생성
 * -----------------------------------------------------------
 */
@Component
public class MessageUtils {
    private static MessageSource messageSource;

    public MessageUtils(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public static String getMessages(String code) {
        return getMessages(code, null);
    }

    public static String getMessages(String code, Object[] objects) {
        try {
            return messageSource.getMessage(code, objects,null);
        } catch (Exception e) {
            return "[" + code + "]" + " messages Not Found";
        }
    }
}

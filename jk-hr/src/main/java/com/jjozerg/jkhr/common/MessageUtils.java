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

    /**
     * messageSource를 통해 메세지를 조회하여 반환한다.
     *
     * @author joguk
     * @date 2022/02/17 11:29 오후
     */
    public static String getMessages(String code, Object[] objects) {
        try {
            return messageSource.getMessage(code, objects,null);
        } catch (Exception e) {
            return "[" + code + "]" + " messages Not Found";
        }
    }
}

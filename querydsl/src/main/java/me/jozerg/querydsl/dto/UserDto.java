package me.jozerg.querydsl.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName : me.jozerg.querydsl.dto
 * fileName : UserDto
 * author : joguk
 * date : 2021/12/04
 * description :
 * <pre></pre>
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2021/12/04 joguk 최초 생성
 * -----------------------------------------------------------
 */

@NoArgsConstructor
@Data
public class UserDto {
    private String name;
    private int age;

    public UserDto(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

package me.jozerg.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName : me.jozerg.querydsl.dto
 * fileName : MemberDto
 * author : joguk
 * date : 2021/12/04
 * description :
 * <pre></pre>
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2021/12/04 joguk 최초 생성
 * -----------------------------------------------------------
 */

@Data
@NoArgsConstructor
public class MemberDto {

    private String username;
    private int age;

    @QueryProjection
    public MemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}

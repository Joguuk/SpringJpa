package com.jjozerg.jkhr.member.dto;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * packageName : com.jjozerg.jkhr.member.dto
 * fileName : LoginReqDto
 * author : joguk
 * date : 2022/02/12
 * description : 로그인 요청 Dto
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2022/02/12 joguk 최초 생성
 * -----------------------------------------------------------
 */

@Getter
@ToString
public class LoginReqDto {
    @NotEmpty
    private String memberLoginId;
    @NotEmpty
    private String memberLoginPassword;
}

package com.jjozerg.jkhr.member.dto;

import com.jjozerg.jkhr.member.entity.Member;
import lombok.Getter;
import lombok.ToString;

/**
 * packageName : com.jjozerg.jkhr.member.dto
 * fileName : MemberResDto
 * author : joguk
 * date : 2022/02/11
 * description : 회원 응답 Dto
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2022/02/11 joguk 최초 생성
 * -----------------------------------------------------------
 */

@ToString
@Getter
public class MemberResDto {
    private Long memberId;
    private String memberLoginId;
    private String memberName;

    public MemberResDto(Member member) {
        memberId = member.getMemberId();
        memberLoginId = member.getMemberLoginId();
        memberName = member.getMemberName();
    }
}

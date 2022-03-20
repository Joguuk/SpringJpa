package com.jjozerg.jkhr.member.repository;

import com.jjozerg.jkhr.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * packageName : com.jjozerg.jkhr.member.repository
 * fileName : MemberRepository
 * author : joguk
 * date : 2022/02/11
 * description : 회원 Repository
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2022/02/11 joguk 최초 생성
 * -----------------------------------------------------------
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findOptionalByMemberLoginId(String memberLoginId);
}

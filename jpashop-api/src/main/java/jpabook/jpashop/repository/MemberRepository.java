package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 놀랍게도 JPA가 아래의 쿼리(구현체)를 만들어줌..
    // select m from Member m where m.name = ?
    List<Member> findByName(String name);
}

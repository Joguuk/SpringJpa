package com.jjozerg.jkhr.member.service;

import com.jjozerg.jkhr.common.MessageUtils;
import com.jjozerg.jkhr.config.exception.BusinessException;
import com.jjozerg.jkhr.member.entity.Member;
import com.jjozerg.jkhr.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * packageName : com.jjozerg.jkhr.member.service
 * fileName : MemberService
 * author : joguk
 * date : 2022/02/11
 * description : 회원 Service
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2022/02/11 joguk 최초 생성
 * -----------------------------------------------------------
 */

@Service
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 전달 받은 회원 ID와 password와 일치하는 회원 정보를 반환한다.
     *
     * @author joguk
     * @date 2022/02/13 7:49 오후
     */
    public Member login(String loginId, String loginPassword) {
        Optional<Member> optionalMember = memberRepository.findOptionalByMemberLoginId(loginId);
        Member member = optionalMember.orElseThrow(() -> new NoSuchElementException(MessageUtils.getMessages("message.login.fail.notfound.error")));

        if (!passwordEncoder.matches(loginPassword, member.getMemberPassword())) {
            throw new BusinessException(MessageUtils.getMessages("message.login.fail.password.notmatched"));
        }

        return member;
    }

    /**
     * 회원목록을 반환한다
     *
     * @author joguk
     * @date 2022/02/13 7:49 오후
     */
    public List<Member> selectMembers() {
        return memberRepository.findAll();
    }
}

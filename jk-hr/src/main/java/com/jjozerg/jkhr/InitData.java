package com.jjozerg.jkhr;

import com.jjozerg.jkhr.member.entity.Member;
import com.jjozerg.jkhr.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.jjozerg.jkhr.common.CroquiscomHrConstants.MemberStatus.ACTIVATION;


/**
 * packageName : com.jjozerg.croquiscomhr
 * fileName : InitData
 * author : joguk
 * date : 2022/02/11
 * description : 기본 회원 데이터를 생성하는 class
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2022/02/11 joguk 최초 생성
 * -----------------------------------------------------------
 */

@Component
public class InitData {
    private MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;

    public InitData(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        createDefaultData();
    }

    private void createDefaultData() {
        createDefaultMemberData();
    }

    /**
     * 기본 회원 데이터를 insert 한다.
     *
     * @author joguk
     * @date 2022/02/13 5:29 오후
     */
    private void createDefaultMemberData() {
        Member kao = Member.builder()
                .memberLoginId("kakao")
                .memberPassword(passwordEncoder.encode("1234"))
                .memberName("kaokao")
                .memberStatus(ACTIVATION)
                .build();
        memberRepository.save(kao);

        Member sty = Member.builder()
                .memberLoginId("style")
                .memberPassword(passwordEncoder.encode("1234"))
                .memberName("sty")
                .memberStatus(ACTIVATION)
                .build();
        memberRepository.save(sty);

        Member zig = Member.builder()
                .memberLoginId("zigzag")
                .memberPassword(passwordEncoder.encode("1234"))
                .memberName("zig")
                .memberStatus(ACTIVATION)
                .build();
        memberRepository.save(zig);
    }
}

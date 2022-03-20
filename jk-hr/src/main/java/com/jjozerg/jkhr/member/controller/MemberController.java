package com.jjozerg.jkhr.member.controller;

import com.jjozerg.jkhr.common.HrConstants;
import com.jjozerg.jkhr.member.dto.MemberResDto;
import com.jjozerg.jkhr.member.entity.Member;
import com.jjozerg.jkhr.member.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName : com.jjozerg.jkhr.member.controller
 * fileName : MemberController
 * author : joguk
 * date : 2022/02/11
 * description : Member Controller
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2022/02/11 joguk 최초 생성
 * -----------------------------------------------------------
 */

@RestController
@RequestMapping("/members/v1")
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 전달받은 ID와 PASSWORD를 통해 로그인을 수행한다.
     *
     * @author joguk
     * @date 2022/02/12 4:51 오후
     */
    @GetMapping("/login")
    public String login(
            @RequestParam   @NotEmpty String loginId,
            @RequestParam   @NotEmpty String loginPassword,
            HttpServletRequest request) {
        Member member = memberService.login(loginId, loginPassword);

        // 세션 저장
        HttpSession session = request.getSession();
        session.setAttribute(HrConstants.LOGIN_MEMBER, member);

        return member.getMemberName();
    }

    /**
     * 사용자 목록을 조회한다.
     *
     * @return
     * @author
     * @date 2022/02/11 10:08 오후
     */
    @GetMapping
    public List<MemberResDto> selectMembers() {
        List<Member> members = memberService.selectMembers();

        return members.stream()
                .map(member -> new MemberResDto(member))
                .collect(Collectors.toList());
    }
}

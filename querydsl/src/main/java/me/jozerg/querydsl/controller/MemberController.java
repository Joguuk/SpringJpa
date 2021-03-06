package me.jozerg.querydsl.controller;

import lombok.RequiredArgsConstructor;
import me.jozerg.querydsl.dto.MemberSearchCondition;
import me.jozerg.querydsl.dto.MemberTeamDto;
import me.jozerg.querydsl.repository.MemberJpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberJpaRepository memberJpaRepository;

    @GetMapping("/v1/members")
    public List<MemberTeamDto> searchMemberV1(MemberSearchCondition condition) {
        return memberJpaRepository.search(condition);
    }
}

package com.jjozerg.jkhr.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jjozerg.jkhr.vacation.entity.VacationRequest;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

import static com.jjozerg.jkhr.common.CroquiscomHrConstants.*;

/**
 * packageName : com.jjozerg.jkhr.member.entity
 * fileName : Member
 * author : joguk
 * date : 2022/02/11
 * description : 회원 Entity
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2022/02/11 joguk 최초 생성
 * -----------------------------------------------------------
 */

@Entity(name = "TB_MEMBER")
@Getter @ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long memberId;

    @NotEmpty
    private String memberLoginId;

    @NotEmpty
    private String memberPassword;

    @Enumerated(value = EnumType.STRING)
    private MemberStatus memberStatus;

    @NotEmpty
    private String memberName;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<VacationRequest> vacationReqs = new ArrayList<>();
}

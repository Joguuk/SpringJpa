package com.jjozerg.jkhr.vacation.entity;

import com.jjozerg.jkhr.common.MessageUtils;
import com.jjozerg.jkhr.config.exception.BusinessException;
import com.jjozerg.jkhr.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.jjozerg.jkhr.common.jkhrConstants.VacationKind;
import static com.jjozerg.jkhr.common.jkhrConstants.VacationStatus;
import static com.jjozerg.jkhr.common.jkhrConstants.VacationStatus.*;
import static com.jjozerg.jkhr.common.jkhrConstants.VacationStatus.CANCEL;

/**
 * packageName : com.jjozerg.jkhr.vacation.entity
 * fileName : VacationRequest
 * author : joguk
 * date : 2022/02/13
 * description : 휴가신청 Entity
 * ===========================================================
 * DATE AUTHOR NOTE
 * 2022/02/13 joguk 최초 생성
 * -----------------------------------------------------------
 */

@Entity(name = "TB_VACATION_REQUEST")
@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VacationRequest {
    @Transient
    public static final Integer DEFAULT_VACATION_COUNT = 15;

    @Id @GeneratedValue
    @Column(name = "vacation_req_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(value = EnumType.STRING)
    private VacationKind vacationKind;

    @Enumerated(value = EnumType.STRING)
    private VacationStatus vacationStatus;

    @NotNull
    private Double vacationCount;
    @NotNull
    private Integer vacationYear;
    @NotNull
    private LocalDateTime vacationStartDttm;
    @NotNull
    private LocalDateTime vacationEndDttm;

    private String note;

    /**
     * 사용한 휴가 수 + 신청 휴가수를 판단하여 신청 가능여부를 검증한다.
     *
     * @author joguk
     * @date 2022/02/13 7:11 오후
     */
    public boolean checkVacationCount(Double useVacationCount) throws Exception {
        if (useVacationCount + vacationCount >= DEFAULT_VACATION_COUNT) {
            throw new Exception(MessageUtils.getMessages("message.vacation.max.count"));
        }

        return true;
    }

    /**
     * 휴가를 취소 상태로 변경한다.
     *
     * @author joguk
     * @date 2022/02/13 8:04 오후
     */
    public void cancelVacation() {
        if (isCancelPossible()) {
            vacationStatus = CANCEL;
        }
    }

    /**
     * 휴가 취소 가능 여부를 확인한다.
     *
     * @author joguk
     * @date 2022/02/13 11:58 오후
     */
    private boolean isCancelPossible() {
        LocalDateTime now = LocalDateTime.now();

        if (vacationStatus.isCancel()) {
            throw new BusinessException(MessageUtils.getMessages("message.vacation.canceled"));
        }

        if (now.isAfter(vacationEndDttm)) {
            throw new BusinessException(MessageUtils.getMessages("message.vacation.last.cancel"));
        }

        if (now.isAfter(vacationStartDttm) && now.isBefore(vacationEndDttm)) {
            throw new BusinessException(MessageUtils.getMessages("message.vacation.in.use.cancel"));
        }

        return true;
    }
}
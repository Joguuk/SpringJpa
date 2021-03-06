package com.jjozerg.jkhr.vacation.entity;

import com.jjozerg.jkhr.common.CroquiscomHrConstants;
import com.jjozerg.jkhr.common.MessageUtils;
import com.jjozerg.jkhr.config.exception.BusinessException;
import com.jjozerg.jkhr.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.jjozerg.jkhr.common.CroquiscomHrConstants.VacationCount.*;
import static com.jjozerg.jkhr.common.CroquiscomHrConstants.VacationKind;
import static com.jjozerg.jkhr.common.CroquiscomHrConstants.VacationStatus;
import static com.jjozerg.jkhr.common.CroquiscomHrConstants.VacationStatus.*;

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
        if (useVacationCount + vacationCount >= DEFAULT.getVacationCount()) {
            throw new Exception(MessageUtils.getMessages("message.vacation.request.fail.max.count"));    // 신청 가능한 휴가 가능일수를 초과했습니다.
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
            throw new BusinessException(MessageUtils.getMessages("message.vacation.cancel.fail.canceled")); // 이미 취소 처리된 휴가입니다.
        }

        if (now.isAfter(vacationEndDttm)) {
            throw new BusinessException(MessageUtils.getMessages("message.vacation.cancel.fail.last"));  // 사용일이 경과된 휴가는 취소할 수 없습니다.
        }

        if (now.isAfter(vacationStartDttm) && now.isBefore(vacationEndDttm)) {
            throw new BusinessException(MessageUtils.getMessages("message.vacation.cancel.fail.in.use"));    // 진행중인 휴가는 취소할 수 없습니다.
        }

        return true;
    }

    /**
     * 휴가 신청 후 남은 휴가 일수를 반환한다.
     *
     * @author joguk
     * @date 2022/02/16 12:00 오전
     */
    public double getRemainingVacationCount(Double usedVacationCount) {
        return DEFAULT.getVacationCount() - usedVacationCount - vacationCount;
    }
}